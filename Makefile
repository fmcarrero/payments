build-image:
		docker build -t myapp:latest .
build-image-build:
		DOCKER_BUILDKIT=1  docker build -t myapp:latest --target build .

run-integration-test:
		docker-compose -f Docker-compose.ci.yml run --build app sh -c "./gradlew integrationTest"