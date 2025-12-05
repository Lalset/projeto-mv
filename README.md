# Projeto MV

Sistema que desenvolvi em **Java 21** com **Spring Boot** e **Oracle Database** para controle de clientes, contas bancárias, movimentações financeiras e cálculo de receita da empresa XPTO.

O objetivo do projeto é permitir que a empresa XPTO controle as receitas e despesas de diversos clientes (pessoas físicas e jurídicas), com base nas movimentações realizadas em suas contas.  
O sistema calcula automaticamente quanto a XPTO recebe por cliente de acordo com o número de movimentações.

---

## Estrutura do Sistema

O projeto segue o padrão **MVC (Model-View-Controller)**, com separação em camadas para melhor manutenção e organização do código.

**Camadas principais:**
- **Model:** Representa as entidades do sistema (Cliente, Conta, Endereco, Movimentacao).
- **Repository:** Interfaces responsáveis pela comunicação com o banco de dados Oracle.
- **Service:** Contém as regras de negócio, incluindo a chamada da função PL/SQL.
- **Controller:** Define os endpoints REST acessados via Postman.

---

## Tecnologias Utilizadas

- Java 21  
- Spring Boot 3  
- Maven  
- Oracle Database (FreePDB1)  
- DBeaver  
- Postman  
- Lombok  
- JDBC (CallableStatement para integração com PL/SQL)

---

## Função PL/SQL Utilizada

O sistema utiliza uma função PL/SQL para calcular o valor devido à XPTO conforme a quantidade de movimentações de cada cliente.  
Essa função é chamada diretamente pelo Java através de um `CallableStatement` na classe `ReceitaService`.

```sql
CREATE OR REPLACE FUNCTION CALCULAR_VALOR_MOV(QTD NUMBER)
RETURN NUMBER
IS
    VALOR NUMBER;
BEGIN
    IF QTD <= 10 THEN
        VALOR := QTD * 1.00;
    ELSIF QTD <= 20 THEN
        VALOR := QTD * 0.75;
    ELSE
        VALOR := QTD * 0.50;
    END IF;
    RETURN VALOR;
END;
/
```
## Lógica da Receita(CALCULAR_VALOR_MOV)
| Quantidade de Movimentações | Valor por Movimentação |
| --------------------------- | ---------------------- |
| Até 10                      | R$ 1,00                |
| De 11 a 20                  | R$ 0,75                |
| Acima de 20                 | R$ 0,50                |
## Exemplo prático

Se o cliente "Empresa XPTO" realizou 15 movimentações:

Primeiras 10 movimentações → R$ 1,00 cada

5 movimentações seguintes → R$ 0,75 cada

Total: (10 × 1,00) + (5 × 0,75) = R$ 13,75
O Endpoint responsável pelo cálculo é GET /movimentacoes/receita/{idConta}

# Endpoints da API

| Método | Endpoint         | Descrição                                 |
| ------ | ---------------- | ----------------------------------------- |
| GET    | `/clientes`      | Lista todos os clientes cadastrados       |
| GET    | `/clientes/{id}` | Busca um cliente específico por ID        |
| POST   | `/clientes`      | Cadastra um novo cliente                  |
| PUT    | `/clientes/{id}` | Atualiza os dados de um cliente existente |
| DELETE | `/clientes/{id}` | Exclui um cliente                         |

| Método | Endpoint                      | Descrição                                       |
| ------ | ----------------------------- | ----------------------------------------------- |
| GET    | `/contas`                     | Lista todas as contas cadastradas               |
| GET    | `/contas/cliente/{idCliente}` | Lista todas as contas pertencentes a um cliente |
| POST   | `/contas/cliente/{idCliente}` | Cadastra uma nova conta vinculada a um cliente  |
| PUT    | `/contas/{id}`                | Atualiza os dados de uma conta existente        |
| DELETE | `/contas/{id}`                | Exclui uma conta bancária                       |

| Método | Endpoint                           | Descrição                                                                                    |
| ------ | ---------------------------------- | -------------------------------------------------------------------------------------------- |
| GET    | `/movimentacoes`                   | Lista todas as movimentações cadastradas                                                     |
| GET    | `/movimentacoes/conta/{idConta}`   | Lista todas as movimentações de uma conta específica                                         |
| POST   | `/movimentacoes/conta/{idConta}`   | Cadastra uma nova movimentação (entrada ou saída)                                            |
| PUT    | `/movimentacoes/{id}`              | Atualiza uma movimentação existente                                                          |
| DELETE | `/movimentacoes/{id}`              | Exclui uma movimentação                                                                      |
| GET    | `/movimentacoes/receita/{idConta}` | Calcula a receita da XPTO com base na quantidade de movimentações (chamada da função PL/SQL) |

| Método | Endpoint                         | Descrição                                         |
| ------ | -------------------------------- | ------------------------------------------------- |
| GET    | `/enderecos`                     | Lista todos os endereços cadastrados              |
| GET    | `/enderecos/cliente/{idCliente}` | Lista todos os endereços de um cliente específico |
| POST   | `/enderecos/cliente/{idCliente}` | Cadastra um novo endereço vinculado a um cliente  |
| PUT    | `/enderecos/{id}`                | Atualiza um endereço existente                    |
| DELETE | `/enderecos/{id}`                | Exclui um endereço                                |

### Relatório Geral da XPTO
Endpoint responsável por retornar os dados completos dos clientes com suas contas, movimentações e endereços

**Requisição**
GET/clientes/relatorio-geral


**Exemplo de Resposta em formato JSON**
```json
[
  {
    "id": 2,
    "nome": "Empresa XPTO",
    "tipo": "PJ",
    "telefone": "81999999995",
    "cnpj": "12345678000199",
    "cpf": null,
    "dataCadastro": "2025-12-04",
    "contas": [
      {
        "id": 2,
        "banco": "Nubank",
        "agencia": "0001",
        "numeroConta": "12345-6",
        "ativa": "S",
        "movimentacoes": [
          {
            "id": 1,
            "tipo": "E",
            "valor": 1500.0,
            "dataMovimentacao": "2025-12-04"
          },
          {
            "id": 2,
            "tipo": "S",
            "valor": 250.0,
            "dataMovimentacao": "2025-12-04"
          }
        ]
      }
    ],
    "enderecos": [
      {
        "id": 1,
        "rua": "Av. Getúlio Vargas",
        "numero": "123",
        "cidade": "Olinda",
        "estado": "PE",
        "cep": "53000-000"
      }
    ]
  }
]
