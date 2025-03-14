/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pt.passenger_transportation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@Data
@PropertySource("application.properties")

public class BotConfig {
    @Value("${telegram.bot.username}")
    String botName;
    @Value("${telegram.bot.token}")
    String token;
    @Value("${spring.datasource.url}")
    String dbUrl;
    @Value("${spring.datasource.username}")
    String dbUsername;
    @Value("${spring.datasource.password}")
    String dbPassword;

    String getBotName() {
        return botName;
    }

    String getToken() {
        return token;
    }

    String getUrl() {
        return dbUrl;
    }
    
    String getUsername() {
        return dbUsername;
    }
    
    String getPassword() {
        return dbPassword;
    }
}
