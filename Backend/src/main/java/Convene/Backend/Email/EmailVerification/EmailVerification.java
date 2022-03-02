package Convene.Backend.Email.EmailVerification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Random;

@Entity
@NoArgsConstructor
public class EmailVerification {
    @Id
    @SequenceGenerator(
            name = "validation_sequence",
            sequenceName = "validation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "validation_sequence"
    )
    private long id;

    @Email(message = "Invalid Email")
    @NotNull(message = "Email can't be blank for validation")
    @Getter
    private String email;

    @NotNull(message = "validation code is null")
    @Getter
    @Range(
            min = 1000,
            max = 9999,
            message = "Validation code generated is out of range"
    )
    private int validationCode;
    @Getter @Setter
    boolean verified;

    public EmailVerification(String email) {
        this.email = email;
        this.validationCode = generateValidationCode();
        this.verified = false;
    }

    private int generateValidationCode(){
        int max = 9999;
        int min = 1000;
        Random random = new Random();
        int validationCode = random.nextInt(max - min) + min;
        return validationCode;
    }
}
