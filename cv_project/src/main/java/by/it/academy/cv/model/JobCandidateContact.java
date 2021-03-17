package by.it.academy.cv.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @Column(name="CONTACT_ID")
    private String jobCandidateContactId;

    @Column(name = "F_CONTACT_NAME", nullable = false)
    private String jobCandidateContactName;

    @ManyToOne
    @JoinColumn(name = "JOB_CANDIDATE_ID", nullable = false)
    private JobCandidate jobCandidate;
}
