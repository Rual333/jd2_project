package by.it.academy.cv.service;

import by.it.academy.cv.TestConfiguration;
import by.it.academy.cv.model.JobCandidate;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = TestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JobCandidateServiceTest extends TestCase {


    @Autowired
    JobCandidateService jobCandidateService;

    @Test
    public void testRead() {

        JobCandidate jobCandidate = jobCandidateService.read(1);

        assertEquals(java.util.Optional.of(1), jobCandidate.getJobCandidateId());
    }
}