$PluginPath = "D:\AbyssDev\Mash\"
$PluginIniPath = "D:\AbyssDev\Mash\src\main\resources\plugin.ini"
$GradlewPath = "D:\AbyssDev\Mash\gradlew"
$GradlewConfig = "D:\AbyssDev\Mash\settings.gradle"
$PluginMainPath = "D:\AbyssDev\Mash\src\main\java\kraken\plugin\mash"
$Plugin = $args[0]
$DateNumber = Get-Date -Format "ddMMyyyyHHmm"
$DateFormatted = Get-Date -Format "dd-MM-yyyy HH:mm"

Write-Host [$DateFormatted] Building plugin $Plugin
Set-Content -Path $PluginIniPath -Value "kraken.plugin.mash.$Plugin"
Set-Content -Path $GradlewConfig -Value "rootProject.name = '$Plugin'"

(Get-Content "$PluginMainPath\$Plugin.java").replace("{{replace_version_here}}", $DateNumber) | Set-Content "$PluginMainPath\$Plugin.java"
Start-Sleep 1

Set-Location $PluginPath
. $GradlewPath jar

Start-Sleep 5
(Get-Content "$PluginMainPath\$Plugin.java").replace($DateNumber, "{{replace_version_here}}") | Set-Content "$PluginMainPath\$Plugin.java"