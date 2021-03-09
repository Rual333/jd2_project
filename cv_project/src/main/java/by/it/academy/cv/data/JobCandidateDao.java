package by.it.academy.cv.data;

import by.it.academy.cv.model.JobCandidate;

public interface JobCandidateDao {

    void save(JobCandidate jobCandidate);

    JobCandidate read(long id);
}
