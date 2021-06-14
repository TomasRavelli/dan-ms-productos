FROM openjdk:11.0.7-slim 
LABEL maintainer="tomyravelli@gmail.com" 
ARG JAR_FILE 
ADD target/${JAR_FILE} dan-ms-productos.jar 
RUN echo ${JAR_FILE} 
ENTRYPOINT ["java","-jar","/dan-ms-productos.jar"]