# ADR 002: Implementação de API Seeding com Rest-Assured

## Status
Aceito

## Contexto
Testes de UI são inerentemente lentos. Realizar pré-condições (login, criação de massa, adição ao carrinho) via UI aumenta o tempo de execução e a fragilidade dos testes.

## Decisão
Implementamos uma camada de **API Seeding** utilizando **Rest-Assured**.

## Consequências
- **Positivas**: Redução drástica no tempo de execução dos testes de UI (ROI de 30-40 segundos por teste), maior confiabilidade nas pré-condições, isolamento do teste de UI para o cenário de negócio alvo.
- **Negativas**: Requer que a aplicação possua endpoints de API correspondentes e acessíveis.
