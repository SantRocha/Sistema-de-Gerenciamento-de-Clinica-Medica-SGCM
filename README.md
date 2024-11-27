# Sistema de Gerenciamento de Clínica Médica (SGCM)

## Atenção

Este projeto foi desenvolvido durante o curso de **Programação Full Stack Avançada** da **WebAcademy** e tem como objetivo ser um exercício acadêmico para o aprendizado de tecnologias e boas práticas de desenvolvimento web e é destinado exclusivamente para fins educacionais.

**Importante**: Este projeto **não deve ser utilizado fora do contexto do curso** devido a restrições de **direitos autorais**. O código e os recursos contidos neste repositório são propriedade da WebAcademy e/ou dos instrutores do curso, e seu uso em ambientes de produção ou para fins pessoais está **estritamente proibido**.

Portanto, não há possibilidade de utilização do projeto fora do escopo educacional, e qualquer tentativa de distribuição ou adaptação para uso fora deste contexto pode infringir direitos autorais e outras restrições legais.


## Descrição

O SGCM é um sistema de gerenciamento de clínicas médicas, desenvolvido para atender às necessidades de gerenciamento de agendamentos, cadastro de profissionais de saúde, pacientes e outros recursos essenciais para o funcionamento de uma clínica. A aplicação foi construída utilizando uma arquitetura de front-end e back-end, proporcionando uma experiência eficiente e integrada para o usuário final. O sistema é composto por duas partes principais: o **back-end** e o **front-end**, que se comunicam via API RESTful.


### Funcionalidades Principais

- **Cadastro e gerenciamento de profissionais**: Permite o cadastro de médicos, enfermeiros e outros profissionais, com informações sobre especialidades, horários de atendimento e dados pessoais.
- **Agendamento de consultas**: Interface para agendar consultas, com validação de campos obrigatórios e verificação de datas válidas. Permite ao usuário visualizar e editar agendamentos existentes.
- **Listagem e busca de registros**: O sistema inclui funcionalidades de busca, ordenação e paginação de registros, melhorando a navegação e a organização das informações.
- **Validação de formulários**: Validação reativa no front-end para garantir a integridade dos dados inseridos pelo usuário.
- **Estilização com Bootstrap**: Interfaces responsivas e amigáveis, utilizando o framework Bootstrap para garantir uma experiência agradável em dispositivos de diferentes tamanhos.

## Tecnologias Utilizadas

### Back-end

- **Spring Boot**: Framework Java utilizado para desenvolvimento do back-end, com a responsabilidade de criar uma API RESTful que se comunica com o front-end e o banco de dados.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional utilizado para armazenar informações sobre agendamentos, profissionais, pacientes, e outros dados necessários para o funcionamento do sistema.
- **Maven**: Ferramenta de automação de builds para gerenciar dependências e realizar o build do projeto Java.

### Front-end

- **Angular**: Framework para desenvolvimento de aplicações front-end. Responsável pela construção da interface de usuário, comunicação com a API e manipulação de formulários reativos.
- **TypeScript**: Linguagem baseada em JavaScript que fornece tipagem estática opcional, garantindo maior robustez e qualidade no desenvolvimento da aplicação.
- **Bootstrap**: Framework CSS utilizado para facilitar a criação de interfaces responsivas e estilizadas de forma consistente.
- **SASS**: Pré-processador CSS utilizado para criar folhas de estilo mais poderosas e fáceis de manter.

### Ferramentas e Outras Tecnologias

- **Node.js**: Ambiente de execução JavaScript necessário para o funcionamento do Angular.
- **Git**: Sistema de controle de versão utilizado para versionamento do código e colaboração entre desenvolvedores.
- **Postman**: Ferramenta utilizada para testar as APIs desenvolvidas, realizando requisições HTTP e analisando as respostas.
- **JDK 17**: Kit de desenvolvimento Java utilizado para executar o back-end desenvolvido em Spring Boot.
- **Maven**: Utilizado para automatizar o build do back-end e gerenciar dependências no projeto Java.
- **MySQL Workbench**: Ferramenta para interação com o banco de dados MySQL, facilitando a administração e execução de comandos SQL.

## Estrutura do Projeto

O projeto é dividido em duas pastas principais:

1. **Back-end (sgcmapi)**: Contém o código do servidor, construído utilizando o Spring Boot, que implementa as APIs necessárias para interagir com o banco de dados e o front-end.
2. **Front-end (sgcmapp)**: Contém o código do cliente, construído com Angular. É responsável pela interface de usuário, validação de formulários e consumo das APIs fornecidas pelo back-end.

### Fluxo de Dados

1. O usuário interage com a interface web (front-end), realizando ações como agendar consultas ou cadastrar profissionais.
2. O front-end faz requisições HTTP para o back-end, que executa a lógica de negócios e interage com o banco de dados.
3. O back-end retorna os dados para o front-end, que exibe as informações de forma estruturada e intuitiva para o usuário.

## Funcionalidades Implementadas

- **Formulários Reativos**: Utilização de formulários dinâmicos e validações em tempo real no front-end com Angular.
- **Paginação e Ordenação**: Funcionalidades implementadas nas tabelas de listagem para melhorar a navegação e a visualização de grandes volumes de dados.
- **Busca**: Sistema de busca que permite encontrar registros rapidamente com base em critérios definidos.
- **Responsividade**: O sistema é totalmente responsivo, adaptando-se a diferentes tamanhos de tela utilizando Bootstrap.

## Instalação e Execução

### Back-end

Para rodar o back-end, siga os seguintes passos:

1. **Instalar o JDK 17 e o Maven**.
2. Navegue até o diretório `sgcmapi` e execute os comandos abaixo para iniciar o projeto:

   ```bash
   mvn install
   mvn spring-boot:run
3. O servidor estará disponível em https://localhost:9000/.

### Front-end

Para rodar o front-end, siga os seguintes passos:

1. Instalar o Node.js e o Angular CLI.
2. Navegue até o diretório sgcmapp e execute:

   ```bash
   npm install
   ng serve
3. A aplicação estará disponível em http://localhost:4200/.

## Considerações FinaisConsiderações Finais

O SGCM é uma aplicação robusta e extensível, projetada para facilitar o gerenciamento de clínicas médicas, proporcionando uma interface amigável e moderna, com funcionalidades essenciais como cadastro de profissionais, agendamento de consultas e validação de dados. A arquitetura baseada em Spring Boot e Angular garante uma solução escalável e eficiente.

## Licença

Este projeto está protegido por **direitos autorais** e **não pode ser utilizado, modificado ou distribuído** sem a permissão explícita dos detentores dos direitos autorais (WebAcademy e/ou instrutores do curso). O código foi desenvolvido exclusivamente para fins educacionais e seu uso está restrito ao contexto do curso.
