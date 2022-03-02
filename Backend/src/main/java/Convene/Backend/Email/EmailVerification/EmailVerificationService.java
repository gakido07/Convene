package Convene.Backend.Email.EmailVerification;

public interface EmailVerificationService {
    void saveEmailVerification(EmailVerification emailVerification);
    void deleteEmailVerification(String email);
    EmailVerification findEmailVerificationRecord(String email);
    EmailVerification verifyCode(EmailVerificationDto.CodeValidationRequest request);
    void sendVerificationEmail(String email) throws Exception;
    String verifyEmail(EmailVerificationDto.EmailVerificationRequest request) throws Exception;
}
