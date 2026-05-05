@echo off
echo ========================================
echo Test Application Simple
echo ========================================
echo.

echo Compilation du test...
javac --module-path "C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\javafx\lib" --add-modules javafx.controls,javafx.fxml TestSimpleApp.java

echo.
echo Lancement du test...
java --module-path "C:\Program Files\Microsoft\jdk-17.0.18.8-hotspot\javafx\lib" --add-modules javafx.controls,javafx.fxml TestSimpleApp

echo.
echo Test termine !
pause
