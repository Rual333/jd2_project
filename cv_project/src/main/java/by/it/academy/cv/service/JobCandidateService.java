package by.it.academy.cv.service;

import by.it.academy.cv.data.JobCandidateDao;
import by.it.academy.cv.model.JobCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Transactional
public class JobCandidateService {

    @Autowired
    private JobCandidateDao jobCandidateDao;

    @Transactional
    public int save(JobCandidate jobCandidate) {
        jobCandidateDao.save(jobCandidate);
        return 1;
    }
    @Transactional
    public JobCandidate read(long id) {
                return jobCandidateDao.read(id);
    }

}
