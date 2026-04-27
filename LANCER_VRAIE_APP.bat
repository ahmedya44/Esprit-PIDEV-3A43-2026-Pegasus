@echo off
echo ========================================
echo Lancement de votre vraie application
echo ========================================
echo.

echo Verification de Java...
"C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin\java.exe" -version
echo.

echo Lancement de l'application JavaFX...
"C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\bin\java.exe" --module-path "C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\javafx\lib" --add-modules javafx.controls,javafx.fxml -cp "target\classes" com.pegasus.MainApp

echo.
echo ========================================
pause
