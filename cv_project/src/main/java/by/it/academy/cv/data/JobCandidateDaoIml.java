package by.it.academy.cv.data;

import by.it.academy.cv.model.JobCandidate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JobCandidateDaoIml implements Dao<JobCandidate, String> {

    private SessionFactory sessionFactory;

    @Autowired
    public JobCandidateDaoIml(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(JobCandidate jobCandidate) {
        final Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(jobCandidate);
    }

    @Override
    @Transactional
    public JobCandidate read(String id) {
        final Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(JobCandidate.class, id);
    }

    @Transactional
    public List<JobCandidate> readUsingQuery(String query){

        final Session currentSession = sessionFactory.getCurrentSession();

        return currentSession
                .createSQLQuery(query)
                .addEntity(JobCandidate.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

}
