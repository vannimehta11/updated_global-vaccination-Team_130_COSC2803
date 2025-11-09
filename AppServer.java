package app;

import io.javalin.Javalin;

public class AppServer {
    public static void main(String[] args) {
        Javalin app = Javalin.create(cfg -> cfg.staticFiles.add("/public")).start(7000);

        app.get("/", ctx -> ctx.redirect("/page1"));
        app.get("/page1", ctx -> ctx.html(Page1B.render()));
        app.get("/page2", ctx -> {
            int year = Integer.parseInt(ctx.queryParam("year", "2023"));
            String disease = ctx.queryParam("disease", "Measles");
            String econ = ctx.queryParam("econ", "All");
            ctx.html(Page2B.render(year, disease, econ));
        });
        app.get("/page3", ctx -> {
            int year = Integer.parseInt(ctx.queryParam("year", "2023"));
            String disease = ctx.queryParam("disease", "Measles");
            ctx.html(Page3B.render(year, disease));
        });

        System.out.println("✅ Running on http://localhost:7000");
    }
}
