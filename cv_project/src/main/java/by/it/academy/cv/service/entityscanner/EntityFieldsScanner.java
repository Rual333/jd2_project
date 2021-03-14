package by.it.academy.cv.service.entityscanner;

import lombok.Getter;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

@Getter
public class EntityFieldsScanner {

    public EntityFieldsScanner() {
    }

    public ArrayList<Field> scanFields(Class<?> entityClass) {
        return new ArrayList<>(List.of(entityClass.getDeclaredFields()));
    }

    public Class<?> getFieldClass(Field field) {
        Class<?> fieldClass = field.getType();
        final Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType;
            Type fieldArgType = pType.getActualTypeArguments()[0];
            fieldClass = (Class<?>) fieldArgType;
        }
        return fieldClass;
    }

    public void scanRelatedFieldsAndAddClassesToSet(Class<?> classFromQueue, Queue<Class<?>> classQueue, List<Field> allFields, Set<Class<?>> classSet) {
        for (Field field : scanFields(classFromQueue)) {
            final Class<?> fieldClass = getFieldClass(field);
            if (classSet.contains(fieldClass)) {
                continue;
            } else {
                classSet.add(fieldClass);
                classQueue.add(fieldClass);
            }
            if (field.isAnnotationPresent(OneToOne.class) ||
                    field.isAnnotationPresent(OneToMany.class) ||
                    field.isAnnotationPresent(ManyToOne.class) ||
                    field.isAnnotationPresent(ManyToMany.class) ||
                    field.isAnnotationPresent(Id.class)) {
                allFields.add(field);
            }
        }
    }
}
