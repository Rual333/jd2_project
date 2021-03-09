package by.it.academy.cv.service;

import by.it.academy.cv.data.JobCandidateDao;
import by.it.academy.cv.model.JobCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class JobCandidateService {

    @Autowired
    private JobCandidateDao jobCandidateDao;


    TransactionTemplate transactionTemplate;

    @Autowired
    public JobCandidateService(PlatformTransactionManager transactionManager){
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    public int save(JobCandidate jobCandidate) {
        return transactionTemplate.execute(transactionStatus -> {
            try {
                System.out.println("записал по Id");
                jobCandidateDao.save(jobCandidate);
            } catch (Exception e) {
                transactionStatus.hasSavepoint();
            }
            return 1;
        });

    }

    public JobCandidate read(long id) {
        return transactionTemplate.execute(transactionStatus -> {
            try {
                System.out.println("прочитал по Id");
                return jobCandidateDao.read(id);

            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
            }
            return null;
        });
    }

}
