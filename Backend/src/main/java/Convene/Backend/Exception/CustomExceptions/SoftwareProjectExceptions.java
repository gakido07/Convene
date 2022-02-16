package Convene.Backend.Exception.CustomExceptions;

public class SoftwareProjectExceptions {

    public static final String SOFTWARE_PROJECT_NOT_FOUND = "Software Project Not Found";
    public static final String KANBAN_SPRINT_EXCEPTION_MESSAGE = "Kanban projects have a maximum of one sprint";

    public static class SoftwareProjectNotFound extends RuntimeException {
        public SoftwareProjectNotFound() {
            super(SOFTWARE_PROJECT_NOT_FOUND);
        }
    }

    public static class KanbanProjectSprintException extends RuntimeException {
        public KanbanProjectSprintException() {
            super(KANBAN_SPRINT_EXCEPTION_MESSAGE);
        }
    }
}
