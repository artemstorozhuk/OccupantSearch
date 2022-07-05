# Occupant Search

The service searches for dead russians mentioned in VKontakte. Features:
1. Uses [VK API](https://github.com/VKCOM/vk-java-sdk) to search for posts
2. [Periodic refresh of new posts](kotlin/com/occupantsearch/update/UpdateController.kt)
3. [Name detection in the post's text](kotlin/com/occupantsearch/person/PersonTextSearcher.kt)
4. [Face detection](kotlin/com/occupantsearch/image) using [OpenCV](https://opencv.org/)
5. Filter by name
6. [JSON file storage, loaded and stored in memory](kotlin/com/occupantsearch/db)
7. Pagination
8. [Posts count by date analytics](kotlin/com/occupantsearch/analytics/AnalyticsController.kt) + [chartjs](https://www.chartjs.org/)
9. WebApp styled with [MUI](https://mui.com/)
10. [Export JSON/CSV](kotlin/com/occupantsearch/export/ExportController.kt)

## Usage

### Server
[Kotlin + ktor](server)

To run locally:
```
./gradlew :server:devRun
```
Server is started at port is 8080.

### Client
[Typescript + React](client)

To run locally:
```
cd client
npm install
npm run
```
Open [localhost:3000](localhost:3000). Hot code reload is enabled.

### Production build
```
./gradlew :server:assembleDist
```
Webapp will output into [`server/src/resources/app`](client/.env).

See [deployment script](deployment/deploy.sh) for script I use to deploy.

## Demo
Demo hosted at my Raspberry PI: [http://81.102.49.163:8080/](http://81.102.49.163:8080/)

![Demo](demo/image1.jpg)
![Demo](demo/image2.jpg)

## Contributing

Open PR and ping me.

## License
What is this?