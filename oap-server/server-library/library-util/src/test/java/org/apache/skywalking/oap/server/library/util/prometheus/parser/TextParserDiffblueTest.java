package org.apache.skywalking.oap.server.library.util.prometheus.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TextParserDiffblueTest {
  /**
   * Test {@link TextParser#TextParser(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code A AXAXAX} Bytes is {@code UTF-8} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#TextParser(InputStream)}
   */
  @Test
  @DisplayName("Test new TextParser(InputStream); then ByteArrayInputStream(byte[]) with 'A AXAXAX' Bytes is 'UTF-8' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testNewTextParser_thenByteArrayInputStreamWithAAxaxaxBytesIsUtf8ReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream("A AXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertNull((new TextParser(inputStream)).parse(1L));
    assertEquals(-1, inputStream.read(new byte[]{}));
  }

  /**
   * Test {@link TextParser#TextParser(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code A AXAXAX} Bytes is {@code UTF-8} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#TextParser(InputStream)}
   */
  @Test
  @DisplayName("Test new TextParser(InputStream); then ByteArrayInputStream(byte[]) with 'A AXAXAX' Bytes is 'UTF-8' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testNewTextParser_thenByteArrayInputStreamWithAAxaxaxBytesIsUtf8ReadIsMinusOne2() throws IOException {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream("A\tAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertNull((new TextParser(inputStream)).parse(1L));
    assertEquals(-1, inputStream.read(new byte[]{}));
  }

  /**
   * Test {@link TextParser#TextParser(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#TextParser(InputStream)}
   */
  @Test
  @DisplayName("Test new TextParser(InputStream); then ByteArrayInputStream(byte[]) with 'AXAXAXAX' Bytes is 'UTF-8' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testNewTextParser_thenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8ReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertNull((new TextParser(inputStream)).parse(1L));
    assertEquals(-1, inputStream.read(new byte[]{}));
  }

  /**
   * Test {@link TextParser#TextParser(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with empty array of {@code byte} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#TextParser(InputStream)}
   */
  @Test
  @DisplayName("Test new TextParser(InputStream); then ByteArrayInputStream(byte[]) with empty array of byte read is minus one")
  @Tag("MaintainedByDiffblue")
  void testNewTextParser_thenByteArrayInputStreamWithEmptyArrayOfByteReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{});

    // Act and Assert
    assertNull((new TextParser(inputStream)).parse(1L));
    assertEquals(-1, inputStream.read(new byte[]{}));
  }

  /**
   * Test {@link TextParser#TextParser(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code #XAXAXAX} Bytes is {@code UTF-8} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#TextParser(InputStream)}
   */
  @Test
  @DisplayName("Test new TextParser(InputStream); then ByteArrayInputStream(byte[]) with '#XAXAXAX' Bytes is 'UTF-8' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testNewTextParser_thenByteArrayInputStreamWithXaxaxaxBytesIsUtf8ReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream("#XAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertNull((new TextParser(inputStream)).parse(1L));
    assertEquals(-1, inputStream.read(new byte[]{}));
  }

  /**
   * Test {@link TextParser#TextParser(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code {XAXAXAX} Bytes is {@code UTF-8} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#TextParser(InputStream)}
   */
  @Test
  @DisplayName("Test new TextParser(InputStream); then ByteArrayInputStream(byte[]) with '{XAXAXAX' Bytes is 'UTF-8' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testNewTextParser_thenByteArrayInputStreamWithXaxaxaxBytesIsUtf8ReadIsMinusOne2() throws IOException {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream("{XAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertNull((new TextParser(inputStream)).parse(1L));
    assertEquals(-1, inputStream.read(new byte[]{}));
  }

  /**
   * Test {@link TextParser#parse(long)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code A AXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#parse(long)}
   */
  @Test
  @DisplayName("Test parse(long); given ByteArrayInputStream(byte[]) with 'A AXAXAX' Bytes is 'UTF-8'")
  @Tag("MaintainedByDiffblue")
  void testParse_givenByteArrayInputStreamWithAAxaxaxBytesIsUtf8() throws IOException {
    // Arrange, Act and Assert
    assertNull((new TextParser(new ByteArrayInputStream("A AXAXAX".getBytes("UTF-8")))).parse(1L));
  }

  /**
   * Test {@link TextParser#parse(long)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code A AXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#parse(long)}
   */
  @Test
  @DisplayName("Test parse(long); given ByteArrayInputStream(byte[]) with 'A AXAXAX' Bytes is 'UTF-8'")
  @Tag("MaintainedByDiffblue")
  void testParse_givenByteArrayInputStreamWithAAxaxaxBytesIsUtf82() throws IOException {
    // Arrange, Act and Assert
    assertNull((new TextParser(new ByteArrayInputStream("A\tAXAXAX".getBytes("UTF-8")))).parse(1L));
  }

  /**
   * Test {@link TextParser#parse(long)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#parse(long)}
   */
  @Test
  @DisplayName("Test parse(long); given ByteArrayInputStream(byte[]) with 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("MaintainedByDiffblue")
  void testParse_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8() throws IOException {
    // Arrange, Act and Assert
    assertNull((new TextParser(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))).parse(1L));
  }

  /**
   * Test {@link TextParser#parse(long)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with empty array of {@code byte}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#parse(long)}
   */
  @Test
  @DisplayName("Test parse(long); given ByteArrayInputStream(byte[]) with empty array of byte")
  @Tag("MaintainedByDiffblue")
  void testParse_givenByteArrayInputStreamWithEmptyArrayOfByte() throws IOException {
    // Arrange, Act and Assert
    assertNull((new TextParser(new ByteArrayInputStream(new byte[]{}))).parse(1L));
  }

  /**
   * Test {@link TextParser#parse(long)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code #XAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#parse(long)}
   */
  @Test
  @DisplayName("Test parse(long); given ByteArrayInputStream(byte[]) with '#XAXAXAX' Bytes is 'UTF-8'")
  @Tag("MaintainedByDiffblue")
  void testParse_givenByteArrayInputStreamWithXaxaxaxBytesIsUtf8() throws IOException {
    // Arrange, Act and Assert
    assertNull((new TextParser(new ByteArrayInputStream("#XAXAXAX".getBytes("UTF-8")))).parse(1L));
  }

  /**
   * Test {@link TextParser#parse(long)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code {XAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextParser#parse(long)}
   */
  @Test
  @DisplayName("Test parse(long); given ByteArrayInputStream(byte[]) with '{XAXAXAX' Bytes is 'UTF-8'")
  @Tag("MaintainedByDiffblue")
  void testParse_givenByteArrayInputStreamWithXaxaxaxBytesIsUtf82() throws IOException {
    // Arrange, Act and Assert
    assertNull((new TextParser(new ByteArrayInputStream("{XAXAXAX".getBytes("UTF-8")))).parse(1L));
  }
}
