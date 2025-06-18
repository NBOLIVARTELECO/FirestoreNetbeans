#!/bin/bash

echo "========================================"
echo "   FirestoreTest - Ejecutor de Pruebas"
echo "========================================"
echo

# Verificar si Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven no está instalado o no está en el PATH"
    echo "Por favor, instala Maven y agrégalo al PATH del sistema"
    exit 1
fi

# Verificar si Java está instalado
if ! command -v java &> /dev/null; then
    echo "ERROR: Java no está instalado o no está en el PATH"
    echo "Por favor, instala Java 22 y agrégalo al PATH del sistema"
    exit 1
fi

echo "Verificando dependencias..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "ERROR: Error al compilar el proyecto"
    exit 1
fi

echo
echo "Compilación exitosa!"
echo
echo "Ejecutando aplicación..."
echo

mvn exec:java

echo
echo "Presiona Enter para salir..."
read 