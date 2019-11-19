#!/bin/sh
# # run mysql
service mysql start
mysql < /run/javaProject/setup.sql

# run tomcat
/usr/local/tomcat/bin/catalina.sh run
