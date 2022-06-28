#!/bin/bash

kill -9 "$(<occupantsearch.pid)"
rm occupantsearch.pid

rm -rf ./OccupantSearch-1.0
unzip OccupantSearch-1.0.zip

nohup ./OccupantSearch-1.0/bin/OccupantSearch & echo "$!" > occupantsearch.pid
