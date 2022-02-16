package Convene.Backend.SoftwareProject.Issue.CustomIssueStatus;

import Convene.Backend.SoftwareProject.SoftwareProject;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data @NoArgsConstructor
@Entity @Table(name = "custom_issue_status")
public class CustomIssueStatus {
    @Id
    @SequenceGenerator(
            name = "custom_issue_status_sequence",
            sequenceName = "custom_issue_issue_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "custom_issue_issue_sequence"
    )
    @Getter
    private long id;

    @Getter @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "software_project", nullable = false, updatable = false)
    @Getter
    private SoftwareProject softwareProject;

    @Getter @Setter
    private int index;

    public CustomIssueStatus(String name, SoftwareProject softwareProject, int index) {
        this.name = name;
        this.softwareProject = softwareProject;
        this.index = index;
    }
}
