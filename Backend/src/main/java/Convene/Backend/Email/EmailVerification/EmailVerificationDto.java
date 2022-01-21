package Convene.Backend.Email.EmailVerification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;

@AllArgsConstructor
public class EmailVerificationDto {
    @Email
    private String email;
    private Boolean verified;

    @Data
    public static class EmailVerificationRequest {
        @Email
        private String email;
    }

    @Getter
    public static class CodeValidationRequest {
        @Email
        private String email;
        private Integer verificationCode;
    }
}
