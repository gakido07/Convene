package Convene.Backend.Exception.CustomExceptions;

public class SoftwareProjectExceptions {

    public static class SoftwareProjectNotFound extends RuntimeException {
        public SoftwareProjectNotFound() {
            super("Software Project Not Found");
        }
    }

    public static class KanbanProjectSprintException extends RuntimeException {
        public KanbanProjectSprintException() {
            super("Kanban projects have a maximum of one sprint");
        }
    }
}
