FROM openjdk:15-alpine

RUN addgroup -S bloggroup
RUN adduser -S --disabled-password --no-create-home blog -G bloggroup

COPY jarpack/*.jar /app.jar

RUN chown blog:bloggroup /app.jar

USER blog
EXPOSE 8080

ENV JAVA_OPTS=""
ENV APPLICATION_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $APPLICATION_OPTS" ]
