package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.enun.EmailType;
import com.music4all.Music4All.enun.StatusEmail;
import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.Email;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.EmailRepository;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.EmailServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailServiceInterface {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${emails.limit}")
    private Integer limitError;
    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    private final String fromEmail = "greencodebr@gmail.com";
    @Override
    public String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);

            for (int i = 0; i < file.length; i++) {
                mimeMessageHelper.addAttachment(
                        file[i].getOriginalFilename(),
                        new ByteArrayResource(file[i].getBytes())
                );
            }

            emailSender.send(mimeMessage);
            return "Email send";

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> void sendEmail(T entity, EmailType typeEmail) {
        Email emailSend = new Email();
        emailSend.setStatusEmail(StatusEmail.PROCESSING);
        emailSend.setSendDateEmail(LocalDateTime.now());
        emailSend.setEmailType(typeEmail);
        emailRepository.save(emailSend);

        // Usando Thymeleaf
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();

//        DateTimeFormatter dataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
//        DateTimeFormatter data = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formatDateTime = emailSend.getSendDateEmail().format(dataHora);
//        String formatDate = emailSend.getSendDateEmail().format(data);
//        String formatHour = emailSend.getSendDateEmail().format(hora);

        //System.out.println(" DataHora: " + formatDateTime + " Data : " + formatDate + " Hora : " + formatHour);

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            switch(emailSend.getEmailType()) {
                case CREATE_USER:
                    if (entity instanceof User user) {
                        createNewUserEmail(user, helper, thymeleafContext, templateModel, message, emailSend);
                    }
                    break;
                case CREATE_BAND:
                    if (entity instanceof Band band) {
                        newBandCreateEmail(band, helper, thymeleafContext, templateModel, message, emailSend);
                    }
                    break;
                case CREATE_DISC:

                    break;
                case RECOVER_PASSWORD:

                    break;
                default:

            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }



    }

    private void createNewUserEmail(User user, MimeMessageHelper helper, Context thymeleafContext,
                                    Map<String, Object> templateModel, MimeMessage message, Email emailSend) throws MessagingException {
        String titleMessage = "Obrigado por se cadastrar no Music4All";
        String body = "A equipe do Music4All gostaria de expressar nosso sincero agradecimento por se cadastrar em nossa plataforma! Estamos entusiasmados por tê-lo(a) conosco.\n" +
                "\n" +
                "                        O Music4All é mais do que uma plataforma de música; é uma comunidade vibrante onde músicos independentes como você podem compartilhar sua paixão, descobrir novas músicas e se conectar com outros artistas de todo o mundo.\n" +
                "\n" +
                "                        Estamos ansiosos para vê-lo(a) explorar todas as funcionalidades que o Music4All oferece. Seja para promover sua música, encontrar colaboradores para novos projetos ou simplesmente se conectar com outros amantes da música, estamos aqui para apoiá-lo(a) em sua jornada musical.\n" +
                "\n" +
                "                        Se surgir alguma dúvida ou se precisar de ajuda, não hesite em entrar em contato conosco. Estamos sempre disponíveis para ajudar.";
        helper.setFrom(fromEmail);
        helper.setTo(user.getEmail());
        helper.setSubject(titleMessage);

        templateModel.put("NAME", user.getName());
        templateModel.put("emailType", EmailType.CREATE_USER);

        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("emailNewUser.html", thymeleafContext);

        helper.setText(htmlBody, true);

        emailSender.send(message);

        //  ADICIONA O 3° STATUS ENVIADO O EMAIL E SALVA NO BANCO
        emailSend.setStatusEmail(StatusEmail.SENT);
        emailSend.setEmailFrom(fromEmail);
        emailSend.setEmailTo(user.getEmail());
        emailSend.setSubject(titleMessage);
        emailSend.setText(body);
        emailRepository.save(emailSend);
        System.out.println("Terceiro Status do Id "+emailSend.getEmailId() + ": " + emailSend.getStatusEmail());
    }

    private void newBandCreateEmail(Band band, MimeMessageHelper helper, Context thymeleafContext,
                                    Map<String, Object> templateModel, MimeMessage message, Email emailSend) throws MessagingException {
        String titleMessage = "Obrigado! a sua banda "+ band.getName() + " foi cadastrada com sucesso!";
        helper.setFrom(fromEmail);
        User user = userRepository.findById(band.getCreatorId()).get();
        helper.setTo(user.getEmail());
        helper.setSubject(titleMessage);

        templateModel.put("bandName", band.getName());
        templateModel.put("creatorName", user.getName());
        templateModel.put("emailType", EmailType.CREATE_BAND);

        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("emailNewBand.html", thymeleafContext);

        helper.setText(htmlBody, true);

        emailSender.send(message);

        //  ADICIONA O 3° STATUS ENVIADO O EMAIL E SALVA NO BANCO
        emailSend.setStatusEmail(StatusEmail.SENT);
        emailSend.setEmailFrom(fromEmail);
        emailSend.setEmailTo(user.getEmail());
        emailSend.setSubject(titleMessage);
        emailSend.setText(titleMessage);
        emailRepository.save(emailSend);
        System.out.println("Terceiro Status do Id "+emailSend.getEmailId() + ": " + emailSend.getStatusEmail());
    }

    public Email sendEmailApi(Email emailModel) {

        //ADICIONA O 2° STATUS PROCESSANDO O EMAIL E SALVA NO BANCO
        emailModel.setStatusEmail(StatusEmail.PROCESSING);
        emailRepository.save(emailModel);
        System.out.println("Segundo Status do Id "+emailModel.getEmailId() + ": " + emailModel.getStatusEmail());
        emailModel.setSendDateEmail(LocalDateTime.now());

        System.out.println("Primeiro Status do Id "+emailModel.getEmailId() + ": "+ emailModel.getStatusEmail());

        try{

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            DateTimeFormatter dataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter data = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formatDateTime = emailModel.getSendDateEmail().format(dataHora);
            String formatDate = emailModel.getSendDateEmail().format(data);
            String formatHour = emailModel.getSendDateEmail().format(hora);

            System.out.println(" DataHora: " + formatDateTime + " Data : " + formatDate + " Hora : " + formatHour);

            helper.setFrom(emailModel.getEmailFrom());
            helper.setTo(emailModel.getEmailTo());
            helper.setSubject(emailModel.getText());

            // Usando Thymeleaf
            Context thymeleafContext = new Context();
            Map<String, Object> templateModel = new HashMap<>();

            templateModel.put("Title", emailModel.getSubject());
            templateModel.put("Descricao", emailModel.getText());
            templateModel.put("Date", formatDate);
            templateModel.put("Hora", formatHour);
            templateModel.put("Status", emailModel.getStatusEmail());
            templateModel.put("To", emailModel.getEmailTo());
            templateModel.put("From", emailModel.getEmailFrom());

            thymeleafContext.setVariables(templateModel);
            String htmlBody = thymeleafTemplateEngine.process("emailSend.html", thymeleafContext);
            //String htmlBody = thymeleafTemplateEngine.process("teste.html", thymeleafContext);

            helper.setText(htmlBody, true);


            emailSender.send(message);

            //  ADICIONA O 3° STATUS ENVIADO O EMAIL E SALVA NO BANCO
            emailModel.setStatusEmail(StatusEmail.SENT);
            emailRepository.save(emailModel);
            System.out.println("Terceiro Status do Id "+emailModel.getEmailId() + ": " + emailModel.getStatusEmail());

        } catch (Exception e){
            Integer countError = emailModel.getCount();

            //  ADICIONA O 4° STATUS ERROR NO EMAIL E SALVA NO BANCO
            emailModel.setStatusEmail(StatusEmail.ERROR);
            emailRepository.save(emailModel);
            System.out.println("Quarto Status do Id "+emailModel.getEmailId()+ ": " + emailModel.getStatusEmail());

            emailModel.setCount(++countError);

            System.out.println("Error ao enviar email:  " + e.getMessage());
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
