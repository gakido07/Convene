package Convene.Backend.SoftwareProject;

import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRoleDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SoftwareProjectDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Date initiationDate;
    private Set<SoftwareProjectRoleDto> roles;
    @NotNull
    private ProjectType type;

    public SoftwareProjectDto(SoftwareProject softwareProject) {
        this.id = softwareProject.getId();
        this.name = softwareProject.getName();
        this.type = softwareProject.getProjectType();
        this.roles = softwareProject.getRoles().stream().map(SoftwareProjectRoleDto::new).collect(Collectors.toSet());
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
        private Long creatorId;
    }

}
