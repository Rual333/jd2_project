package by.it.academy.cv.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "T_CONTACTS")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"jobCandidate"})
@ToString(exclude = {"jobCandidate"})
public class JobCandidateContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CONTACT_ID")
    private Long jobCandidateContactId;

    @Column(name = "F_CONTACT_NAME", nullable = false)
    private String jobCandidateContactName;

    @ManyToOne
    @JoinColumn(name = "JOB_CANDIDATE_ID", nullable = false)
    private JobCandidate jobCandidate;
}
