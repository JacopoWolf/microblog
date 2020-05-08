# microblog
A spring boot template project

![school project](https://img.shields.io/badge/-SCHOOL_PROJECT-critical)
![template](https://img.shields.io/badge/-TEMPLATE_PROJECT-informational)

---

### Requirements

A TypeScript compiler is required to build the JavaScript necessary for the WebPage to work.

1. Install [Node.js](https://nodejs.org)
2. from *src/main/typescript* run `npm ci` to install dependencies
3. checkout directory `./src/main/typescript`
4. start the compiler by simply running `npm run tsc`

- *In alternative install typescript globally.*

**Note**: tsc will start in watch mode. remember to stop it.

### Notes

- Swagger documentation is at `/rest/swagger.json`

- If you don't have Java 14 i suggest you update `<java.version>` in your `pom.xml` to solve compilation issues.