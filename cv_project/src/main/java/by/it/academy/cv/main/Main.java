package by.it.academy.cv.main;

import by.it.academy.cv.data.DaoConfiguration;
import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.service.builder.SQLBuilder;
import by.it.academy.cv.service.builder.SQLQueryAndParams;
import by.it.academy.cv.service.JobCandidateService;
import by.it.academy.cv.service.entityscanner.EntitiesScannedInformation;
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

        SQLBuilder sqlBuilder = new SQLBuilder(JobCandidate.class);
        SQLQueryAndParams entityWithParams = sqlBuilder.build();

        System.out.println(entityWithParams.getQuery());

        System.out.println();

        System.out.println(jobCandidate.getClass().getDeclaredField("gender").getDeclaringClass().getName());

        StringBuilder sb = new StringBuilder("");

        System.out.println(sb.length());

        sb.append(" AND ");

        System.out.println(sb.charAt(sb.length()-1));

        EntitiesScannedInformation entitiesScannedInformation = new EntitiesScannedInformation(JobCandidate.class);
        System.out.println(entitiesScannedInformation.getFieldsNamesToColumnsNamesMap().keySet());
    }
}
