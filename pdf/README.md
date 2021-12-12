# opener

## Run application
First you need to build the app:
```
mvn clean install
```

Then download node_modules, which you need to run frontend application:
```
cd frontend
npm install
```

Then, build the docker images:
```
cd ..
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

## Technologies
The project content following technologies:
- React - frontend
- Spring - server
- MySQL - database
- Apache Tika - detecting files' types
- Jackson - parsing JSONs
- Apache PDFBox - generating PDF files
- iText PDF - generating DPDF files
- rsuite - React components
- Docker and Docker-compose - containerizing application

## Architecture
![architecture](Architecture.png)

