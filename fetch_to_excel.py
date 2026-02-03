#!/usr/bin/env python3
"""Simple GET -> JSON -> Excel (openpyxl), auto paging."""

import json
import time
from urllib.parse import urlencode
from urllib.request import Request, urlopen

from openpyxl import Workbook

URL = "https://api.example.com/items"
START_PAGE = 1
LIMIT = 10
KEYWORD = "foo"  # set to None to omit
ENABLED = True  # True/False/None
OUTPUT = "data.xlsx"
SLEEP = 0.0  # seconds between pages

# headers for request (add token/cookie here)
HEADERS = {
    "Accept": "application/json",
    # "Authorization": "Bearer xxx",
}

all_rows = []
page = START_PAGE
total_pages = None

while True:
    params = {
        "page": page,
        "limit": LIMIT,
        "keyword": KEYWORD,
        "enabled": str(ENABLED).lower() if ENABLED is not None else None,
    }
    params = {k: v for k, v in params.items() if v is not None}
    full_url = URL + ("?" + urlencode(params) if params else "")

    req = Request(full_url, headers=HEADERS)
    data = json.loads(urlopen(req).read().decode("utf-8"))
    rows = data.get("data", [])
    if isinstance(rows, list):
        all_rows.extend(rows)
    else:
        raise RuntimeError("response data is not a list")

    total_pages = data.get("total_pages", total_pages)
    if total_pages is None or page >= total_pages:
        break

    page += 1
    if SLEEP > 0:
        time.sleep(SLEEP)

wb = Workbook()
ws = wb.active
ws.title = "data"

if all_rows:
    headers = list(all_rows[0].keys())
    ws.append(headers)
    for row in all_rows:
        ws.append([row.get(h) for h in headers])
else:
    ws.append(["no data"])

wb.save(OUTPUT)
print(f"done, rows={len(all_rows)}, output={OUTPUT}, total_pages={total_pages}")
