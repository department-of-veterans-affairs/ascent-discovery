FROM jluck/ascent-base

ENV JAR_FILE "/ascent-discovery.jar"
ADD target/ascent-discovery-*.jar $JAR_FILE

