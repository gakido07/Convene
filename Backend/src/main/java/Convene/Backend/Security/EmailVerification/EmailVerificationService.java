package Convene.Backend.Security.EmailVerification;

import Convene.Backend.Email.JavaMailSenderConfig;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    private final String EMAIL_VALIDATION_MESSAGE = "Here is your email validation code: ";
    private final String SUBJECT = "Convene Email Validation";

    @Autowired
    private JavaMailSenderConfig mailSender;

    @Autowired
    private EmailVerificationRepository validationRepository;

    public EmailVerification loadVerificationRecord(String email) throws Exception {
        return validationRepository.findByEmail(email)
                .orElseThrow(() -> new AuthExceptions.EmailValidationRecordNotFoundException());
    }

    public void saveVerificationRequest(String email){
        EmailVerification emailVerification = new EmailVerification(email);
        validationRepository.save(emailVerification);
    }


    public EmailVerification verifyCode(EmailVerificationDto.CodeValidationRequest request) throws Exception {
        EmailVerification emailVerification = loadVerificationRecord(request.getEmail());
        System.out.println(emailVerification.getEmail());
        if(!request.getVerificationCode().equals(emailVerification.getValidationCode())){
            throw new AuthExceptions.InvalidVerificationCodeException();
        }

        emailVerification.setVerified(true);
        validationRepository.save(emailVerification);

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
        validationRepository.delete(emailVerification);
    }


}
