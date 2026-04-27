@echo off
echo ========================================
echo Lancement final de votre application
echo ========================================
echo.

set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot
set PATH=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin;%PATH%

echo Verification de Java...
java -version
echo.

echo Lancement de l'application avec Maven...
.\apache-maven-3.9.6\bin\mvn.cmd compile javafx:run

echo.
echo ========================================
pause
