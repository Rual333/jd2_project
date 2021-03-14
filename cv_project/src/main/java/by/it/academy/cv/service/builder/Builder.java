package by.it.academy.cv.service.builder;

import by.it.academy.cv.exeptions.IncorrectBuildingUsage;

import java.util.List;

public interface Builder {

    String getResultQuery();

    List<String> getQueryParameters();

    void build() throws IncorrectBuildingUsage;
}
