package by.it.academy.cv.service.builder;


import by.it.academy.cv.exeptions.IncorrectBuildingUsage;

import java.util.List;

public class QueryGenerator {

    private final String resultQuery;

    private final List<String> params;

    public QueryGenerator(Builder builder) throws IncorrectBuildingUsage {
        builder.build();
        resultQuery=builder.getResultQuery();
        params=builder.getQueryParameters();
    }

    public String getResultQuery() {
        return resultQuery;
    }

    public List<String> getParams() {
        return params;
    }
}
