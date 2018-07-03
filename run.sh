#!/bin/bash

export DB_SCHEMA=kitchen_talk
export DB_USER=postgres
export DB_PASSWORD=test1234
export DB_DATABASE=postgres
export DB_POOL_SIZE=10
export DB_QUERY_TIMEOUT=20000
export DB_HOST=localhost
export APP_PORT=9002

mvn clean package

#java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -jar target/kt-employee-fat.jar
java -Xms256m -Xmx512m -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -Dlogback.configurationFile=src/main/resources/logback.xml -jar target/kt-employee-fat.jar
