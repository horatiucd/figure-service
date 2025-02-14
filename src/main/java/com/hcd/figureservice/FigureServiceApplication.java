package com.hcd.figureservice;

import com.hcd.figureservice.controller.FigureService;
import com.hcd.figureservice.domain.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FigureServiceApplication {

    private final Logger log = LoggerFactory.getLogger(FigureServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FigureServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(FigureService figureService) {
        return args -> {
            log.info("Loading data...");
            figureService.create(new Figure("Lloyd"));
            figureService.create(new Figure("Jay"));
            figureService.create(new Figure("Kay"));
            figureService.create(new Figure("Cole"));
            figureService.create(new Figure("Zane"));
            figureService.create(new Figure("Nya"));

            log.info("Available figures:");
            figureService.findAll()
                    .forEach(figure -> log.info("{}", figure));
        };
    }

}
