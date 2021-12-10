# opener

First you need to build the app:
```
mvn clean install
```

Then, build the docker images:
```
docker-compose build
```

From now on, you can start our app by just using:
```
docker-compose up
```

To turn everything off, press `Ctrl+C` and then type:
```
docker-compose down
```
to remove the containers.
