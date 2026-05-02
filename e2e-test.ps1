param(
    [int]$Port = 4567,
    [int]$TimeoutSeconds = 30
)

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $projectRoot

Write-Host 'Building project...'
$mvn = 'mvn'
$build = & $mvn clean package
if ($LASTEXITCODE -ne 0) {
    Write-Error 'Build failed. Execute `mvn clean package` manually to inspect the error.'
    exit $LASTEXITCODE
}

$logPath = Join-Path $projectRoot 'e2e-app.log'
$errPath = Join-Path $projectRoot 'e2e-app.err'

Write-Host 'Starting application for E2E test...'
$process = Start-Process -FilePath $mvn -ArgumentList 'exec:java', '-Dexec.mainClass=com.example.passwordgen.App' -NoNewWindow -RedirectStandardOutput $logPath -RedirectStandardError $errPath -PassThru
try {
    $deadline = (Get-Date).AddSeconds($TimeoutSeconds)
    $response = $null

    while ((Get-Date) -lt $deadline) {
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:$Port" -TimeoutSec 5
            if ($response.StatusCode -eq 200) { break }
        } catch {
            Start-Sleep -Seconds 1
        }
    }

    if (-not $response -or $response.StatusCode -ne 200) {
        throw "Servidor não respondeu em http://localhost:$Port dentro de $TimeoutSeconds segundos."
    }

    Write-Host 'Verificando página inicial...'
    if ($response.Content -notmatch 'Gerador de Senhas Seguras') {
        throw 'Texto esperado não encontrado na página inicial.'
    }
    Write-Host 'Página inicial ok.'

    Write-Host 'Verificando endpoint /generate...'
    $generateUrl = "http://localhost:$Port/generate?length=12&upper=on&lower=on&digits=on&symbols=on"
    $generateResponse = Invoke-WebRequest -Uri $generateUrl -TimeoutSec 10
    if ($generateResponse.StatusCode -ne 200) {
        throw "Endpoint /generate retornou status $($generateResponse.StatusCode)."
    }
    if ($generateResponse.Content -notmatch 'Senha gerada com sucesso') {
        throw 'Texto esperado não encontrado na resposta do endpoint /generate.'
    }

    Write-Host 'E2E test passed.'
    exit 0
} catch {
    Write-Error $_
    Write-Host "Verifique os logs em $logPath e $errPath para mais detalhes."
    exit 1
} finally {
    if ($process -and -not $process.HasExited) {
        $process.Kill()
        Start-Sleep -Milliseconds 500
    }
}
