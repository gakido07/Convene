package Convene.Backend.SoftwareProject.SoftwareProjectRole;
import Convene.Backend.AppUser.AppUser;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SoftwareProjectRoleDto {
    private long id;
    private String role;
    private Set<String> emails;

    public SoftwareProjectRoleDto(SoftwareProjectRole role) {
        this.id = role.getId();
        this.role = role.getRole();
        this.emails = role.getTeamMembers().stream().map(AppUser::getEmail).collect(Collectors.toSet());
    }
}
