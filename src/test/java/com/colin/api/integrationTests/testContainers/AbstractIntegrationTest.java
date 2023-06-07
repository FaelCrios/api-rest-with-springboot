package com.colin.api.integrationTests.testContainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

import static com.colin.api.integrationTests.testContainers.AbstractIntegrationTest.initializer.mysql;


@ContextConfiguration(initializers = AbstractIntegrationTest.initializer.class)
public class AbstractIntegrationTest {

    public static class initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.33");

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            MapPropertySource testContainers = new MapPropertySource("testContainers",

                    (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testContainers);
        }
    }

    private static Map<String, String> createConnectionConfiguration() {
        return Map.of(
                "spring.datasource.url", mysql.getJdbcUrl(),
                "spring.datasource.username", mysql.getUsername(),
                "spring.datasource.password", mysql.getPassword()

        );
    }


}
