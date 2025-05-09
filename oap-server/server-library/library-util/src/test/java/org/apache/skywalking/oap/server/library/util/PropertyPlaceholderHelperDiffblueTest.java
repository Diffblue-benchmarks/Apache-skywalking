package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.skywalking.oap.server.library.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PropertyPlaceholderHelperDiffblueTest {
  /**
   * Test {@link PropertyPlaceholderHelper#replacePlaceholders(String, PlaceholderResolver)} with {@code value}, {@code placeholderResolver}.
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#replacePlaceholders(String, PlaceholderResolver)}
   */
  @Test
  @DisplayName("Test replacePlaceholders(String, PlaceholderResolver) with 'value', 'placeholderResolver'")
  @Tag("MaintainedByDiffblue")
  void testReplacePlaceholdersWithValuePlaceholderResolver() {
    // Arrange, Act and Assert
    assertEquals(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX, PropertyPlaceholderHelper.INSTANCE
        .replacePlaceholders(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX, mock(PlaceholderResolver.class)));
  }

  /**
   * Test {@link PropertyPlaceholderHelper#replacePlaceholders(String, PlaceholderResolver)} with {@code value}, {@code placeholderResolver}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#replacePlaceholders(String, PlaceholderResolver)}
   */
  @Test
  @DisplayName("Test replacePlaceholders(String, PlaceholderResolver) with 'value', 'placeholderResolver'; when '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  void testReplacePlaceholdersWithValuePlaceholderResolver_when42_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", PropertyPlaceholderHelper.INSTANCE.replacePlaceholders("42", mock(PlaceholderResolver.class)));
  }

  /**
   * Test {@link PropertyPlaceholderHelper#replacePlaceholders(String, Properties)} with {@code value}, {@code properties}.
   * <ul>
   *   <li>Then return {@link PlaceholderConfigurerSupport#DEFAULT_PLACEHOLDER_PREFIX}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#replacePlaceholders(String, Properties)}
   */
  @Test
  @DisplayName("Test replacePlaceholders(String, Properties) with 'value', 'properties'; then return DEFAULT_PLACEHOLDER_PREFIX")
  @Tag("MaintainedByDiffblue")
  void testReplacePlaceholdersWithValueProperties_thenReturnDefault_placeholder_prefix() {
    // Arrange, Act and Assert
    assertEquals(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX, PropertyPlaceholderHelper.INSTANCE
        .replacePlaceholders(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX, new Properties()));
  }

  /**
   * Test {@link PropertyPlaceholderHelper#replacePlaceholders(String, Properties)} with {@code value}, {@code properties}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#replacePlaceholders(String, Properties)}
   */
  @Test
  @DisplayName("Test replacePlaceholders(String, Properties) with 'value', 'properties'; when '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  void testReplacePlaceholdersWithValueProperties_when42_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", PropertyPlaceholderHelper.INSTANCE.replacePlaceholders("42", new Properties()));
  }

  /**
   * Test {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}
   */
  @Test
  @DisplayName("Test parseStringValue(String, PlaceholderResolver, Set); given '42'; when HashSet() add '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  void testParseStringValue_given42_whenHashSetAdd42_thenReturn42() {
    // Arrange
    PlaceholderResolver placeholderResolver = mock(PlaceholderResolver.class);

    HashSet<String> visitedPlaceholders = new HashSet<>();
    visitedPlaceholders.add("42");
    visitedPlaceholders.add("foo");

    // Act and Assert
    assertEquals("42",
        PropertyPlaceholderHelper.INSTANCE.parseStringValue("42", placeholderResolver, visitedPlaceholders));
  }

  /**
   * Test {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}
   */
  @Test
  @DisplayName("Test parseStringValue(String, PlaceholderResolver, Set); given 'foo'; when HashSet() add 'foo'; then return '42'")
  @Tag("MaintainedByDiffblue")
  void testParseStringValue_givenFoo_whenHashSetAddFoo_thenReturn42() {
    // Arrange
    PlaceholderResolver placeholderResolver = mock(PlaceholderResolver.class);

    HashSet<String> visitedPlaceholders = new HashSet<>();
    visitedPlaceholders.add("foo");

    // Act and Assert
    assertEquals("42",
        PropertyPlaceholderHelper.INSTANCE.parseStringValue("42", placeholderResolver, visitedPlaceholders));
  }

  /**
   * Test {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}.
   * <ul>
   *   <li>Then return {@link PlaceholderConfigurerSupport#DEFAULT_PLACEHOLDER_PREFIX}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}
   */
  @Test
  @DisplayName("Test parseStringValue(String, PlaceholderResolver, Set); then return DEFAULT_PLACEHOLDER_PREFIX")
  @Tag("MaintainedByDiffblue")
  void testParseStringValue_thenReturnDefault_placeholder_prefix() {
    // Arrange
    PlaceholderResolver placeholderResolver = mock(PlaceholderResolver.class);

    // Act and Assert
    assertEquals(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX,
        PropertyPlaceholderHelper.INSTANCE.parseStringValue(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX,
            placeholderResolver, new HashSet<>()));
  }

  /**
   * Test {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PropertyPlaceholderHelper#parseStringValue(String, PlaceholderResolver, Set)}
   */
  @Test
  @DisplayName("Test parseStringValue(String, PlaceholderResolver, Set); when HashSet(); then return '42'")
  @Tag("MaintainedByDiffblue")
  void testParseStringValue_whenHashSet_thenReturn42() {
    // Arrange
    PlaceholderResolver placeholderResolver = mock(PlaceholderResolver.class);

    // Act and Assert
    assertEquals("42", PropertyPlaceholderHelper.INSTANCE.parseStringValue("42", placeholderResolver, new HashSet<>()));
  }
}
