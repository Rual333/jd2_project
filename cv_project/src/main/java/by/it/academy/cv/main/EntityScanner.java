package by.it.academy.cv.main;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityScanner {

    private Field[] entityFields;

    private Class entityClass;

    private Map<Field, Annotation[]> entityFieldAnnotationMap;

    EntityScanner(Class entityClass) {
        this.entityClass = entityClass;
        entityFieldAnnotationMap = new HashMap<>();
        scanFields();
        scanAndMapAnnotations();
    }

    //Void?
    private Field[] scanFields() {
        entityFields = entityClass.getDeclaredFields();
        for (Field f: entityFields) {
            System.out.println(f.getName());
        }

        return entityFields;
    }

    //Void?
    private Map<Field, Annotation[]> scanAndMapAnnotations() {
        for (Field field:entityFields) {
            Annotation[] annotations = field.getAnnotations();
            System.out.println(annotations);
            entityFieldAnnotationMap.put(field, annotations);
        }
        return entityFieldAnnotationMap;
    }

    public void printFieldsAndAnnotations(){
        System.out.println(entityFieldAnnotationMap);
    }
}
