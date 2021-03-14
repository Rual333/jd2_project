package by.it.academy.cv.model;

import lombok.*;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "T_JOB_CANDIDATES")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class JobCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JOB_CANDIDATE_ID")
    private Long jobCandidateId;

    @Column(name = "F_FIRST_NAME")
    private String firstName;

    @Column(name = "F_LAST_NAME")
    private String lastName;

    @Column(name = "F_PATRONYMIC")
    private String patronymic;

    @Column(name = "F_DATE_OF_BIRTH")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "GENDER_ID")
    private Gender gender;

    @ManyToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinTable(name = "T_JOB_CANDIDATES_TECHNOLOGIES",
            joinColumns = @JoinColumn(name = "JOB_CANDIDATE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TECHNOLOGY_ID"))
    private List<Technology> technologies;

    @OneToMany(mappedBy = "jobCandidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobCandidateContact> contacts;
}
