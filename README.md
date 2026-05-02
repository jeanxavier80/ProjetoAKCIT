# Gerador de Senhas Seguras

Mini-projeto Java com interface web simples para gerar senhas fortes e configuráveis.

## Recursos

- Gera senhas com comprimento customizável.
- Permite escolher letras maiúsculas/minúsculas, dígitos e símbolos.
- Usa `SecureRandom` e método `random_int()` para selecionar caracteres.
- Interface web leve acessível em `http://localhost:4567`.
- Testes com JUnit 5.
- Estrutura gerada com apoio do Copilot para organização e implementação.

## Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- `make` (opcional, para usar o `Makefile`)

## Como executar

Execute no diretório do projeto:

```bash
mvn clean package
mvn exec:java -Dexec.mainClass=com.example.passwordgen.App
```

Ou usando o `Makefile`:

```bash
make run
```

Depois, abra no navegador:

```
http://localhost:4567
```

## Estrutura do projeto

- `src/main/java/com/example/passwordgen/PasswordGenerator.java` — lógica de geração da senha
- `src/main/java/com/example/passwordgen/WebServer.java` — servidor HTTP simples
- `src/main/java/com/example/passwordgen/App.java` — ponto de entrada da aplicação
- `src/test/java/com/example/passwordgen/PasswordGeneratorTest.java` — testes unitários
- `pom.xml` — configuração do Maven
- `Makefile` — comandos de build e execução

## Testes

Para rodar os testes:

```bash
mvn test
```

## Notas

- O servidor web é implementado usando o servidor HTTP embutido do JDK, sem dependências externas para a interface.
- O gerador garante que cada tipo de caractere selecionado apareça pelo menos uma vez na senha.

## Licença

Este projeto está licenciado sob a licença MIT.
