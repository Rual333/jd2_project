package by.it.academy.cv.service.builder;


import by.it.academy.cv.exeptions.IncorrectBuildingUsage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SQLBuiilderResultQueryAndParams {

    private List<String> parameters;

    private String query;

    SQLBuiilderResultQueryAndParams() {
        parameters = new ArrayList<>();
    }

    public void addParameter(String parameter) throws IncorrectBuildingUsage {

            parameters.add(parameter);

    }

}
