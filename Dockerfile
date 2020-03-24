FROM openjdk:13-alpine
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8082
ENTRYPOINT ["java","-Dspring.profiles.active=production","-cp","app:app/lib/*","com.slyszmarta.bemygoods.BeMyGoodsApplication"]
