package com.karka_deh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Karka Deh API", version = "v1", description = "Karka Deh is an articles site, allowing students to post and get answers from others", contact = @Contact(name = "Abdullah Al-Banna", email = "abdu.albanna@proton.me"), license = @License(name = "GPL")), servers = @Server(url = "http://localhost:8080", description = "Local server"))
@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}
