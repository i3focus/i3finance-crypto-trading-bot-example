FROM adoptopenjdk/openjdk11

LABEL version="1.0.0"
LABEL maintainer="Jether Rodrigues <jether@i3focus.com.br>"
LABEL service="I3finance - Trading Bot Example"

ARG JAR_FILE
COPY ${JAR_FILE} i3finance-trading-bot-example.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -Dlog4j2.formatMsgNoLookups=true","-jar","/i3finance-trading-bot-example.jar"]