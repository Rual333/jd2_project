package by.it.academy.cv.service;

import by.it.academy.cv.data.Dao;
import by.it.academy.cv.model.JobCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobCandidateService {

    @Autowired
    private Dao<JobCandidate, String> dao;

    @Transactional
    public JobCandidate read(String id) {
        return (JobCandidate) dao.read(id);
    }

    @Transactional
    public int save(JobCandidate jobCandidate) {
        dao.save(jobCandidate);
        return 1;
    }

    @Transactional
    public List<?> readUsingSQLQuery(String query){
       return dao.readUsingQuery(query);
    }
}
