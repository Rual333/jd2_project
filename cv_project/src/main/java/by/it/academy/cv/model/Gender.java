package by.it.academy.cv.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "T_GENDERS")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Gender {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @Column(name="GENDER_ID")
    private String genderId;

    @Column(name = "F_GENDER_NAME", nullable = false)
    private String genderName;

}
