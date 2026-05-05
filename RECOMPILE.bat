@echo off
echo ========================================
echo Recompilation de votre application
echo ========================================
echo.

set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot
set PATH=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin;%PATH%

echo Compilation en cours...
.\apache-maven-3.9.6\bin\mvn.cmd compile

echo.
echo ========================================
echo Compilation terminee !
echo Lancez FINAL_LAUNCH.bat pour demarrer l'application
echo ========================================
pause
