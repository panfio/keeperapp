#!/bin/bash
#This script builds artifacts and docker container

#build 
cd ./frontend 
npm install
npm run-script build
cd ..

#build server
rm -rf ./keeper/src/main/resources/static/*
cp -r ./frontend/build/* ./keeper/src/main/resources/static/
cd ./keeper
./mvnw clean install package

#build container
cd ..
docker build --tag panfio/keeper .

#docker-compose up 




