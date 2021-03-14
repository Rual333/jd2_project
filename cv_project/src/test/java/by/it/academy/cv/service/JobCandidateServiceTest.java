package by.it.academy.cv.service;

import by.it.academy.cv.TestConfiguration;
import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.model.Gender;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.model.JobCandidateContact;
import by.it.academy.cv.model.Technology;
import by.it.academy.cv.service.builder.SQLBuilder;
import junit.framework.TestCase;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@ContextConfiguration(classes = TestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JobCandidateServiceTest extends TestCase {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    JobCandidateService jobCandidateService;


        @Test
        @Transactional
        public void testsFromTaskDefinition () throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
            //Given
            final Session currentSession = sessionFactory.getCurrentSession();
//
            //When
            SQLBuilder sqlBuilder = new SQLBuilder(JobCandidate.class);
            final String query= sqlBuilder.like("lastName", "%ов")
                    .or()
                    .equal("genderName", "женщина")
                    .build().getQuery();

            System.out.println(query);

            final List list = currentSession
                    .createSQLQuery(query)
                    .addEntity(JobCandidate.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();

            sqlBuilder = new SQLBuilder(JobCandidate.class);
            final String query1 = sqlBuilder.equal("lastName", "Петров")
                    .and()
                    .equal("patronymic", "Петрович")
                    .and()
                    .equal("firstName", "Петр").build().getQuery();

            final List list1 = currentSession
                    .createSQLQuery(query1)
                    .addEntity(JobCandidate.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();

            System.out.println(list);
            System.out.println(list1);

            assertEquals(1, list1.size());
            assertEquals(3, list.size());
        }
    }
