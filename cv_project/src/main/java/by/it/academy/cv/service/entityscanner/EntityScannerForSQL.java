package by.it.academy.cv.service.entityscanner;

import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.*;

@Getter
@ToString
public class EntityScannerForSQL implements EntityScanner<ScannedEntityInformationForSQL> {

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
        initNewScannedInfo();
    }

    private void initNewScannedInfo() throws IncorrectEntityDefinitionExpression {

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
        entityFieldsScanner.scanRelatedFieldsAndAddClassesToSet(classFromQueue, classQueue, scannedInfo.getAllRelatedFields(), classSet);
    }

}
