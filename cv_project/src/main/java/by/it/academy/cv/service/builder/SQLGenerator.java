package by.it.academy.cv.service.builder;

import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.exeptions.IncorrectGeneratorUsage;
import by.it.academy.cv.service.entityscanner.EntityScannerForSQL;
import by.it.academy.cv.service.entityscanner.ScannedEntityInformationForSQL;
import lombok.Setter;

import java.util.List;


@Setter
public class SQLGenerator implements QueryGenerator {

    private String resultQuery;
    Class<?> entityClass;
    private List<String> params;

    private ScannedEntityInformationForSQL entityInformation;

    private StringBuilder select;
    private StringBuilder from;
    private StringBuilder join;

    private SQLJoinOnHandler sqlJoinOnHandler = new SQLJoinOnHandler();

    public SQLGenerator(Class<?> entityClass) throws IncorrectGeneratorUsage, IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        if(entityClass==null){
            throw new IncorrectGeneratorUsage("Entity class Can't be null");
        }
        this.entityClass=entityClass;
        resultQuery = createSelectFrom(entityClass);
    }

    public SQLGenerator(QueryParameters queryParameters,Class<?> entityClass) throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage, IncorrectGeneratorUsage {
        if(queryParameters ==null || entityClass==null){
            throw new IncorrectGeneratorUsage("Query parameters or Entity class Can't be null");
        }
        params = queryParameters.getQueryParameters();
        this.entityClass=entityClass;
        createQuery();
    }

    private void createQuery() throws IncorrectEntityDefinitionExpression, IncorrectBuildingUsage {
        resultQuery = createSelectFrom(entityClass) + getRestrictionsFromParameters(params);
    }

    private String getRestrictionsFromParameters(List<String> params) throws IncorrectBuildingUsage {
        StringBuilder sb = new StringBuilder();
        for (String s: params) {
           sb.append(s);
        }
        String restrictions = sb.toString();
        for (String s: entityInformation.getFieldNameToColumnNameMap().keySet()) {
            restrictions = restrictions.replaceAll(s, getColumnName(s));
        }
        return restrictions;
    }

    @Override
    public String getResultQuery() {
        return resultQuery;
    }

    public List<String> getQueryParameters() {
        return params;
    }

    private String createSelectFrom(Class<?> entityClass) throws IncorrectEntityDefinitionExpression {
        entityInformation = new EntityScannerForSQL().scan(entityClass);
        createSelect();
        createFrom();
        createJoinOn();
        setPrimaryKeysToSelect();
        return select.append(from).append(join).toString();
    }

    private void createSelect() {
        select = new StringBuilder();
        String columnNames = entityInformation.getFieldNameToColumnNameMap().values().toString();
        select.append("SELECT ")
                .append(columnNames, 1, columnNames.length() - 1);
    }

    private void createFrom() {
        from = new StringBuilder();
        from.append(" FROM ")
                .append(entityInformation.getRootTableName().trim());
    }

    private void setPrimaryKeysToSelect() {
        sqlJoinOnHandler.getPrimaryKeyWithTableNames().forEach(pk -> select.append(",")
                .append(pk));
    }

    private void createJoinOn() {
        join = new StringBuilder();
        entityInformation.getAllRelatedFields().forEach(field -> {
            try {
                join.append(sqlJoinOnHandler.getJoinOnCondition(field));
            } catch (IncorrectEntityDefinitionExpression incorrectEntityDefinitionExpression) {
                incorrectEntityDefinitionExpression.printStackTrace();
            }
        });
    }

    private String getColumnName(String fieldName) throws IncorrectBuildingUsage {
        String columnName = entityInformation.getFieldNameToColumnNameMap().get(fieldName.trim());
        if (columnName == null) {
            throw new IncorrectBuildingUsage("There is no such field name, available fields name: " +
                    entityInformation.getFieldNameToColumnNameMap().keySet());
        }
        return columnName.trim();
    }

}
