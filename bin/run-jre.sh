cd "$(dirname "$0")"  # 进入该文件所在的文件夹
BASE_DIR=.
APP_NAME="ming-tools"
APP_JAR="${BASE_DIR}/lib/ming-tools.jar"

JAVA="${BASE_DIR}/jre/bin/java"
JAVA_OPT=""
SPRING_BOOT_OPT="--spring.config.location=${BASE_DIR}/conf/application.yaml"

${JAVA} -jar ${JAVA_OPT} ${APP_JAR} ${SPRING_BOOT_OPT} > ${APP_NAME}_start.out &

## 判断操作系统
pl=echo
if [[ "$(uname)"=="Darwin" ]]
then
    # Mac OS X 操作系统
    pl=/bin/echo
fi
num=1
while [ -f ${APP_NAME}_start.out ]
do
    result=`grep "Started application"  ${APP_NAME}_start.out`
    result_err=`grep "Caused by"  ${APP_NAME}_start.out`
    if [[ "$result" != "" ]]
    then
        ${pl} ""
        ${pl} ${result}
        ${pl} "应用启动完成！"
        break
    else
        if [ $num -eq 1 ]
        then
            ${pl} -n "正在启动.."
            ((num++))
        else
            ${pl} -n ".."
        fi
        sleep 1s
    fi
    if [[ "$result_err" != "" ]]
    then
        ${pl} ""
        ${pl} ${result_err}
        ${pl} "应用启动失败！"
        break
    fi
done


exit 1
