package fr.tokazio.filecollector;

import java.nio.file.Path;

public interface CollectorFilter {

    boolean filter(Path path);
}
