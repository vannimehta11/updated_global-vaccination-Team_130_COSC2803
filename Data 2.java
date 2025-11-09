package app;

import java.util.*;
import java.util.stream.Collectors;

public class Data {
    public static final List<Row> ROWS = Arrays.asList(
        new Row("Australia","High",2023,"Measles",7.0),
        new Row("India","Lower-Middle",2023,"Measles",22.0),
        new Row("Kenya","Lower-Middle",2023,"Measles",28.0),
        new Row("Brazil","Upper-Middle",2023,"Measles",19.5),
        new Row("Germany","High",2023,"Measles",8.4),
        new Row("Nepal","Lower-Middle",2023,"Measles",null)
    );

    public static List<Row> filter(int year, String disease, String econ) {
        return ROWS.stream()
                .filter(r -> r.year == year && r.disease.equals(disease))
                .filter(r -> econ.equals("All") || r.region.equals(econ))
                .toList();
    }
}