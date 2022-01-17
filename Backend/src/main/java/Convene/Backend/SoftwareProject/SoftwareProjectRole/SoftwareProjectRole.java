package Convene.Backend.SoftwareProject.SoftwareProjectRole;
import Convene.Backend.SoftwareProject.SoftwareProject;
import Convene.Backend.User.AppUser;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "software_project_role")
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
}
