package by.it.academy.cv.service;

import by.it.academy.cv.TestConfiguration;
import by.it.academy.cv.model.Gender;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.model.Technology;
import junit.framework.TestCase;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@ContextConfiguration(classes = TestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JobCandidateServiceTest extends TestCase {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    JobCandidateService jobCandidateService;

    Logger logger = Logger.getLogger(getClass().getName());

    @Test
    @Transactional
    public void testRead() {

        JobCandidate jobCandidate = jobCandidateService.read(1);
        final Session currentSession = sessionFactory.getCurrentSession();
        final List resultList = currentSession.createSQLQuery("Select * from " +
                        "T_TECHNOLOGIES where F_TECHNOLOGY_NAME='Git'").addEntity(Technology.class).getResultList();
        resultList.stream().distinct().forEach(System.out::println);
        logger.info("Query: " + resultList.get(0));

        final Technology technology = currentSession.get(Technology.class, 1);
        System.out.println(technology);
        System.out.println();
        System.out.println(technology.getJobCandidates());
        assertEquals(java.util.Optional.of(1), jobCandidate.getJobCandidateId());
    }
}