
# Desafio Itau API de Transação

Projeto para desafio do itau: https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior

---

## Rodando localmente

Clone o projeto

```bash
git clone https://github.com/MiguelLopesDel/Transaction-Api-Desafio-Itau.git
```

Entre no diretório do projeto

```bash
cd Transaction-Api-Desafio-Itau
```

Compile o projeto

```bash
mvn clean install
```

### Rodando com Docker

Com o Dockerfile já configurado, você pode rodar a aplicação diretamente com o seguinte comando:

1. **Construir a imagem Docker**:

   ```bash
   docker build -t transaction-api .
   ```

2. **Rodar o container**:

   ```bash
   docker run -p 8080:8080 transaction-api
   ```

A aplicação estará disponível em `http://localhost:8080`.

---

## Documentação da API

A documentação da API está disponível através do Swagger. Para acessá-la, basta abrir o navegador e ir até o seguinte endereço após iniciar a aplicação:

```
http://localhost:8080/swagger-ui.html
```
---
## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn test
```

