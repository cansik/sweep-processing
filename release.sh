#!/bin/sh
if [ $# -eq 0 ]
  then
  	echo ""
    echo "Please provide following arguments:"
    echo "$0 [VERSION]"
    exit
fi

# parameter
VERSION=$1

echo $PWD

echo clean up...
rm -r -f build

echo compiling...

if [ "$(expr substr $(uname -s) 1 6)" == "CYGWIN" ];then
    echo running gradle commands on windows
    gradlew.bat build
    gradlew.bat copyToLib
    gradlew.bat jar
    gradlew.bat javadoc
else
    echo running gradle commands on unix
    gradle build
    gradle copyToLib
    gradle jar
    gradle javadoc
fi

echo "copy files..."
OUTPUT="release/SweepProcessing"
OUTPUTV="release/SweepProcessing_$VERSION"
rm -r -f "$OUTPUT"
rm -r -f "$OUTPUTV"

mkdir -p "$OUTPUT/library"

# copy files
cp -f library.properties "release/SweepProcessing.txt"
cp -a "build/libs/lib/." "$OUTPUT/library/"
cp "build/libs/SweepProcessing.jar" "$OUTPUT/library/"
# cp -r shader "$OUTPUT/library/"
cp -r "build/docs/javadoc" "$OUTPUT/reference"

cp -r "examples" "$OUTPUT/"
cp library.properties "$OUTPUT/"
cp README.md "$OUTPUT/"
cp -r "src" "$OUTPUT/"

# create release files
cd "release/"
rm -f "SweepProcessing.zip"
zip -r "SweepProcessing.zip" "SweepProcessing" -x "*.DS_Store"

# store it with version number
cd ..
mv -f "$OUTPUT" "$OUTPUTV"

echo "-------------------------"
echo "finished release $VERSION"