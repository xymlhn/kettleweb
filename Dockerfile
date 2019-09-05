# FROM 指定使用哪个镜像作为基准
FROM openjdk:8-jdk-alpine
ADD target/*.jar /app.jar
# VOLUME 为挂载路径  -v
VOLUME /tmp
# RUN 为初始化时运行的命令  touch 更新 app.jar
RUN sh -c 'touch /app.jar'
# ENV 为设置环境变量
ENV JAVA_OPTS=""
# ENTRYPOINT 为启动时运行的命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
