package Convene.Backend.Exception.CustomExceptions;

public class SoftwareProjectRoleExceptions {

    public static final String SOFTWARE_PROJECT_ROLE_NOT_FOUND = "Role not found";

    public static class SoftwareProjectRoleNotFoundException extends RuntimeException {
        public SoftwareProjectRoleNotFoundException() {
            super(SOFTWARE_PROJECT_ROLE_NOT_FOUND);
        }
    }

}
