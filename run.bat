@echo off
echo ========================================
echo    FirestoreTest - Ejecutor de Pruebas
echo ========================================
echo.

REM Verificar si Maven está instalado
mvn --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven no está instalado o no está en el PATH
    echo Por favor, instala Maven y agrégalo al PATH del sistema
    pause
    exit /b 1
)

REM Verificar si Java está instalado
java --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo Por favor, instala Java 22 y agrégalo al PATH del sistema
    pause
    exit /b 1
)

echo Verificando dependencias...
call mvn clean compile

if errorlevel 1 (
    echo ERROR: Error al compilar el proyecto
    pause
    exit /b 1
)

echo.
echo Compilación exitosa!
echo.
echo Ejecutando aplicación...
echo.

call mvn exec:java

echo.
echo Presiona cualquier tecla para salir...
pause >nul 