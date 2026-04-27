@echo off
echo Ajout de la colonne likes...
echo.

echo Recherche de MySQL dans XAMPP...
if exist "C:\xampp\mysql\bin\mysql.exe" (
    echo MySQL trouve dans XAMPP
    "C:\xampp\mysql\bin\mysql.exe" -u root artwork < add_likes.sql
) else if exist "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" (
    echo MySQL trouve dans Program Files
    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root artwork < add_likes.sql
) else (
    echo MySQL non trouve. Essayez les alternatives:
    echo 1. XAMPP: C:\xampp\mysql\bin\mysql.exe
    echo 2. MySQL Workbench (ouvert manuellement)
    echo 3. phpMyAdmin (via navigateur)
    echo.
    echo Execution avec mysql (si dans PATH)...
    mysql -u root artwork < add_likes.sql
)

echo.
echo Si la commande a fonctionne, vous devriez voir la structure mise a jour
pause
