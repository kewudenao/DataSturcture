#!/usr/bin/env python3
"""Simple GET -> JSON -> Excel (openpyxl)."""

import json
from urllib.parse import urlencode
from urllib.request import urlopen

from openpyxl import Workbook

URL = "https://api.example.com/items"
PAGE = 1
LIMIT = 10
KEYWORD = "foo"  # set to None to omit
ENABLED = True  # True/False/None
OUTPUT = "data.xlsx"

params = {
    "page": PAGE,
    "limit": LIMIT,
    "keyword": KEYWORD,
    "enabled": str(ENABLED).lower() if ENABLED is not None else None,
}
params = {k: v for k, v in params.items() if v is not None}
full_url = URL + ("?" + urlencode(params) if params else "")

data = json.loads(urlopen(full_url).read().decode("utf-8"))
rows = data.get("data", [])

wb = Workbook()
ws = wb.active
ws.title = "data"

if rows:
    headers = list(rows[0].keys())
    ws.append(headers)
    for row in rows:
        ws.append([row.get(h) for h in headers])
else:
    ws.append(["no data"])

wb.save(OUTPUT)
print(f"done, rows={len(rows)}, output={OUTPUT}")
