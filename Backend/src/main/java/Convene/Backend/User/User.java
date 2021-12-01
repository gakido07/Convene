package Convene.Backend.User;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class User extends Convene.Backend.Models.User {
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
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String projectRole;
    private Role role;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Role getRole() {
        return  role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getProjectRole() {
        return projectRole;
    }

    @Override
    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }
}
