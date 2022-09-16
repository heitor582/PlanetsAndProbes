build:
    ./gradlew clean build
    cp build/libs/

up: build
    docker-compose up --build -d

down:
    docker-compose down --remove-orphans