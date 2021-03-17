package by.it.academy.cv.service.builder;

import by.it.academy.cv.data.DaoConfiguration;
import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.exeptions.IncorrectGeneratorUsage;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.service.JobCandidateService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.logging.Logger;

@ContextConfiguration(classes = DaoConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SQLParametersTest extends TestCase {

    @Autowired
    JobCandidateService jobCandidateService;

    Logger logger = Logger.getLogger(getClass().getName());

    @Test(expected = IncorrectBuildingUsage.class)
    public void testEqualEqual() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        SQLParameters params = new SQLParameters.ParametersBuilder()
                .equal("lastName", "Морская")
                .equal("firstName", "Мария").build();
    }

    @Test(expected = IncorrectBuildingUsage.class)
    public void testOrBeforeCondition() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        SQLParameters params = new SQLParameters.ParametersBuilder()
                .or()
                .equal("genderName", "женщина").build();

    }

    @Test(expected = IncorrectBuildingUsage.class)
    public void testNullParameterInCondition() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        SQLParameters params = new SQLParameters.ParametersBuilder()
                .equal(null, "женщина").build();
    }

    @Test(expected = IncorrectGeneratorUsage.class)
    public void testNullClassIntoGenerator() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage, IncorrectGeneratorUsage {
        //When
        SQLParameters params = new SQLParameters.ParametersBuilder()
                .equal("patronymic", "Васильевна")
                .and()
                .equal("firstName", "Мария").build();

        SQLGenerator generator = new SQLGenerator(null, JobCandidate.class);
        String query = generator.getResultQuery();
    }

    @Test()
    public void testWithoutParameters() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage, IncorrectGeneratorUsage {
        //When
        SQLGenerator generator = new SQLGenerator(JobCandidate.class);
        String query = generator.getResultQuery();
        //Then
        logger.info("Query:" + query);
        logger.info("Parameters: " + generator.getQueryParameters());

        final List list = jobCandidateService.readUsingSQLQuery(query);

        assertEquals(3, list.size());
    }
}