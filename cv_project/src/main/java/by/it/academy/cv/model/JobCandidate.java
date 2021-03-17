package by.it.academy.cv.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "T_JOB_CANDIDATES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class JobCandidate {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @Column(name = "JOB_CANDIDATE_ID")
    private String jobCandidateId;

    @Column(name = "F_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "F_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "F_PATRONYMIC", nullable = false)
    private String patronymic;

    @Column(name = "F_DATE_OF_BIRTH", nullable = false)
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "GENDER_ID", nullable = false)
    private Gender gender;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_JOB_CANDIDATES_TECHNOLOGIES",
            joinColumns = @JoinColumn(name = "JOB_CANDIDATE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TECHNOLOGY_ID"))
    private List<Technology> technologies;

    @OneToMany(mappedBy = "jobCandidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobCandidatesContact> contacts;
}
