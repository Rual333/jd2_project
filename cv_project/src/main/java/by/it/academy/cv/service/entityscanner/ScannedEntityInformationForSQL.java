package by.it.academy.cv.service.entityscanner;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.*;

@Getter
@Setter
public class ScannedEntityInformationForSQL {

    private Map<String, String> fieldNameToColumnNameMap;
    private List<Field> allRelatedFields;
    private final String rootTableName;
    private final String rootIdName;

    public ScannedEntityInformationForSQL(String rootIdName, String rootTableName){
        super();
        allRelatedFields = new ArrayList<>();
        fieldNameToColumnNameMap = new HashMap<>();
        this.rootIdName=rootIdName;
        this.rootTableName=rootTableName;
    }


}
