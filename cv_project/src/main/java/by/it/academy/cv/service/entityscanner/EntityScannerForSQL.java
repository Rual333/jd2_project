package by.it.academy.cv.service.entityscanner;

import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

@Service("entityScanner")
public class EntityScannerForSQL implements EntityScanner {

    private EntityNamesScanner entityNamesScanner;
    private EntityFieldsScanner entityFieldsScanner;
    private Set<Class<?>> classSet;
    private Queue<Class<?>> classQueue;
    private Class<?> entityClass;

    private ScannedEntityInformationForSQL scannedInfo;

    public EntityScannerForSQL() {
        init();
    }

    private void init() {
        entityNamesScanner = new EntityNamesScanner();
        entityFieldsScanner = new EntityFieldsScanner();
    }

    @Override
    public ScannedEntityInformationForSQL scan(Class<?> entityClass) throws IncorrectEntityDefinitionExpression {
        prepareToNewScan(entityClass);
        Class<?> classFromQueue;
        while ((classFromQueue = classQueue.poll()) != null) {
            scanEntity(classFromQueue);
        }
        return scannedInfo;
    }

    private void prepareToNewScan(Class<?> entityClass) throws IncorrectEntityDefinitionExpression {
        this.entityClass = entityClass;
        initNewQueueAndClassSet();
        SetRootIdAndTableNameToScannedInfo();
    }

    private void SetRootIdAndTableNameToScannedInfo() throws IncorrectEntityDefinitionExpression {

        String rootIdName = entityNamesScanner.scanIdColumnName(entityClass);
        String rootTableName = entityNamesScanner.scanTableName(entityClass);

        scannedInfo = new ScannedEntityInformationForSQL(rootIdName, rootTableName);

    }

    private void initNewQueueAndClassSet() {
        classQueue = new ArrayDeque<>();
        classSet = new HashSet<>();
        classQueue.add(entityClass);
        classSet.add(entityClass);
    }

    private void scanEntity(Class<?> classFromQueue) {
        entityNamesScanner.scanAndAddToFieldsNamesToColumnsNamesMap(classFromQueue, scannedInfo.getFieldNameToColumnNameMap());
        scanRelatedFieldsAndAddClassesToSet(classFromQueue, scannedInfo.getAllRelatedFields());

    }

    public void scanRelatedFieldsAndAddClassesToSet(Class<?> entityClass, List<Field> allFields) {
        for (Field field : entityFieldsScanner.scanFields(entityClass)) {
            final Class<?> fieldClass = entityFieldsScanner.getFieldType(field);
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
