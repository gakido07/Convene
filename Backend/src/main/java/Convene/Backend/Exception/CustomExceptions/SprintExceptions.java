package Convene.Backend.Exception.CustomExceptions;

public class SprintExceptions {
    public static final String SPRINT_NOT_FOUND = "Sprint not found";

    public static class SprintNotFoundException extends RuntimeException {
        public SprintNotFoundException() {
            super(SPRINT_NOT_FOUND);
        }
    }
}
