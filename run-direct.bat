@echo off
echo ========================================
echo Lancement direct avec Java...
echo ========================================
echo.

set CLASSPATH=target\classes;lib\*
set MAIN_CLASS=com.pegasus.MainApp

echo Verification du classpath...
if not exist target\classes (
    echo Erreur: Le dossier target\classes n'existe pas!
    echo Compilation necessaire...
    pause
    exit /b 1
)

echo Lancement de l'application...
java -cp target\classes %MAIN_CLASS%

pause
