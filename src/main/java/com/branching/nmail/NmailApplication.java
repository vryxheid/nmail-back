package com.branching.nmail;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Nmail backend MicroService",
        version = "1.0.3",
        description = "APIs Swagger Microservicio nameMS",
        license = @License(name = "Apache 2.0", url = ""),
        contact = @Contact(url = "S", name = "MS-nameMS", email = "")),
        security = {@SecurityRequirement(name = "")},
        servers = {
                @Server(description = "local environment", url = "http://localhost:8080/"),
                @Server(description = "dev environment url exposed by Apigateway", url = ""),
                @Server(description = "qa environment url exposed by  Apigateway", url = "https://"),
                @Server(description = "prod environment url exposed by  Apigateway", url = "https:/")
        }
)
@SpringBootApplication
public class NmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmailApplication.class, args);

    }

}
