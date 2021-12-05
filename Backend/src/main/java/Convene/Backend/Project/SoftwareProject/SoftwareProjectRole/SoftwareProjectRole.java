package Convene.Backend.Project.SoftwareProject.SoftwareProjectRole;
import Convene.Backend.Project.SoftwareProject.SoftwareProject;
import Convene.Backend.User.AppUser;
import javax.persistence.*;
import java.util.Set;

@Entity
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
    @JoinColumn(
            name = "software_project_id",
            nullable = false
    )
    private SoftwareProject softwareProject;
    private String role;
    @OneToMany(mappedBy = "softwareProjectRole")
    private Set<AppUser> teamMembers;
}
