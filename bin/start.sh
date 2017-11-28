echo ========start: `date +%Y%m%d%H%M`  =========
SCRIPT_NAME=$(readlink -f "$0")
dir=`dirname ${SCRIPT_NAME}`
cd $dir
LIB_DIR=$dir/lib
CLASSPATH="grammar_test-1.0-SNAPSHOT.jar"
files=`ls -1 ${LIB_DIR}`
for file in $files ;do
  CLASSPATH=$CLASSPATH:$LIB_DIR/$file
done
PARSER_CLASS=grammer.json.JsonTest
echo $CLASSPATH
java -cp $CLASSPATH $PARSER_CLASS
