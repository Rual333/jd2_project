package by.it.academy.cv.main;

import by.it.academy.cv.data.DaoConfiguration;
import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.service.builder.SQLBuilder;
import by.it.academy.cv.service.JobCandidateService;
import by.it.academy.cv.service.entityscanner.ScannedEntityInformationForSQL;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoConfiguration.class);

        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        JobCandidateService service = context.getBean(JobCandidateService.class);

        JobCandidate jobCandidate = service.read(1L);
        System.out.println(jobCandidate);
    }
}
