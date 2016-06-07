export CLASSPATH=soot/axml-2.0.jar:soot/slf4j-api-1.7.5.jar:soot/slf4j-simple-1.7.5.jar:soot/soot-infoflow-android.jar:soot/soot-infoflow.jar:soot/soot-trunk.jar:.

javac *.java
cd regex_checker/rxxr/code
./build.sh
