# Gerador de Senhas Seguras

Um pequeno projeto Java que fornece uma interface web leve para gerar senhas fortes com opções de personalização. O objetivo é demonstrar um aplicativo completo com geração segura de senhas, servidor HTTP embutido e cobertura de testes.

## O que é este projeto

Este aplicativo gera senhas aleatórias usando `SecureRandom` e permite ao usuário escolher:

- comprimento da senha,
- inclusão de letras maiúsculas,
- inclusão de letras minúsculas,
- inclusão de dígitos,
- inclusão de símbolos.

A aplicação expõe uma página web simples em `http://localhost` para facilitar o uso.

## Por que usar

- Geração de senhas segura com `SecureRandom`
- Interface web sem dependências externas de servidor
- Código modular e fácil de entender
- Testes unitários para garantir comportamento esperado

## Tecnologias usadas

- Java 25
- Maven
- JUnit 5
- Servidor HTTP embutido do JDK (`com.sun.net.httpserver`)

## Pré-requisitos

- Java 25 instalado
- Maven 3.8+ instalado
- Acesso à linha de comando

> Se você estiver usando o projeto em um ambiente Windows, execute os comandos no PowerShell.

## Como executar

No diretório do projeto:

```powershell
mvn clean package
mvn exec:java -Dexec.mainClass=com.example.passwordgen.App
```

Depois, abra o navegador em:

```text
http://localhost
```

### Alternativa com Makefile

Se você tiver o `make` instalado, use:

```bash
make run
```

## Variáveis de ambiente

Este projeto não exige um arquivo `.env` para rodar, mas você pode definir as variáveis abaixo para reproduzir o ambiente de execução usado nos testes:

```env
JAVA_HOME=C:\Users\seu-usuario\.jdk\jdk-25.0.2
MAVEN_OPTS=-Xmx512m
```

No Windows PowerShell, defina o `JAVA_HOME` assim:

```powershell
$env:JAVA_HOME = 'C:\Users\seu-usuario\.jdk\jdk-25.0.2'
```

## Estrutura do projeto

```text
ProjetoAKCIT/
├── .gitignore          # Arquivos e pastas ignoradas pelo Git
├── LICENSE             # Licença do projeto
├── Makefile            # Atalhos para build e execução
├── pom.xml             # Configuração do Maven
├── README.md           # Documentação do projeto
├── src/
│   ├── main/java/com/example/passwordgen/
│   │   ├── App.java            # Ponto de entrada da aplicação
│   │   ├── PasswordGenerator.java # Lógica de geração de senha
│   │   └── WebServer.java      # Servidor HTTP e handlers
│   └── test/java/com/example/passwordgen/
│       └── PasswordGeneratorTest.java # Testes unitários
└── target/              # Artefatos gerados pelo Maven (ignorado no Git)
```

## Testes

Execute o conjunto de testes automatizados com:

```bash
mvn test
```

O projeto foi testado com Java 25 e Maven 3.9.15.

### Teste End-to-End

O projeto inclui um script E2E para validar o fluxo de usuário completo:

```powershell
.\e2e-test.ps1
```

O script:

- compila o projeto com `mvn clean package`
- inicia a aplicação localmente
- verifica a página inicial
- verifica o endpoint `/generate`

O projeto também oferece um alvo `make` para executar o teste E2E:

```bash
make e2e
```

O projeto inclui testes para verificar que:

- senhas geradas contêm letras maiúsculas quando selecionadas,
- senhas geradas contêm letras minúsculas quando selecionadas,
- senhas vazias não são aceitas,
- tamanho mínimo e requisitos de tipos são validados corretamente.

## Organização do repositório

O repositório está estruturado para ser claro e fácil de navegar:

- `src/main/java` contém o código fonte da aplicação.
- `src/test/java` contém os testes unitários.
- `pom.xml` define dependências, plugins e configuração de compilação.
- `.gitignore` impede que artefatos de build e configurações locais sejam enviados ao Git.

## Como contribuir

1. Faça um fork do repositório.
2. Crie uma branch com a sua melhoria: `git checkout -b minha-melhoria`
3. Faça as alterações e rode os testes: `mvn test`
4. Abra um pull request explicando a mudança.

## Assistência de IA

Este projeto foi construído com auxílio de uma inteligência artificial especializada em desenvolvimento de software. A IA contribuiu para:

- definir a estrutura do projeto Maven;
- implementar o servidor HTTP leve e o gerador de senhas seguras;
- criar testes unitários e um script de teste end-to-end;
- documentar o uso, a execução e o fluxo do projeto no README.

Essa colaboração acelerou o desenvolvimento e ajudou a manter o código claro e bem documentado.

## Licença

Este projeto está licenciado sob a licença MIT.
