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