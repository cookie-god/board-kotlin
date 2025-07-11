package cookie.backend.board.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components().addSecuritySchemes(
                "bearerAuth", SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                )
            )
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .info(apiInfo())
    }

    private fun apiInfo(): Info {
        return Info()
            .title("Board API")
            .description("Board API")
            .version("v1")
    }
}