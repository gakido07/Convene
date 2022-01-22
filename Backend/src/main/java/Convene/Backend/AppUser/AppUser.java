package Convene.Backend.AppUser;

import Convene.Backend.SoftwareProject.Issue.Issue;
import Convene.Backend.SoftwareProject.SoftwareProject;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "app_user_sequence",
            sequenceName = "app_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_sequence"
    )
    @Getter
    private Long id;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "user should have first name")
    private String firstName;

    @NotNull(message = "user should have last name")
    private String lastName;

    @NotNull(message = "password can't be blank")
    private String password;

    private boolean accountLocked;

    @Getter @ManyToMany(mappedBy = "teamMembers", fetch = FetchType.EAGER)
    private Set<SoftwareProject> projects;

    @OneToMany(mappedBy = "assignee", fetch = FetchType.EAGER)
    private Set<Issue> issues;

    @ManyToMany(mappedBy = "teamMembers", fetch = FetchType.EAGER)
    private Set<SoftwareProjectRole> projectRoles;

    public AppUser(AppUserDto.SignUpRequest signUpRequest) {
        this.email = signUpRequest.getEmail();
        this.firstName = signUpRequest.getFirstName();
        this.lastName = signUpRequest.getLastName();
        this.password = signUpRequest.getPassword();
        this.accountLocked = false;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountLocked(boolean accountLocked){
        this.accountLocked = accountLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(projectRoles.size() == 0) {
            return null;
        }
        final String ADMIN = "ADMIN";
        final String MEMBER = "MEMBER";
        List<Long> adminPrivileges = new ArrayList<>();
        List<Long> memberPrivileges = new ArrayList<>();
         projectRoles.forEach(role -> {
             if(role.getRole().equals("ADMIN")) {
                 adminPrivileges.add(role.getSoftwareProject().getId());
             }
             if(role.getRole().equals("MEMBER")) {
                 memberPrivileges.add(role.getSoftwareProject().getId());
             }
        });
         authorities.add(new SimpleGrantedAuthority(ADMIN + " " + Arrays.toString(adminPrivileges.toArray())));
         authorities.add(new SimpleGrantedAuthority(MEMBER + " " + Arrays.toString(memberPrivileges.toArray())));

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
