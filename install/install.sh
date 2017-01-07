#!/bin/bash

#params
read -p "Enter printer HostName or Ip:" -e host
read -p "Enter folder for scaned images:" -e scanpath

#coping files and scripts
cp ../target/HPScan2Linux-1.0.1-jar-with-dependencies.jar /usr/bin/hpscan2linux.jar
cp profiles /etc/scanserver/
cp hpscan2linux /etc/init.d/

#add user for daemon
useradd -U scanuser

#reload services configs
systemctl daemon-reload

#config folder
mkdir /etc/scanserver

echo "<settings>" > /etc/scanserver/conf.xml
echo "  <scanpath>$scanpath</scanpath>" >> /etc/scanserver/conf.xml
echo "  <profiles>/etc/scanserver/profiles/</profiles>" >> /etc/scanserver/conf.xml
echo "  <printer_addr>$host</printer_addr>" >> /etc/scanserver/conf.xml
echo "  <printer_port>8080</printer_port>" >> /etc/scanserver/conf.xml
echo "</settings>" >> /etc/scanserver/conf.xml