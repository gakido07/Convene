package Convene.Backend.Security.EmailVerification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Random;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
    private Long id;
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
    private Integer validationCode;
    Boolean verified;

    public EmailVerification(String email) {
        this.email = email;
        this.validationCode = generateValidationCode();
        this.verified = false;
    }


    private Integer generateValidationCode(){
        Integer max = 9999;
        Integer min = 1000;

        Random random = new Random();

        Integer validationCode = random.nextInt(max - min) + min;

        return validationCode;
    }
}
