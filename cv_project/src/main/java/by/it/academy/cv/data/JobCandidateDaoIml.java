package by.it.academy.cv.data;

import by.it.academy.cv.model.JobCandidate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JobCandidateDaoIml implements JobCandidateDao{

    @PersistenceContext
    EntityManager em;

    @Override
    public void save(JobCandidate jobCandidate) {
        em.persist(jobCandidate);
    }

    @Override
    public JobCandidate read(long id) {
        return em.find(JobCandidate.class, id);
    }
}
