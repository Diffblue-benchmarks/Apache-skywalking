package org.apache.skywalking.apm.network.ebpf.accesslog.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.skywalking.apm.network.ebpf.accesslog.v3.ConnectionAddress.AddressCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConnectionAddressDiffblueTest {
  /**
   * Test AddressCase {@link AddressCase#forNumber(int)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test AddressCase forNumber(int); when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseForNumber_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(AddressCase.forNumber(42));
  }

  /**
   * Test AddressCase {@link AddressCase#forNumber(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code KUBERNETES}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test AddressCase forNumber(int); when one; then return 'KUBERNETES'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseForNumber_whenOne_thenReturnKubernetes() {
    // Arrange, Act and Assert
    assertEquals(AddressCase.KUBERNETES, AddressCase.forNumber(1));
  }

  /**
   * Test AddressCase {@link AddressCase#forNumber(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code IP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test AddressCase forNumber(int); when two; then return 'IP'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseForNumber_whenTwo_thenReturnIp() {
    // Arrange, Act and Assert
    assertEquals(AddressCase.IP, AddressCase.forNumber(2));
  }

  /**
   * Test AddressCase {@link AddressCase#forNumber(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code ADDRESS_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#forNumber(int)}
   */
  @Test
  @DisplayName("Test AddressCase forNumber(int); when zero; then return 'ADDRESS_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseForNumber_whenZero_thenReturnAddressNotSet() {
    // Arrange, Act and Assert
    assertEquals(AddressCase.ADDRESS_NOT_SET, AddressCase.forNumber(0));
  }

  /**
   * Test AddressCase {@link AddressCase#getNumber()}.
   * <p>
   * Method under test: {@link AddressCase#getNumber()}
   */
  @Test
  @DisplayName("Test AddressCase getNumber()")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseGetNumber() {
    // Arrange, Act and Assert
    assertEquals(1, AddressCase.valueOf("KUBERNETES").getNumber());
  }

  /**
   * Test AddressCase {@link AddressCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test AddressCase valueOf(int) with 'value'; when forty-two; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseValueOfWithValue_whenFortyTwo_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(AddressCase.valueOf(42));
  }

  /**
   * Test AddressCase {@link AddressCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code KUBERNETES}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test AddressCase valueOf(int) with 'value'; when one; then return 'KUBERNETES'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseValueOfWithValue_whenOne_thenReturnKubernetes() {
    // Arrange, Act and Assert
    assertEquals(AddressCase.KUBERNETES, AddressCase.valueOf(1));
  }

  /**
   * Test AddressCase {@link AddressCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@code IP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test AddressCase valueOf(int) with 'value'; when two; then return 'IP'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseValueOfWithValue_whenTwo_thenReturnIp() {
    // Arrange, Act and Assert
    assertEquals(AddressCase.IP, AddressCase.valueOf(2));
  }

  /**
   * Test AddressCase {@link AddressCase#valueOf(int)} with {@code value}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code ADDRESS_NOT_SET}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddressCase#valueOf(int)}
   */
  @Test
  @DisplayName("Test AddressCase valueOf(int) with 'value'; when zero; then return 'ADDRESS_NOT_SET'")
  @Tag("MaintainedByDiffblue")
  void testAddressCaseValueOfWithValue_whenZero_thenReturnAddressNotSet() {
    // Arrange, Act and Assert
    assertEquals(AddressCase.ADDRESS_NOT_SET, AddressCase.valueOf(0));
  }
}
