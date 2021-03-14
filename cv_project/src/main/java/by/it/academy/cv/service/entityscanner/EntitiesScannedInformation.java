package by.it.academy.cv.service.entityscanner;

import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.*;

@Getter
@ToString
public class EntitiesScannedInformation {

    private EntityNamesScanner entityNamesScanner;
    private EntityFieldsScanner entityFieldsScanner;
    private Queue<Class<?>> classQueue;
    private Map<String, String> fieldsNamesToColumnsNamesMap;
    private Set<Class<?>> classSet;
    private List<Field> allRelatedFields;
    private final String tableName;
    private final String idName;

    public EntitiesScannedInformation(Class<?> entityClass) throws IncorrectEntityDefinitionExpression {
        init();
        classQueue.add(entityClass);
        classSet.add(entityClass);
        tableName = entityNamesScanner.scanTableName(entityClass);
        idName = entityNamesScanner.scanIdColumnName(entityClass);
        scanEntity();
    }

    private void init(){
        entityNamesScanner = new EntityNamesScanner();
        entityFieldsScanner = new EntityFieldsScanner();

        fieldsNamesToColumnsNamesMap = new HashMap<>();
        classQueue = new ArrayDeque<>();
        classSet = new HashSet<>();

        allRelatedFields = new ArrayList<>();
    }

    private void scanEntity() {
        Class<?> classFromQueue ;
        while ((classFromQueue = classQueue.poll()) != null) {
            scanEntity(classFromQueue);
        }
    }

    private void scanEntity(Class<?> classFromQueue) {
        entityNamesScanner.scanFieldsNamesToColumnsNames(classFromQueue, fieldsNamesToColumnsNamesMap);
        entityFieldsScanner.scanRelatedFieldsAndAddClassesToSet(classFromQueue, classQueue, allRelatedFields, classSet);
    }
}
