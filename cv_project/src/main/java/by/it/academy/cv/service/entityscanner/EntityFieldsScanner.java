package by.it.academy.cv.service.entityscanner;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class EntityFieldsScanner {

    public EntityFieldsScanner() {
    }

    public ArrayList<Field> scanFields(Class<?> entityClass) {
        return new ArrayList<>(List.of(entityClass.getDeclaredFields()));
    }

    public Class<?> getFieldType(Field field) {
        Class<?> fieldClass = field.getType();
        final Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType;
            Type fieldArgType = pType.getActualTypeArguments()[0];
            fieldClass = (Class<?>) fieldArgType;
        }
        return fieldClass;
    }


}
