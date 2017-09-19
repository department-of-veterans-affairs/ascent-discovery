FROM java:8

ADD target/ascent-discovery-*.jar /ascent-discovery.jar
ENTRYPOINT ["java", "-Xms64m", "-Xmx256m", "-jar", "/ascent-discovery.jar"]
