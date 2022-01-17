package Convene.Backend.User;

import Convene.Backend.SoftwareProject.ProjectType;
import Convene.Backend.SoftwareProject.SoftwareProject;
import Convene.Backend.SoftwareProject.SoftwareProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AppUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Set<SoftwareProjectDto> projects;

    public AppUserDto(AppUserDtoProjection userDtoProjection) {
        this.firstName = userDtoProjection.getFirstName();
        this.lastName = userDtoProjection.getLastName();
        this.email = userDtoProjection.getEmail();
        this.projects = userDtoProjection.getProjects().stream().map(dto -> new SoftwareProjectDto(dto)).collect(Collectors.toSet());
    }

    @Data
    public static class SignUpRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String confirmPassword;
    }

    @AllArgsConstructor @Data
    public static class LogInRequest {
        private String email;
        private String password;
    }


    public interface AppUserDtoProjection {
        String getFirstName();
        String getLastName();
        String getEmail();
        Set<SoftwareProject> getProjects();
    }
}
