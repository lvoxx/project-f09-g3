package com.cp2396f09_sem4_grp3.onmart_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "OpenApi specification - OnMart", version = "1.0.0", description = "OpenApi decumentation for OnMart software made by Spring Boot 3", license = @License(name = "Apache License", url = "http://www.apache.org/licenses/LICENSE-2.0"), contact = @Contact(name = "Lvoxx", email = "lvoxxartist@gmail.com", url = "https://github.com/lvoxx")), servers = {
        @Server(url = "http://localhost:9090", description = "Default Local Server URL"),
        @Server(url = "http://localhost:9090/api/v1", description = "Product Local Server URL"), })
public class SwaggerConfig {
}
