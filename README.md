## Exercício Individual
### Adicionar paralelismo na atividade anterior

#### bonus - adicionado endpoint que recebe .txt e devolve zip com todos os mapas.
```bash
POST http://localhost:8080/api/mapa  
RequestBody MultipartFile file
```

#### Enunciado + paralelismo
Este exercício é uma continuacao do projeto do Mapa Astral iniciado aula passada.

1) criar arquivo com nome dos participantes de cada grupo com as seguintes informacoes:

Nome
ZoneId de nascimento (Local)
Data de nascimento
Ex.: Joarez da Silva,America/Sao_Paulo,1990-08-30 Jarred Jefferson,America/Sao_Paulo,1999-12-15

2) Ler arquivo utilizando a API java.nio e salvar em um novo objeto

3) Fazer o mapa astral + funcionalidades

4) Criar 1 arquivo para cada usuario da lista inicial com um relatorio com mapa quantico.

Critérios de avaliacao:

Uso correto da API de streams - 5 pontos

Uso correto da API java.nio para arquivos - 10 pontos

Uso de OO, com criacao de classes e métodos com nomes significativos - 5 pontos