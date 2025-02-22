docker rmi -f reactive_post_liqui
docker run  -p 5432:5432 --name postgres  -e POSTGRES_PASSWORD=test  -v C:\Data\volume:/var/lib/postgresql/data postgres
mvn clean package -DskipTests=true
docker pull redis
docker pull postgres
docker pull openjdk:17-jdk-slim 
docker build -t reactive_post_liqui:latest  .


