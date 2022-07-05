#!/bin/bash

kill -9 "$(<occupantsearch.pid)"
rm occupantsearch.pid

rm -rf ./server-1.0.0
unzip server-1.0.0.zip

nohup ./server-1.0.0/bin/server & echo "$!" > occupantsearch.pid
