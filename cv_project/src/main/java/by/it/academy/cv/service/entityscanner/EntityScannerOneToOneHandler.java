package by.it.academy.cv.service.entityscanner;


import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@ToString(exclude = "entityScanner")
public class EntityScannerOneToOneHandler implements FieldScanner{


    EntityScanner entityScanner;

    private Map<String, Class> oneToOneMappedByName = new HashMap<>();

    private Map<String, Class> oneToOneJoinColumnName = new HashMap<>();

    public EntityScannerOneToOneHandler(EntityScanner entityScanner) {
        this.entityScanner = entityScanner;
        scan();
    }

    public void scan() {
        for (Field field : entityScanner.getOneToOneFields()) {
            final Class<?> fieldType = field.getType();
            if (field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn annotation = field.getAnnotation(JoinColumn.class);
                String columnName = annotation.name();
                if (columnName.isEmpty()) {
                    final String fieldTypePrimaryKey = entityScanner.scanIdColumnName(fieldType);
                    columnName = field.getName() + "_" + fieldTypePrimaryKey;
                }
                oneToOneJoinColumnName.put(columnName, fieldType);
            } else {
                OneToOne annotation = field.getAnnotation(OneToOne.class);
                String fieldName = annotation.mappedBy();
                oneToOneMappedByName.put(fieldName, fieldType);
            }
        }
    }
}