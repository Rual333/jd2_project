package by.it.academy.cv.service.builder;

import by.it.academy.cv.exeptions.IncorrectEntityDefinitionExpression;
import by.it.academy.cv.service.entityscanner.EntityFieldsScanner;
import by.it.academy.cv.service.entityscanner.EntityNamesScanner;
import lombok.Getter;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Getter
public class BuilderJoinOnHandler {


    private final EntityNamesScanner entityNamesScanner;
    private EntityFieldsScanner fieldClassScanner;
    private String tableName;
    private String primaryKeyName;
    private String relatedClassTableName;
    private String joinTableName;
    private String joinColumnName;
    private String inverseJoinColumnName;
    private Set<String> primaryKeyWithTableNames;

    public BuilderJoinOnHandler() {
        entityNamesScanner = new EntityNamesScanner();
        fieldClassScanner = new EntityFieldsScanner();
        primaryKeyWithTableNames = new HashSet<>();
    }

    public String getJoinOnCondition(Field field) throws IncorrectEntityDefinitionExpression {
        if (field.isAnnotationPresent(OneToOne.class)) {
            return getOneToOneCondition(field);
        }
        if (field.isAnnotationPresent(OneToMany.class)) {
            return getOneToManyCondition(field);
        }
        if (field.isAnnotationPresent(ManyToOne.class)) {
            return getManyToOneCondition(field);
        }
        if (field.isAnnotationPresent(ManyToMany.class)) {
            return getManyToManyCondition(field);
        }
        return "";
    }

    public String getOneToOneCondition(Field field) throws IncorrectEntityDefinitionExpression {
        final Class<?> fieldType = field.getType();
        tableName = entityNamesScanner.scanTableName(field.getDeclaringClass());
        relatedClassTableName = entityNamesScanner.scanTableName(fieldType);
        if (field.isAnnotationPresent(JoinColumn.class)) {
            primaryKeyName = entityNamesScanner.scanJoinColumnName(field);
        } else {
            OneToOne annotation = field.getAnnotation(OneToOne.class);
            String fieldName = annotation.mappedBy();
            try {
                Field relatedField = fieldType.getDeclaredField(fieldName);
                primaryKeyName = entityNamesScanner.scanJoinColumnName(relatedField);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new IncorrectEntityDefinitionExpression("there is no field to which mappedBy points");
            }
        }
        return buildCondition();
    }

    public String getOneToManyCondition(Field field) throws IncorrectEntityDefinitionExpression {
        final Class<?> fieldType = fieldClassScanner.getFieldClass(field);
        tableName = entityNamesScanner.scanTableName(field.getDeclaringClass());
        relatedClassTableName = entityNamesScanner.scanTableName(fieldType);
        OneToMany annotation = field.getAnnotation(OneToMany.class);
        String fieldName = annotation.mappedBy();
        try {
            Field relatedField = fieldType.getDeclaredField(fieldName);
            primaryKeyName = entityNamesScanner.scanJoinColumnName(relatedField);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new IncorrectEntityDefinitionExpression("there is no field to which mappedBy points");
        }
        return buildCondition();
    }

    public String getManyToOneCondition(Field field) throws IncorrectEntityDefinitionExpression {
        final Class<?> fieldType = field.getType();
        tableName = entityNamesScanner.scanTableName(field.getDeclaringClass());
        relatedClassTableName = entityNamesScanner.scanTableName(fieldType);
        primaryKeyName = entityNamesScanner.scanJoinColumnName(field);
        return buildCondition();
    }

    public String getManyToManyCondition(Field field) throws IncorrectEntityDefinitionExpression {
        final Class<?> fieldType = fieldClassScanner.getFieldClass(field);
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        tableName = entityNamesScanner.scanTableName(field.getDeclaringClass());
        relatedClassTableName = entityNamesScanner.scanTableName(fieldType);
        if (field.isAnnotationPresent(JoinTable.class)) {
            JoinTable joinTableAnnotation = field.getAnnotation(JoinTable.class);
            joinTableName = joinTableAnnotation.name();
            JoinColumn joinColumn = joinTableAnnotation.joinColumns()[0];
            joinColumnName = joinColumn.name();
            JoinColumn inverseJoinColumn = joinTableAnnotation.inverseJoinColumns()[0];
            inverseJoinColumnName = inverseJoinColumn.name();
            if (joinTableName.isEmpty()) {
                String tableName = entityNamesScanner.scanTableName(fieldType);
                joinTableName = entityNamesScanner.scanTableName(field.getDeclaringClass()) +
                        "_" + tableName;
            }
        } else {
            String fieldName = annotation.mappedBy();
            try {
                Field relatedField = fieldType.getDeclaredField(fieldName);
                return getManyToManyCondition(relatedField);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new IncorrectEntityDefinitionExpression("there is no field to which mappedBy points");
            }
        }
        return buildManyToManyCondition();
    }

    private String buildManyToManyCondition() {
        primaryKeyWithTableNames.add(relatedClassTableName + "." + inverseJoinColumnName);
        return " LEFT OUTER JOIN " + joinTableName + " ON " +
                tableName + "." + joinColumnName + "=" +
                joinTableName + "." + joinColumnName +
                " LEFT OUTER JOIN " + relatedClassTableName + " ON " +
                relatedClassTableName + "." + inverseJoinColumnName + "=" +
                joinTableName + "." + inverseJoinColumnName + " ";
    }

    private String buildCondition() {
        StringBuilder leftPK = new StringBuilder().append(tableName).append(".").append(primaryKeyName);
        StringBuilder rightPK = new StringBuilder().append(relatedClassTableName).append(".").append(primaryKeyName);
        primaryKeyWithTableNames.add(leftPK.toString());
        primaryKeyWithTableNames.add(rightPK.toString());
        return " LEFT OUTER JOIN " + relatedClassTableName + " ON " +
                leftPK + "=" + rightPK +
                " ";

    }
}
