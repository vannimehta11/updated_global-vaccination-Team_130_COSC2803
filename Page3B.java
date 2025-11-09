package app;

import java.util.*;

public class Page3B {
    public static String render(int year, String disease) {
        List<Row> rows = Data.filter(year, disease, "All");
        double avg = rows.stream().filter(r -> r.vaccinationRate != null)
                .mapToDouble(r -> r.vaccinationRate).average().orElse(0);

        StringBuilder sb = new StringBuilder();
        sb.append("<div class='card'><h1>3B — Above-Average Infection Rate</h1>")
          .append("<div class='explanation'>")
          .append("<span class='key-term'>Calculation:</span> Global average infection rate is computed from all countries with available data. This page shows countries with rates <strong>above this average</strong>, indicating areas that may need targeted public health interventions.")
          .append("</div>")
          .append("<form method='get' action='/page3'>")
          .append("Year <input name='year' value='").append(year).append("' size='5'/> ")
          .append("Disease <input name='disease' value='").append(disease).append("' size='10'/> ")
          .append("<button type='submit'>Apply</button></form>")
          .append("<p><strong>Global average:</strong> ")
          .append(String.format("%.1f", avg))
          .append(" per 100k</p></div>");

        // Add visual highlight for above-average countries
        long aboveAvgCount = rows.stream()
            .filter(r -> r.vaccinationRate != null && r.vaccinationRate > avg)
            .count();
            
        if (aboveAvgCount > 0) {
            sb.append("<div class='highlight-box highlight-warning'>")
              .append("🔥 <strong>Priority Areas:</strong> These countries have infection rates above global average and may need urgent public health attention.")
              .append("</div>");
        }

        sb.append("<table><tr><th>Country</th><th>Phase</th><th>Infections / 100k</th></tr>");
        rows.stream()
            .filter(r -> r.vaccinationRate != null && r.vaccinationRate > avg)
            .sorted((a,b) -> Double.compare(b.vaccinationRate, a.vaccinationRate))
            .forEach(r -> sb.append("<tr><td>").append(r.country)
                            .append("</td><td>").append(r.region)
                            .append("</td><td>").append(String.format("%.1f", r.vaccinationRate))
                            .append("</td></tr>"));
        sb.append("</table>");

        return Template.wrap("3B — Above Average", sb.toString());
    }
}