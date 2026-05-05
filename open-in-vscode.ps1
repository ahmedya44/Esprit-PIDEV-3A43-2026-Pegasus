$projectPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $projectPath

$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

code .
