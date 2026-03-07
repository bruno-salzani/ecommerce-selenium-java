# ADR 003: Implementação de Self-Healing (Smart Locators)

## Status
Aceito

## Contexto
Um dos maiores problemas em automação de UI com Selenium é a fragilidade dos seletores (IDs, CSS Classes) que mudam frequentemente durante o desenvolvimento, causando falhas nos testes que não são bugs reais da aplicação.

## Decisão
Implementamos uma lógica de **Self-Healing** na `BasePage`. Quando o localizador primário falha, o framework tenta automaticamente uma estratégia secundária baseada em padrões de texto e atributos parciais.

## Consequências
- **Positivas**: Redução drástica na manutenção de scripts, maior resiliência a mudanças menores na UI, log detalhado no Allure quando a "autocura" é acionada (facilitando a atualização do código posteriormente).
- **Negativas**: Pequeno aumento no tempo de timeout em casos onde o elemento realmente não existe.
