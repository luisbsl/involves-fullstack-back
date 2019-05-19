# Central de Alertas - Involves Fullstack Backend

REST API para importação e consulta de alertas dos __pontos de vendas__

## Endpoints
* /api/v1/alertas/processar 	- Importa os alertas e armazena em banco de dados mongodb
* /api/v1/alertas 				- Retorna todos os alertas importados

## Libraries
* Java 				- 8
* Spring Boot 		- 2.1.5
* Gradle 			- 5.4.1
* Docker 			- 18.09.2
* Docker Compose 	- 1.23.2

## Rodando standalone

### Build e testes

```./gradlew bootJar```

### Rodando a aplicação

```java -jar build/libs/involves-fullstack-1.0.0.jar```

Processar alertas 	- http://localhost:8080/api/v1/alertas/processar
Exibir alertas 		- http://localhost:8080/api/v1/alertas

## Rodando no Docker

### Criando imagem

```docker-compose build && docker-compose up```

### Rodando container

Processar alertas 	- http://localhost:8080/api/v1/alertas/processar
Exibir alertas 		- http://localhost:8080/api/v1/alertas