package Convene.Backend.Exception.CustomExceptions;

public class AuthExceptions {
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException() {
            super("User not found");
        }
    }

    public static class UserExistsException extends RuntimeException {
        public UserExistsException() {
            super("User already Exists");
        }
    }

    public static class EmailValidationRecordNotFoundException extends RuntimeException {
        public EmailValidationRecordNotFoundException() {
            super("Validation record not found");
        }
    }

    public static class InvalidVerificationCodeException extends RuntimeException {
        public InvalidVerificationCodeException(){
            super("Wrong verification code");
        }
    }

    public static class AccountLockedException extends RuntimeException {
        public AccountLockedException() { super("Account locked"); }
    }

    public static class InvalidAuthCredentialsException extends RuntimeException {
        public InvalidAuthCredentialsException() {
            super("Wrong email or password");
        }
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException() { super("You are unauthorized to access this route"); }
    }
}
