@echo off
echo ========================================
echo Test de connexion à la base de données
echo ========================================
echo.

set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot
set PATH=C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin;%PATH%

echo Compilation du test...
"C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin\javac.exe" -cp "target\classes" TestDB.java

echo Lancement du test...
"C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin\java.exe" -cp "target\classes" TestDB

echo.
echo ========================================
pause
