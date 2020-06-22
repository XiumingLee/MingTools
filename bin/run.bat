@echo off
rem use gbk
rem %APP_NAME%_start.out

set BASE_DIR=%~dp0
cd %BASE_DIR%
set APP_NAME="ming-tools"
set APP_JAR="%BASE_DIR%\lib\ming-tools.jar"
set "JAVA_OPT=%JAVA_OPT% -server -Xms30m -Xmx30m -Xmn20m -XX:MetaspaceSize=50m -XX:MaxMetaspaceSize=50m"
set SPRING_BOOT_OPT="--spring.config.location=%BASE_DIR%\conf\application.yaml"
java -jar  %APP_JAR% %SPRING_BOOT_OPT%

pause
