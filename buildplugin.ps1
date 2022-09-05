$PluginPath = "D:\AbyssDev\Mash\"
$PluginIniPath = "D:\AbyssDev\Mash\src\main\resources\plugin.ini"
$GradlewPath = "D:\AbyssDev\Mash\gradlew"
$GradlewConfig = "D:\AbyssDev\Mash\settings.gradle"
$Plugin = $args[0]

Write-Host Building plugin $Plugin
Set-Content -Path $PluginIniPath -Value "kraken.plugin.mash.$Plugin"
Set-Content -Path $GradlewConfig -Value "rootProject.name = '$Plugin'"

Set-Location $PluginPath
. $GradlewPath jar