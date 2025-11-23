#!/bin/bash

# ============================================================
# SCRIPT DE INFRAESTRUTURA - WORKCONNECT (PaaS)
# ============================================================

# Verifica se as variáveis sensíveis foram definidas
if [ -z "$DB_USER" ] || [ -z "$DB_PASSWORD" ]; then
    echo "ERRO: As variáveis de ambiente DB_USER e DB_PASSWORD não estão definidas."
    echo "Por favor, exporte-as antes de rodar o script."
    echo "Exemplo: export DB_USER='admin' && export DB_PASSWORD='senha'"
    exit 1
fi

# Variáveis de Nomes
RG_NAME="rg-workconnect-gs2025"
LOCATION="eastus2"
SQL_SERVER_NAME="sql-workconnect-gs-2025" # Nome único global
DB_NAME="db_workconnect"
PLAN_NAME="asp-workconnect"
WEBAPP_NAME="webapp-workconnect-gs-2025" # Nome único global
SKU="B1"

echo "Iniciando provisionamento..."

# 1. Criar Resource Group
echo "Criando Resource Group: $RG_NAME..."
az group create --name $RG_NAME --location $LOCATION

# 2. Criar Servidor SQL
echo "Criando SQL Server: $SQL_SERVER_NAME..."
az sql server create --name $SQL_SERVER_NAME \
    --resource-group $RG_NAME \
    --location $LOCATION \
    --admin-user "$DB_USER" \
    --admin-password "$DB_PASSWORD"

# 3. Regra de Firewall (Permite que o Azure acesse o banco)
echo "Configurando Firewall..."
az sql server firewall-rule create --resource-group $RG_NAME \
    --server $SQL_SERVER_NAME \
    --name AllowAzureServices \
    --start-ip-address 0.0.0.0 \
    --end-ip-address 0.0.0.0

# 4. Criar Banco de Dados
echo "Criando Banco de Dados: $DB_NAME..."
az sql db create --resource-group $RG_NAME \
    --server $SQL_SERVER_NAME \
    --name $DB_NAME \
    --service-objective Basic

# 5. Criar App Service Plan (Linux)
echo "Criando App Service Plan: $PLAN_NAME..."
az appservice plan create --name $PLAN_NAME \
    --resource-group $RG_NAME \
    --sku $SKU \
    --is-linux

# 6. Criar Web App (Java 17)
echo "Criando Web App: $WEBAPP_NAME..."
az webapp create --name $WEBAPP_NAME \
    --resource-group $RG_NAME \
    --plan $PLAN_NAME \
    --runtime "JAVA:17-java17"

echo "============================================================"
echo "INFRAESTRUTURA CRIADA COM SUCESSO!"
echo "URL do Web App: https://$WEBAPP_NAME.azurewebsites.net"
echo "Servidor SQL: $SQL_SERVER_NAME.database.windows.net"
echo "============================================================"