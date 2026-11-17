package io.github.smartdoc.plugin.util;

import io.github.smartdoc.model.SourceCodePath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link MojoUtils#mergeSourceCodePaths(File, String, List, java.util.Collection)}.
 * <p>
 * The user configured {@code sourceCodePaths} must be additive with the paths derived
 * from {@code codePath}, which is the behaviour requested in
 * smart-doc-group/smart-doc#54.
 */
class MojoUtilsMergeTest {

    /**
     * User configured source paths must survive the codePath based derivation, and a user
     * path that points to the same directory as codePath must be de-duplicated.
     * @param tempDir temporary directory provided by JUnit
     */
    @DisplayName("merges user source paths with the paths derived from codePath")
    @Test
    void userPathsAreMergedWithCodePathDerivedPaths(@TempDir Path tempDir) {
        File basedir = new File(tempDir.toFile(), "project");
        String moduleGenerated = new File(tempDir.toFile(),
                "module-a/target/generated-sources/annotations").getAbsolutePath();
        List<SourceCodePath> userPaths = Arrays.asList(
                SourceCodePath.builder().setPath("src/main/java").setDesc("base"),
                SourceCodePath.builder().setPath("target/generated-sources/annotations").setDesc("generated"));

        List<SourceCodePath> merged = MojoUtils.mergeSourceCodePaths(basedir, "src/main/java", userPaths,
                Arrays.asList(moduleGenerated));

        // src/main/java appears once: the user path is de-duplicated against codePath
        assertEquals(1, count(merged, new File(basedir, "src/main/java")));
        assertEquals(1, count(merged, new File(basedir, "target/generated-sources/annotations")));
        assertEquals(1, count(merged, new File(moduleGenerated)));
        assertEquals(3, merged.size());
    }

    /**
     * Without a user configured path the result must equal the legacy codePath
     * derivation, so the default automatic source detection stays unchanged.
     * @param tempDir temporary directory provided by JUnit
     */
    @DisplayName("keeps the legacy codePath derivation when no user path is configured")
    @Test
    void noUserPathsKeepsTheLegacyCodePathDerivation(@TempDir Path tempDir) {
        File basedir = new File(tempDir.toFile(), "project");
        String moduleGenerated = new File(tempDir.toFile(),
                "module-a/target/generated-sources/annotations").getAbsolutePath();

        List<SourceCodePath> merged = MojoUtils.mergeSourceCodePaths(basedir, "src/main/java", null,
                Arrays.asList(moduleGenerated));

        assertEquals(2, merged.size());
        assertEquals(new File(moduleGenerated).getAbsolutePath(),
                new File(merged.get(0).getPath()).getAbsolutePath());
        assertEquals(new File(basedir, "src/main/java").getAbsolutePath(),
                new File(merged.get(1).getPath()).getAbsolutePath());
    }

    /**
     * A relative user path must be resolved against the project base directory instead of
     * the process working directory, which may point to the reactor root.
     * @param tempDir temporary directory provided by JUnit
     */
    @DisplayName("resolves a relative user path against the project base dir")
    @Test
    void relativeUserPathIsResolvedAgainstBasedir(@TempDir Path tempDir) {
        File basedir = new File(tempDir.toFile(), "project");

        List<SourceCodePath> merged = MojoUtils.mergeSourceCodePaths(basedir, "src/main/java",
                Arrays.asList(SourceCodePath.builder().setPath("target/generated-sources/annotations")), null);

        assertEquals(1, count(merged, new File(basedir, "target/generated-sources/annotations")));
    }

    /**
     * Two user entries that resolve to the same directory must be merged into a single
     * entry by their absolute path.
     * @param tempDir temporary directory provided by JUnit
     */
    @DisplayName("de-duplicates user paths that point to the same directory")
    @Test
    void duplicateUserPathsAreDeDuplicated(@TempDir Path tempDir) {
        File basedir = new File(tempDir.toFile(), "project");
        String generated = new File(basedir, "target/generated-sources/annotations").getAbsolutePath();

        List<SourceCodePath> userPaths = Arrays.asList(
                SourceCodePath.builder().setPath("target/generated-sources/annotations"),
                SourceCodePath.builder().setPath(generated));

        List<SourceCodePath> merged = MojoUtils.mergeSourceCodePaths(basedir, "src/main/java", userPaths, null);

        assertEquals(1, count(merged, new File(generated)));
    }

    /**
     * Null entries and entries with a blank path must be skipped without failing the
     * merge.
     * @param tempDir temporary directory provided by JUnit
     */
    @DisplayName("ignores null or blank user paths")
    @Test
    void nullAndEmptyUserPathsAreIgnored(@TempDir Path tempDir) {
        File basedir = new File(tempDir.toFile(), "project");
        List<SourceCodePath> userPaths = new ArrayList<>();
        userPaths.add(null);
        userPaths.add(SourceCodePath.builder().setPath(""));
        userPaths.add(SourceCodePath.builder());

        List<SourceCodePath> merged = MojoUtils.mergeSourceCodePaths(basedir, "src/main/java", userPaths, null);

        assertEquals(1, merged.size());
        assertEquals(new File(basedir, "src/main/java").getAbsolutePath(),
                new File(merged.get(0).getPath()).getAbsolutePath());
    }

    /**
     * The description of a user configured source path must be kept on the merged entry.
     * @param tempDir temporary directory provided by JUnit
     */
    @DisplayName("preserves the description of a user path")
    @Test
    void userPathDescriptionIsPreserved(@TempDir Path tempDir) {
        File basedir = new File(tempDir.toFile(), "project");

        List<SourceCodePath> merged = MojoUtils.mergeSourceCodePaths(basedir, "target/classes",
                Arrays.asList(SourceCodePath.builder().setPath("src/main/java").setDesc("hand-written")), null);

        assertEquals("hand-written", merged.get(0).getDesc());
    }

    /**
     * Counts how many merged entries resolve to the expected file.
     * @param paths merged source code paths
     * @param expected expected file
     * @return the number of entries that point to the expected file
     */
    private static long count(List<SourceCodePath> paths, File expected) {
        String expectedPath = expected.getAbsolutePath();
        long count = 0;
        for (SourceCodePath path : paths) {
            if (new File(path.getPath()).getAbsolutePath().equals(expectedPath)) {
                count++;
            }
        }
        return count;
    }
}
