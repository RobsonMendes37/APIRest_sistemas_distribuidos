# Projeto Final de Sistemas Distribuídos: API de RH Hospitalar

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)

Relatório do projeto final da disciplina de Sistemas Distribuídos, ministrada pelo professor Rafael Braga. Este projeto consiste na reimplementação de um serviço remoto de gerenciamento de Recursos Humanos (RH) para um hospital, migrando de uma tecnologia legada (Java RMI) para uma API RESTful moderna e interoperável.

**Autor:** Robson Mendes

---

## 📜 Tabela de Conteúdos

1. [Sobre o Projeto](#-sobre-o-projeto)  
2. [Arquitetura da Solução](#-arquitetura-da-solução)  
3. [Arquitetura do Servidor (API REST)](#-arquitetura-do-servidor-api-rest)  
4. [Implementação dos Clientes](#-implementação-dos-clientes)  
5. [Como Executar](#-como-executar)  
6. [Rotas da API (Endpoints)](#-rotas-da-api-endpoints)  
7. [Conclusão](#-conclusão)

---

## 📝 Sobre o Projeto

O objetivo principal deste trabalho foi modernizar um serviço remoto de RH, substituindo a comunicação baseada em Java RMI por uma API RESTful. Esta nova abordagem, que utiliza o protocolo HTTP, oferece maior flexibilidade e interoperabilidade.

Para demonstrar essa capacidade, a solução inclui dois clientes desenvolvidos em linguagens diferentes da utilizada no servidor, sendo um em Python e outro em JavaScript.

---

## 🛠️ Arquitetura da Solução

O sistema foi projetado seguindo o padrão cliente-servidor, com uma clara separação de responsabilidades.

- **Servidor (Back-end)**: Desenvolvido em **Java** com o framework **Spring Boot**. Ele expõe uma API RESTful que centraliza toda a lógica de negócio e a persistência de dados, comunicando-se através de HTTP e transacionando dados em formato JSON.
- **Clientes (Front-end)**:
  - **Cliente de Terminal (Python)**: Uma aplicação de console que permite ao usuário interagir com todas as funcionalidades da API por meio de um menu de texto.
  - **Cliente Web (JavaScript)**: Uma Single-Page Application (SPA) desenvolvida com HTML, CSS e JavaScript, que roda diretamente no navegador.

---

## ⚙️ Arquitetura do Servidor (API REST)

O servidor foi estruturado com base nos princípios da arquitetura em camadas para garantir manutenibilidade, testabilidade e separação de responsabilidades.

### Camadas da Aplicação

- **`model`**: Contém as entidades centrais do sistema. A classe abstrata `Funcionario` é a base para `Enfermeiro`, `MedicoEfetivo` e `MedicoPlantonista`. A serialização/desserialização polimórfica é gerenciada pela anotação `@JsonTypeInfo`.

- **`repository`**: Abstrai o acesso aos dados com duas estratégias de persistência:
  - `FuncionarioRepositoryCsv`: Persiste os dados de `Enfermeiro` em um arquivo CSV.
  - `FuncionarioRepository`: Gerencia os dados de `Medico` (Efetivo e Plantonista) em um `Map` em memória.

- **`service`**: A classe `RHService` contém a lógica de negócio da aplicação, orquestrando as chamadas aos repositórios e aplicando regras como a verificação de matrículas duplicadas.

- **`controller`**: Expõe os endpoints da API REST através de controllers específicos por entidade, utilizando anotações como `@GetMapping`, `@PostMapping`, etc. A validação dos dados de entrada é feita com DTOs (Data Transfer Objects).

- **`config` e `exception`**:
  - **Segurança**: A API é protegida por `Spring Security`, exigindo Autenticação Básica (Basic Auth) para todas as requisições.
  - **Tratamento de Exceções**: Um `RestExceptionHandler` global captura exceções e retorna respostas padronizadas em JSON.

---

## 🚀 Implementação dos Clientes

### 1. Cliente Python (Aplicação de Terminal)

- `api_client.py`: Camada de comunicação com a API, usando `requests`.
- `app_logic.py`: Lógica de interface com menus e interação com o usuário.
- `main.py`: Ponto de entrada da aplicação, define constantes e executa o menu.

### 2. Cliente JavaScript (SPA)

- `index.html`: Interface completa com Bootstrap 5, interativa e dinâmica.
- Utiliza `fetch`, `async/await` e autenticação básica para consumir a API.

---

## ⚡ Como Executar

### Requisitos

- Java 11+ e Maven
- Python 3+ com `requests` (`pip install requests`)
- Navegador moderno

### Executar o Servidor

```bash
cd /caminho/para/servidor-api
mvn spring-boot:run
```

### Executar o Cliente Python

```bash
cd /caminho/para/cliente-python
python main.py
```

### Executar o Cliente Web

Abra o arquivo `index.html` no navegador.

---

## 🗺️ Rotas da API (Endpoints)

### Endpoints Gerais (RHController)

- `GET /api/rh/funcionarios`: Lista todos os funcionários.
- `GET /api/rh/funcionarios/{matricula}`: Consulta por matrícula.
- `DELETE /api/rh/funcionarios/{matricula}`: Demite um funcionário.

### Endpoints de Enfermeiros (EnfermeiroController)

- `POST /api/enfermeiros`: Cadastra novo enfermeiro.
- `GET /api/enfermeiros`: Lista enfermeiros.
- `PUT /api/enfermeiros/{matricula}`: Atualiza dados.
- `DELETE /api/enfermeiros/{matricula}`: Remove enfermeiro.

### Endpoints de Médicos

- `POST /api/medicos-efetivos` e `POST /api/medicos-plantonistas`: Criação.
- `GET`, `PUT`, `DELETE`: Funcionalidades equivalentes aos enfermeiros.

---

## ✅ Conclusão

O projeto atingiu seus objetivos ao migrar de uma arquitetura legada para uma solução REST moderna, modular, segura e interoperável. A implementação em múltiplas linguagens prova a flexibilidade do sistema e serve como base sólida para futuras extensões ou integração com sistemas maiores.
