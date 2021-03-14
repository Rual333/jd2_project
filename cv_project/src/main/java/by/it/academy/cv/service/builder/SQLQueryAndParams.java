package by.it.academy.cv.service.builder;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SQLQueryAndParams {

    private List<String> parameters;

    private String query;

}
