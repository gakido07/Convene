package Convene.Backend.SoftwareProject.Issue.SubIssue;

import Convene.Backend.SoftwareProject.Issue.Issue;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name="sub_issue")
public class SubIssue {
    @Id
    @SequenceGenerator(
            name = "sub_issue_sequence",
            sequenceName = "sub_issue_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_issue_sequence"
    )
    private long id;
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "issue", referencedColumnName = "id")
    @NotNull
    private Issue issue;


    public SubIssue(String name, Issue issue) {
        this.name = name;
        this.issue = issue;
    }
}
