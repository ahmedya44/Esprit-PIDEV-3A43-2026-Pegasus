@echo off
echo ========================================
echo Configuration de la base de données
echo ========================================
echo.

echo Vérification de la connexion MySQL...
mysql --version

echo.
echo Création de la base de données et des tables...
mysql -u root -p < create_sample_data.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Base de données configurée avec succès !
    echo ========================================
) else (
    echo.
    echo ========================================
    echo Erreur lors de la configuration
    echo ========================================
    echo Vérifiez que MySQL est installé et que le mot de passe root est correct.
)

echo.
pause
