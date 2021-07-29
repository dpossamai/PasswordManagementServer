# PasswordManagementServer


Passos para subir a aplicação:

1 - executar docker-compose up -d na pasta raiz onde está o docker-compose.yml. Esse comando irá baixar, buildar e subir os containers do postgresql e o pgadmin

Para buildar o projeto:

=> mvn clean install

Para executar o servidor:

=> java -jar PasswordManagement-1.0-SNAPSHOT.jar
