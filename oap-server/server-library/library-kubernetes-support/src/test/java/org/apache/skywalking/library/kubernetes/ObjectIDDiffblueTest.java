package org.apache.skywalking.library.kubernetes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ObjectIDDiffblueTest {
  /**
   * Test {@link ObjectID#toString()}.
   * <ul>
   *   <li>Given builder name {@code Name} namespace empty string build.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectID#toString()}
   */
  @Test
  @DisplayName("Test toString(); given builder name 'Name' namespace empty string build; then return 'Name'")
  @Tag("MaintainedByDiffblue")
  void testToString_givenBuilderNameNameNamespaceEmptyStringBuild_thenReturnName() {
    // Arrange
    ObjectID buildResult = ObjectID.builder().name("Name").namespace("").build();

    // Act and Assert
    assertEquals("Name", buildResult.toString());
  }

  /**
   * Test {@link ObjectID#toString()}.
   * <ul>
   *   <li>Given builder name {@code Name} namespace {@code Namespace} build.</li>
   *   <li>Then return {@code Name.Namespace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectID#toString()}
   */
  @Test
  @DisplayName("Test toString(); given builder name 'Name' namespace 'Namespace' build; then return 'Name.Namespace'")
  @Tag("MaintainedByDiffblue")
  void testToString_givenBuilderNameNameNamespaceNamespaceBuild_thenReturnNameNamespace() {
    // Arrange
    ObjectID buildResult = ObjectID.builder().name("Name").namespace("Namespace").build();

    // Act and Assert
    assertEquals("Name.Namespace", buildResult.toString());
  }

  /**
   * Test {@link ObjectID#toString()}.
   * <ul>
   *   <li>Given {@link ObjectID#EMPTY}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectID#toString()}
   */
  @Test
  @DisplayName("Test toString(); given EMPTY; then return empty string")
  @Tag("MaintainedByDiffblue")
  void testToString_givenEmpty_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", ObjectID.EMPTY.toString());
  }
}
