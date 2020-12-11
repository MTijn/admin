FROM openjdk:15-alpine

VOLUME /tmp
RUN addgroup -S bloggroup
RUN adduser -S --disabled-password --no-create-home blog -G bloggroup

VOLUME /tmp
COPY jarpack/${JAR_FILE} app.jar
COPY jarpack/libs libs
RUN chown blog:bloggroup /app.jar
RUN chown -R blog:bloggroup /libs

USER blog
EXPOSE 8080

ENV JAVA_OPTS=""
ENV APPLICATION_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $APPLICATION_OPTS" ]
