# File collector
![Java CI with Gradle](https://github.com/tokazio/filecollector/workflows/Java%20CI%20with%20Gradle/badge.svg)

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