/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pt.passenger_transportation;

import com.pt.passenger_transportation.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author 8314
 */

@SpringBootApplication
@EntityScan(basePackages = {"com.pt.passenger_transportation"})
@EnableJpaRepositories(basePackages = {"com.pt.passenger_transportation"})
@ComponentScan(basePackages = {"com.pt.passenger_transportation"})
public class Passenger_transportation {
    public static void main(String[] args) {
        SpringApplication.run(Passenger_transportation.class, args);
    }
}
