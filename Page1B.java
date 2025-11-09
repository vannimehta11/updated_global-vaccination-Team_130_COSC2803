package app;

public class Page1B {
    public static String render() {
        String html = """
        <div class='card'>
          <h1>Global Vaccination — Mission (1B)</h1>
          <p>Our mission is to help people make sense of infection and immunisation data
          without jargon. We translate complex global datasets into clear, plain-English insights.</p>

          <div class='explanation'>
            <span class='key-term'>How this works:</span> This dashboard provides infection rate data organized by economic development levels. 
            <span class='tooltip'>Economic phases
                <span class='tooltiptext'>Countries grouped by income levels: High, Upper-Middle, Lower-Middle, Low income economies</span>
            </span> help identify healthcare access disparities across different development stages.
          </div>

          <h3>How to Use</h3>
          <ol>
            <li>Go to <a href='/page2'>2B – Infections by Economic Status</a> to view infection data by filters.</li>
            <li>Go to <a href='/page3'>3B – Above-Average Infection Rate</a> to explore deeper trends.</li>
          </ol>

          <h3>Personas</h3>
          <ul>
            <li><strong>Amara Okoye</strong> – NGO Coordinator who needs quick, exportable data views.</li>
            <li><strong>Daniel Reid</strong> – School Teacher who wants simplified visuals and clear terms.</li>
          </ul>
        </div>
        """;
        return Template.wrap("1B – Mission", html);
    }
}