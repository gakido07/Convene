package Convene.Backend.Exception.CustomExceptions;

public class AppUserExceptions {

    public static class PasswordChangeException extends RuntimeException {
        public PasswordChangeException() {
            super("Passwords don't match");
        }
    }
}
