# MegaFarma

API Restful com Spring Boot que lista os remédios de uma farmácia, feita durante o curso na FIAP.

## Estrutura (arquitetura em camadas)

- `MegaFarmaApplication` — classe principal que inicia a aplicação Spring Boot.
- `resource/RemedioResource` — controller REST (`/megafarma`).
- `bo/RemedioBO` — regras de negócio.
- `dao/RemedioDAO` — acesso aos dados (por enquanto, uma lista fixa em memória).
- `to/RemedioTO` — código, nome, preço, data de fabricação e data de validade.

## Como rodar

Precisa de Java 17+. O Maven já vem junto no projeto (Maven Wrapper):

```bash
./mvnw spring-boot:run      # Linux/Mac
mvnw.cmd spring-boot:run    # Windows
```

A API sobe em `http://localhost:8080`.

## Endpoints

| Método | Rota          | O que faz                 |
|--------|---------------|---------------------------|
| GET    | `/megafarma`  | Lista todos os remédios   |

## Tecnologias

- Java 17
- Spring Boot 3 (Spring Web)
- Maven
