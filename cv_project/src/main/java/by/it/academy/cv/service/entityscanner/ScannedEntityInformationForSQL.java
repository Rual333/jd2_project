package by.it.academy.cv.service.entityscanner;

import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
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
