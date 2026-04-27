@echo off
echo ========================================
echo Test direct de votre application
echo ========================================
echo.

echo Verification de Java...
"C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin\java.exe" -version
echo.

echo Compilation en cours...
"C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin\javac.exe" -cp "target\classes" -d target\classes src\main\java\com\pegasus\*.java src\main\java\com\pegasus\**\*.java 2>nul

echo Lancement de l'application...
"C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin\java.exe" -cp "target\classes" com.pegasus.MainApp

pause
