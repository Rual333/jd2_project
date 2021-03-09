package by.it.academy.cv.main;

import by.it.academy.cv.data.DaoConfiguration;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.service.JobCandidateService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoConfiguration.class);

        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        JobCandidateService service = context.getBean(JobCandidateService.class);

        JobCandidate jobCandidate = service.read(1L);
        System.out.println(jobCandidate);

        EntityScanner es = new EntityScanner(JobCandidate.class);

        es.printFieldsAndAnnotations();

    }
}
