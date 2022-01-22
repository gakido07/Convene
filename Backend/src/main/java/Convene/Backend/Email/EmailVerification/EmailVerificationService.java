package Convene.Backend.Email.EmailVerification;

import Convene.Backend.AppUser.AppUserRepository;
import Convene.Backend.AppUser.AppUserService;
import Convene.Backend.Email.JavaMailSenderConfig;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import com.sun.mail.smtp.SMTPSendFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailVerificationService {

    private final String EMAIL_VALIDATION_MESSAGE = "Here is your email validation code: ";
    private final String SUBJECT = "Convene Email Validation";

    private JavaMailSenderConfig mailSender;

    private EmailVerificationRepository verificationRepository;

    private AppUserRepository appUserRepository;

    @Autowired
    public EmailVerificationService(JavaMailSenderConfig mailSender, EmailVerificationRepository repository, AppUserRepository appUserRepository) {
        this.mailSender = mailSender;
        this.verificationRepository = repository;
        this.appUserRepository = appUserRepository;
    }

    public EmailVerification loadVerificationRecord(String email) throws Exception {
        return verificationRepository.findByEmail(email)
                .orElseThrow(() -> new AuthExceptions.EmailValidationRecordNotFoundException());
    }

    private void saveVerificationRequest(String email){
        EmailVerification emailVerification = new EmailVerification(email);
        verificationRepository.save(emailVerification);
    }

    public EmailVerification verifyCode(EmailVerificationDto.CodeValidationRequest request) throws Exception {
        EmailVerification emailVerification = loadVerificationRecord(request.getEmail());
        System.out.println(emailVerification.getEmail());
        if(!request.getVerificationCode().equals(emailVerification.getValidationCode())){
            throw new AuthExceptions.InvalidVerificationCodeException();
        }
        emailVerification.setVerified(true);
        verificationRepository.save(emailVerification);
        return emailVerification;
    }

    public void sendVerificationEmail(String email) throws Exception {
        EmailVerification emailVerification = loadVerificationRecord(email);
        String message = EMAIL_VALIDATION_MESSAGE + emailVerification.getValidationCode().toString();
        mailSender.sendSimpleMessage(
                email,
                SUBJECT,
                message
        );
    }

    public void deleteVerificationRecord(String email) throws Exception {
        EmailVerification emailVerification = loadVerificationRecord(email);
        verificationRepository.delete(emailVerification);
    }

    public String verifyEmail(EmailVerificationDto.EmailVerificationRequest verificationRequest) throws Exception {
        String message = "";
        Boolean userExists = appUserRepository.findAppUserByEmail(verificationRequest.getEmail()).isPresent();
        if (userExists){
            throw new AuthExceptions.UserExistsException();
        }
        try {
            saveVerificationRequest(verificationRequest.getEmail());
            sendVerificationEmail(verificationRequest.getEmail());
            message = "Verification code sent";
        } catch (SMTPSendFailedException failedException) {
            failedException.printStackTrace();
            log.error("Email sending failed for verification request", verificationRequest);
            EmailVerification emailVerification = verificationRepository.findByEmail(verificationRequest.getEmail()).get();
            verificationRepository.delete(emailVerification);
        }
        return message;
    }

}
