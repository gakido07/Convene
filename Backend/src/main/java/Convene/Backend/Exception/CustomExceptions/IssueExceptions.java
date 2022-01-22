package Convene.Backend.Exception.CustomExceptions;

public class IssueExceptions {

    public static final String ISSUE_NOT_FOUND = "Issue Not Found";

    public static class IssueNotFoundException extends RuntimeException {
        public IssueNotFoundException() {
            super(ISSUE_NOT_FOUND);
        }
    }
}
