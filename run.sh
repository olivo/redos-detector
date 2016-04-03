export CLASSPATH=soot/axml-2.0.jar:soot/slf4j-api-1.7.5.jar:soot/slf4j-simple-1.7.5.jar:soot/soot-infoflow-android.jar:soot/soot-infoflow.jar:soot/soot-trunk.jar:.

APK_FILE=$1

if [ -z "$APK_FILE" ]; then
    echo "Usage: ./run.sh <apk_file>"
    exit 1
fi

java ReDoSDetector ${APK_FILE}
