package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUser;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRoleDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SoftwareProjectDto {
    @NotNull
    private long id;
    @NotNull
    private String name;
    @NotNull
    private AppUser owner;
    @NotNull
    private ProjectType type;

    public SoftwareProjectDto(SoftwareProject softwareProject) {
        this.id = softwareProject.getId();
        this.name = softwareProject.getName();
        this.type = softwareProject.getProjectType();
        this.owner = softwareProject.getOwner();
    }

    @Data
    public static class CreateSoftwareProjectRequest {
        @NotNull
        private String name;
        @NotNull
        private ProjectType type;
        @NotNull
        private String description;
        @NotNull
        private long creatorId;
    }
}
