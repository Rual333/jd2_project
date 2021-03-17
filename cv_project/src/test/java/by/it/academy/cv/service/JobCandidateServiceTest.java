package by.it.academy.cv.service;

import by.it.academy.cv.data.DaoConfiguration;
import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.exeptions.IncorrectGeneratorUsage;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.service.builder.SQLGenerator;
import by.it.academy.cv.service.builder.SQLParameters;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@ContextConfiguration(classes = DaoConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JobCandidateServiceTest extends TestCase {

    @Autowired
    JobCandidateService jobCandidateService;

    Logger logger = Logger.getLogger(getClass().getName());

    @Test
    @Transactional
    public void test1FromTaskDefinition() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage, IncorrectGeneratorUsage {
        //When
        SQLParameters params = new SQLParameters.ParametersBuilder()
                .equal("lastName", "Морская")
                .and()
                .equal("patronymic", "Васильевна")
                .and()
                .equal("firstName", "Мария").build();

        SQLGenerator generator = new SQLGenerator(params, JobCandidate.class);
        String query = generator.getResultQuery();
        //Then
        logger.info("Query:" + query);
        logger.info("Parameters: " + generator.getQueryParameters());

        final List list = jobCandidateService.readUsingSQLQuery(query);

        assertEquals(1, list.size());
        JobCandidate result = (JobCandidate) list.get(0);
        String resultId = result.getJobCandidateId();
        assertEquals("402881e478020e9b0178020ea3e70002", resultId);
    }

    @Test
    @Transactional
    public void test2FromTaskDefinition() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage, IncorrectGeneratorUsage {
        //When
        SQLParameters params = new SQLParameters.ParametersBuilder()
                .like("lastName", "%ов")
                .or()
                .equal("genderName", "женщина").build();
        SQLGenerator generator = new SQLGenerator(params, JobCandidate.class);
        String result = generator.getResultQuery();
        logger.info("Query:" + result);
        logger.info("Parameters: " + generator.getQueryParameters());
        //Then

        final List list = jobCandidateService.readUsingSQLQuery(result);
        assertEquals(3, list.size());
    }
}
