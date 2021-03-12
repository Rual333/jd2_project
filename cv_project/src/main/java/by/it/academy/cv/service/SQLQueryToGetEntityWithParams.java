package by.it.academy.cv.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SQLQueryToGetEntityWithParams {

    String[] parameters;

    String query;

}
