package org.apache.skywalking.oal.rt.grammar;

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

class OALLexerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link OALLexer#getATN()}
   *   <li>{@link OALLexer#getChannelNames()}
   *   <li>{@link OALLexer#getGrammarFileName()}
   *   <li>{@link OALLexer#getModeNames()}
   *   <li>{@link OALLexer#getRuleNames()}
   *   <li>{@link OALLexer#getSerializedATN()}
   *   <li>{@link OALLexer#getTokenNames()}
   *   <li>{@link OALLexer#getVocabulary()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    OALLexer oalLexer = new OALLexer(new ANTLRInputStream("Input"));

    // Act
    ATN actualATN = oalLexer.getATN();
    String[] actualChannelNames = oalLexer.getChannelNames();
    String actualGrammarFileName = oalLexer.getGrammarFileName();
    String[] actualModeNames = oalLexer.getModeNames();
    String[] actualRuleNames = oalLexer.getRuleNames();
    String actualSerializedATN = oalLexer.getSerializedATN();
    String[] actualTokenNames = oalLexer.getTokenNames();

    // Assert
    assertTrue(oalLexer.getVocabulary() instanceof VocabularyImpl);
    assertEquals("OALLexer.g4", actualGrammarFileName);
    assertEquals(OALLexer._serializedATN, actualSerializedATN);
    assertSame(oalLexer._ATN, actualATN);
    assertSame(oalLexer.channelNames, actualChannelNames);
    assertSame(oalLexer.ruleNames, actualRuleNames);
    assertSame(oalLexer.tokenNames, actualTokenNames);
    assertArrayEquals(new String[]{"DEFAULT_MODE"}, actualModeNames);
  }
}
