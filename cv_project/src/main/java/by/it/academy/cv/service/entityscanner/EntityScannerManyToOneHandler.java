package by.it.academy.cv.service.entityscanner;

import lombok.ToString;

import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@ToString(exclude = "entityScanner")
public class EntityScannerManyToOneHandler implements FieldScanner{

    EntityScanner entityScanner;

    private Map<String, Class> manyToOneJoinColumnNameAndClassMap = new HashMap<>();

    public EntityScannerManyToOneHandler(EntityScanner entityScanner) {
        this.entityScanner = entityScanner;
        scan();
    }

    public void scan() {
        for (Field field : entityScanner.getManyToOneFields()) {
            final Class<?> fieldType = field.getType();
            if (field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn annotation = field.getAnnotation(JoinColumn.class);
                String columnName = annotation.name();
                if (columnName.isEmpty()) {
                    final String fieldTypePrimaryKey = entityScanner.scanIdColumnName(fieldType);
                    columnName = field.getName() + "_" + fieldTypePrimaryKey;
                }
                manyToOneJoinColumnNameAndClassMap.put(columnName, fieldType);
            }
        }
    }
}
