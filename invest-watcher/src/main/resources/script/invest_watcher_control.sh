#!/bin/bash

root_dir=$(cd $(dirname $0); pwd)

echo $root_dir
program_name=invest-watcher
port=8080

function check_service()
{
    for ((i = 0; i < 3; ++i))
    do
        if [ "$(curl localhost:$port/health)" == "OK" ]; then
            return 0
        elif [ $i -ne 2 ]; then
            sleep 1
        fi
    done
    return 1
}

# lock
pid=$(cat $root_dir/.pid)
if [ "$pid" != "" ] && [ $(ps -ef | grep $pid | grep $(basename $0) | wc -l) -eq 1 ]; then
    echo $(basename $0) is currently running!
    exit 1
fi
echo $$ > $root_dir/.pid

check_service
if [ $? -ne 0 ]; then
    pid=$(ps -ef | grep $program_name | grep "java" | awk '{print $4}')
    if [ "$pid" != "" ]; then
        kill $pid
        sleep 5
    fi
    pid=$(ps -ef | grep $program_name | grep "java" | awk '{print $4}')
    if [ "$pid" != "" ]; then
        kill -9 $pid
        sleep 5
    fi
    cd $root_dir
    java -jar ${program_name}-1.0-SNAPSHOT.jar > /dev/null 2&>1 &
fi

# clear logs
rm -rf $root_dir/invest-watcher.log.$(date -d "3 days ago" +%Y-%m-%d)*
