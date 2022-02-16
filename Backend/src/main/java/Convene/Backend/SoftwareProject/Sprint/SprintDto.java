package Convene.Backend.SoftwareProject.Sprint;

import Convene.Backend.SoftwareProject.SoftwareProject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

public class SprintDto {

    @AllArgsConstructor
    @Data
    public static class CreateSprintRequest {
        private String name;
        private long softwareProjectId;
        private Date startDate;
        private Date endDate;
    }
}
