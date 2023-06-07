package com.colin.api.integrationTests.testContainers.swagger;


import static io.restassured.RestAssured.given;

import com.colin.api.configs.TestConfigs;
import com.colin.api.integrationTests.testContainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {


	@Test
	void showDisplaySwaggerUiPage() {
		var content = given()
				.basePath("swagger-ui/index.html")
				.port(TestConfigs.SERVER_PORT)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		Assertions.assertTrue(content.contains("Swagger UI"));
	}

}
