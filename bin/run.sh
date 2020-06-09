APP_NAME="ming-tools"
BASE_DIR=$(dirname $0)
APP_JAR="${BASE_DIR}/lib/ming-tools.jar"

JAVA_OPT=""
SPRING_BOOT_OPT="--spring.config.location=${BASE_DIR}/conf/application.yaml"

nohup java -jar ${JAVA_OPT} ${APP_JAR} ${SPRING_BOOT_OPT} > ${APP_NAME}.log &
exit 1
