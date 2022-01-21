package Convene.Backend.SoftwareProject.SoftwareProjectRole;
import Convene.Backend.SoftwareProject.SoftwareProject;
import Convene.Backend.AppUser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "software_project_role")
@Getter
@NoArgsConstructor
public class SoftwareProjectRole {

    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private SoftwareProject softwareProject;
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> teamMembers;
    private String role;

    public SoftwareProjectRole(String role, SoftwareProject softwareProject) {
        this.role = role;
        this.softwareProject = softwareProject;
    }

    public SoftwareProjectRole withAppUsers(Set<AppUser> appUsers) {
        if(this.teamMembers == null) {
            this.teamMembers = new HashSet<>();
        }
        appUsers.forEach(appUser -> {
            this.teamMembers.add(appUser);
        });

        return this;
    }
}
