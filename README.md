# MegaFarma

API REST em Java com Spring que lista remédios de uma farmácia, feita durante o curso na FIAP.

## Estrutura (arquitetura em camadas)

- `resource/RemedioResource` — controller REST (`/megafarma`).
- `bo/RemedioBO` — regras de negócio.
- `dao/RemedioDAO` — acesso aos dados (por enquanto, uma lista fixa em memória).
- `to/RemedioTO` — objeto de transferência: código, nome, preço, data de fabricação e validade.

## Tecnologias

- Java
- Spring Web

## Status

🚧 Em desenvolvimento. Ainda falta o `pom.xml` e mapear o método `findAll` com `@GetMapping`.
