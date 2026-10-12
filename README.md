# api_cores_java_servlet

A API sobe no Tomcat em `http://127.0.0.1:8080/myservlet`. Os corpos de requisição e resposta são JSON.

```bash
make sintaxe    # só compila
make teste      # compila e roda os testes
make deploy     # gera o WAR e publica em /opt/tomcat/webapps
```

O log do serviço e o arquivo do dia pedem `sudo`:

```bash
sudo journalctl -u tomcat -n 100 --no-pager
sudo journalctl -u tomcat -f
sudo tail -n 100 /opt/tomcat/logs/catalina.$(date +%F).log
sudo tail -f /opt/tomcat/logs/localhost.$(date +%F).log
```

Para esvaziar os arquivos de log, pare o Tomcat, zere os arquivos e suba de novo. O processo mantém o arquivo aberto, então o corte só vale com o serviço parado.

```bash
sudo systemctl stop tomcat
sudo find /opt/tomcat/logs -type f -exec truncate -s 0 {} +
sudo systemctl start tomcat
```

O MySQL local precisa dos bancos `api_cores` (usuário `root`, senha `123456`) e `bd` (o mesmo usuário). Tabelas usadas:

| Banco | Tabela | Colunas |
| --- | --- | --- |
| `api_cores` | `cores` | `r`, `g`, `b`, `h`, `s`, `v`, `descricao` |
| `api_cores` | `padroes_media_lab` | `estagio_id`, `l_value`, `a_value`, `b_value` |
| `api_cores` | `estagios` | `id`, `nome` |
| `bd` | `tomates` | `r`, `g`, `b`, `maturacao` |

`/color` e `/cor_detect` aceitam o navegador em `http://localhost:3001`. `/cores` aceita `http://localhost:5173`.

## POST /myservlet/color

Grava uma cor RGB/HSV na tabela `cores`.

```bash
curl -s -X POST http://127.0.0.1:8080/myservlet/color \
  -H 'Content-Type: application/json' \
  -d '{"r":180,"g":40,"b":30,"h":8.5,"s":0.83,"v":0.70,"descricao":1}'
```

Resposta:

```json
{"message":"Data received successfully","receivedData":{"r":180,"g":40,"b":30,"h":8.5,"s":0.83,"v":0.70,"descricao":1}}
```

Falha de banco: HTTP 500 em JSON, com a mensagem do MySQL em `details`.

```json
{"status":500,"error":"Erro ao salvar no banco de dados","details":"Access denied for user 'api-cores'@'172.17.0.1' (using password: YES)"}
```

## GET /myservlet/color

Lista as cores gravadas.

```bash
curl -s http://127.0.0.1:8080/myservlet/color
```

Resposta: `{"cores":[...]}`. Falha de banco: HTTP 500, `{"status":500,"error":"Erro ao recuperar dados do banco de dados","details":"..."}`.

## GET /myservlet/cores

Lista os padrões Lab médios de `padroes_media_lab`.

```bash
curl -s http://127.0.0.1:8080/myservlet/cores
```

```json
[{"L":52.3,"A":41.1,"B":28.7}]
```

Falha de banco: HTTP 500, `{"status":500,"error":"Erro ao consultar o banco de dados","details":"..."}`.

## POST /myservlet/cor_detect

Converte o RGB enviado para Lab e devolve o padrão de `padroes_media_lab` com menor distância CIEDE2000, junto com o nome do estágio.

```bash
curl -s -X POST http://127.0.0.1:8080/myservlet/cor_detect \
  -H 'Content-Type: application/json' \
  -d '{"r":180,"g":40,"b":30}'
```

```json
{"distancia":3.14,"padrao":"maduro"}
```

O `GET` nesse caminho não devolve corpo. Falha de banco no `POST`: HTTP 500, `{"status":500,"error":"Erro ao consultar o banco de dados","details":"..."}`.

## POST /myservlet/preverMaturacao

Classifica a maturação por k-NN (`k = 5`) usando as linhas de `bd.tomates` em que `maturacao` não é nula.

```bash
curl -s -X POST http://127.0.0.1:8080/myservlet/preverMaturacao \
  -H 'Content-Type: application/json' \
  -d '{"r":180,"g":40,"b":30}'
```

```json
{"maturacao_prevista":"maduro"}
```

Sem vizinhos, a resposta é `"Desconhecido"`. Falha de banco: HTTP 500, `{"status":500,"error":"Erro no banco","details":"..."}`.
