# microblog
A spring boot template project

![school project](https://img.shields.io/badge/-SCHOOL_PROJECT-critical)
![template](https://img.shields.io/badge/-TEMPLATE_PROJECT-informational)

---

## Requirements

- [Node.js](https://nodejs.org)


## Starting the app

### VSCode

1. install frontend dependencies with `npm install`
2. build the frontend
   - Once with `npm run build`
   - In watch mode `npm run watch`
3. run the application
   - **spring-boot dashboard** 
     1. right click the grey dot and select *Start*
     2. wait for maven do download your dependencies (might take a lot of time) and the application to start
     3. right click again on the now green dot and *Open In Browser*
   - normal run
     1. run: *Start Without Debugging* 
     2. open your browser and go to `http://localost:8080`


### Outside VSCode

1. install frontend dependencies with `npm install`
2. build the frontend
   - Once with `npm run build`
   - In watch mode `npm run watch`
3. set your main class to *edu.marconivr.jacopo.microblog.Application*
4. build the application
5. open your browser and go to `http://localost:8080`


## Notes

- Swagger documentation is at `/rest/swagger.json`

- If you don't have Java 14 i suggest you update `<java.version>` in your `pom.xml` to solve compilation issues.
