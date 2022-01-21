package Convene.Backend.SoftwareProject;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SoftwareProjectDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private ProjectType type;

    public SoftwareProjectDto(SoftwareProject softwareProject) {
        this.id = softwareProject.getId();
        this.name = softwareProject.getName();
        this.type = softwareProject.getProjectType();
    }

    @Data
    public static class CreateSoftwareProjectRequest {
        @NotNull
        private String name;
        @NotNull
        private ProjectType type;
        @NotNull
        private String description;
    }
}
