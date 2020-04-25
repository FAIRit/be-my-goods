./gradlew clean build
cd build
mkdir -p dependency
cd dependency
jar -xf ../libs/*.jar
docker-compose build --build-arg LASTFM_API_KEY="${LASTFM_API_KEY}" --build-arg JWT_SECRET="${JWT_SECRET}"
docker-compose up