package by.it.academy.cv.service.entityscanner;

import by.it.academy.cv.service.builder.SQLBuilder;

public class SQLDirector {

    SQLBuilder sqlBuilder;

    SQLDirector(SQLBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }
}
