package by.it.academy.cv.service.entityscanner;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FieldClassScanner {

    public FieldClassScanner() {
    }

    public Class scan(Field field) {
        Class fieldClass = field.getType();
        final Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType;
            Type fieldArgType = pType.getActualTypeArguments()[0];
            fieldClass = (Class<?>) fieldArgType;
        }
        return fieldClass;
    }

}
