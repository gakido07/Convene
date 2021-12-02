package Convene.Backend.Project;
import Convene.Backend.Models.Project;
import Convene.Backend.User.AppUser;
import javax.persistence.*;
import java.util.Set;

@Entity
public class ProjectRole {
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
    private Long projectId;
    private String role;
    @OneToMany(mappedBy = "projectRole")
    private Set<AppUser> teamMembers;
}
