package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ResourceUtilsDiffblueTest {
  /**
   * Test {@link ResourceUtils#read(String)}.
   * <ul>
   *   <li>When {@code foo.txt}.</li>
   *   <li>Then throw {@link FileNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResourceUtils#read(String)}
   */
  @Test
  @DisplayName("Test read(String); when 'foo.txt'; then throw FileNotFoundException")
  @Tag("MaintainedByDiffblue")
  void testRead_whenFooTxt_thenThrowFileNotFoundException() throws FileNotFoundException {
    // Arrange, Act and Assert
    assertThrows(FileNotFoundException.class, () -> ResourceUtils.read("foo.txt"));
  }

  /**
   * Test {@link ResourceUtils#readToStream(String)}.
   * <ul>
   *   <li>When {@code foo.txt}.</li>
   *   <li>Then throw {@link FileNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResourceUtils#readToStream(String)}
   */
  @Test
  @DisplayName("Test readToStream(String); when 'foo.txt'; then throw FileNotFoundException")
  @Tag("MaintainedByDiffblue")
  void testReadToStream_whenFooTxt_thenThrowFileNotFoundException() throws FileNotFoundException {
    // Arrange, Act and Assert
    assertThrows(FileNotFoundException.class, () -> ResourceUtils.readToStream("foo.txt"));
  }

  /**
   * Test {@link ResourceUtils#getPathFiles(String)}.
   * <ul>
   *   <li>When {@code Path}.</li>
   *   <li>Then throw {@link FileNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResourceUtils#getPathFiles(String)}
   */
  @Test
  @DisplayName("Test getPathFiles(String); when 'Path'; then throw FileNotFoundException")
  @Tag("MaintainedByDiffblue")
  void testGetPathFiles_whenPath_thenThrowFileNotFoundException() throws FileNotFoundException {
    // Arrange, Act and Assert
    assertThrows(FileNotFoundException.class, () -> ResourceUtils.getPathFiles("Path"));
  }

  /**
   * Test {@link ResourceUtils#getDirectoryFilesRecursive(String, int)} with {@code directoryPath}, {@code maxDepth}.
   * <p>
   * Method under test: {@link ResourceUtils#getDirectoryFilesRecursive(String, int)}
   */
  @Test
  @DisplayName("Test getDirectoryFilesRecursive(String, int) with 'directoryPath', 'maxDepth'")
  @Tag("MaintainedByDiffblue")
  void testGetDirectoryFilesRecursiveWithDirectoryPathMaxDepth() throws FileNotFoundException {
    // Arrange, Act and Assert
    assertThrows(FileNotFoundException.class, () -> ResourceUtils.getDirectoryFilesRecursive("/directory", 2));
  }

  /**
   * Test {@link ResourceUtils#getDirectoryFilesRecursive(String, int)} with {@code directoryPath}, {@code maxDepth}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResourceUtils#getDirectoryFilesRecursive(String, int)}
   */
  @Test
  @DisplayName("Test getDirectoryFilesRecursive(String, int) with 'directoryPath', 'maxDepth'; when minus one")
  @Tag("MaintainedByDiffblue")
  void testGetDirectoryFilesRecursiveWithDirectoryPathMaxDepth_whenMinusOne() throws FileNotFoundException {
    // Arrange and Act
    List<File> actualDirectoryFilesRecursive = ResourceUtils.getDirectoryFilesRecursive("", -1);

    // Assert
    assertTrue(actualDirectoryFilesRecursive.isEmpty());
  }
}
