package org.apache.skywalking.mqe.rt.grammar;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MQELexerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MQELexer#getATN()}
   *   <li>{@link MQELexer#getChannelNames()}
   *   <li>{@link MQELexer#getGrammarFileName()}
   *   <li>{@link MQELexer#getModeNames()}
   *   <li>{@link MQELexer#getRuleNames()}
   *   <li>{@link MQELexer#getSerializedATN()}
   *   <li>{@link MQELexer#getTokenNames()}
   *   <li>{@link MQELexer#getVocabulary()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    MQELexer mqeLexer = new MQELexer(new ANTLRInputStream("Input"));

    // Act
    ATN actualATN = mqeLexer.getATN();
    String[] actualChannelNames = mqeLexer.getChannelNames();
    String actualGrammarFileName = mqeLexer.getGrammarFileName();
    String[] actualModeNames = mqeLexer.getModeNames();
    String[] actualRuleNames = mqeLexer.getRuleNames();
    String actualSerializedATN = mqeLexer.getSerializedATN();
    String[] actualTokenNames = mqeLexer.getTokenNames();

    // Assert
    assertTrue(mqeLexer.getVocabulary() instanceof VocabularyImpl);
    assertEquals("MQELexer.g4", actualGrammarFileName);
    assertEquals(MQELexer._serializedATN, actualSerializedATN);
    assertSame(mqeLexer._ATN, actualATN);
    assertSame(mqeLexer.channelNames, actualChannelNames);
    assertSame(mqeLexer.ruleNames, actualRuleNames);
    assertSame(mqeLexer.tokenNames, actualTokenNames);
    assertArrayEquals(new String[]{"DEFAULT_MODE"}, actualModeNames);
  }
}
