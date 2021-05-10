# Shopify-project
Run these docker commands:
docker pull minio/mc 

docker run -p 9000:9000 minio/minio server /data

docker pull mysql    

docker run -p 3306:3306 --name img-repo-mysql -e MYSQL_ROOT_PASSWORD=26v99yv7jhqc4z8pv2gn -e MYSQL_DATABASE=imgrepo -e MYSQL_USER=spring -e MYSQL_PASSWORD=51t2q8zn3h8h26v94uui -d mysql   

Run the JAR on the command line with:

java -jar imagerepo-0.0.1-SNAPSHOT.jar

Instructions on how to use it:

go to localhost:8080/create to upload an image

go to /images to see a list of images

click on view to view it.

click on delete to delete it.
