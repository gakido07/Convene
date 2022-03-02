package Convene.Backend.Exception.CustomExceptions;

public class SoftwareProjectRoleExceptions {

    public static class SoftwareProjectRoleNotFoundException extends RuntimeException {
        public SoftwareProjectRoleNotFoundException() {
            super("Role not found");
        }
    }

}
