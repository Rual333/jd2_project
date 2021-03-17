package by.it.academy.cv.service.entityscanner;

import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;

public interface EntityScanner {

    ScannedEntityInformationForSQL scan(Class<?> entityClass) throws IncorrectEntityDefinitionExpression;

}
