package by.it.academy.cv.service.builder;

import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.service.entityscanner.EntitiesScannedInformation;

import java.util.ArrayList;
import java.util.List;

public class SQLBuilder {

    private EntitiesScannedInformation eScannedInfo;
    private StringBuilder select;
    private StringBuilder from;
    private StringBuilder join;
    private StringBuilder where;
    private StringBuilder groupBy;
    private StringBuilder having;
    private BuilderJoinOnHandler builderJoinOnHandler;
    private List<String> parameters;
    private boolean ifItsWhere;

    public SQLBuilder(Class<?> entityClass) throws IncorrectEntityDefinitionExpression {
        init(entityClass);
    }

    private void init(Class<?> entityClass) throws IncorrectEntityDefinitionExpression {
        eScannedInfo = new EntitiesScannedInformation(entityClass);
        join = new StringBuilder();
        where = new StringBuilder();
        groupBy = new StringBuilder();
        having = new StringBuilder();
        ifItsWhere = true;
        builderJoinOnHandler = new BuilderJoinOnHandler();
        parameters = new ArrayList<>();
        createSelectFrom();
        createJoinOn();
        setPrimaryKeysToSelect();
    }

    private void createSelectFrom() {
        String columnNames = eScannedInfo.getFieldsNamesToColumnsNamesMap().values().toString();
        select = new StringBuilder();
        from = new StringBuilder();
        select.append("SELECT ")
                .append(columnNames, 1, columnNames.length() - 1);
        from.append(" FROM ")
                .append(eScannedInfo.getTableName().trim());
//        .append(" left outer join T_GENDERS gender1_ on T_JOB_CANDIDATES.GENDER_ID=gender1_.GENDER_ID");
    }

    private void setPrimaryKeysToSelect() {
        builderJoinOnHandler.getPrimaryKeyWithTableNames().forEach(pk -> {
            select.append(",")
                    .append(pk);
        });
    }

    private void createJoinOn() {
        eScannedInfo.getAllRelatedFields().forEach(field -> {
            try {
                join.append(builderJoinOnHandler.getJoinOnCondition(field));
            } catch (IncorrectEntityDefinitionExpression incorrectEntityDefinitionExpression) {
                incorrectEntityDefinitionExpression.printStackTrace();
            }
        });
    }

    public SQLBuilder and() throws IncorrectBuildingUsage {
        addingOperator(" AND ");
        return this;
    }

    public SQLBuilder or() throws IncorrectBuildingUsage {
        addingOperator(" OR ");
        return this;
    }

    public SQLBuilder not() throws IncorrectBuildingUsage {
        addingOperator(" NOT ");
        return this;
    }

    private SQLBuilder addingOperator(String operator) throws IncorrectBuildingUsage {
        if (ifItsWhere) {
            return addOperatorToWhere(operator);
        } else {
            return addOperatorToHaving(operator);
        }
    }

    private SQLBuilder addOperatorToWhere(String operator) throws IncorrectBuildingUsage {
        checkingCapacity(where);
        lastCharacterHasNotToBeSpace(where);
        where.append(operator);
        return this;
    }

    private SQLBuilder addOperatorToHaving(String operator) throws IncorrectBuildingUsage {
        checkingCapacity(where);
        lastCharacterHasNotToBeSpace(having);
        having.append(operator);
        return this;
    }

    public SQLBuilder equal(String fieldName, String parameter) throws IncorrectBuildingUsage {
        return addingCondition(fieldName, "=", parameter);
    }

    public SQLBuilder like(String fieldName, String parameter) throws IncorrectBuildingUsage {
        return addingCondition(fieldName, " LIKE ", parameter);
    }

    public SQLBuilder greater(String fieldName, String parameter) throws IncorrectBuildingUsage {
        return addingCondition(fieldName, ">", parameter);
    }

    public SQLBuilder less(String fieldName, String parameter) throws IncorrectBuildingUsage {
        return addingCondition(fieldName, "<", parameter);
    }

    public SQLBuilder greaterOrEq(String fieldName, String parameter) throws IncorrectBuildingUsage {
        return addingCondition(fieldName, ">=", parameter);
    }

    public SQLBuilder lessOrEq(String fieldName, String parameter) throws IncorrectBuildingUsage {
        return addingCondition(fieldName, "<=", parameter);
    }

    public SQLBuilder notEqual(String fieldName, String parameter) throws IncorrectBuildingUsage {
        return addingCondition(fieldName, "<>", parameter);
    }

    private SQLBuilder addingCondition(String fieldName, String condition, String parameter) throws IncorrectBuildingUsage {
        if (parameter == null) {
            throw new IncorrectBuildingUsage("parameters has not to be null");
        }
        parameter = "'" + parameter + "'";
//        System.out.println(parameter);
        if (ifItsWhere) {
            return addConditionToWhere(fieldName, condition, parameter);
        } else {
            return addConditionToHaving(fieldName, condition, parameter);
        }

    }

    private SQLBuilder addConditionToWhere(String fieldName, String condition, String parameter) throws IncorrectBuildingUsage {
        String columnName = getColumnName(fieldName);
        if (where.length() == 0) {
            where.append(" WHERE ");
        }
        lastCharacterHasToBeSpace(where);
        where.append(columnName).append(condition).append(parameter.trim());
        return this;
    }

    private SQLBuilder addConditionToHaving(String fieldName, String condition, String parameter) throws IncorrectBuildingUsage {
        String columnName = getColumnName(fieldName);
        if (having.length() == 0) {
            having.append(" HAVING ");
        }
        lastCharacterHasToBeSpace(having);
        having.append(columnName).append(condition).append(parameter.trim());
        return this;
    }

    public SQLQueryAndParams build() throws IncorrectBuildingUsage {
        lastCharacterHasNotToBeSpace(where);
        lastCharacterHasNotToBeSpace(having);
        StringBuilder resultQuery = select
                .append(from)
                .append(join)
                .append(where)
                .append(groupBy)
                .append(having)
                .append(";");
        return new SQLQueryAndParams(parameters, resultQuery.toString());
    }

    private String getColumnName(String fieldName) throws IncorrectBuildingUsage {
        String columnName = eScannedInfo.getFieldsNamesToColumnsNamesMap().get(fieldName.trim());
//        System.out.println(eScannedInfo.getFieldsNamesToColumnsNamesMap().values());
//        System.out.println();
//        System.out.println(fieldName);
        if (columnName == null) {
            throw new IncorrectBuildingUsage("There is no such field name, available fields name: "+
                    eScannedInfo.getFieldsNamesToColumnsNamesMap().keySet());
        }
        return columnName.trim();
    }

    private void lastCharacterHasToBeSpace(StringBuilder builder) throws IncorrectBuildingUsage {
        if (!isLastCharacterSpace(builder)) {
            throw new IncorrectBuildingUsage("Please make sure that you are using methods in a right way" +
                    " use help() function if you need hint");
        }
    }

    private void lastCharacterHasNotToBeSpace(StringBuilder builder) throws IncorrectBuildingUsage {
        if (isLastCharacterSpace(builder)) {
            throw new IncorrectBuildingUsage("Please make sure that you are using methods in a right way" +
                    " use help() function if you need hint");
        }
    }

    private void checkingCapacity(StringBuilder builder) throws IncorrectBuildingUsage {
        if (builder.capacity() == 0) {
            throw new IncorrectBuildingUsage("don't use and(), or(), not() methods before any condition");
        }
    }

    private boolean isLastCharacterSpace(StringBuilder builder) {
        if (builder.length() == 0) {
            return false;
        }
        return builder.charAt(builder.length() - 1) == ' ';
    }

    public String help() {
        return "Please make sure that you are using methods in a right way." +
                "Use condition methods before any AND, OR, NOT operators." +
                "Don't use operators one after another without condition between them." +
                "Don't use conditions one after another without operators between them." +
                "Don't use operators before build() method. ";
    }

}
