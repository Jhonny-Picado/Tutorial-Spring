# Guía para usar perfiles usando Dockerfile y Maven

## Hacer esto en la carpeta base del programa

Hacer esto en la carpeta base del programa

- Crear archivo Dockerfile:
```
nano Dockerfile

#Fase de creacion del .jar
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean package

#Fase de ejecucion del jar
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=default
ENTRYPOINT ["java","-jar","app.jar"]
```
- Construir la imagen
```
docker build -t tutorial-spring .
```

- Ejecutar el contenedor docker:
```
docker run -p 8080:8080 tutorial-spring
```

- Para hurgar dentro de la imagen se puede utilizar el comando:
```
docker run -ti --entrypoint /bin/sh tutorial-spring 
```

- Si hay un docker en ejecucion se puede revisar usando:
```
docker run --name tutorial-spring -ti --entrypoint /bin/sh tutorial-spring
docker exec -ti tutorial-spring /bin/sh
```

- Activar Perfil-dev del Tutorial:
```
docker run -p 8080:8585 -e SPRING_PROFILES_ACTIVE=dev tutorial-spring
```

- Activar Perfil-test de Test:
```
docker run -p 8080:9000 -e SPRING_PROFILES_ACTIVE=test tutorial-spring
```

## Configuraciones de los Perfiles
Se hace un método que configura los valores de inicialización de la bd para cada perfil usando @bean y @profile:
```
package payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    @Profile("dev")
    CommandLineRunner initDatabaseDev(EmployeeRepository employeeRepository, OrderRepository orderRepository) {

        return args -> {
            employeeRepository.save(new Employee().setFirstName("Bilbo").setLastName("Baggins").setRole("burglar"));
            employeeRepository.save(new Employee().setFirstName("Frodo").setLastName("Baggins").setRole("thief"));
            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

            orderRepository.save(new Order().setDescription("MacBook Pro").setStatus(Status.COMPLETED));
            orderRepository.save(new Order().setDescription("iPhone").setStatus(Status.IN_PROGRESS));
            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });

        };
    }

    @Bean
    @Profile("test")
    CommandLineRunner initDatabaseTest(EmployeeRepository employeeRepository, OrderRepository orderRepository) {

        return args -> {
            employeeRepository.save(new Employee().setFirstName("Jhonny").setLastName("Picado").setRole("fullstack"));
            employeeRepository.save(new Employee().setFirstName("Breiner").setLastName("Carranza").setRole("fullstack"));
            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

            orderRepository.save(new Order().setDescription("MSI PC").setStatus(Status.COMPLETED));
            orderRepository.save(new Order().setDescription("S12 Samsung").setStatus(Status.IN_PROGRESS));
            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
```

## Archivo properties de Test
src/main/resources/application-test.properties:
```
server.port = 9000
```

## Archivo properties de Dev
src/main/resources/application-dev.properties:
```
server.port = 8585
```

## Cambio realizado por un error en los tests
Se hace un cambio en el archivo src/test/java/com/example/demo/PayrollApplicationTests.java para solucionar un error:
```
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payroll.PayrollApplication;

@SpringBootTest(classes = PayrollApplication.class)
class PayrollApplicationTests {

	@Test
	void contextLoads() {
	}

}
```
