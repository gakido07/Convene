package Convene.Backend.Exception.CustomExceptions;

public class AuthExceptions {
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String USER_EXISTS_MESSAGE = "User already Exists";

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


}
