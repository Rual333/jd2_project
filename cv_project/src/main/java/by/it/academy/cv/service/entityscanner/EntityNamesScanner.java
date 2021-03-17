package by.it.academy.cv.service.entityscanner;

import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Map;


public class EntityNamesScanner {

    public EntityNamesScanner() {
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

    public void scanAndAddToFieldsNamesToColumnsNamesMap(Class<?> entityClass, Map<String, String> FieldNameToColumnNameMap) {
        Field[] entityFields = entityClass.getDeclaredFields();
        for (Field field : entityFields) {
            if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
                Column annotation = field.getAnnotation(Column.class);
                String columnName = annotation.name();
                if (columnName.isEmpty()) {
                    columnName = field.getName();
                }
                FieldNameToColumnNameMap.put(field.getName(),columnName);
            }
        }
    }

    public String scanIdColumnName(Class<?> entityClass) throws IncorrectEntityDefinitionExpression {
        String primaryKeyColumnName = "";
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column annotation = field.getAnnotation(Column.class);
                    primaryKeyColumnName = annotation.name();
                }
                if (primaryKeyColumnName.isEmpty()) {
                    primaryKeyColumnName = field.getName();
                }
                return primaryKeyColumnName;
            }
        }
        throw new IncorrectEntityDefinitionExpression("Entity hasn't an Id annotation");
    }

    public String scanJoinColumnName(Field field) throws IncorrectEntityDefinitionExpression {
        String columnName = "";
        if (field.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn annotation = field.getAnnotation(JoinColumn.class);
            columnName = annotation.name();
        }
        if (columnName.isEmpty()) {
            final String fieldTypePrimaryKey = scanIdColumnName(field.getType());
            columnName = field.getName() + "_" + fieldTypePrimaryKey;
        }
        return columnName;
    }
}
