@echo off
echo ========================================
echo Test de la base de données avec Maven
echo ========================================
echo.

set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot
set PATH=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin;%PATH%

echo Lancement du test avec Maven...
.\apache-maven-3.9.6\bin\mvn.cmd exec:java -Dexec.mainClass="TestDBSimple" -Dexec.classpathScope=compile -q

echo.
echo ========================================
pause
