# ADR 001: Uso da biblioteca OWNER para Gerenciamento de Configuração

## Status
Aceito

## Contexto
Precisávamos de uma forma robusta e escalável para gerenciar configurações de ambiente (URL, browser, timeouts, targets). O uso de `System.getProperty` puro ou `Properties` manual torna o código verboso e propenso a erros de tipagem.

## Decisão
Decidimos utilizar a biblioteca **OWNER** para criar uma interface de configuração tipada.

## Consequências
- **Positivas**: Código mais limpo, suporte nativo a múltiplos ambientes, carregamento automático de variáveis de ambiente e propriedades do sistema, tipagem estática para valores como `int` e `boolean`.
- **Negativas**: Introdução de uma nova dependência no projeto.
