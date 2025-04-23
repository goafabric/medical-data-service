# docker compose
go to /src/deploy/docker and do "./stack up"

# run jvm multi image
docker run --pull always --name medical-data-service --rm -p50500:50500 goafabric/medical-data-service:$(grep '^version=' gradle.properties | cut -d'=' -f2)

# run native image
docker run --pull always --name medical-data-service-native --rm -p50500:50500 goafabric/medical-data-service-native:$(grep '^version=' gradle.properties | cut -d'=' -f2) -Xmx32m

# run native image arm
docker run --pull always --name medical-data-service-native --rm -p50500:50500 goafabric/medical-data-service-native-arm64v8:$(grep '^version=' gradle.properties | cut -d'=' -f2) -Xmx32m
                                              

