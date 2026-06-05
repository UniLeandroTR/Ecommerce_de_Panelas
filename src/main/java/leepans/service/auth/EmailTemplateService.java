package leepans.service.auth;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailTemplateService {
    
    private static final String TEMPLATE_PATH = "templates/email-recuperacao-senha.html";
    private static final String TOKEN_PLACEHOLDER = "{token}";
    private String cachedTemplate;

    public String renderPasswordRecoveryTemplate(String token) {
        try {
            String templateContent = getCachedTemplate();
            return templateContent.replace(TOKEN_PLACEHOLDER, token);
        } catch (Exception e) {
            Log.error("Erro ao renderizar template de email", e);
            throw new RuntimeException("Erro ao renderizar template de email", e);
        }
    }

    private String getCachedTemplate() throws IOException {
        if (cachedTemplate != null) {
            return cachedTemplate;
        }
        
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(TEMPLATE_PATH)) {
            
            if (inputStream == null) {
                throw new IOException("Template não encontrado em: " + TEMPLATE_PATH);
            }
            
            cachedTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            Log.info("Template de email carregado com sucesso");
            return cachedTemplate;
        }
    }
}
