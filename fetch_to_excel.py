#!/usr/bin/env python3
"""
Fetch paginated data via HTTP GET and export to Excel.

Example:
  python fetch_to_excel.py \
    --url "https://api.example.com/items" \
    --page 1 --limit 10 --keyword "foo" --enabled true \
    --all-pages --output "data.xlsx"
"""

from __future__ import annotations

import argparse
import json
import sys
import time
from typing import Any, Dict, Iterable, List, Optional, Tuple
from urllib.error import HTTPError, URLError
from urllib.parse import parse_qsl, urlencode, urlsplit, urlunsplit
from urllib.request import Request, urlopen


FIELD_ORDER = [
    "id",
    "position",
    "document_id",
    "content",
    "sign_content",
    "answer",
    "word_count",
    "tokens",
    "keywords",
    "index_node_id",
    "index_node_hash",
    "hit_count",
    "enabled",
    "disabled_at",
    "disabled_by",
    "status",
    "created_by",
    "created_at",
    "updated_at",
    "updated_by",
    "indexing_at",
    "completed_at",
    "error",
    "stopped_at",
]


def parse_bool(value: Optional[str]) -> Optional[bool]:
    if value is None:
        return None
    value = value.strip().lower()
    if value in {"true", "1", "yes", "y"}:
        return True
    if value in {"false", "0", "no", "n"}:
        return False
    raise argparse.ArgumentTypeError(
        "enabled must be one of: true/false/1/0/yes/no"
    )


def build_url(base_url: str, params: Dict[str, Any]) -> str:
    url_parts = urlsplit(base_url)
    query_items = dict(parse_qsl(url_parts.query, keep_blank_values=True))
    for key, value in params.items():
        if value is None:
            continue
        query_items[key] = str(value)
    new_query = urlencode(query_items, doseq=True)
    return urlunsplit(
        (url_parts.scheme, url_parts.netloc, url_parts.path, new_query, url_parts.fragment)
    )


def http_get_json(url: str, timeout: int) -> Dict[str, Any]:
    request = Request(url, headers={"Accept": "application/json"})
    try:
        with urlopen(request, timeout=timeout) as response:
            payload = response.read().decode("utf-8")
    except HTTPError as exc:
        raise RuntimeError(f"HTTP {exc.code}: {exc.reason}") from exc
    except URLError as exc:
        raise RuntimeError(f"Network error: {exc.reason}") from exc

    try:
        return json.loads(payload)
    except json.JSONDecodeError as exc:
        raise RuntimeError("Response is not valid JSON") from exc


def normalize_value(value: Any) -> Any:
    if isinstance(value, (dict, list)):
        return json.dumps(value, ensure_ascii=False)
    return value


def build_columns(records: Iterable[Dict[str, Any]]) -> List[str]:
    extra_keys = set()
    for record in records:
        extra_keys.update(record.keys())
    ordered = [key for key in FIELD_ORDER if key in extra_keys]
    remaining = sorted(extra_keys - set(ordered))
    return ordered + remaining


def write_excel(records: List[Dict[str, Any]], output_path: str) -> None:
    try:
        from openpyxl import Workbook
        from openpyxl.utils import get_column_letter
    except ImportError as exc:
        raise RuntimeError(
            "Missing dependency: openpyxl. Install with: pip install openpyxl"
        ) from exc

    workbook = Workbook()
    sheet = workbook.active
    sheet.title = "data"

    if not records:
        sheet.append(["no data"])
        workbook.save(output_path)
        return

    columns = build_columns(records)
    sheet.append(columns)

    for record in records:
        row = [normalize_value(record.get(column)) for column in columns]
        sheet.append(row)

    # Light column sizing for readability (cap width at 50).
    for idx, column_name in enumerate(columns, start=1):
        max_len = len(str(column_name))
        for row_idx in range(2, min(sheet.max_row, 2000) + 1):
            value = sheet.cell(row=row_idx, column=idx).value
            if value is None:
                continue
            max_len = max(max_len, len(str(value)))
        sheet.column_dimensions[get_column_letter(idx)].width = min(50, max(10, max_len + 2))

    workbook.save(output_path)


def fetch_pages(
    base_url: str,
    page: int,
    limit: int,
    keyword: Optional[str],
    enabled: Optional[bool],
    all_pages: bool,
    timeout: int,
    sleep_seconds: float,
) -> Tuple[List[Dict[str, Any]], Dict[str, Any]]:
    records: List[Dict[str, Any]] = []
    metadata: Dict[str, Any] = {}

    current_page = page
    while True:
        params = {
            "page": current_page,
            "limit": limit,
            "keyword": keyword,
            "enabled": str(enabled).lower() if enabled is not None else None,
        }
        url = build_url(base_url, params)
        response = http_get_json(url, timeout=timeout)

        page_records = response.get("data", [])
        if not isinstance(page_records, list):
            raise RuntimeError("Response field 'data' is not a list")

        records.extend(page_records)
        metadata = response

        if not all_pages:
            break

        total_pages = response.get("total_pages")
        if not isinstance(total_pages, int):
            break

        if current_page >= total_pages:
            break

        current_page += 1
        if sleep_seconds > 0:
            time.sleep(sleep_seconds)

    return records, metadata


def build_arg_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="Fetch data and export to Excel.")
    parser.add_argument("--url", required=True, help="Base API URL")
    parser.add_argument("--page", type=int, default=1, help="Page number (default: 1)")
    parser.add_argument("--limit", type=int, default=10, help="Page size (default: 10)")
    parser.add_argument("--keyword", default=None, help="Keyword filter")
    parser.add_argument(
        "--enabled",
        type=parse_bool,
        default=None,
        help="Enabled filter: true/false/1/0/yes/no",
    )
    parser.add_argument(
        "--all-pages",
        action="store_true",
        help="Fetch all pages until total_pages is reached",
    )
    parser.add_argument(
        "--timeout",
        type=int,
        default=30,
        help="HTTP timeout in seconds (default: 30)",
    )
    parser.add_argument(
        "--sleep",
        type=float,
        default=0.0,
        help="Sleep seconds between pages (default: 0)",
    )
    parser.add_argument(
        "--output",
        default="data.xlsx",
        help="Output Excel file path (default: data.xlsx)",
    )
    return parser


def main() -> int:
    parser = build_arg_parser()
    args = parser.parse_args()

    if args.page < 1:
        print("page must be >= 1", file=sys.stderr)
        return 2
    if args.limit < 1:
        print("limit must be >= 1", file=sys.stderr)
        return 2

    try:
        records, metadata = fetch_pages(
            base_url=args.url,
            page=args.page,
            limit=args.limit,
            keyword=args.keyword,
            enabled=args.enabled,
            all_pages=args.all_pages,
            timeout=args.timeout,
            sleep_seconds=args.sleep,
        )
    except RuntimeError as exc:
        print(f"Error: {exc}", file=sys.stderr)
        return 1

    try:
        write_excel(records, args.output)
    except RuntimeError as exc:
        print(f"Error: {exc}", file=sys.stderr)
        return 1

    total = metadata.get("total")
    total_pages = metadata.get("total_pages")
    print(
        f"Done. rows={len(records)} total={total} total_pages={total_pages} output={args.output}"
    )
    return 0


if __name__ == "__main__":
    sys.exit(main())
