# Find JDK installation paths
Write-Host "Searching for JDK installations..." -ForegroundColor Green
Write-Host ""

$jdkPaths = @()

# Check common installation locations
$commonPaths = @(
    "C:\Program Files\Java",
    "C:\Program Files (x86)\Java"
)

foreach ($path in $commonPaths) {
    if (Test-Path $path) {
        $jdkDirs = Get-ChildItem $path -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -like "jdk*" }
        foreach ($dir in $jdkDirs) {
            $jdkPaths += $dir.FullName
        }
    }
}

# Check JAVA_HOME environment variable
if ($env:JAVA_HOME) {
    if (Test-Path $env:JAVA_HOME) {
        $jdkPaths += $env:JAVA_HOME
    }
}

# Check registry
try {
    $regPaths = Get-ItemProperty "HKLM:\SOFTWARE\JavaSoft\Java Development Kit\*" -ErrorAction SilentlyContinue
    foreach ($regPath in $regPaths) {
        if ($regPath.JavaHome -and (Test-Path $regPath.JavaHome)) {
            $jdkPaths += $regPath.JavaHome
        }
    }
} catch {}

try {
    $regPaths = Get-ItemProperty "HKLM:\SOFTWARE\WOW6432Node\JavaSoft\Java Development Kit\*" -ErrorAction SilentlyContinue
    foreach ($regPath in $regPaths) {
        if ($regPath.JavaHome -and (Test-Path $regPath.JavaHome)) {
            $jdkPaths += $regPath.JavaHome
        }
    }
} catch {}

# Remove duplicates and display results
$uniquePaths = $jdkPaths | Select-Object -Unique

if ($uniquePaths.Count -eq 0) {
    Write-Host "No JDK installation found." -ForegroundColor Yellow
    Write-Host "Please install JDK 8 and run this script again." -ForegroundColor Yellow
} else {
    Write-Host "Found JDK installation(s):" -ForegroundColor Green
    Write-Host ""
    $index = 1
    foreach ($jdkPath in $uniquePaths) {
        # Check if it's a valid JDK (contains bin\javac.exe)
        $javacPath = Join-Path $jdkPath "bin\javac.exe"
        $isValid = Test-Path $javacPath
        
        if ($isValid) {
            Write-Host "[$index] $jdkPath" -ForegroundColor Cyan
            Write-Host "    (Valid JDK - contains javac.exe)" -ForegroundColor Gray
            
            # Try to get version info
            try {
                $javaExe = Join-Path $jdkPath "bin\java.exe"
                if (Test-Path $javaExe) {
                    $version = & $javaExe -version 2>&1 | Select-Object -First 1
                    Write-Host "    Version: $version" -ForegroundColor Gray
                }
            } catch {}
        } else {
            Write-Host "[$index] $jdkPath" -ForegroundColor Yellow
            Write-Host "    (Warning: May not be a valid JDK path)" -ForegroundColor Yellow
        }
        Write-Host ""
        $index++
    }
    
    Write-Host "Copy the JDK path to the 'path' field in .vscode/settings.json" -ForegroundColor Green
    Write-Host ""
    Write-Host "Example configuration:" -ForegroundColor Yellow
    $examplePath = $uniquePaths[0] -replace '\\', '\\'
    Write-Host '{'
    Write-Host '  "java.configuration.runtimes": ['
    Write-Host '    {'
    Write-Host '      "name": "JavaSE-1.8",'
    Write-Host ('      "path": "' + $examplePath + '",')
    Write-Host '      "default": true'
    Write-Host '    }'
    Write-Host '  ]'
    Write-Host '}'
}
