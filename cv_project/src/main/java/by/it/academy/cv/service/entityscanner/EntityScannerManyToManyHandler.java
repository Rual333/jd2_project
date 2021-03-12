package by.it.academy.cv.service.entityscanner;

import lombok.ToString;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ToString(exclude = "entityScanner")
public class EntityScannerManyToManyHandler implements FieldScanner {
    EntityScanner entityScanner;

    private Map<String, Class> manyToManyMappedByName = new HashMap<>();

    private Map<ArrayList<String>, Class> manyToManyJoinTableName = new HashMap<>();

    public EntityScannerManyToManyHandler(EntityScanner entityScanner) {
        this.entityScanner = entityScanner;
        scan();
    }

    public void scan() {
        for (Field field : entityScanner.getManyToManyFields()) {
            ArrayList<String> joinTableAndColumns = new ArrayList<>();
            Class<?> fieldType = field.getType();
            final Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericType;
                Type fieldArgType = pType.getActualTypeArguments()[0];
                fieldType = (Class<?>) fieldArgType;
            }
            ManyToMany annotation = field.getAnnotation(ManyToMany.class);
            if (field.isAnnotationPresent(JoinTable.class)) {
                JoinTable joinTableAnnotation = field.getAnnotation(JoinTable.class);
                String joinTableName = joinTableAnnotation.name();
                JoinColumn joinColumn = joinTableAnnotation.joinColumns()[0];
                final String joinColumnName = joinColumn.name();
                JoinColumn inverseJoinColumn = joinTableAnnotation.inverseJoinColumns()[0];
                final String inverseJoinColumnName = inverseJoinColumn.name();
                if (joinTableName.isEmpty()) {
                    String tableName = new EntityScanner(fieldType).getTableName();
                    joinTableName = entityScanner.getTableName() + tableName;
                }
                joinTableAndColumns.add(joinTableName);
                joinTableAndColumns.add(joinColumnName);
                joinTableAndColumns.add(inverseJoinColumnName);
                manyToManyJoinTableName.put(joinTableAndColumns, fieldType);

            } else {
                //Примем что mappedBy никогда не пустое
                String fieldName = annotation.mappedBy();
                manyToManyMappedByName.put(fieldName, fieldType);
            }
        }
    }
}
