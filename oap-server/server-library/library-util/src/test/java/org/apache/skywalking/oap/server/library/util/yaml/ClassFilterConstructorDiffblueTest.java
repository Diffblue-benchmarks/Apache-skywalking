package org.apache.skywalking.oap.server.library.util.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.inspector.UnTrustedTagInspector;
import org.yaml.snakeyaml.introspector.PropertyUtils;

class ClassFilterConstructorDiffblueTest {
  /**
   * Test {@link ClassFilterConstructor#ClassFilterConstructor(Class[])}.
   * <p>
   * Method under test: {@link ClassFilterConstructor#ClassFilterConstructor(Class[])}
   */
  @Test
  @DisplayName("Test new ClassFilterConstructor(Class[])")
  @Tag("MaintainedByDiffblue")
  void testNewClassFilterConstructor() {
    // Arrange
    Class<Object> forNameResult = Object.class;

    // Act
    ClassFilterConstructor actualClassFilterConstructor = new ClassFilterConstructor(new Class[]{forNameResult});

    // Assert
    LoaderOptions loadingConfig = actualClassFilterConstructor.getLoadingConfig();
    assertTrue(loadingConfig.getTagInspector() instanceof UnTrustedTagInspector);
    assertEquals(3145728, loadingConfig.getCodePointLimit());
    assertEquals(50, loadingConfig.getMaxAliasesForCollections());
    assertEquals(50, loadingConfig.getNestingDepthLimit());
    assertFalse(loadingConfig.getAllowRecursiveKeys());
    assertFalse(loadingConfig.isProcessComments());
    assertFalse(loadingConfig.isWrappedToRootException());
    assertFalse(actualClassFilterConstructor.isEnumCaseSensitive());
    assertFalse(actualClassFilterConstructor.isExplicitPropertyUtils());
    assertFalse(actualClassFilterConstructor.isWrappedToRootException());
    PropertyUtils propertyUtils = actualClassFilterConstructor.getPropertyUtils();
    assertFalse(propertyUtils.isAllowReadOnlyProperties());
    assertFalse(propertyUtils.isSkipMissingProperties());
    assertTrue(loadingConfig.isAllowDuplicateKeys());
    assertTrue(loadingConfig.isEnumCaseSensitive());
    assertTrue(actualClassFilterConstructor.isAllowDuplicateKeys());
  }

  /**
   * Test {@link ClassFilterConstructor#getClassForName(String)}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClassFilterConstructor#getClassForName(String)}
   */
  @Test
  @DisplayName("Test getClassForName(String); given 'java.lang.Object'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  void testGetClassForName_givenJavaLangObject_thenThrowIllegalArgumentException() throws ClassNotFoundException {
    // Arrange
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> (new ClassFilterConstructor(new Class[]{forNameResult})).getClassForName("Name"));
  }
}
