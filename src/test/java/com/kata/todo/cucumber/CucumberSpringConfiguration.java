package com.kata.todo.cucumber;

import com.kata.todo.TestcontainersConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
}
