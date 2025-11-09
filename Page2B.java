package app;

import java.util.*;
import java.util.stream.Collectors;

public class Page2B {
    public static String render(int year, String disease, String econ) {
        List<Row> filtered = Data.filter(year, disease, econ);

        StringBuilder sb = new StringBuilder();
        sb.append("<div class='card'><h1>2B — Infections by Economic Status</h1>")
          .append("<div class='explanation'>")
          .append("<span class='key-term'>Infection Rates:</span> Shows disease cases per 100,000 people. Lower rates indicate better disease control. Data organized by ")
          .append("<span class='tooltip'>economic development level")
          .append("<span class='tooltiptext'>Countries grouped by income: High, Upper-Middle, Lower-Middle, Low. Helps identify healthcare access disparities.</span>")
          .append("</span> to highlight equity issues.")
          .append("</div>")
          .append("<form method='get' action='/page2'>")
          .append("Year <input name='year' value='").append(year).append("' size='5'/> ")
          .append("Disease <input name='disease' value='").append(disease).append("' size='10'/> ")
          .append("Economic Phase <input name='econ' value='").append(econ).append("' size='14'/> ")
          .append("<button type='submit'>Apply</button></form></div>");

        sb.append("<table><tr><th>Country</th><th>Economic Phase</th><th>Infections / 100k</th></tr>");
        for (Row r : filtered) {
            sb.append("<tr><td>").append(r.country).append("</td><td>")
              .append(r.region).append("</td><td>")
              .append(r.vaccinationRate == null ? "—" : String.format("%.1f", r.vaccinationRate))
              .append("</td></tr>");
        }
        sb.append("</table>");

        // Summary by phase
        Map<String, List<Row>> byPhase = filtered.stream().collect(Collectors.groupingBy(r -> r.region));
        sb.append("<div class='card'><h3>Summary by Economic Phase</h3>");
        
        // Add visual highlighting for concerning averages
        boolean hasHighInfections = false;
        for (var entry : byPhase.entrySet()) {
            var valid = entry.getValue().stream().filter(r -> r.vaccinationRate != null).toList();
            double avg = valid.stream().mapToDouble(r -> r.vaccinationRate).average().orElse(Double.NaN);
            if (!Double.isNaN(avg) && avg > 15) { // Threshold for high infections
                hasHighInfections = true;
            }
        }
        
        if (hasHighInfections) {
            sb.append("<div class='highlight-box highlight-warning'>")
              .append("⚠️ <strong>High Infection Rates:</strong> Some economic phases show elevated infection rates that may need attention.")
              .append("</div>");
        }
        
        sb.append("<ul>");
        for (var entry : byPhase.entrySet()) {
            var valid = entry.getValue().stream().filter(r -> r.vaccinationRate != null).toList();
            double avg = valid.stream().mapToDouble(r -> r.vaccinationRate).average().orElse(Double.NaN);
            sb.append("<li>").append(entry.getKey())
              .append(": Avg ").append(Double.isNaN(avg) ? "—" : String.format("%.1f", avg))
              .append("</li>");
        }
        sb.append("</ul></div>");

        return Template.wrap("2B — Infections", sb.toString());
    }
}