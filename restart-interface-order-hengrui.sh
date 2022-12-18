#!/bin/bash
PROCESS=`ps -ef|grep interface-order-hengrui-1.0.1-Release.jar|grep -v grep|grep -v PPID|awk '{ print $2}'`
for i in $PROCESS
do
  echo "Kill the $1 process [ $i ]"
  kill -9 $i
done

nohup java -jar -Djava.io.tmpdir="/opt/crm_interface_hengrui/tmp" -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC /opt/crm_interface_hengrui/interface-order-hengrui-1.0.1-Release.jar --spring.profiles.active=pro >/dev/null  2>&1 &
