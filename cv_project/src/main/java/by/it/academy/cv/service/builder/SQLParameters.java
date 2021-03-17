package by.it.academy.cv.service.builder;

import by.it.academy.cv.exeptions.IncorrectBuildingUsage;

import java.util.ArrayList;
import java.util.List;

public class SQLParameters implements QueryParameters {

    private List<String> parameters;

    private SQLParameters(ParametersBuilder builder) {
        parameters = new ArrayList<>();
        parameters.add(builder.where.toString());
        parameters.add(builder.groupBy.toString());
        parameters.add(builder.having.toString());
        parameters.add(builder.order.toString());
    }

    @Override
    public List<String> getQueryParameters() {
        return parameters;
    }

    public static class ParametersBuilder {

        private StringBuilder where;
        private StringBuilder groupBy;
        private StringBuilder having;
        private StringBuilder order;
        private boolean ifItsWhere;
        private boolean conditionPresent;
        private boolean operatorPresent;

        public ParametersBuilder() {
            init();
        }

        private void init() {
            where = new StringBuilder(0);
            groupBy = new StringBuilder(0);
            having = new StringBuilder(0);
            order = new StringBuilder(0);
            ifItsWhere = true;
        }

        public ParametersBuilder and() throws IncorrectBuildingUsage {
            addingOperator(" AND ");
            return this;
        }

        public ParametersBuilder or() throws IncorrectBuildingUsage {
            addingOperator(" OR ");
            return this;
        }

        public ParametersBuilder not() throws IncorrectBuildingUsage {
            addingOperator(" NOT ");
            return this;
        }

        private ParametersBuilder addingOperator(String operator) throws IncorrectBuildingUsage {
            if (ifItsWhere) {
                return addOperatorToWhere(operator);
            } else {
                return addOperatorToHaving(operator);
            }
        }

        private ParametersBuilder addOperatorToWhere(String operator) throws IncorrectBuildingUsage {
            checkingCapacity(where);
            isOperatorAlreadyPresent(where);
            where.append(operator);
            return this;
        }

        private ParametersBuilder addOperatorToHaving(String operator) throws IncorrectBuildingUsage {
            checkingCapacity(where);
            isOperatorAlreadyPresent(having);
            having.append(operator);
            return this;
        }

        public ParametersBuilder equal(String fieldName, String parameter) throws IncorrectBuildingUsage {
            return addingCondition(fieldName, "=", parameter);
        }

        public ParametersBuilder like(String fieldName, String parameter) throws IncorrectBuildingUsage {
            return addingCondition(fieldName, " LIKE ", parameter);
        }

        public ParametersBuilder greater(String fieldName, String parameter) throws IncorrectBuildingUsage {
            return addingCondition(fieldName, ">", parameter);
        }

        public ParametersBuilder less(String fieldName, String parameter) throws IncorrectBuildingUsage {
            return addingCondition(fieldName, "<", parameter);
        }

        public ParametersBuilder greaterOrEq(String fieldName, String parameter) throws IncorrectBuildingUsage {
            return addingCondition(fieldName, ">=", parameter);
        }

        public ParametersBuilder lessOrEq(String fieldName, String parameter) throws IncorrectBuildingUsage {
            return addingCondition(fieldName, "<=", parameter);
        }

        public ParametersBuilder notEqual(String fieldName, String parameter) throws IncorrectBuildingUsage {
            return addingCondition(fieldName, "<>", parameter);
        }

        private ParametersBuilder addingCondition(String fieldName, String condition, String parameter) throws IncorrectBuildingUsage {
            checkIfParametersNull(fieldName, parameter);
            parameter = "'" + parameter + "'";
            if (ifItsWhere) {
                return addConditionToWhere(fieldName, condition, parameter);
            } else {
                return addConditionToHaving(fieldName, condition, parameter);
            }

        }

        private ParametersBuilder addConditionToWhere(String fieldName, String condition, String parameter) throws IncorrectBuildingUsage {
            if (where.length() == 0) {
                where.append(" WHERE ");
                conditionPresent = false;
            }
            isConditionAlreadyPresent(where);
            where.append(fieldName).append(condition).append(parameter.trim());
            return this;
        }

        private ParametersBuilder addConditionToHaving(String fieldName, String condition, String parameter) throws IncorrectBuildingUsage {
            if (having.length() == 0) {
                having.append(" HAVING ");
                ifItsWhere = false;
                conditionPresent = false;
                operatorPresent = false;
            }
            isConditionAlreadyPresent(having);
            having.append(fieldName).append(condition).append(parameter.trim());
            return this;
        }

        private void isOperatorAlreadyPresent(StringBuilder builder) throws IncorrectBuildingUsage {
            if (operatorPresent) {
                throw new IncorrectBuildingUsage("Please make sure that you are using methods in a right way" +
                        " use help() function if you need hint");
            }
            operatorPresent = true;
            conditionPresent = false;
        }

        private void isConditionAlreadyPresent(StringBuilder builder) throws IncorrectBuildingUsage {
            if (conditionPresent) {
                throw new IncorrectBuildingUsage("Please make sure that you are using methods in a right way" +
                        " use help() function if you need hint");
            }
            conditionPresent = true;
            operatorPresent = false;
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

        public SQLParameters build() throws IncorrectBuildingUsage {
            checkBeforeBuild();
            return new SQLParameters(this);
        }

        private void checkBeforeBuild() throws IncorrectBuildingUsage {
            if (having.length() != 0) {
                isOperatorAlreadyPresent(having);
            }
            if (where.length() != 0) {
                isOperatorAlreadyPresent(where);
            }
        }

        private void checkIfParametersNull(String... strings) throws IncorrectBuildingUsage {
            for (String s : strings) {
                if (s == null) {
                    throw new IncorrectBuildingUsage("parameters has not to be null");
                }
            }
        }

        private void checkingCapacity(StringBuilder builder) throws IncorrectBuildingUsage {
            if (builder.capacity() == 0) {
                throw new IncorrectBuildingUsage("don't use and(), or(), not() methods before any condition");
            }
        }

    }
}
