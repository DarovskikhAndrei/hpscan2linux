# hpscan2linux
Service for HP scan to linux (scan from device to linux server)

# Supported devices
- HP Deskjet Ink Advantage 3525

# Run
You should install Oracle JRE/JDK for hpscan2linux.service starting

Example:

1. Download jdk-8u131-linux-x64.tar.gz from Oracle web site to /opt

2. Extract it:

        tar xfz jdk-8u131-linux-x64.tar.gz
        
3. Create symbolic link:

        ln -s /opt/jdk1.8.0_131 /opt/java
        
4. Start it:

        systemctl start hpscan2linux
        
4.1. By default, installation script enables hpscan2linux.service to system boot (systemctl enable hpscan2linux.service), so, after reboot, hpscan2linux will start automatically

5. You can see log messages in your syslog:

        tailf /var/log/messages | grep hpscan2linux
