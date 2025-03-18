package com.kata.todo;

import org.springframework.boot.SpringApplication;

public class TestTodoListBackApplication {

	public static void main(String[] args) {
		SpringApplication.from(TodoListBackApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
