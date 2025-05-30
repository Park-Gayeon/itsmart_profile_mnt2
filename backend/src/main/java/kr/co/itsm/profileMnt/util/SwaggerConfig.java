package kr.co.itsm.profileMnt.util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My API Documentation")
                        .version("1.0.0")
                        .description("Spring Boot API Documentation")
                        .contact(new Contact()
                                .name("gypark")
                                .email("gypark@itsmart.co.kr")))
                .servers(List.of(
                        new Server().url("http://localhost:9999").description("Development Server")
                ));
    }
}
