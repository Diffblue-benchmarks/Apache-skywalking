package org.apache.skywalking.oap.server.library.util.prometheus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.skywalking.oap.server.library.util.prometheus.parser.TextParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ParsersDiffblueTest {
  /**
   * Test {@link Parsers#text(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parsers#text(InputStream)}
   */
  @Test
  @DisplayName("Test text(InputStream); then ByteArrayInputStream(byte[]) with 'AXAXAXAX' Bytes is 'UTF-8' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testText_thenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8ReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream stream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    Parser actualTextResult = Parsers.text(stream);

    // Assert
    assertTrue(actualTextResult instanceof TextParser);
    assertNull(actualTextResult.parse(1L));
    assertEquals(-1, stream.read(new byte[]{}));
  }

  /**
   * Test {@link Parsers#text(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with empty array of {@code byte} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parsers#text(InputStream)}
   */
  @Test
  @DisplayName("Test text(InputStream); then ByteArrayInputStream(byte[]) with empty array of byte read is minus one")
  @Tag("MaintainedByDiffblue")
  void testText_thenByteArrayInputStreamWithEmptyArrayOfByteReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream stream = new ByteArrayInputStream(new byte[]{});

    // Act
    Parser actualTextResult = Parsers.text(stream);

    // Assert
    assertTrue(actualTextResult instanceof TextParser);
    assertNull(actualTextResult.parse(1L));
    assertEquals(-1, stream.read(new byte[]{}));
  }

  /**
   * Test {@link Parsers#text(InputStream)}.
   * <ul>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code #XAXAXAX} Bytes is {@code UTF-8} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parsers#text(InputStream)}
   */
  @Test
  @DisplayName("Test text(InputStream); then ByteArrayInputStream(byte[]) with '#XAXAXAX' Bytes is 'UTF-8' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testText_thenByteArrayInputStreamWithXaxaxaxBytesIsUtf8ReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream stream = new ByteArrayInputStream("#XAXAXAX".getBytes("UTF-8"));

    // Act
    Parser actualTextResult = Parsers.text(stream);

    // Assert
    assertTrue(actualTextResult instanceof TextParser);
    assertNull(actualTextResult.parse(1L));
    assertEquals(-1, stream.read(new byte[]{}));
  }

  /**
   * Test {@link Parsers#text(InputStream)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with array of {@code byte} with one and {@code X} read is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parsers#text(InputStream)}
   */
  @Test
  @DisplayName("Test text(InputStream); when 'A'; then ByteArrayInputStream(byte[]) with array of byte with one and 'X' read is minus one")
  @Tag("MaintainedByDiffblue")
  void testText_whenA_thenByteArrayInputStreamWithArrayOfByteWithOneAndXReadIsMinusOne() throws IOException {
    // Arrange
    ByteArrayInputStream stream = new ByteArrayInputStream(new byte[]{1, 'X', 'A', 'X', 'A', 'X', 'A', 'X'});

    // Act
    Parser actualTextResult = Parsers.text(stream);

    // Assert
    assertTrue(actualTextResult instanceof TextParser);
    assertNull(actualTextResult.parse(1L));
    assertEquals(-1, stream.read(new byte[]{}));
  }
}
