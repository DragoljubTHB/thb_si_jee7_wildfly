FROM airhacks/wildfly

ENV DEPLOYMENT_DIR ${WILDFLY_HOME}/standalone/deployments/
RUN rm ${WILDFLY_HOME}/bin/standalone.conf
ADD standalone.conf ${WILDFLY_HOME}

COPY ../../../target/si.war $DEPLOYMENT_DIR
