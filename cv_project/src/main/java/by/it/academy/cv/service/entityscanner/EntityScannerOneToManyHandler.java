package by.it.academy.cv.service.entityscanner;

import lombok.ToString;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@ToString(exclude = "entityScanner")
public class EntityScannerOneToManyHandler implements FieldScanner{

    EntityScanner entityScanner;

    private Map<String, Class> oneToManyMappedByNameAndClassMap = new HashMap<>();

    public EntityScannerOneToManyHandler(EntityScanner entityScanner) {
        this.entityScanner = entityScanner;
        scan();
    }


    public void scan() {
        for (Field field : entityScanner.getOneToManyFields()) {
            Class<?> fieldType = field.getType();
            final Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericType;
                Type fieldArgType = pType.getActualTypeArguments()[0];
                fieldType = (Class<?>) fieldArgType;
            }
            OneToMany annotation = field.getAnnotation(OneToMany.class);
            System.out.println(annotation.targetEntity());
            String fieldName = annotation.mappedBy();
                if (fieldName.isEmpty()) {
                    final String fieldTypePrimaryKey = entityScanner.scanIdColumnName(fieldType);
                    fieldName = field.getName() + "_" + fieldTypePrimaryKey;
                }
            oneToManyMappedByNameAndClassMap.put(fieldName, fieldType);
        }
    }
}

