@echo off
chcp 65001 >nul
echo Lancement avec encodage UTF-8...
echo.

set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot
.\apache-maven-3.9.6\bin\mvn.cmd compile javafx:run
