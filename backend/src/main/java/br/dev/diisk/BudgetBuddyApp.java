package br.dev.diisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BudgetBuddyApp {

    public static void main(String[] args) {
        SpringApplication.run(BudgetBuddyApp.class, args);
    }

}
