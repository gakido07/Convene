package Convene.Backend.Exception.CustomExceptions;

public class SprintExceptions {
    public static class SprintNotFoundException extends RuntimeException {
        public SprintNotFoundException() {
            super("Sprint not found");
        }
    }
}
