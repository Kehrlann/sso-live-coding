#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname $0)"
echo -e '---
geometry: margin=1cm
output: pdf_document
---

```java
' > code.md

# Print @Controller and all the following lines
awk 'f{print;} /@Controller/{print; f=1}' ./src/main/java/wf/garnier/sso/demo/HelloController.java >> code.md
echo '```' >> code.md

pandoc code.md -f markdown -s -o code.pdf
open code.pdf
rm code.md
