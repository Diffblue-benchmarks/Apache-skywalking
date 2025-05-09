package org.apache.skywalking.apm.network.ebpf.profiling.process.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.profiling.process.v3.EBPFProcessProperties.MetadataCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EBPFProcessPropertiesDiffblueTest {
  /**
   * Test MetadataCase {@link MetadataCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetadataCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(MetadataCase.forNumber(42));
  }

  /**
   * Test MetadataCase {@link MetadataCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code HOSTPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetadataCase forNumber(int); when one; then return 'HOSTPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseForNumber_whenOne_thenReturnHostprocess() {
    // Arrange, Act and Assert
    assertEquals(MetadataCase.HOSTPROCESS, MetadataCase.forNumber(1));
  }

  /**
   * Test MetadataCase {@link MetadataCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code K8SPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetadataCase forNumber(int); when two; then return 'K8SPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseForNumber_whenTwo_thenReturnK8sprocess() {
    // Arrange, Act and Assert
    assertEquals(MetadataCase.K8SPROCESS, MetadataCase.forNumber(2));
  }

  /**
   * Test MetadataCase {@link MetadataCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code METADATA_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test MetadataCase forNumber(int); when zero; then return 'METADATA_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseForNumber_whenZero_thenReturnMetadataNotSet() {
    // Arrange, Act and Assert
    assertEquals(MetadataCase.METADATA_NOT_SET, MetadataCase.forNumber(0));
  }

  /**
   * Test MetadataCase {@link MetadataCase#getNumber()}.
   * <p>
   * Method under test: {@link MetadataCase#getNumber()}
   */
  @Test
  @DisplayName("Test MetadataCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, MetadataCase.valueOf("HOSTPROCESS").getNumber());
  }

  /**
   * Test MetadataCase {@link MetadataCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetadataCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(MetadataCase.valueOf(42));
  }

  /**
   * Test MetadataCase {@link MetadataCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code HOSTPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetadataCase valueOf(int) with 'value'; when one; then return 'HOSTPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseValueOfWithValue_whenOne_thenReturnHostprocess() {
    // Arrange, Act and Assert
    assertEquals(MetadataCase.HOSTPROCESS, MetadataCase.valueOf(1));
  }

  /**
   * Test MetadataCase {@link MetadataCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code K8SPROCESS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetadataCase valueOf(int) with 'value'; when two; then return 'K8SPROCESS'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseValueOfWithValue_whenTwo_thenReturnK8sprocess() {
    // Arrange, Act and Assert
    assertEquals(MetadataCase.K8SPROCESS, MetadataCase.valueOf(2));
  }

  /**
   * Test MetadataCase {@link MetadataCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code METADATA_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MetadataCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test MetadataCase valueOf(int) with 'value'; when zero; then return 'METADATA_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testMetadataCaseValueOfWithValue_whenZero_thenReturnMetadataNotSet() {
    // Arrange, Act and Assert
    assertEquals(MetadataCase.METADATA_NOT_SET, MetadataCase.valueOf(0));
  }
}
