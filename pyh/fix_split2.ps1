$path = 'C:\pyh\src\main\webapp\user\detail.jsp'
$bytes = [System.IO.File]::ReadAllBytes($path)
$text = [System.Text.Encoding]::UTF8.GetString($bytes)
$old = "`${sz.split('|')}"
$new = "`${sz.split('\\|')}"
$text = $text.Replace($old, $new)
[System.IO.File]::WriteAllText($path, [System.Text.Encoding]::UTF8.GetBytes($text), [System.Text.Encoding]::UTF8)
Write-Host "Done"
