#!/bin/bash
# encoding: utf-8

# author: Darren.qiu
# Email : Darren.qiu@gwtsz.net
# Create Date: 2017年6月26日
###
# chkconfig: 345 20 80
# description: spring Boot application service script
# processname: java
#
# Installation (CentOS):
# copy file to /etc/init.d
# chmod +x /etc/init.d/launcher.sh
# chkconfig --add /etc/init.d/launcher.sh
# chkconfig launcher.sh on
#
# Installation (Ubuntu):
# copy file to /etc/init.d
# chmod +x /etc/init.d/launcher.sh
# update-rc.d launcher.sh defaults
#
#
# Usage: (as root)
# service launcher.sh start
# service launcher.sh stop
# service launcher.sh status
#
###

# The directory in which your application is installed
APP_NAME="账户诊断报告API"
APP_VERSION="1.0.6"
APP_DIR="/dasapi/accountanalyze/V${APP_VERSION}/das-api-ms-accountanalyze"
APP_EN_NAME="das-api-ms-accountanalyze"
# The fat jar containing your application
APP_JAR="${APP_DIR}/${APP_EN_NAME}-${APP_VERSION}.jar"
JAVA_OPTIONS_INITIAL=-Xms128M
JAVA_OPTIONS_MAX=-Xmx512M
port=$2

# ***********************************************
OUT_FILE=/log/${APP_EN_NAME}-out-${port}.log
RUNNING_PID="${APP_DIR}"/RUNNING_PID
RUNNING_PID_PORT="${APP_DIR}"/RUNNING_PID_PORT_${port}
# ***********************************************

# colors
red='\e[0;31m'
green='\e[0;32m'
yellow='\e[0;33m'
reset='\e[0m'

echoRed() { echo -e "${red}$1${reset}"; }
echoGreen() { echo -e "${green}$1${reset}"; }
echoYellow() { echo -e "${yellow}$1${reset}"; }

#修改端口
getPort(){    
    if [ -n "${port}" ]
    then
        echo "${APP_EN_NAME} port is ${port}"
    else      
        echo "port is empty,set ${APP_EN_NAME} default port is 9998"
	    port=9998
	    OUT_FILE=/log/${APP_EN_NAME}-out-${port}.log
        RUNNING_PID="${APP_DIR}"/RUNNING_PID
        RUNNING_PID_PORT="${APP_DIR}"/RUNNING_PID_PORT_${port}
    fi
}

# Check whether the application is running.
# The check is pretty simple: open a running pid file and check that the process
# is alive.
isrunning() {
    # Check for running app
    if [ -f "${RUNNING_PID_PORT}" ]; then
        proc=$(cat ${RUNNING_PID_PORT});
        if /bin/ps --pid ${proc} 1>&2 >/dev/null;
        then
            return 0
        fi
    fi
    return 1
}

#启动方法
start(){
    if isrunning; then
        echoYellow "---------------The das-api-ms-accountanalyze is already running------------"
        return 0
    fi   
   
   pushd ${APP_DIR} > /dev/null
   #exec java -Xms128m -Xmx2048m -jar $APP_JAR 5 >$OUT_FILE & 
   nohup java -jar  ${APP_JAR} --spring.config.name=application --server.port=${port} > ${OUT_FILE} 2>&1 &
   echo $! > ${RUNNING_PID_PORT}
   popd > /dev/null
    
   if isrunning; then
        echoGreen "--------------The das-api-ms-accountanalyze started----------------------"
        exit 0
    else
        echoRed "The das-api-ms-accountanalyze has not started - check log"
        exit 3
    fi
}

#重启方法
restart() {
    echo "Restarting ${APP_NAME} with ${APP_JAR}"
    stop
    start
}

#停止方法
stop() {
    echoYellow "Stopping ${APP_NAME} with ${APP_JAR}"
       
    
    if isrunning; then
        kill `cat ${RUNNING_PID_PORT}`
        i=5;

        while [[ running && i -gt 0 ]]
        do
            echo -n "$i "
            sleep 1;
            ((i--))
        done;
        if isrunning; then
            kill -KILL `cat ${RUNNING_PID_PORT}`
        fi
        rm $RUNNING_PID_PORT
        echo -e "\n"
    fi
}

#查询运行状态方法
status() {
    if isrunning; then
        echoGreen "${APP_NAME} is running"
    else
        echoRed "${APP_NAME} is either stopped or inaccessible"
    fi
}

case "$1" in
    start)
        getPort
        start
        ;;
    stop)
        getPort
        if isrunning; then
            stop
            exit 0
        else
            echoRed "${APP_NAME} not running"
            exit 3
        fi
        ;;
    restart)
        getPort
        stop
        start
        ;;
    status)
        getPort
        status
        exit 0
        ;;
    *)
        printf 'Usage: %s {status|start|stop|restart}\n'
        exit 1
        ;;
esac


