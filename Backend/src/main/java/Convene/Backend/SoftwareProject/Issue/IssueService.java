package Convene.Backend.SoftwareProject.Issue;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IssueService implements  IssueServiceImpl{

    private final IssueRepository issueRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

}
