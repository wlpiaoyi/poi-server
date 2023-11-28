#!/bin/sh
# ./ry.sh start 启动 stop 停止 restart 重启 status 状态 logs 日志
APP_NAME=poie
APP_HOME=$(cd `dirname $0`;pwd)
# https://zhuanlan.zhihu.com/p/181609730
echo -e "当前路径:\033[0;32m${APP_HOME}\033[0m"

LOG_PATH=${APP_HOME}/logs
APP_PATH=${APP_HOME}/target

if [ ! -d "${LOG_PATH}" ];then
    mkdir ${LOG_PATH}
fi


active='dev'

# JVM参数
GC_DEBUG=" -XX:+PrintTenuringDistribution -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -Xloggc:jvm.log -verbose:gc "
GC_PROD="-XX:+UseBiasedLocking -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:NewRatio=2 -XX:+CMSIncrementalMode -XX:-ReduceInitialCardMarks -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseCMSInitiatingOccupancyOnly"
EX="-XX:+OptimizeStringConcat -XX:+DoEscapeAnalysis -XX:+UseNUMA"
HEAP=" -Xms256M -Xmx512M -XX:CompressedClassSpaceSize=128m -XX:MetaspaceSize=300m -XX:MaxMetaspaceSize=300m -XX:MaxDirectMemorySize=128m"


if [ "$1" = "" ];
then
  echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
  exit 1
fi

if [ "${APP_NAME}" = "" ];
then
  echo -e "\033[0;31m 未输入应用名 \033[0m"
  exit 1
fi

start(){
  PID=`ps -ef |grep java|grep ${APP_NAME}|grep -v grep|awk '{print $2}'`
  if [ x"$PID" != x"" ]; then
    echo -e "\033[0;32m${APP_NAME}\033[0m is running..."
  else

    JAVA_OPTIONS=""
    if [ x"${active}" = x"dev" ]; then
      JAVA_OPTIONS="${GC_DEBUG} ${EX} ${HEAP}"
    else
      JAVA_OPTIONS="${GC} ${EX} ${HEAP}"
    fi

    echo -e "jvm params: \033[0;34m${JAVA_OPTIONS}\033[0m"
    sleep 1

    nohup java -Dfile.encoding=utf-8 ${JAVA_OPTIONS} -jar ${APP_PATH}/${APP_NAME}.jar \
    --spring.profiles.active=${active} > ${LOG_PATH}/${APP_NAME}.log 2>&1 &

    PID=`ps -ef |grep java|grep ${APP_NAME}|grep -v grep|wc -l`
    echo -e "Start \033[0;32m${APP_NAME}\033[0m, (pid: \033[0;36m$PID\033[0m) success..."
  fi

  if read -t 5 -p "Are you need view logs, please input (Y/N):" is_view
  then
    if [ x"$is_view" = x"Y" -o x"$is_view" = x"y" ]; then
      logs
    fi
  else
    echo
    echo "Sorry, bye..."
  fi

}

stop(){
  echo -e "Stop \033[0;32m${APP_NAME}\033[0m"

	PID=""
	query(){
		PID=`ps -ef |grep java|grep ${APP_NAME}|grep -v grep|awk '{print $2}'`
	}

	query
	if [ x"$PID" != x"" ]; then
		kill -TERM $PID
		echo -e "\033[0;32m${APP_NAME}\033[0m (pid:\033[0;36m$PID\033[0m) exiting..."
		while [ x"$PID" != x"" ]
		do
			sleep 1
			query
		done
		echo -e "\033[0;32m${APP_NAME}\033[0m exited."
	else
		echo -e "\033[0;32m${APP_NAME}\033[0m already stopped."
	fi
}

restart(){
    stop
    sleep 2
    start
}

status(){
    PID=`ps -ef |grep java|grep ${APP_NAME}|grep -v grep|wc -l`
    if [ $PID != 0 ];then
        echo -e "\033[0;32m${APP_NAME} \033[0m is running..."
    else
        echo -e "\033[0;32m${APP_NAME} \033[0m is not running..."
    fi
}

logs(){
    # 实时查看启动日志（此处正在想办法启动成功后退出）
    tail -f ${LOG_PATH}/${APP_NAME}.log
}

case $1 in
    start)
    start;;
    stop)
    stop;;
    restart)
    restart;;
    status)
    status;;
    logs)
    logs;;
    *)

esac
