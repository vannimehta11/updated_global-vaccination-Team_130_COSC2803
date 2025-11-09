Global Vaccination — A (JavaFX + CSS)
=====================================

Pure Java + CSS (JavaFX). Three tabs: 1A Mission, 2A Shallow Glance, 3A Deep Dive.

Run with JavaFX SDK (example Windows commands):
  mkdir out
  javac --module-path C:\javafx\javafx-sdk-21\lib --add-modules javafx.controls,javafx.graphics -d out src\main\java\app\*.java
  xcopy src\main\resources out /E /I /Y
  java --module-path C:\javafx\javafx-sdk-21\lib --add-modules javafx.controls,javafx.graphics -cp out app.AppMain
