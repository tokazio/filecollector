# File collector

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