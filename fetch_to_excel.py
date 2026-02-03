#!/usr/bin/env python3
"""Simple GET -> JSON -> TXT (one line per record), auto paging."""

import json
import time
from urllib.parse import urlencode
from urllib.request import Request, urlopen

URL = "https://api.example.com/items"
START_PAGE = 1
LIMIT = 10
KEYWORD = "foo"  # set to None to omit
ENABLED = True  # True/False/None
OUTPUT = "data.txt"
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

with open(OUTPUT, "w", encoding="utf-8") as file:
    for row in all_rows:
        file.write(json.dumps(row, ensure_ascii=False) + "\n")

print(f"done, rows={len(all_rows)}, output={OUTPUT}, total_pages={total_pages}")
