package leepans.service.auth;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailTemplateService {
    
    private static final String TEMPLATE_RECUPERACAO_SENHA = "templates/email-recuperacao-senha.html";
    private static final String TEMPLATE_PEDIDO_CONFIRMADO = "templates/email-pedido-confirmado.html";
    private static final String TEMPLATE_PAGAMENTO_APROVADO = "templates/email-pagamento-aprovado.html";
    
    private static final String TOKEN_PLACEHOLDER = "{token}";
    private static final String NOME_CLIENTE_PLACEHOLDER = "{nomeCliente}";
    private static final String NUMERO_PEDIDO_PLACEHOLDER = "{numeroPedido}";
    
    private String cachedTemplateRecuperacaoSenha;
    private String cachedTemplatePedidoConfirmado;
    private String cachedTemplatePagamentoAprovado;

    public String renderPasswordRecoveryTemplate(String token) {
        try {
            String templateContent = getCachedTemplate(TEMPLATE_RECUPERACAO_SENHA, "cachedTemplateRecuperacaoSenha");
            return templateContent.replace(TOKEN_PLACEHOLDER, token);
        } catch (Exception e) {
            Log.error("Erro ao renderizar template de recuperação de senha", e);
            throw new RuntimeException("Erro ao renderizar template de recuperação de senha", e);
        }
    }

    public String renderOrderConfirmedTemplate(String nomeCliente, String numeroPedido) {
        try {
            String templateContent = getCachedTemplateOrderConfirmed();
            return templateContent
                    .replace(NOME_CLIENTE_PLACEHOLDER, nomeCliente)
                    .replace(NUMERO_PEDIDO_PLACEHOLDER, numeroPedido);
        } catch (Exception e) {
            Log.error("Erro ao renderizar template de pedido confirmado", e);
            throw new RuntimeException("Erro ao renderizar template de pedido confirmado", e);
        }
    }

    public String renderPaymentApprovedTemplate(String nomeCliente, String numeroPedido) {
        try {
            String templateContent = getCachedTemplatePaymentApproved();
            return templateContent
                    .replace(NOME_CLIENTE_PLACEHOLDER, nomeCliente)
                    .replace(NUMERO_PEDIDO_PLACEHOLDER, numeroPedido);
        } catch (Exception e) {
            Log.error("Erro ao renderizar template de pagamento aprovado", e);
            throw new RuntimeException("Erro ao renderizar template de pagamento aprovado", e);
        }
    }

    private String getCachedTemplate(String templatePath, String cacheFieldName) throws IOException {
        if (cacheFieldName.equals("cachedTemplateRecuperacaoSenha") && cachedTemplateRecuperacaoSenha != null) {
            return cachedTemplateRecuperacaoSenha;
        }
        
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(templatePath)) {
            
            if (inputStream == null) {
                throw new IOException("Template não encontrado em: " + templatePath);
            }
            
            String template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            
            if (cacheFieldName.equals("cachedTemplateRecuperacaoSenha")) {
                cachedTemplateRecuperacaoSenha = template;
            }
            
            Log.info("Template de email carregado com sucesso: " + templatePath);
            return template;
        }
    }

    private String getCachedTemplateOrderConfirmed() throws IOException {
        if (cachedTemplatePedidoConfirmado != null) {
            return cachedTemplatePedidoConfirmado;
        }
        
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(TEMPLATE_PEDIDO_CONFIRMADO)) {
            
            if (inputStream == null) {
                throw new IOException("Template não encontrado em: " + TEMPLATE_PEDIDO_CONFIRMADO);
            }
            
            cachedTemplatePedidoConfirmado = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            Log.info("Template de pedido confirmado carregado com sucesso");
            return cachedTemplatePedidoConfirmado;
        }
    }

    private String getCachedTemplatePaymentApproved() throws IOException {
        if (cachedTemplatePagamentoAprovado != null) {
            return cachedTemplatePagamentoAprovado;
        }
        
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(TEMPLATE_PAGAMENTO_APROVADO)) {
            
            if (inputStream == null) {
                throw new IOException("Template não encontrado em: " + TEMPLATE_PAGAMENTO_APROVADO);
            }
            
            cachedTemplatePagamentoAprovado = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            Log.info("Template de pagamento aprovado carregado com sucesso");
            return cachedTemplatePagamentoAprovado;
        }
    }
}
