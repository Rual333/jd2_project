package by.it.academy.cv.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_TECHNOLOGIES")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"jobCandidates"})
@ToString(exclude = {"jobCandidates"})
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TECHNOLOGY_ID")
    private Long technologyId;

    @Column(name = "F_TECHNOLOGY_NAME", nullable = false)
    private String technologyName;

    @ManyToMany(mappedBy = "technologies")
    private List<JobCandidate> jobCandidates;
}
