# Magalu – Plataforma de Comunicação (Sprint 1)

Agendador de envio de comunicações (e-mail, SMS, push, WhatsApp) com **Java 17 + Spring Boot 3**, salvando o agendamento no banco de dados e expondo endpoints REST para **criar**, **consultar** e **remover** agendamentos. Requisitos conforme o PDF do desafio (Sprint 1).

> Endpoints em JSON seguindo REST. Banco: **PostgreSQL** (pode adaptar para MySQL).

---

## ✅ Endpoints

### 1) Criar agendamento
- **POST** `/api/agendamentos`
- **Body (JSON)**:
```json
{
  "destinatario": "5511999999999",
  "mensagem": "Seu pedido foi enviado!",
  "tipoComunicacao": "WHATSAPP",
  "dataHoraEnvio": "2025-09-20T14:30:00"
}
```
- **Resposta 200 OK (exemplo)**:
```json
{
  "id": 1,
  "destinatario": "5511999999999",
  "mensagem": "Seu pedido foi enviado!",
  "tipoComunicacao": "WHATSAPP",
  "dataHoraEnvio": "2025-09-20T14:30:00",
  "status": "AGENDADO",
  "criadoEm": "2025-09-18T00:00:00"
}
```

### 2) Consultar agendamento por ID
- **GET** `/api/agendamentos/{id}`
- **Resposta 200 OK**: objeto do agendamento
- **404** se não existir

### 3) Remover agendamento
- **DELETE** `/api/agendamentos/{id}`
- **Resposta 200 OK** (sem corpo)
- **404** se não existir

> **Observações**
> - Datas em **ISO-8601** (`yyyy-MM-dd'T'HH:mm:ss`), mapeadas para `LocalDateTime`.
> - `tipoComunicacao`: `EMAIL`, `SMS`, `PUSH`, `WHATSAPP` (enum).
> - `status`: começa como `AGENDADO`. (Pensado para evoluir no futuro: `ENVIADO`, `ERRO`, `CANCELADO`.)

---

## ▶️ Como rodar localmente

### Pré-requisitos
- **JDK 17**
- **Maven** _ou_ **Gradle**
- **PostgreSQL** rodando localmente

### Configuração do banco
Crie um banco vazio (ex.: `magalu_comm`). Use o arquivo `application-example.yml` como base:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/magalu_comm
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
  jackson:
    serialization:
      WRITE_DATES_AS_TIMESTAMPS: false
server:
  port: 8080
```

Renomeie para `application.yml` em `src/main/resources/` e ajuste usuário/senha/porta conforme seu ambiente.

### Rodando
- **Maven**: `./mvnw spring-boot:run` (Linux/macOS) ou `mvnw.cmd spring-boot:run` (Windows)
- **Gradle**: `./gradlew bootRun` (Linux/macOS) ou `gradlew.bat bootRun` (Windows)

App em: `http://localhost:8080`

---

## 🧪 Testando no Insomnia

> Importar o arquivo: **`insomnia_magalu.json`** (está neste repositório).

### Passo a passo
1. Abra o **Insomnia** → **Create** → **Import** → **From File** → selecione `insomnia_magalu.json`.
2. No ambiente **Base Environment**, confirme a variável:  
   ```json
   { "base_url": "http://localhost:8080" }
   ```
3. Use as 3 requisições já prontas:
   - **Create Agendamento** (POST) → edite o JSON se quiser.
   - **Get Agendamento by ID** (GET) → troque `{id}` pelo ID retornado no POST.
   - **Delete Agendamento by ID** (DELETE) → informe o mesmo `{id}`.

### cURL (alternativo)
```bash
# Criar
curl -X POST "{{base_url}}/api/agendamentos"   -H "Content-Type: application/json"   -d '{
    "destinatario": "5511999999999",
    "mensagem": "Seu pedido foi enviado!",
    "tipoComunicacao": "WHATSAPP",
    "dataHoraEnvio": "2025-09-20T14:30:00"
  }'

# Buscar
curl -X GET "{{base_url}}/api/agendamentos/1"

# Remover
curl -X DELETE "{{base_url}}/api/agendamentos/1"
```

> **Erros comuns**
> - 400: corpo JSON inválido ou campo ausente.
> - 404: ID inexistente (buscar/remover).
> - 415: faltou `Content-Type: application/json` no POST.
> - Datas fora do formato ISO-8601.

---

## 🧩 Estrutura (sugestão)

- `controller/AgendamentoController.java`  
- `service/AgendamentoService.java`  
- `repository/AgendamentoRepository.java`  
- `model/Agendamento.java`  
- `dto/AgendamentoRequestDto.java`, `dto/AgendamentoResponseDto.java`  
- `enums/StatusAgendamento.java`

> Fluxo: **Controller → Service → Repository → DB**

---

## 🧪 Testes
- **JUnit 5** + **Spring Boot Test**
- Rodar: `mvn test` ou `./gradlew test`

---

## 📝 Licença
Este projeto está sob a licença **MIT**. Veja o arquivo [`LICENSE`](./LICENSE).

---

## 🚀 Como criar o repositório e enviar o código

```bash
# dentro da pasta do projeto
git init
git add .
git commit -m "feat: initial commit - Magalu communication scheduler (sprint 1)"
git branch -M main
git remote add origin https://github.com/<seu-usuario>/<seu-repo>.git
git push -u origin main
```

> Dica: faça **commits pequenos e bem descritos** (ex.: `feat:`, `fix:`, `test:`, `docs:`).

---

## 📌 Observação sobre o desafio
Este repositório cobre a **Sprint 1** (agendamento, consulta e remoção). A parte de **envio efetivo** e **mudança de status** ficará para a próxima sprint (estrutura preparada para isso).
