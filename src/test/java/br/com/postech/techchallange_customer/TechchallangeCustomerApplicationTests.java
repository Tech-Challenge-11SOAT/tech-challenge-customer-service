package br.com.postech.techchallange_customer;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class TechchallangeCustomerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainMethodShouldStartApplication() {
		try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
			ConfigurableApplicationContext contextMock = mock(ConfigurableApplicationContext.class);

			springApplicationMock
					.when(() -> SpringApplication.run(eq(TechchallangeCustomerApplication.class), any(String[].class)))
					.thenReturn(contextMock);

			TechchallangeCustomerApplication.main(new String[] {});

			springApplicationMock.verify(
					() -> SpringApplication.run(eq(TechchallangeCustomerApplication.class), any(String[].class)));
		}
	}

	@Test
	void constructorShouldCreateInstance() {
		TechchallangeCustomerApplication application = new TechchallangeCustomerApplication();
		assertThat(application).isNotNull();
	}

}
