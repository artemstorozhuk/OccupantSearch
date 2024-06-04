#!/bin/bash

kill -9 "$(<natasha_server.pid)"
rm natasha_server.pid
nohup python3 ./natasha_server.py > natasha_server_info.log 2> natasha_server_error.log < /dev/null & echo "$!" > natasha_server.pid &

kill -9 "$(<occupantsearch.pid)"
rm occupantsearch.pid
rm -rf ./server-1.0.0
unzip server-1.0.0.zip
nohup ./server-1.0.0/bin/server > info.log 2> error.log < /dev/null & echo "$!" > occupantsearch.pid &
