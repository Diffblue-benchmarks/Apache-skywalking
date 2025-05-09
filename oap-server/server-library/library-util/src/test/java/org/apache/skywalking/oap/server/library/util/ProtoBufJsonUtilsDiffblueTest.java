package org.apache.skywalking.oap.server.library.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.BytesValue;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.EnumOptions;
import com.google.protobuf.DescriptorProtos.FeatureSetDefaults;
import com.google.protobuf.DescriptorProtos.FeatureSetDefaults.FeatureSetEditionDefault;
import com.google.protobuf.DescriptorProtos.FieldOptions;
import com.google.protobuf.DescriptorProtos.FieldOptions.EditionDefault;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.Value;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProtoBufJsonUtilsDiffblueTest {
  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@link Boolean#FALSE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return FALSE toString")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnFalseToString() throws IOException {
    // Arrange and Act
    String actualToJSONResult = ProtoBufJsonUtils.toJSON(BoolValue.getDefaultInstance());

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualToJSONResult);
  }

  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return '{}'")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnLeftCurlyBracketRightCurlyBracket() throws IOException {
    // Arrange, Act and Assert
    assertEquals("{}", ProtoBufJsonUtils.toJSON(Any.getDefaultInstance()));
  }

  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@code { }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return '{ }'")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnLeftCurlyBracketSpaceRightCurlyBracket() throws IOException {
    // Arrange, Act and Assert
    assertEquals("{\n}", ProtoBufJsonUtils.toJSON(FeatureSetDefaults.getDefaultInstance()));
  }

  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@code { }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return '{ }'")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnLeftCurlyBracketSpaceRightCurlyBracket2() throws IOException {
    // Arrange, Act and Assert
    assertEquals("{\n}", ProtoBufJsonUtils.toJSON(FeatureSetEditionDefault.getDefaultInstance()));
  }

  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@code { }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return '{ }'")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnLeftCurlyBracketSpaceRightCurlyBracket3() throws IOException {
    // Arrange, Act and Assert
    assertEquals("{\n}", ProtoBufJsonUtils.toJSON(EditionDefault.getDefaultInstance()));
  }

  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@code { }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return '{ }'")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnLeftCurlyBracketSpaceRightCurlyBracket4() throws IOException {
    // Arrange, Act and Assert
    assertEquals("{\n}", ProtoBufJsonUtils.toJSON(DescriptorProto.getDefaultInstance()));
  }

  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@code { }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return '{ }'")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnLeftCurlyBracketSpaceRightCurlyBracket5() throws IOException {
    // Arrange, Act and Assert
    assertEquals("{\n}", ProtoBufJsonUtils.toJSON(EnumOptions.getDefaultInstance()));
  }

  /**
   * Test {@link ProtoBufJsonUtils#toJSON(Message)}.
   * <ul>
   *   <li>When DefaultInstance.</li>
   *   <li>Then return {@code ""}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#toJSON(Message)}
   */
  @Test
  @DisplayName("Test toJSON(Message); when DefaultInstance; then return '\"\"'")
  @Tag("MaintainedByDiffblue")
  void testToJSON_whenDefaultInstance_thenReturnQuotationMarkQuotationMark() throws IOException {
    // Arrange, Act and Assert
    assertEquals("\"\"", ProtoBufJsonUtils.toJSON(BytesValue.getDefaultInstance()));
  }

  /**
   * Test {@link ProtoBufJsonUtils#fromJSON(String, Builder)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link Message.Builder#getDescriptorForType()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#fromJSON(String, Message.Builder)}
   */
  @Test
  @DisplayName("Test fromJSON(String, Builder); given 'null'; then calls getDescriptorForType()")
  @Tag("MaintainedByDiffblue")
  void testFromJSON_givenNull_thenCallsGetDescriptorForType() throws IOException {
    // Arrange
    Builder targetBuilder = mock(Builder.class);
    when(targetBuilder.setField(Mockito.<FieldDescriptor>any(), Mockito.<Object>any())).thenReturn(null);
    when(targetBuilder.getDescriptorForType()).thenReturn(Value.getDescriptor());

    // Act
    ProtoBufJsonUtils.fromJSON("Json", targetBuilder);

    // Assert
    verify(targetBuilder, atLeast(1)).getDescriptorForType();
    verify(targetBuilder).setField(isA(FieldDescriptor.class), isA(Object.class));
  }

  /**
   * Test {@link ProtoBufJsonUtils#fromJSON(String, Builder)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link Message.Builder#getDescriptorForType()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#fromJSON(String, Message.Builder)}
   */
  @Test
  @DisplayName("Test fromJSON(String, Builder); given 'null'; then calls getDescriptorForType()")
  @Tag("MaintainedByDiffblue")
  void testFromJSON_givenNull_thenCallsGetDescriptorForType2() throws IOException {
    // Arrange
    Builder targetBuilder = mock(Builder.class);
    when(targetBuilder.setField(Mockito.<FieldDescriptor>any(), Mockito.<Object>any())).thenReturn(null);
    when(targetBuilder.getDescriptorForType()).thenReturn(BytesValue.getDescriptor());

    // Act
    ProtoBufJsonUtils.fromJSON("Json", targetBuilder);

    // Assert
    verify(targetBuilder, atLeast(1)).getDescriptorForType();
    verify(targetBuilder).setField(isA(FieldDescriptor.class), isA(Object.class));
  }

  /**
   * Test {@link ProtoBufJsonUtils#fromJSON(String, Builder)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then calls {@link Message.Builder#getDescriptorForType()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProtoBufJsonUtils#fromJSON(String, Message.Builder)}
   */
  @Test
  @DisplayName("Test fromJSON(String, Builder); given 'null'; when '42'; then calls getDescriptorForType()")
  @Tag("MaintainedByDiffblue")
  void testFromJSON_givenNull_when42_thenCallsGetDescriptorForType() throws IOException {
    // Arrange
    Builder targetBuilder = mock(Builder.class);
    when(targetBuilder.setField(Mockito.<FieldDescriptor>any(), Mockito.<Object>any())).thenReturn(null);
    when(targetBuilder.getDescriptorForType()).thenReturn(BytesValue.getDescriptor());

    // Act
    ProtoBufJsonUtils.fromJSON("42", targetBuilder);

    // Assert
    verify(targetBuilder, atLeast(1)).getDescriptorForType();
    verify(targetBuilder).setField(isA(FieldDescriptor.class), isA(Object.class));
  }
}
