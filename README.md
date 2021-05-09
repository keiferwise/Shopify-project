# Shopify-project
docker pull minio/mc 

docker run -p 9000:9000 minio/minio server /data

docker pull mysql    

docker run -p 3306:3306 --name img-repo-mysql -e MYSQL_ROOT_PASSWORD=26v99yv7jhqc4z8pv2gn -e MYSQL_DATABASE=imgrepo -e MYSQL_USER=spring -e MYSQL_PASSWORD=51t2q8zn3h8h26v94uui -d mysql   

