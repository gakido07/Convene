package Convene.Backend.Email.EmailVerification;

import Convene.Backend.AppUser.AppUserRepository;
import Convene.Backend.Email.JavaMailSenderConfig;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import com.sun.mail.smtp.SMTPSendFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final String EMAIL_VALIDATION_MESSAGE = "Here is your email validation code: ";
    private final String SUBJECT = "Convene Email Validation";

    private final JavaMailSenderConfig mailSender;

    private final EmailVerificationRepository verificationRepository;

    private final AppUserRepository appUserRepository;

    @Autowired
    public EmailVerificationServiceImpl(JavaMailSenderConfig mailSender, EmailVerificationRepository repository, AppUserRepository appUserRepository) {
        this.mailSender = mailSender;
        this.verificationRepository = repository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void saveEmailVerification(EmailVerification emailVerification) {
        verificationRepository.save(emailVerification);
    }

    @Override
    public void deleteEmailVerification(String email) {
        verificationRepository.deleteEmailVerificationByEmail(email);
    }

    @Override
    public EmailVerification findEmailVerificationRecord(String email) {
        return verificationRepository.findByEmail(email).orElseThrow(AuthExceptions.EmailValidationRecordNotFoundException::new);
    }

    @Override
    public EmailVerification verifyCode(EmailVerificationDto.CodeValidationRequest request) {
        EmailVerification emailVerification = findEmailVerificationRecord(request.getEmail());
        if(!(request.getVerificationCode() == emailVerification.getValidationCode())){
            throw new AuthExceptions.InvalidVerificationCodeException();
        }
        emailVerification.setVerified(true);
        verificationRepository.save(emailVerification);
        return emailVerification;
    }

    @Override
    public void sendVerificationEmail(String email) throws Exception {
        if(!verificationRepository.findByEmail(email).isPresent()){
            EmailVerification emailVerification = new EmailVerification(email);
            String message = EMAIL_VALIDATION_MESSAGE + Integer.toString(emailVerification.getValidationCode());
            mailSender.sendSimpleMessage(
                    email,
                    SUBJECT,
                    message
            );
        };
    }

    @Override
    public String verifyEmail(EmailVerificationDto.EmailVerificationRequest verificationRequest) throws Exception {
        String message = "";
        boolean userExists = appUserRepository.findAppUserByEmail(verificationRequest.getEmail()).isPresent();
        if (userExists){
            throw new AuthExceptions.UserExistsException();
        }
        EmailVerification emailVerification = new EmailVerification(verificationRequest.getEmail());
        try {
            sendVerificationEmail(verificationRequest.getEmail());
            message = "Verification code sent";
            saveEmailVerification(emailVerification);
        } catch (SMTPSendFailedException failedException) {
            failedException.printStackTrace();
            verificationRepository.delete(emailVerification);
            log.error("Email sending failed for verification request", verificationRequest);
            return message = "Error while sending email, try again";
        }

        return message;
    }
}
