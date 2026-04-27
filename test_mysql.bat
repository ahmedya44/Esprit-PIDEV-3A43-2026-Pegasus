@echo off
echo Test de connexion MySQL...
echo.

echo Test avec PowerShell Test-NetConnection:
powershell -Command "Test-NetConnection -ComputerName localhost -Port 3306"

echo.
echo Si TcpTestSucceeded = True, MySQL fonctionne
echo Sinon, MySQL n'est probablement pas démarré
pause
