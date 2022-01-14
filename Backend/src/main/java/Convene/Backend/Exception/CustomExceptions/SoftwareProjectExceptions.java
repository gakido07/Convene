package Convene.Backend.Exception.CustomExceptions;

public class SoftwareProjectExceptions {
    public static final String SOFTWARE_PROJECT_NOT_FOUND = "Software Project Not Found";

    public static class SoftwareProjectNotFound extends RuntimeException {
        public SoftwareProjectNotFound() {
            super(SOFTWARE_PROJECT_NOT_FOUND);
        }
    }
}
