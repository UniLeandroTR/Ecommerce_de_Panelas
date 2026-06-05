package leepans.service.auth;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailService {
    
    @Inject
    Mailer mailer;

    @Inject
    CacheService cacheService;

    @Inject
    EmailTemplateService emailTemplateService;

    public String sendEmail(String login){
        String token = cacheService.getTokenSenha(login);
        String destinatario = "leandrotavares@unitins.br";
        String assunto = "Recuperação de senha - LeePans";
        String htmlContent = emailTemplateService.renderPasswordRecoveryTemplate(token);

        try{
            mailer.send(Mail.withHtml(destinatario, assunto, htmlContent));
            return "email enviado com sucesso";
        }catch(Exception e){
            return "falha ao enviar email: " + e.getMessage();
        }
    }
}
