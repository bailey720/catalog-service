# catalog-service

## Coding

**Build image via Spring Boot**

```aiignore
# maven
mvn spring-boot:build-image

# gradle
./gradlew bootBuildImage
```

**Build image via Spring Boot with container registry values** \
Upon completion, GitHub will contain the container registry (see `github -> profiile -> packages`) and Docker will contain the image (`docker image ls`).

NOTE: don't forget to replace `<github_username>` and `<github_token>` in the example. \
NOTE: variable names with dots (`.`) may need to be surrounded with quotes in the Windows terminal.

```aiignore
# maven
mvn spring-boot:build-image -DskipTests -DregistryUrl=ghcr.io -DregistryUsername=<github_username> -DregistryToken=<github_token> -Dspring-boot.build-image.publish=true -Dspring-boot.build-image.imageName=ghcr.io/<github_usernme>/catalog-service -f pom.xml
```

## Docker

### Network

**Create a network inside which images can talk to each other**

```aiignore
docker network create catalog-network
```

### Containerization

**Run postgreSQL container**

```aiignore
docker run -d --name polar-postgres --net catalog-network -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=polardb_catalog -p 5432:5432 postgres:14.12
```

**Build catalog-service container image**

```aiignore
docker build --build-arg JAR_FILE=target/*.jar -t catalog-service .
```

**Run catalog-service container**

```aiignore
docker run -d --name catalog-service --net catalog-network -p 9001:9001 -e SPRING_DATASOURCE_URL=jdbc:postgresql://polar-postgres:5432/polardb_catalog -e SPRING_PROFILES_ACTIVE=testdata catalog-service
```

**Start a container, joining the network**

Note: Check if the network exists before running this.

```aiignore
docker run -d --name polar-postgres --net catalog-network -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=polardb_catalog -p 5432:5432 postgres:14.4
```

## Kubernetes

**Start/Stop Minikube**
```aiignore
minikube start
```
```aiignore
minikube stop
```
**Load a local image**

```aiignore
minikube image load catalog-service:0.0.1-SNAPSHOT
```

**Set a deployment resource instance**

NOTE: In this command, `kubectl create` creates a K8S resource, `deployment` is the type of resource to create, `catalog-service` is the name of the deployment and `--image...` is the image to run.

```aiignore
kubectl create deployment catalog-service --image=catalog-service:0.0.1-SNAPSHOT
```

**Get deployments**

```aiignore
kubectl get deployment
```

**Get pods**

```aiignore
kubectl get pod
```

**Make pod accessible (exposed)**

NOTE: In this command, `kubectl expose` exposes a K8S resource, `deployment` is the type of resource to expose, `catalog-service` is the name of the deployment to expose, `--name=...` is the name of the service and `--port=...` is the port number from which the service is exposed.

```aiignore
kubectl expose deployment catalog-service --name=catalog-service --port=8080
```

**Check that the application is exposed**

```aiignore
kubectl get service catalog-service
```

**Forward the service's port**

NOTE: In this command, `kubectl port-foreward` begins the forwarding command, `service/catalog-service` identifies which resource to expose, `8000` is the port on your localhost and `8080` is the port of the service.

```aiignore
kubectl port-forward service/catalog-service 8000:8080
```

**Delete the service**

```aiignore
kubectl delete service catalog-service
```

**Delete the deployment**

```aiignore
kubectl delete deployment catalog-service
```
