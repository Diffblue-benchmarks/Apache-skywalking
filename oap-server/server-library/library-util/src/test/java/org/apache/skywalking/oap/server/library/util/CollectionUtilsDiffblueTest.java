package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CollectionUtilsDiffblueTest {
  /**
   * Test {@link CollectionUtils#isEmpty(byte[])} with {@code array}.
   * <ul>
   *   <li>When {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(byte[])}
   */
  @Test
  @DisplayName("Test isEmpty(byte[]) with 'array'; when 'AXAXAXAX' Bytes is 'UTF-8'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithArray_whenAxaxaxaxBytesIsUtf8_thenReturnFalse() throws UnsupportedEncodingException {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isEmpty("AXAXAXAX".getBytes("UTF-8")));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(byte[])} with {@code array}.
   * <ul>
   *   <li>When empty array of {@code byte}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(byte[])}
   */
  @Test
  @DisplayName("Test isEmpty(byte[]) with 'array'; when empty array of byte; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithArray_whenEmptyArrayOfByte_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty(new byte[]{}));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(byte[])} with {@code array}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(byte[])}
   */
  @Test
  @DisplayName("Test isEmpty(byte[]) with 'array'; when 'null'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithArray_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty((byte[]) null));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(List)} with {@code list}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(List)}
   */
  @Test
  @DisplayName("Test isEmpty(List) with 'list'; given '42'; when ArrayList() add '42'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithList_given42_whenArrayListAdd42_thenReturnFalse() {
    // Arrange
    ArrayList<Object> list = new ArrayList<>();
    list.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(list));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(List)} with {@code list}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(List)}
   */
  @Test
  @DisplayName("Test isEmpty(List) with 'list'; given '42'; when ArrayList() add '42'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithList_given42_whenArrayListAdd42_thenReturnFalse2() {
    // Arrange
    ArrayList<Object> list = new ArrayList<>();
    list.add("42");
    list.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(list));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(List)} with {@code list}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(List)}
   */
  @Test
  @DisplayName("Test isEmpty(List) with 'list'; when ArrayList(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithList_whenArrayList_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty(new ArrayList<>()));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(List)} with {@code list}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(List)}
   */
  @Test
  @DisplayName("Test isEmpty(List) with 'list'; when 'null'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithList_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty((List) null));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Map)} with {@code map}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Map)}
   */
  @Test
  @DisplayName("Test isEmpty(Map) with 'map'; given '42'; when HashMap() '42' is '42'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithMap_given42_whenHashMap42Is42_thenReturnFalse() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put("42", "42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(map));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Map)} with {@code map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Map)}
   */
  @Test
  @DisplayName("Test isEmpty(Map) with 'map'; when HashMap(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithMap_whenHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty(new HashMap<>()));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Map)} with {@code map}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Map)}
   */
  @Test
  @DisplayName("Test isEmpty(Map) with 'map'; when 'null'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithMap_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty((Map) null));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Set)} with {@code set}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Set)}
   */
  @Test
  @DisplayName("Test isEmpty(Set) with 'set'; given '42'; when HashSet() add '42'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithSet_given42_whenHashSetAdd42_thenReturnFalse() {
    // Arrange
    HashSet<Object> set = new HashSet<>();
    set.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(set));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Set)} with {@code set}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link HashSet#HashSet()} add two.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Set)}
   */
  @Test
  @DisplayName("Test isEmpty(Set) with 'set'; given two; when HashSet() add two; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithSet_givenTwo_whenHashSetAddTwo_thenReturnFalse() {
    // Arrange
    HashSet<Object> set = new HashSet<>();
    set.add(2);
    set.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(set));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Set)} with {@code set}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Set)}
   */
  @Test
  @DisplayName("Test isEmpty(Set) with 'set'; when HashSet(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithSet_whenHashSet_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty(new HashSet<>()));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Set)} with {@code set}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Set)}
   */
  @Test
  @DisplayName("Test isEmpty(Set) with 'set'; when 'null'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsEmptyWithSet_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty((Set) null));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(byte[])} with {@code byte[]}.
   * <ul>
   *   <li>When {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(byte[])}
   */
  @Test
  @DisplayName("Test isNotEmpty(byte[]) with 'byte[]'; when 'AXAXAXAX' Bytes is 'UTF-8'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithByte_whenAxaxaxaxBytesIsUtf8_thenReturnTrue() throws UnsupportedEncodingException {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isNotEmpty("AXAXAXAX".getBytes("UTF-8")));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(byte[])} with {@code byte[]}.
   * <ul>
   *   <li>When empty array of {@code byte}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(byte[])}
   */
  @Test
  @DisplayName("Test isNotEmpty(byte[]) with 'byte[]'; when empty array of byte; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithByte_whenEmptyArrayOfByte_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(new byte[]{}));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(byte[])} with {@code byte[]}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(byte[])}
   */
  @Test
  @DisplayName("Test isNotEmpty(byte[]) with 'byte[]'; when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithByte_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty((byte[]) null));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(List)} with {@code List}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(List)}
   */
  @Test
  @DisplayName("Test isNotEmpty(List) with 'List'; given '42'; when ArrayList() add '42'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithList_given42_whenArrayListAdd42_thenReturnTrue() {
    // Arrange
    ArrayList<Object> list = new ArrayList<>();
    list.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(list));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(List)} with {@code List}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(List)}
   */
  @Test
  @DisplayName("Test isNotEmpty(List) with 'List'; given '42'; when ArrayList() add '42'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithList_given42_whenArrayListAdd42_thenReturnTrue2() {
    // Arrange
    ArrayList<Object> list = new ArrayList<>();
    list.add("42");
    list.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(list));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(List)} with {@code List}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(List)}
   */
  @Test
  @DisplayName("Test isNotEmpty(List) with 'List'; when ArrayList(); then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithList_whenArrayList_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(new ArrayList<>()));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(List)} with {@code List}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(List)}
   */
  @Test
  @DisplayName("Test isNotEmpty(List) with 'List'; when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithList_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty((List) null));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Map)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Map) with 'Map'; given '42'; when HashMap() '42' is '42'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithMap_given42_whenHashMap42Is42_thenReturnTrue() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put("42", "42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(map));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Map)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Map) with 'Map'; when HashMap(); then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithMap_whenHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(new HashMap<>()));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Map)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Map) with 'Map'; when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithMap_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty((Map) null));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Object[])} with {@code Object[]}.
   * <ul>
   *   <li>When array of {@link Object} with {@code Array}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Object[])}
   */
  @Test
  @DisplayName("Test isNotEmpty(Object[]) with 'Object[]'; when array of Object with 'Array'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithObject_whenArrayOfObjectWithArray_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(new Object[]{"Array"}));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Object[])} with {@code Object[]}.
   * <ul>
   *   <li>When empty array of {@link Object}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Object[])}
   */
  @Test
  @DisplayName("Test isNotEmpty(Object[]) with 'Object[]'; when empty array of Object; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithObject_whenEmptyArrayOfObject_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(new Object[]{}));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Set)} with {@code Set}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Set)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Set) with 'Set'; given '42'; when HashSet() add '42'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithSet_given42_whenHashSetAdd42_thenReturnTrue() {
    // Arrange
    HashSet<Object> set = new HashSet<>();
    set.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(set));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Set)} with {@code Set}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link HashSet#HashSet()} add two.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Set)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Set) with 'Set'; given two; when HashSet() add two; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithSet_givenTwo_whenHashSetAddTwo_thenReturnTrue() {
    // Arrange
    HashSet<Object> set = new HashSet<>();
    set.add(2);
    set.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(set));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Set)} with {@code Set}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Set)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Set) with 'Set'; when HashSet(); then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithSet_whenHashSet_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(new HashSet<>()));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Set)} with {@code Set}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Set)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Set) with 'Set'; when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testIsNotEmptyWithSet_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty((Set) null));
  }
}
