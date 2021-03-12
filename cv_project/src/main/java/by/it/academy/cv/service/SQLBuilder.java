package by.it.academy.cv.service;

import by.it.academy.cv.service.entityscanner.EntityScanner;
import by.it.academy.cv.service.entityscanner.FieldClassScanner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class SQLBuilder {

    Class entityClass;
    SQLQueryToGetEntityWithParams result;
    String[] parameters;
    EntityScanner es;
    private Map<String, Field> columnNameFieldNameMap;
    StringBuilder select = new StringBuilder("");
    StringBuilder from = new StringBuilder("");
    StringBuilder join = new StringBuilder("");
    StringBuilder where = new StringBuilder("");
    StringBuilder groupBy = new StringBuilder("");
    StringBuilder having = new StringBuilder("");

    public SQLBuilder(Class entityClass) {
        this.entityClass = entityClass;
        es = new EntityScanner(entityClass);
        columnNameFieldNameMap = es.getColumnNameFieldNameMap();
    }

    public SQLBuilder createSelectFrom(){
        String columnNames = columnNameFieldNameMap.keySet().toString();
        select.append("SELECT ")
                .append(columnNames, 1, columnNames.length()-1)
                .append(" ");
        from.append(" FROM ")
                .append(es.getTableName())
                .append(" ");
        return this;
    }

    public SQLBuilder createJoinOn() {
        Queue<Field> fieldQueue = es.getFieldQueue();
        Field field;
        FieldClassScanner fs = new FieldClassScanner();
        while ((field = fieldQueue.poll()) != null) {
            final Class fieldClass = fs.scan(field);
            es.scanColumnAndFieldsNames(fieldClass, columnNameFieldNameMap);
            join.append(" JOIN ")
                    .append(es.scanTableName(fieldClass))
                    .append(" ON ");
        }
        return this;
    }



    public SQLQueryToGetEntityWithParams build() {
        StringBuilder resultQuery = select
                .append(from)
                .append(join)
                .append(where)
                .append(groupBy)
                .append(having)
                .append(";");
        result = new SQLQueryToGetEntityWithParams(parameters, resultQuery.toString());
        return result;
    }
}
