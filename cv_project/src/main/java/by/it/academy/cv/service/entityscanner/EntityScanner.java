package by.it.academy.cv.service.entityscanner;

import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;

public interface EntityScanner<R> {

    R scan(Class<?> entityClass) throws IncorrectEntityDefinitionExpression;

}
