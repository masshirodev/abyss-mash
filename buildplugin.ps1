$PluginPath = "D:\AbyssDev\MashToolbox\"
$PluginIniPath = "D:\AbyssDev\MashToolbox\src\main\resources\plugin.ini"
$GradlewPath = "D:\AbyssDev\MashToolbox\gradlew"
$GradlewConfig = "D:\AbyssDev\MashToolbox\settings.gradle"
$Plugin = $args[0]

Write-Host Building plugin $Plugin
Set-Content -Path $PluginIniPath -Value "kraken.plugin.mash.$Plugin"
Set-Content -Path $GradlewConfig -Value "rootProject.name = '$Plugin'"

Set-Location $PluginPath
. $GradlewPath jar