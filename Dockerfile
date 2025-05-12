FROM tomcat:10.1-jdk17-temporal

COPY target/B2CPlatform.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]
