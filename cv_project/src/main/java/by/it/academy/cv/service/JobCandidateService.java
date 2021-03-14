package by.it.academy.cv.service;

import by.it.academy.cv.data.Dao;
import by.it.academy.cv.model.JobCandidate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobCandidateService {

    @Autowired
    private Dao dao;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    public JobCandidateService(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public JobCandidate read(Long id) {
        return (JobCandidate) dao.read(id);
    }

    @Transactional
    public int save(JobCandidate jobCandidate) {
        dao.save(jobCandidate);
        return 1;
    }

    @Transactional
    public List<?> readUsingSQLQuery(String query, Class<?> rootClass){

        final Session currentSession = sessionFactory.getCurrentSession();

        final List list = currentSession
                .createSQLQuery(query)
                .addEntity(rootClass)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        return list;
    }
}
