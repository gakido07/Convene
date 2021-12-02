package Convene.Backend.User;

import Convene.Backend.Models.Project;
import Convene.Backend.Project.ProjectRole;
import Convene.Backend.Project.SoftwareProject.SoftwareProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.Mapping;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@NoArgsConstructor
public class AppUser implements UserDetails {
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
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean accountLocked;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany
    @JoinTable(
            name = "user_projects",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "software_project_id")
    )
    private Set<SoftwareProject> projects;
    @ManyToOne
    @JoinColumn(name = "project_role_id", nullable = false)
    private ProjectRole projectRole;

    public AppUser(SignUpRequest signUpRequest) {
        this.email = signUpRequest.email;
        this.firstName = signUpRequest.firstName;
        this.lastName = signUpRequest.lastName;
        this.password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(signUpRequest.password);
        this.accountLocked = false;
        if(signUpRequest.getJoinProject()){
            this.role = Role.PROJECT_MEMBER;
        }
        else{
            this.role = Role.PROJECT_ADMIN;
        }
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountLocked(boolean accountLocked){
        this.accountLocked = accountLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    @AllArgsConstructor
    @Data
    public static class SignUpRequest {
        private String email;
        private String firstName;
        private String lastName;
        private String password;
        private String confirmPassword;
        private String projectRole;
        private boolean joinProject;

        public boolean getJoinProject(){
            return joinProject;
        }
    }


    @AllArgsConstructor
    @Data
    public class LogInRequest{
        private String email;
        private String password;
    }

    @Data
    public static class UserDao{
        String email;
    }


}

