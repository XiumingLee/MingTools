export BASE_DIR=$(dirname $0)
JAVA_OPT=""
SPRING_BOOT_OPT="--spring.config.location=${BASE_DIR}/conf/application.yaml"
APP="${BASE_DIR}/lib/ming-tools.jar"
nohub java -jar ${JAVA_OPT} ${APP} ${SPRING_BOOT_OPT} &
