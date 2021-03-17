package by.it.academy.cv.service;

import by.it.academy.cv.TestConfiguration;
import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.model.Technology;
import by.it.academy.cv.service.builder.QueryGenerator;
import by.it.academy.cv.service.builder.SQLBuilder;
import junit.framework.TestCase;
import org.hibernate.Criteria;
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
    public void test1FromTaskDefinition() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        //Given
        Session currentSession = sessionFactory.getCurrentSession();
        SQLBuilder sqlBuilder = new SQLBuilder(JobCandidate.class);

        //When

        sqlBuilder.equal("lastName", "Морская")
                .and()
                .equal("patronymic", "Васильевна")
                .and()
                .equal("firstName", "Мария");

        QueryGenerator queryGenerator= new QueryGenerator(sqlBuilder);

        String query = queryGenerator.getResultQuery();

        final List list = currentSession
                .createSQLQuery(query)
                .addEntity(JobCandidate.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .getResultList();

        //Then
        logger.info("Query from SQLBuilder: " + query);
        logger.info("Parameters: " + queryGenerator.getParams());
        assertEquals(1, list.size());
        JobCandidate result = (JobCandidate) list.get(0);
        String resultId = result.getJobCandidateId();
        assertEquals("402881e478020e9b0178020ea3e70002", resultId);
    }

    @Test
    @Transactional
    public void test2FromTaskDefinition() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        //Given
        final Session currentSession = sessionFactory.getCurrentSession();

        //When
        SQLBuilder sqlBuilder = new SQLBuilder(JobCandidate.class);
//        SQLBuilder sqlBuilder1 = new SQLBuilder(Technology.class);
//        sqlBuilder1.build();
//        System.out.println();
//        System.out.println(sqlBuilder1.getResultQuery());
        sqlBuilder.like("lastName", "%ов")
                .or()
                .equal("genderName", "женщина");

//        System.out.println(sqlBuilder.getResultQuery());
        QueryGenerator queryGenerator=new QueryGenerator(sqlBuilder);
        String result = queryGenerator.getResultQuery();

        System.out.println(result);
        final List list = currentSession
                .createSQLQuery(result)
                .addEntity(JobCandidate.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        //Then
        assertEquals(3, list.size());
    }
}
