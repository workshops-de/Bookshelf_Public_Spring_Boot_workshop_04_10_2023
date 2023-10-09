package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class SwaggerConfiguration {

    @Bean
    public OpenAPI api(BookshelfProperties properties) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(properties.getTitle())
                                .version(properties.getVersion())
                                .description("This bookshelf has a capacity of %d books.".formatted(properties.getCapacity()))
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")
                                )
                );
    }
}
