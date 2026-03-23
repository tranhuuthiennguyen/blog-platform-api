#!/bin/bash
set -e

read -p "Project name (e.g. my-app): " PROJECT_NAME
read -p "Base package (e.g. com.thiennth.myapp): " PACKAGE_NAME
read -p "Description: " DESCRIPTION

PACKAGE_PATH="${PACKAGE_NAME//.//}"
OLD_PACKAGE="com.thiennth.boilerplate"
OLD_PACKAGE_PATH="com/thiennth/boilerplate"
OLD_ARTIFACT="boilerplate"

echo "Setting up project: $PROJECT_NAME..."

# 1. pom.xml
sed -i "s|<artifactId>$OLD_ARTIFACT</artifactId>|<artifactId>$PROJECT_NAME</artifactId>|g" pom.xml
sed -i "s|<groupId>$OLD_PACKAGE</groupId>|<groupId>$PACKAGE_NAME</groupId>|g" pom.xml
sed -i "s|<description>.*</description>|<description>$DESCRIPTION</description>|g" pom.xml
sed -i "s|<name>$OLD_ARTIFACT</name>|<name>$PROJECT_NAME</name>|g" pom.xml

# 2. Replace package declarations in all Java files (main + test)
find src -name "*.java" -exec sed -i "s|$OLD_PACKAGE|$PACKAGE_NAME|g" {} +
find src -name "*.java" -exec sed -i "s|$OLD_PACKAGE_PATH|$PACKAGE_PATH|g" {} +

# 3. Rename main source directory in place
OLD_MAIN="src/main/java/$OLD_PACKAGE_PATH"
NEW_MAIN="src/main/java/$PACKAGE_PATH"
mkdir -p "$(dirname "$NEW_MAIN")"
mv "$OLD_MAIN" "$NEW_MAIN"
rmdir -p "$(dirname "$OLD_MAIN")" 2>/dev/null || true

# 4. Rename test source directory in place
OLD_TEST="src/test/java/$OLD_PACKAGE_PATH"
NEW_TEST="src/test/java/$PACKAGE_PATH"
mkdir -p "$(dirname "$NEW_TEST")"
mv "$OLD_TEST" "$NEW_TEST"
rmdir -p "$(dirname "$OLD_TEST")" 2>/dev/null || true

# 5. application.yml
sed -i "s|$OLD_ARTIFACT|$PROJECT_NAME|g" src/main/resources/application.properties

# 6. Rename root folder to match project name
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PARENT_DIR="$(dirname "$SCRIPT_DIR")"
CURRENT_DIR_NAME="$(basename "$SCRIPT_DIR")"

if [ "$CURRENT_DIR_NAME" != "$PROJECT_NAME" ]; then
  mv "$SCRIPT_DIR" "$PARENT_DIR/$PROJECT_NAME"
  echo "Renamed project folder: $CURRENT_DIR_NAME → $PROJECT_NAME"
fi

# rm -rf .git
# git init
# git add .
# git commit -m "init: $PROJECT_NAME from template"

echo "Done! Project '$PROJECT_NAME' is ready."
echo "  Folder:  $PARENT_DIR/$PROJECT_NAME"
echo "  Package: $PACKAGE_NAME"
echo "  Artifact: $PROJECT_NAME"