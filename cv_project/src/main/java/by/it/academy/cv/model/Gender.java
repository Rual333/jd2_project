package by.it.academy.cv.model;

import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="GENDER_ID")
    private Long genderId;

    @Column(name = "F_GENDER_NAME", nullable = false)
    private String genderName;

}
