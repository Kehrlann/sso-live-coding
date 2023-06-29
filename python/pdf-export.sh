#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname $0)"
echo -e '---
geometry: margin=1cm
output: pdf_document
---

```python
' > code.md

# Print @Controller and all the following lines
cat app.py >> code.md
echo '```' >> code.md

pandoc code.md -f markdown -s -o code.pdf
open code.pdf
rm code.md
