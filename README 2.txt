Global Vaccination — Level B (Web Application)
==============================================

Includes pages for:
  1B — Mission
  2B — Infections by Economic Status
  3B — Above-Average Infection Rate

How to run:
  mvn clean compile exec:java -Dexec.mainClass=app.AppServer
or
  mvn package
  java -cp target/classes;target/dependency/* app.AppServer

Then open http://localhost:7000

No JavaScript required. All HTML rendered server-side with Javalin.
