build-image:
		docker build -t myapp:latest .
build-image-build:
		DOCKER_BUILDKIT=1  docker build -t myapp:latest --target build .

run-test:
		./gradlew test
run-integration-test:
		docker-compose -f Docker-compose.ci.yml  run  app sh -c "./gradlew integrationTest"
