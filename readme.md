# File collector
![Java CI with Gradle](https://github.com/tokazio/filecollector/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=tokazio_filecollector&metric=coverage)](https://sonarcloud.io/dashboard?id=tokazio_filecollector)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=tokazio_filecollector&metric=bugs)](https://sonarcloud.io/dashboard?id=tokazio_filecollector)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=tokazio_filecollector&metric=code_smells)](https://sonarcloud.io/dashboard?id=tokazio_filecollector)

Fast collect files into list from a given folder.
Use DirectoryStream API and multi threading.

```
final List<File> files = new CollectorEngine()
    .filter(new CollectorFilter() {
        @Override
        public boolean filter(Path path) {
            return path.toString().endsWith(".java");
        }
    })
    .dirFilternew CollectorFilter() {
        @Override
        public boolean filter(Path path) {
            return path.toString().equals("java");
        }
    })
    .collect("myFolder");
```
