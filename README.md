# 🚀 Test Automation - Ecommerce Playground (Selenium Java)

[![Java Version](https://img.shields.io/badge/Stack-Java%2017%2B-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Selenium Version](https://img.shields.io/badge/Framework-Selenium%204-green.svg)](https://www.selenium.dev/)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)]()
[![Allure Report](https://img.shields.io/badge/Report-Allure-yellow.svg)](https://docs.qameta.io/allure/)

Automação end-to-end de nível **Elite** para o e-commerce de demonstração [Ecommerce Playground](https://ecommerce-playground.lambdatest.io/). A suíte foi arquitetada com foco em **escalabilidade massiva**, **resiliência (Self-Healing)** e **performance híbrida (API + UI)**, seguindo os padrões de engenharia de qualidade das Big Techs.

---

# 🎯 Objetivo do Projeto

Garantir a qualidade e estabilidade dos fluxos críticos de negócio, simulando a jornada real do usuário com robustez técnica.

- **Fluxos Críticos:** Login, Cadastro, Busca, Carrinho e Checkout (E2E).
- **Validação de Regras:** Testes negativos para autenticação e cadastro duplicado.
- **Performance:** Redução de tempo de execução via setup de dados por API.

Foco em:
- **Zero Flakiness:** Estratégias de espera fluida e locators resilientes.
- **Manutenibilidade:** Código limpo, componentizado e desacoplado.
- **Observabilidade:** Logs detalhados, screenshots automáticos e categorização de falhas.

---

# 🧠 Estratégia e Arquitetura

O projeto utiliza uma arquitetura de camadas moderna e robusta:

1.  **Fluent Page Objects:** Classes de página que retornam a si mesmas ou a próxima página, permitindo escrita de testes como uma "frase".
2.  **Componentization:** Elementos reutilizáveis (ex: Barra de Busca) isolados em componentes.
3.  **Hybrid Automation (API Seeding):** Uso de **Rest-Assured** para criar estado (Login/Carrinho) via API antes de iniciar a UI.
4.  **Configuration Manager (OWNER):** Gestão centralizada e tipada de propriedades (Local, Grid, Docker).
5.  **Self-Healing Engine:** Mecanismo na `BasePage` que recupera a execução em caso de falha de seletores primários.

---

# � Fluxos Cobertos

1.  **Autenticação**
    - Login com sucesso e tentativa inválida.
    - Cadastro de novo usuário e validação de e-mail duplicado.
2.  **Jornada de Compra**
    - Busca de produtos e validação de resultados vazios.
    - Adição, edição e remoção de itens no carrinho.
    - Checkout completo (End-to-End) até a confirmação do pedido.
3.  **Resiliência**
    - Recuperação automática de seletores quebrados.
    - Validação de mensagens de erro e feedback visual.

---

# 📁 Estrutura do Projeto

```text
src/
├── main/java/com/bruno/ecommerce/
│   ├── api/            # Clientes para Setup de Dados via API
│   ├── base/           # Core Engine, DriverFactory e Self-Healing
│   ├── components/     # Componentes reutilizáveis (Search, Menu)
│   ├── config/         # Gerenciamento de Configuração (OWNER)
│   ├── models/         # POJOs para mapeamento de dados (Jackson)
│   └── pages/          # Fluent Page Objects (Lógica de interação)
├── test/java/com/bruno/ecommerce/
│   └── tests/          # Suítes de Teste (E2E, Regressão, Negativos)
└── test/resources/
    ├── allure/         # Categorias de falha personalizadas
    ├── config/         # Arquivos .properties por ambiente
    └── data/           # Massa de dados (JSON)
```

---

# ⚙️ Funcionalidades Automatizadas

## 🛡️ Engenharia de Confiabilidade (Elite Level)

### Self-Healing Locators
O framework possui uma "inteligência" na `BasePage` que atua quando um seletor primário falha (ex: mudança de ID). Ele tenta automaticamente estratégias secundárias (match parcial de texto/atributos) e loga um alerta, evitando que o teste quebre por mudanças triviais na UI.

### Mutation Testing (PITest)
Integração com **PITest** para validar a eficácia dos asserts. O sistema altera o código da aplicação propositalmente para garantir que os testes falhem quando devem falhar (evitando falsos positivos).

## ⚡ Hybrid Automation (ROI)
Testes de UI são lentos. Para otimizar, implementamos **API Seeding**:
- **Cenário Tradicional:** Abrir browser -> Clicar Login -> Digitar -> Clicar Entrar -> Buscar Produto -> Clicar Adicionar.
- **Cenário Híbrido:** Chamada API Login + AddCart (ms) -> Abrir Browser já no Checkout.
- **Resultado:** Redução de **30-40s** por caso de teste.

---

# 🧪 Boas Práticas Aplicadas

- **Thread-Safe:** Uso de `ThreadLocal` no `DriverFactory` para execução paralela segura.
- **Clean Code:** Remoção de imports não usados, variáveis mortas e código duplicado.
- **Quality Gates:** Pipeline que falha se o código não seguir os padrões (Checkstyle/SpotBugs).
- **Testcontainers:** Infraestrutura "Zero-Setup" que sobe o ambiente de teste (Browser) em Docker automaticamente.

---

# 🛠️ Tecnologias

- **Linguagem:** Java 17
- **Core:** Selenium WebDriver 4
- **Test Runner:** JUnit 5
- **API Testing:** Rest-Assured
- **Reporting:** Allure Report
- **Infra:** Testcontainers & Docker
- **Config:** Owner Library
- **Build:** Maven

---

# ▶️ Como Executar

### 1️⃣ Pré-requisitos
- JDK 17+
- Maven
- Docker (Opcional, para execução isolada)

### 2️⃣ Execução via CLI (Perfis Maven)

O projeto utiliza **Maven Profiles** para facilitar o uso:

```bash
# Smoke Tests (Validação Rápida)
mvn test -Psmoke

# Regressão Completa (Todos os fluxos)
mvn test -Pregression

# Execução "Zero-Setup" (Via Docker - Não requer Chrome instalado)
mvn test -Psmoke -Dtarget=docker -Dbrowser=chrome
```

### 3️⃣ Relatórios

Após a execução, gere o relatório visual detalhado:

```bash
mvn allure:serve
```

---

# 📄 Relatórios & Observabilidade

- **Screenshots Automáticos:** Capturados a cada falha.
- **Browser Logs:** Erros de console (JS/Network) anexados ao relatório para RCA instantânea.
- **Categorização:** O Allure separa automaticamente "Bugs de Produto" de "Falhas de Automação".

---

# � Diferenciais do Projeto

- **Arquitetura de Big Tech:** Estrutura pensada para escalar para milhares de testes.
- **Documentação Viva:** ADRs (Architecture Decision Records) documentando decisões técnicas em `docs/adr/`.
- **Independência:** Roda em qualquer máquina com Docker sem configuração manual de drivers.

---

# 🤝 Conclusão

Este framework demonstra não apenas "como automatizar um site", mas como construir uma **plataforma de qualidade** resiliente, rápida e confiável, pronta para ambientes corporativos exigentes.
