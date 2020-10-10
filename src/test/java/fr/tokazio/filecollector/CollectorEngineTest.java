package fr.tokazio.filecollector;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectorEngineTest {

    @Test
    public void noFiltersShouldBeNotEmpty() {
        //Given
        final CollectorEngine engine = new CollectorEngine();
        //When
        final List<File> actual = engine.collect("./");

        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    public void javaFileFilter() {
        //Given
        final CollectorEngine engine = new CollectorEngine();
        //When
        final List<File> actual = engine
                .fileFilter(new CollectorFilter() {
                    @Override
                    public boolean filter(Path path) {
                        return path.toString().endsWith(".java");
                    }
                }).collect("./");

        //Then
        assertThat(actual).hasSize(3);
    }

    @Test
    public void javaDirFilter() {
        //Given
        final CollectorEngine engine = new CollectorEngine();
        //When
        final List<File> actual = engine
                .dirFilter(new CollectorFilter() {
                    @Override
                    public boolean filter(Path path) {
                        return path.toString().equals("java");
                    }
                }).collect("./");

        //Then
        assertThat(actual).hasSize(7);
    }
}
