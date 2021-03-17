package by.it.academy.cv.main;

import by.it.academy.cv.data.DaoConfiguration;
import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.model.Gender;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.model.Technology;
import by.it.academy.cv.service.JobCandidateService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoConfiguration.class);

        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        JobCandidateService service = context.getBean(JobCandidateService.class);

        JobCandidate jobCandidate = service.read(1L);
        System.out.println(jobCandidate);

//        JobCandidate jobCandidate1 = new JobCandidate(null, "Петр", "Петров", "Петрович",
//                new Date(1L),new Gender(), List.of(new Technology()), List.of());



    }
}
