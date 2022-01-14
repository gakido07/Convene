package Convene.Backend.SoftwareProject;

import Convene.Backend.User.AppUser;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class SoftwareProjectDto {

    @Data
    public static class CreateSoftwareProjectRequest {
        @NotNull
        private String name;
        @NotNull
        private ProjectType type;
        @NotNull
        private String description;
        @NotNull
        private AppUser user;
    }
}
