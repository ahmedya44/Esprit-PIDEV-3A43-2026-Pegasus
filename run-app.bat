@echo off
echo ========================================
echo Lancement de l'application...
echo ========================================
echo.
echo Verification de Java...
java -version
echo.
echo Verification de Maven...
.\apache-maven-3.9.6\bin\mvn.cmd --version
echo.
echo Lancement de l'application JavaFX...
echo ========================================
.\apache-maven-3.9.6\bin\mvn.cmd compile javafx:run
echo.
echo ========================================
echo Application terminee
pause
