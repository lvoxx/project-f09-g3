@ECHO OFF

IF "%1"=="start" (
    cd /d "%~dp0setup"
    
    ECHO Starting OnMart BE...
    start "OnMart BE" java -jar -Dspring.profiles.active=dev "%~dp0setup\onmart_service-1.0.0.jar"
) ELSE IF "%1"=="stop" (
    ECHO Stopping OnMart BE...
    TASKKILL /FI "WINDOWTITLE eq OnMart BE"
) ELSE (
    ECHO Please, use "run.bat start" or "run.bat stop"
)

pause