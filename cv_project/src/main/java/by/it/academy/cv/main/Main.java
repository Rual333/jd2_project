package by.it.academy.cv.main;

import by.it.academy.cv.data.DaoConfiguration;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.service.SQLBuilder;
import by.it.academy.cv.service.SQLQueryToGetEntityWithParams;
import by.it.academy.cv.service.entityscanner.*;
import by.it.academy.cv.service.JobCandidateService;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(DaoConfiguration.class);

        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        JobCandidateService service = context.getBean(JobCandidateService.class);

        JobCandidate jobCandidate = service.read(1L);
        System.out.println(jobCandidate);

        EntityScanner es = new EntityScanner(JobCandidate.class);
        System.out.println(es.toString());

        EntityScannerOneToOneHandler esOneToOne = new EntityScannerOneToOneHandler(es);
        System.out.println("One To One: " + esOneToOne.toString());

        EntityScannerOneToManyHandler esOneToMany = new EntityScannerOneToManyHandler(es);
        System.out.println("One To Many: " + esOneToMany);

        EntityScannerManyToOneHandler esManyToOne = new EntityScannerManyToOneHandler(es);
        System.out.println("Many To One: " + esManyToOne);

        EntityScannerManyToManyHandler esManyToMany = new EntityScannerManyToManyHandler(es);
        System.out.println("Many To Many: " + esManyToMany);

        SQLBuilder sqlBuilder = new SQLBuilder(JobCandidate.class);
        sqlBuilder.createSelectFrom();
        SQLQueryToGetEntityWithParams entityWithParams = sqlBuilder.build();

        System.out.println(entityWithParams.getQuery());

        System.out.println(es.getEntityFields().toString());
    }
}
