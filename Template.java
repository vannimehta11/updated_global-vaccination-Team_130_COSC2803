package app;

public class Template {
    public static String wrap(String title, String body) {
        return """
        <!DOCTYPE html>
        <html><head><meta charset='UTF-8'>
        <title>""" + title + """</title>
        <style>
            /* Enhanced CSS with tooltips and visual highlights */
            body{font-family:Segoe UI,Arial,sans-serif;margin:20px;background:#f8fafc;color:#0f172a}
            .nav a{margin-right:10px;color:#1e3a8a;text-decoration:none;font-weight:500}
            .card{background:#fff;border:1px solid #cbd5e1;border-radius:10px;padding:16px;margin-bottom:20px}
            table{border-collapse:collapse;width:100%;margin-top:10px}
            th,td{border:1px solid #d1d5db;padding:6px 12px;text-align:left}
            th{background:#e2e8f0}
            button{background:#111827;color:white;padding:6px 12px;border:none;border-radius:8px;cursor:pointer}
            button:hover{background:#1f2937}
            
            /* Tooltip styles */
            .tooltip {
                position: relative;
                display: inline-block;
                border-bottom: 1px dotted #3b82f6;
                cursor: help;
                margin: 0 2px;
            }
            
            .tooltip .tooltiptext {
                visibility: hidden;
                width: 280px;
                background-color: #1f2937;
                color: white;
                text-align: center;
                border-radius: 6px;
                padding: 10px;
                position: absolute;
                z-index: 1000;
                bottom: 125%;
                left: 50%;
                margin-left: -140px;
                opacity: 0;
                transition: opacity 0.3s;
                font-size: 13px;
                font-weight: normal;
                line-height: 1.4;
            }
            
            .tooltip:hover .tooltiptext {
                visibility: visible;
                opacity: 1;
            }

            /* Visual highlighting styles */
            .highlight-box {
                background-color: #fef3c7;
                border: 2px solid #f59e0b;
                border-radius: 8px;
                padding: 12px 16px;
                margin: 15px 0;
                font-weight: 600;
            }

            .highlight-warning {
                background-color: #fee2e2;
                border: 2px solid #ef4444;
            }

            .highlight-success {
                background-color: #d1fae5;
                border: 2px solid #10b981;
            }

            /* Explanation text styles */
            .explanation {
                background-color: #f8fafc;
                border-left: 4px solid #3b82f6;
                padding: 12px 16px;
                margin: 15px 0;
                font-size: 14px;
                line-height: 1.5;
                border-radius: 0 8px 8px 0;
            }

            .key-term {
                font-weight: 600;
                color: #1e40af;
            }
        </style>
        </head><body>
        <nav class='nav'>
            <a href='/page1'>1B — Mission</a> |
            <a href='/page2'>2B — Infections</a> |
            <a href='/page3'>3B — Above Average</a>
        </nav>
        """ + body + "</body></html>";
    }
}