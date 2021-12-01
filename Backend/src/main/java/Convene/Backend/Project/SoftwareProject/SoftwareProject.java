package Convene.Backend.Project.SoftwareProject;

import Convene.Backend.Models.Project;
import Convene.Backend.Project.ProjectType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
public class SoftwareProject extends Project{
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private Long teamId;
    private String name;
    private Date initiationDate;
    private String description;
    private Long leadId;
    private ProjectType projectType;


}
