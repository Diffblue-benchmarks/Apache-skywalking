package org.apache.skywalking.apm.network.ebpf.profiling.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.profiling.v3.ContinuousProfilingURICause.UriCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ContinuousProfilingURICauseDiffblueTest {
  /**
   * Test UriCase {@link UriCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test UriCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(UriCase.forNumber(42));
  }

  /**
   * Test UriCase {@link UriCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code REGEX}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test UriCase forNumber(int); when one; then return 'REGEX'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseForNumber_whenOne_thenReturnRegex() {
    // Arrange, Act and Assert
    assertEquals(UriCase.REGEX, UriCase.forNumber(1));
  }

  /**
   * Test UriCase {@link UriCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code PATH}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test UriCase forNumber(int); when two; then return 'PATH'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseForNumber_whenTwo_thenReturnPath() {
    // Arrange, Act and Assert
    assertEquals(UriCase.PATH, UriCase.forNumber(2));
  }

  /**
   * Test UriCase {@link UriCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code URI_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test UriCase forNumber(int); when zero; then return 'URI_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseForNumber_whenZero_thenReturnUriNotSet() {
    // Arrange, Act and Assert
    assertEquals(UriCase.URI_NOT_SET, UriCase.forNumber(0));
  }

  /**
   * Test UriCase {@link UriCase#getNumber()}.
   * <p>
   * Method under test: {@link UriCase#getNumber()}
   */
  @Test
  @DisplayName("Test UriCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testUriCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, UriCase.valueOf("REGEX").getNumber());
  }

  /**
   * Test UriCase {@link UriCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test UriCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(UriCase.valueOf(42));
  }

  /**
   * Test UriCase {@link UriCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code REGEX}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test UriCase valueOf(int) with 'value'; when one; then return 'REGEX'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseValueOfWithValue_whenOne_thenReturnRegex() {
    // Arrange, Act and Assert
    assertEquals(UriCase.REGEX, UriCase.valueOf(1));
  }

  /**
   * Test UriCase {@link UriCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code PATH}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test UriCase valueOf(int) with 'value'; when two; then return 'PATH'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseValueOfWithValue_whenTwo_thenReturnPath() {
    // Arrange, Act and Assert
    assertEquals(UriCase.PATH, UriCase.valueOf(2));
  }

  /**
   * Test UriCase {@link UriCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code URI_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UriCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test UriCase valueOf(int) with 'value'; when zero; then return 'URI_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testUriCaseValueOfWithValue_whenZero_thenReturnUriNotSet() {
    // Arrange, Act and Assert
    assertEquals(UriCase.URI_NOT_SET, UriCase.valueOf(0));
  }
}
