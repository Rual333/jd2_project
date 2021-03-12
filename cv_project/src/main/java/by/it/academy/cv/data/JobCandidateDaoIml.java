package by.it.academy.cv.data;

import by.it.academy.cv.model.JobCandidate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JobCandidateDaoIml implements JobCandidateDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(JobCandidate jobCandidate) {
        final Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(jobCandidate);
    }

    @Override
    public JobCandidate read(long id) {
        final Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(JobCandidate.class, id);
    }

}
