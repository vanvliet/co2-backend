FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE

ENV HOME=/home/appuser
ENV APP_HOME=$HOME/app

RUN mkdir -p $APP_HOME

RUN groupadd -g 1001 appuser && useradd -u 1001 -g appuser -s /bin/sh appuser

RUN chown -R appuser:appuser $HOME
RUN chmod -R 777 $HOME

USER appuser
WORKDIR $HOME

COPY $JAR_FILE $APP_HOME/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "app/app.jar"]
