package Convene.Backend.Exception.CustomExceptions;

public class AuthExceptions {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String USER_EXISTS_MESSAGE = "User already Exists";
    private static final String EMAIL_VALIDATION_MESSAGE = "Validation record not found";
    private static final String INVALID_VERIFICATION_CODE = "Wrong verification code";

    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException() {
            super(USER_NOT_FOUND_MESSAGE);
        }
    }

    public static class UserExistsException extends RuntimeException {
        public UserExistsException() {
            super(USER_EXISTS_MESSAGE);
        }
    }

    public static class EmailValidationRecordNotFoundException extends RuntimeException {
        public EmailValidationRecordNotFoundException() {
            super(EMAIL_VALIDATION_MESSAGE);
        }
    }

    public static class InvalidVerificationCodeException extends RuntimeException {
        public InvalidVerificationCodeException(){
            super(INVALID_VERIFICATION_CODE);
        }
    }

}
