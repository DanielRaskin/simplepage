Very simple example of Jersey + Guice Web application with embedded Jetty server.
It's just little Web RESTful application. It shows how easy use Jersey with Guice (no Spring in this example),
how use HTML and JS content, how config gradle.

To build project, just type:

./gradlew jar

To run project, type:

cd build/libs

java -jar simplepage-0.01.jar

Then open http://localhost:8181/ in your browser.

That's all. No special Web server setup, you need only JDK.
