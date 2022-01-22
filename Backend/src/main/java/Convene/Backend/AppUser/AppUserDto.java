package Convene.Backend.AppUser;

import Convene.Backend.SoftwareProject.SoftwareProject;
import Convene.Backend.SoftwareProject.SoftwareProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AppUserDto {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private Set<SoftwareProjectDto> projects;

    public AppUserDto(AppUserDtoProjection userDtoProjection) {
        this.firstName = userDtoProjection.getFirstName();
        this.lastName = userDtoProjection.getLastName();
        this.email = userDtoProjection.getEmail();
        this.projects = userDtoProjection.getProjects().stream().map(SoftwareProjectDto::new).collect(Collectors.toSet());
    }

    public AppUserDto(AppUser appUser) {
        this.firstName = appUser.getFirstName();
        this.lastName = appUser.getLastName();
        this.email = appUser.getEmail();
        this.projects = appUser.getProjects().stream().map(SoftwareProjectDto::new).collect(Collectors.toSet());
    }

    @Data
    public static class SignUpRequest {
        private String firstName;
        private String lastName;
        @Email
        private String email;
        private String password;
        private String confirmPassword;
    }

    @AllArgsConstructor @Data
    public static class LogInRequest {
        @Email
        private String email;
        @NotNull
        private String password;
    }

    public interface AppUserDtoProjection {
        String getFirstName();
        String getLastName();
        String getEmail();
        Set<SoftwareProject> getProjects();
    }
}
