FROM openjdk:13-alpine
ARG lastfm_api_key
ARG jwt_secret
ENV lastfm_api_key ${lastfm_api_key}
ENV jwt_secret ${jwt_secret}
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8082
ENTRYPOINT ["java","-cp","app:app/lib/*","com.slyszmarta.bemygoods.BeMyGoodsApplication"]
