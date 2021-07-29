# PasswordManagementServer


Passos para subir a aplicação:

1 - executar docker-compose up -d na pasta raiz onde está o docker-compose.yml. Esse comando irá baixar, buildar e subir os containers do postgresql e o pgadmin

2 - criar o banco de dados: 'password_management' no pgadmin.

3 - restaurar dump do banco de dados de teste com o nome de: 'dump-db-postgres' 

Para buildar o projeto:

=> mvn clean install

Para executar o servidor:

=> java -jar target/PasswordManagement-1.0-SNAPSHOT.jar


- O servidor possui URLs restritas para o usuário GERENTE. 
- O método de autenticação utilizado é o JWT.


Para executar a interface, ver o README do projeto https://github.com/dpossamai/PasswordManagementUI/blob/main/README.md
