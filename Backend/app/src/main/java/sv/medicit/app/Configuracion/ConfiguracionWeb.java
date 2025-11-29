package sv.medicit.app.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.MediaType;
import sv.medicit.app.Utilidades.ControlAccesoInterceptor;

/**
 * Configuración de Web MVC para soportar JSON correctamente
 * y registrar interceptores de control de acceso.
 */
@Configuration
public class ConfiguracionWeb implements WebMvcConfigurer {
    
    @Autowired
    private ControlAccesoInterceptor controlAccesoInterceptor;
    
    /**
     * Configura la negociación de contenido para soportar JSON.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorPathExtension(false)
            .favorParameter(false)
            .ignoreAcceptHeader(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON);
    }
    
    /**
     * Registra el interceptor de control de acceso para validar sesiones.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controlAccesoInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns(
                "/api/autenticacion/login",
                "/api/autenticacion/logout",
                "/api/autenticacion/recuperar-contrasenia",
                "/api/autenticacion/preguntas-seguridad/**"
            );
    }
}
