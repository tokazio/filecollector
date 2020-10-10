package fr.tokazio.filecollector;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CollectorEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorEngine.class);

    private static final int cores = Runtime.getRuntime().availableProcessors();
    private final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(cores);
    private final List<CollectorFilter> fileFilters = new LinkedList<>();
    private final List<CollectorFilter> dirFilters = new LinkedList<>();
    private List<File> files;

    public List<File> collect(final String pathName) {
        LOGGER.info("Collecting '" + pathName + "' with " + cores + " core(s)...");
        files = new CopyOnWriteArrayList<>();
        final long start = System.currentTimeMillis();
        async(Paths.get(pathName), 0);
        while (executorService.getActiveCount() > 0) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Collectiong ends with " + files.size() + " file(s) in " + (System.currentTimeMillis() - start) + "ms");
        executorService.shutdown();
        return files;
    }

    private void async(final Path path, final int level) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    CollectorEngine.this.walk(path, level);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void walk(final Path path, final int level) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            entries:
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    for (CollectorFilter f : dirFilters) {
                        if (!f.filter(entry)) {
                            continue entries;
                        }
                    }
                    async(entry, level + 1);
                } else {
                    for (CollectorFilter f : fileFilters) {
                        if (!f.filter(entry)) {
                            continue entries;
                        }
                    }
                    files.add(entry.toFile());
                }
            }
        }
    }

    public CollectorEngine fileFilter(final CollectorFilter filter) {
        if (filter != null) {
            fileFilters.add(filter);
        }
        return this;
    }

    public CollectorEngine dirFilter(final CollectorFilter filter) {
        if (filter != null) {
            dirFilters.add(filter);
        }
        return this;
    }
}
