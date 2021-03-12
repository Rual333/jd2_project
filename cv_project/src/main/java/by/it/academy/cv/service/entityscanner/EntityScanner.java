package by.it.academy.cv.service.entityscanner;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

@ToString
@Getter
public class EntityScanner {

    private Field[] entityFields;
    private Class entityClass;
    private String tableName;
    private String idColumnName;
    private Map<String, Field> columnNameFieldNameMap;
    private Queue<Field> fieldQueue = new LinkedList<>();
    private Queue<Field> manyToManyFieldQueue = new LinkedList<>();
    private ArrayList<Field> oneToOneFields = new ArrayList<>();
    private ArrayList<Field> oneToManyFields = new ArrayList<>();
    private ArrayList<Field> manyToOneFields = new ArrayList<>();
    private ArrayList<Field> manyToManyFields = new ArrayList<>();

    public EntityScanner(Class<?> entityClass) {
        this.entityClass = entityClass;
        columnNameFieldNameMap = new HashMap<>();
        scanTableName();
        scanRootFields();
        scanIdColumnName();
        scanColumnAndFieldsNames(entityClass, columnNameFieldNameMap);
        scanRelatedFields();
    }

    public EntityScanner() {

    }

    private void scanTableName() {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table annotation = (Table) entityClass.getAnnotation(Table.class);
            tableName = annotation.name();
        }
        if (tableName.isEmpty()) {
            tableName = entityClass.getSimpleName();
        }
    }

    public String scanTableName(Class<?> entityClass) {
        String tableName = "";
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table annotation = entityClass.getAnnotation(Table.class);
            tableName = annotation.name();
        }
        if (tableName.isEmpty()) {
            tableName = entityClass.getSimpleName();
        }
        return tableName;
    }

    private void scanRootFields() {
        this.entityFields = entityClass.getDeclaredFields();
    }
    public ArrayList<Field> scanFields(Class entityClass) {
        ArrayList<Field> entityFields = new ArrayList<>(List.of(entityClass.getDeclaredFields()));
        return entityFields;
    }

    public void scanColumnAndFieldsNames(Class entityClass, Map<String, Field> columnNameFieldNameMap) {
        Field[] entityFields = entityClass.getDeclaredFields();
        for (Field field : entityFields) {
            if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
                Column annotation = field.getAnnotation(Column.class);
                String columnName = annotation.name();
                if (columnName.isEmpty()) {
                    columnName = field.getName();
                }
                columnNameFieldNameMap.put(columnName,field);
            }
        }
    }

    private void scanRelatedFields() {
        for (Field field : entityFields) {
            if (field.isAnnotationPresent(OneToOne.class)) {
                fieldQueue.add(field);
                oneToOneFields.add(field);
            }
            if (field.isAnnotationPresent(OneToMany.class)) {
                fieldQueue.add(field);
                oneToManyFields.add(field);
            }
            if (field.isAnnotationPresent(ManyToOne.class)) {
                fieldQueue.add(field);
                manyToOneFields.add(field);
            }
            if (field.isAnnotationPresent(ManyToMany.class)) {
                manyToManyFieldQueue.add(field);
                manyToManyFields.add(field);
            }
        }
    }

    public String scanIdColumnName(Class<?> entityClass) {
        idColumnName = "";
        for (Field field : entityFields) {
            if (field.isAnnotationPresent(Id.class)) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column annotation = field.getAnnotation(Column.class);
                    idColumnName = annotation.name();
                }
                if (idColumnName.isEmpty()) {
                    idColumnName = field.getName();
                }
                return idColumnName;
            }
        }
        return idColumnName;
    }

    private void scanIdColumnName() {
        idColumnName = "";
        for (Field field : entityFields) {
            if (field.isAnnotationPresent(Id.class)) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column annotation = field.getAnnotation(Column.class);
                    idColumnName = annotation.name();
                }
                if (idColumnName.isEmpty()) {
                    idColumnName = field.getName();
                }
            }
        }
    }
}
