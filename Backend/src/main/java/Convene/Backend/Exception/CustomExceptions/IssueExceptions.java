package Convene.Backend.Exception.CustomExceptions;

public class IssueExceptions {
    public static class IssueNotFoundException extends RuntimeException {
        public IssueNotFoundException() {
            super("Issue Not Found");
        }
    }
}
