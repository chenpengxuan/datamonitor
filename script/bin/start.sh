#!/bin/bash

source "/etc/profile"
GCLOGPATH="logs/gc.log"
MAIN_CLASS="com.ymatou.datamonitor.Application"
APP_NAME="datamonitor.ymatou.cn"
CLASS_PATH="lib/*:conf"
JAVA_OPTS="-Xms1096M -Xmx1096M -Xmn500M -XX:PermSize=256M -XX:MaxPermSize=512M \
    -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75 \
    -XX:+PrintGCDetails -XX:+PrintGCDateStamps -verbose:gc -XX:+DisableExplicitGC"

#############intial work##########################
cd /usr/local/${APP_NAME}/default
if [ -e "logs" ]; then
    rm logs
fi
ln -s /usr/local/log/${APP_NAME}/ logs

##############launch the service##################
nohup java ${JAVA_OPTS} -cp ${CLASS_PATH} ${MAIN_CLASS} >> ${GCLOGPATH} 2>&1 &

##############check the service####################
ps aux | grep ${MAIN_CLASS} | grep -v grep > /dev/null 2>&1
if [ $? -eq 0 ]; then
    exit 0
else
    exit 1
fi
