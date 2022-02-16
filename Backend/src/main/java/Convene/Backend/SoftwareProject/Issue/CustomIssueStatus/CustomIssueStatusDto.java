package Convene.Backend.SoftwareProject.Issue.CustomIssueStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class CustomIssueStatusDto {

    @Getter
    @AllArgsConstructor
    public static class CreateCustomIssueStatusRequest {
        @NotNull
        String name;

        @NotNull
        long softwareProjectId;
    }
}
