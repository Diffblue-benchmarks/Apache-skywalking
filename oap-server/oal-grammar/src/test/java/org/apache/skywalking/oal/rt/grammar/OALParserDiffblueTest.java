package org.apache.skywalking.oal.rt.grammar;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.ListTokenSource;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Parser.TraceListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.ProxyErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.BasicBlockStartState;
import org.antlr.v4.runtime.atn.BlockEndState;
import org.antlr.v4.runtime.atn.DecisionState;
import org.antlr.v4.runtime.atn.EpsilonTransition;
import org.antlr.v4.runtime.atn.RuleStopState;
import org.antlr.v4.runtime.atn.StarBlockStartState;
import org.antlr.v4.runtime.atn.StarLoopEntryState;
import org.antlr.v4.runtime.atn.Transition;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ErrorNodeImpl;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.xpath.XPathLexer;
import org.apache.skywalking.oal.rt.grammar.OALParser.AggregateFunctionContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.AggregationStatementContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.AttributeExpressionContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.AttributeExpressionSegmentContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.BooleanConditionValueContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.BooleanMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.BooleanNotEqualMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.CastStmtContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.ConditionAttributeContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.ConditionAttributeStmtContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.ContainMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.DecorateSourceContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.DisableSourceContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.DisableStatementContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.EnumConditionValueContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.ExpressionAttrCastContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.ExpressionContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.FilterExpressionContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.FilterStatementContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.FuncParamExpressionContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.FunctionArgCastContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.FunctionNameContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.GreaterEqualMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.GreaterMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.InMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.LessEqualMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.LessMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.LikeMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.LiteralExpressionContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.MapAttributeContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.MetricStatementContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.MultiConditionValueContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.NotContainMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.NotEqualMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.NullConditionValueContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.NumberConditionValueContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.NumberMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.RootContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.SourceAttrCastContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.SourceAttributeContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.SourceAttributeStmtContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.SourceContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.StringConditionValueContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.StringMatchContext;
import org.apache.skywalking.oal.rt.grammar.OALParser.VariableContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class OALParserDiffblueTest {
  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#attributeExpression()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#attributeExpression()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext attributeExpression()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextAttributeExpression() {
    // Arrange, Act and Assert
    assertTrue((new AggregateFunctionContext(new ParserRuleContext(), 1)).attributeExpression().isEmpty());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#attributeExpression(int)} with {@code int}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#attributeExpression(int)}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext attributeExpression(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextAttributeExpressionWithInt() {
    // Arrange, Act and Assert
    assertNull((new AggregateFunctionContext(new ParserRuleContext(), 1)).attributeExpression(1));
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#COMMA()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#COMMA()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext COMMA()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextComma() {
    // Arrange, Act and Assert
    assertNull((new AggregateFunctionContext(new ParserRuleContext(), 1)).COMMA());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#funcParamExpression()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#funcParamExpression()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext funcParamExpression()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextFuncParamExpression() {
    // Arrange, Act and Assert
    assertTrue((new AggregateFunctionContext(new ParserRuleContext(), 1)).funcParamExpression().isEmpty());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#funcParamExpression(int)} with {@code int}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#funcParamExpression(int)}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext funcParamExpression(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextFuncParamExpressionWithInt() {
    // Arrange, Act and Assert
    assertNull((new AggregateFunctionContext(new ParserRuleContext(), 1)).funcParamExpression(1));
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#functionName()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#functionName()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext functionName()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextFunctionName() {
    // Arrange, Act and Assert
    assertNull((new AggregateFunctionContext(new ParserRuleContext(), 1)).functionName());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_TCP_SERVICE_INSTANCE_RELATION,
        (new AggregateFunctionContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#literalExpression()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#literalExpression()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext literalExpression()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextLiteralExpression() {
    // Arrange, Act and Assert
    assertTrue((new AggregateFunctionContext(new ParserRuleContext(), 1)).literalExpression().isEmpty());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#literalExpression(int)} with {@code int}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#literalExpression(int)}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext literalExpression(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextLiteralExpressionWithInt() {
    // Arrange, Act and Assert
    assertNull((new AggregateFunctionContext(new ParserRuleContext(), 1)).literalExpression(1));
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#LR_BRACKET()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#LR_BRACKET()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext LR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextLr_bracket() {
    // Arrange, Act and Assert
    assertNull((new AggregateFunctionContext(new ParserRuleContext(), 1)).LR_BRACKET());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#AggregateFunctionContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#AggregateFunctionContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext new AggregateFunctionContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextNewAggregateFunctionContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AggregateFunctionContext actualAggregateFunctionContext = new AggregateFunctionContext(parent, 1);

    // Assert
    assertNull(actualAggregateFunctionContext.getStart());
    assertNull(actualAggregateFunctionContext.getStop());
    assertSame(parent, actualAggregateFunctionContext.getParent());
  }

  /**
   * Test AggregateFunctionContext {@link AggregateFunctionContext#RR_BRACKET()}.
   * <p>
   * Method under test: {@link AggregateFunctionContext#RR_BRACKET()}
   */
  @Test
  @DisplayName("Test AggregateFunctionContext RR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunctionContextRr_bracket() {
    // Arrange, Act and Assert
    assertNull((new AggregateFunctionContext(new ParserRuleContext(), 1)).RR_BRACKET());
  }

  /**
   * Test {@link OALParser#aggregateFunction()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregateFunction()}
   */
  @Test
  @DisplayName("Test aggregateFunction(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunction_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.aggregateFunction().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.LR_BRACKET}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#aggregateFunction()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregateFunction()}
   */
  @Test
  @DisplayName("Test aggregateFunction(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunction_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.aggregateFunction().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.LR_BRACKET}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#aggregateFunction()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([178] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregateFunction()}
   */
  @Test
  @DisplayName("Test aggregateFunction(); then return toStringTree is '([] ([178] Input))'")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunction_thenReturnToStringTreeIs178Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    AggregateFunctionContext actualAggregateFunctionResult = oalParser.aggregateFunction();

    // Assert
    Token start = actualAggregateFunctionResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregateFunctionResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([178] Input))", actualAggregateFunctionResult.toStringTree());
    assertEquals("Input", actualAggregateFunctionResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualAggregateFunctionResult, recognitionException.getCtx());
    assertSame(start, actualAggregateFunctionResult.getStop());
  }

  /**
   * Test {@link OALParser#aggregateFunction()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([178] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregateFunction()}
   */
  @Test
  @DisplayName("Test aggregateFunction(); then return toStringTree is '([] ([178] 42))'")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunction_thenReturnToStringTreeIs17842() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    AggregateFunctionContext actualAggregateFunctionResult = oalParser.aggregateFunction();

    // Assert
    Token start = actualAggregateFunctionResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregateFunctionResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([178] 42))", actualAggregateFunctionResult.toStringTree());
    assertEquals("42", actualAggregateFunctionResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualAggregateFunctionResult, recognitionException.getCtx());
    assertSame(start, actualAggregateFunctionResult.getStop());
  }

  /**
   * Test {@link OALParser#aggregateFunction()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregateFunction()}
   */
  @Test
  @DisplayName("Test aggregateFunction(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testAggregateFunction_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    AggregateFunctionContext actualAggregateFunctionResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 24, 'A', 24, 'A', 24, 'A', 24}))))))
            .aggregateFunction();

    // Assert
    assertTrue(actualAggregateFunctionResult.getStop() instanceof CommonToken);
    assertTrue(actualAggregateFunctionResult.exception instanceof NoViableAltException);
    assertEquals("([] ([178] A) <missing '('> A A A)", actualAggregateFunctionResult.toStringTree());
    assertEquals("A<missing '('>AAA", actualAggregateFunctionResult.getText());
    assertEquals(5, actualAggregateFunctionResult.children.size());
    assertEquals(5, actualAggregateFunctionResult.getChildCount());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#DelimitedComment()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#DelimitedComment()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext DelimitedComment()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextDelimitedComment() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).DelimitedComment());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#EOF()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#EOF()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext EOF()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextEof() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).EOF());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#EQUAL()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#EQUAL()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext EQUAL()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextEqual() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).EQUAL());
  }

  /**
   * Test AggregationStatementContext getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AggregationStatementContext#AggregationStatementContext(ParserRuleContext, int)}
   *   <li>{@link AggregationStatementContext#getRuleIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test AggregationStatementContext getters and setters")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextGettersAndSetters() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AggregationStatementContext actualAggregationStatementContext = new AggregationStatementContext(parent, 1);
    int actualRuleIndex = actualAggregationStatementContext.getRuleIndex();

    // Assert
    assertNull(actualAggregationStatementContext.getStart());
    assertNull(actualAggregationStatementContext.getStop());
    assertEquals(1, actualRuleIndex);
    assertSame(parent, actualAggregationStatementContext.getParent());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#LineComment()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#LineComment()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext LineComment()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextLineComment() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).LineComment());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#metricStatement()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#metricStatement()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext metricStatement()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextMetricStatement() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).metricStatement());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#SEMI()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#SEMI()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext SEMI()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextSemi() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).SEMI());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#SPACE()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#SPACE()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext SPACE()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextSpace() {
    // Arrange, Act and Assert
    assertTrue((new AggregationStatementContext(new ParserRuleContext(), 1)).SPACE().isEmpty());
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#SPACE(int)} with {@code int}.
   * <p>
   * Method under test: {@link AggregationStatementContext#SPACE(int)}
   */
  @Test
  @DisplayName("Test AggregationStatementContext SPACE(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextSpaceWithInt() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).SPACE(1));
  }

  /**
   * Test AggregationStatementContext {@link AggregationStatementContext#variable()}.
   * <p>
   * Method under test: {@link AggregationStatementContext#variable()}
   */
  @Test
  @DisplayName("Test AggregationStatementContext variable()")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatementContextVariable() {
    // Arrange, Act and Assert
    assertNull((new AggregationStatementContext(new ParserRuleContext(), 1)).variable());
  }

  /**
   * Test {@link OALParser#aggregationStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregationStatement()}
   */
  @Test
  @DisplayName("Test aggregationStatement(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatement_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.aggregationStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.EQUAL, OALParser.SPACE}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#aggregationStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregationStatement()}
   */
  @Test
  @DisplayName("Test aggregationStatement(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatement_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.aggregationStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.EQUAL, OALParser.SPACE}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#aggregationStatement()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregationStatement()}
   */
  @Test
  @DisplayName("Test aggregationStatement(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatement_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    AggregationStatementContext actualAggregationStatementResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).aggregationStatement();

    // Assert
    assertEquals("", actualAggregationStatementResult.getText());
    assertEquals("([] [97])", actualAggregationStatementResult.toStringTree());
    assertNull(actualAggregationStatementResult.getStop());
    assertEquals(1, actualAggregationStatementResult.children.size());
  }

  /**
   * Test {@link OALParser#aggregationStatement()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([97] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregationStatement()}
   */
  @Test
  @DisplayName("Test aggregationStatement(); then return toStringTree is '([] ([97] Input))'")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatement_thenReturnToStringTreeIs97Input() throws RecognitionException {
    // Arrange and Act
    AggregationStatementContext actualAggregationStatementResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).aggregationStatement();

    // Assert
    assertEquals("([] ([97] Input))", actualAggregationStatementResult.toStringTree());
    assertEquals("Input", actualAggregationStatementResult.getText());
    assertEquals(1, actualAggregationStatementResult.children.size());
  }

  /**
   * Test {@link OALParser#aggregationStatement()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([97] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregationStatement()}
   */
  @Test
  @DisplayName("Test aggregationStatement(); then return toStringTree is '([] ([97] 42))'")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatement_thenReturnToStringTreeIs9742() throws RecognitionException {
    // Arrange and Act
    AggregationStatementContext actualAggregationStatementResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).aggregationStatement();

    // Assert
    assertEquals("([] ([97] 42))", actualAggregationStatementResult.toStringTree());
    assertEquals("42", actualAggregationStatementResult.getText());
    assertEquals(1, actualAggregationStatementResult.children.size());
  }

  /**
   * Test {@link OALParser#aggregationStatement()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#aggregationStatement()}
   */
  @Test
  @DisplayName("Test aggregationStatement(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testAggregationStatement_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    AggregationStatementContext actualAggregationStatementResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 2, 'A', 2, 'A', 2, 'A', 2}))))))
            .aggregationStatement();

    // Assert
    assertTrue(actualAggregationStatementResult.getStop() instanceof CommonToken);
    assertEquals("([] ([97] A) A A A)", actualAggregationStatementResult.toStringTree());
    assertEquals("AAAA", actualAggregationStatementResult.getText());
    assertEquals(4, actualAggregationStatementResult.children.size());
    assertEquals(4, actualAggregationStatementResult.getChildCount());
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.setErrorHandler(new BailErrorStrategy());
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    AttributeExpressionContext actualAttributeExpressionResult = oalParser.attributeExpression();

    // Assert
    Token start = actualAttributeExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([206] Input))", actualAttributeExpressionResult.toStringTree());
    assertEquals("Input", actualAttributeExpressionResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeExpressionResult.getStop());
  }

  /**
   * Test AttributeExpressionContext {@link AttributeExpressionContext#attributeExpressionSegment()}.
   * <p>
   * Method under test: {@link AttributeExpressionContext#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test AttributeExpressionContext attributeExpressionSegment()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionContextAttributeExpressionSegment() {
    // Arrange, Act and Assert
    assertTrue((new AttributeExpressionContext(new ParserRuleContext(), 1)).attributeExpressionSegment().isEmpty());
  }

  /**
   * Test AttributeExpressionContext {@link AttributeExpressionContext#attributeExpressionSegment(int)} with {@code int}.
   * <p>
   * Method under test: {@link AttributeExpressionContext#attributeExpressionSegment(int)}
   */
  @Test
  @DisplayName("Test AttributeExpressionContext attributeExpressionSegment(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionContextAttributeExpressionSegmentWithInt() {
    // Arrange, Act and Assert
    assertNull((new AttributeExpressionContext(new ParserRuleContext(), 1)).attributeExpressionSegment(1));
  }

  /**
   * Test AttributeExpressionContext {@link AttributeExpressionContext#DOT()}.
   * <p>
   * Method under test: {@link AttributeExpressionContext#DOT()}
   */
  @Test
  @DisplayName("Test AttributeExpressionContext DOT()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionContextDot() {
    // Arrange, Act and Assert
    assertTrue((new AttributeExpressionContext(new ParserRuleContext(), 1)).DOT().isEmpty());
  }

  /**
   * Test AttributeExpressionContext {@link AttributeExpressionContext#DOT(int)} with {@code int}.
   * <p>
   * Method under test: {@link AttributeExpressionContext#DOT(int)}
   */
  @Test
  @DisplayName("Test AttributeExpressionContext DOT(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionContextDotWithInt() {
    // Arrange, Act and Assert
    assertNull((new AttributeExpressionContext(new ParserRuleContext(), 1)).DOT(1));
  }

  /**
   * Test AttributeExpressionContext {@link AttributeExpressionContext#functionArgCast()}.
   * <p>
   * Method under test: {@link AttributeExpressionContext#functionArgCast()}
   */
  @Test
  @DisplayName("Test AttributeExpressionContext functionArgCast()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionContextFunctionArgCast() {
    // Arrange, Act and Assert
    assertNull((new AttributeExpressionContext(new ParserRuleContext(), 1)).functionArgCast());
  }

  /**
   * Test AttributeExpressionContext {@link AttributeExpressionContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AttributeExpressionContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AttributeExpressionContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_JVM_MEMORY_POOL,
        (new AttributeExpressionContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AttributeExpressionContext {@link AttributeExpressionContext#AttributeExpressionContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AttributeExpressionContext#AttributeExpressionContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AttributeExpressionContext new AttributeExpressionContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionContextNewAttributeExpressionContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AttributeExpressionContext actualAttributeExpressionContext = new AttributeExpressionContext(parent, 1);

    // Assert
    assertNull(actualAttributeExpressionContext.getStart());
    assertNull(actualAttributeExpressionContext.getStop());
    assertSame(parent, actualAttributeExpressionContext.getParent());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setErrorHandler(new BailErrorStrategy());
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = oalParser.attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualAttributeExpressionSegmentResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    assertTrue(getResult instanceof TerminalNodeImpl);
    TokenSource tokenSource = start.getTokenSource();
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("Input", ((OALLexer) tokenSource).getText());
    assertEquals("Input", actualAttributeExpressionSegmentResult.getText());
    assertEquals("Input", start.getText());
    assertEquals("Input", getResult.getText());
    assertEquals("Input", getResult.toStringTree());
    assertEquals(5, tokenSource.getCharPositionInLine());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = oalParser.attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualAttributeExpressionSegmentResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    assertTrue(getResult instanceof TerminalNodeImpl);
    TokenSource tokenSource = start.getTokenSource();
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("Input", ((OALLexer) tokenSource).getText());
    assertEquals("Input", actualAttributeExpressionSegmentResult.getText());
    assertEquals("Input", start.getText());
    assertEquals("Input", getResult.getText());
    assertEquals("Input", getResult.toStringTree());
    assertEquals(5, tokenSource.getCharPositionInLine());
  }

  /**
   * Test AttributeExpressionSegmentContext {@link AttributeExpressionSegmentContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AttributeExpressionSegmentContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AttributeExpressionSegmentContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegmentContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_JVM_GC,
        (new AttributeExpressionSegmentContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AttributeExpressionSegmentContext {@link AttributeExpressionSegmentContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link AttributeExpressionSegmentContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test AttributeExpressionSegmentContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegmentContextIdentifier() {
    // Arrange, Act and Assert
    assertNull((new AttributeExpressionSegmentContext(new ParserRuleContext(), 1)).IDENTIFIER());
  }

  /**
   * Test AttributeExpressionSegmentContext {@link AttributeExpressionSegmentContext#mapAttribute()}.
   * <p>
   * Method under test: {@link AttributeExpressionSegmentContext#mapAttribute()}
   */
  @Test
  @DisplayName("Test AttributeExpressionSegmentContext mapAttribute()")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegmentContextMapAttribute() {
    // Arrange, Act and Assert
    assertNull((new AttributeExpressionSegmentContext(new ParserRuleContext(), 1)).mapAttribute());
  }

  /**
   * Test AttributeExpressionSegmentContext {@link AttributeExpressionSegmentContext#AttributeExpressionSegmentContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AttributeExpressionSegmentContext#AttributeExpressionSegmentContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AttributeExpressionSegmentContext new AttributeExpressionSegmentContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegmentContextNewAttributeExpressionSegmentContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentContext = new AttributeExpressionSegmentContext(
        parent, 1);

    // Assert
    assertNull(actualAttributeExpressionSegmentContext.getStart());
    assertNull(actualAttributeExpressionSegmentContext.getStop());
    assertSame(parent, actualAttributeExpressionSegmentContext.getParent());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] "A" A)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); given 'A'; then return toStringTree is '([] \"A\" A)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_givenA_thenReturnToStringTreeIsAA() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{17, '"', 'A', '"', 'A', '"', 'A', '"'}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = (new OALParser(
        new BufferedTokenStream(tokenSource))).attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    Token stop = actualAttributeExpressionSegmentResult.getStop();
    assertTrue(stop instanceof CommonToken);
    assertEquals("([] \"A\" A)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("\"A\"", start.getText());
    assertEquals("\"A\"A", actualAttributeExpressionSegmentResult.getText());
    assertEquals(1, start.getCharPositionInLine());
    assertEquals(3, start.getStopIndex());
    assertEquals(4, stop.getCharPositionInLine());
    assertEquals(4, stop.getStartIndex());
    assertEquals(4, stop.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input, stop.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(tokenSource, stop.getTokenSource());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = oalParser.attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAttributeExpressionSegmentResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualAttributeExpressionSegmentResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([34] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return toStringTree is '([34] Input)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnToStringTreeIs34Input() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    ParserRuleContext localctx = new ParserRuleContext();
    oalParser.enterRule(localctx, OALParser.RULE_conditionAttribute, 1);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = oalParser.attributeExpressionSegment();

    // Assert
    assertEquals("([34] Input)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals(2, actualAttributeExpressionSegmentResult.depth());
    assertFalse(actualAttributeExpressionSegmentResult.isEmpty());
    assertEquals(OALParser.RULE_conditionAttribute, actualAttributeExpressionSegmentResult.invokingState);
    assertSame(localctx, actualAttributeExpressionSegmentResult.getParent());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = oalParser.attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAttributeExpressionSegmentResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("42", actualAttributeExpressionSegmentResult.getText());
    assertEquals("42", start.getText());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualAttributeExpressionSegmentResult, recognitionException.getCtx());
    assertSame(start, actualAttributeExpressionSegmentResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] A)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return toStringTree is '([] A)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnToStringTreeIsA() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A\"A\"A\"A\"".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = (new OALParser(
        new BufferedTokenStream(tokenSource))).attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] A)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("A", actualAttributeExpressionSegmentResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeExpressionSegmentResult.getStop());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] "" A)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return toStringTree is '([] \"\" A)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnToStringTreeIsA2() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("\"\"A\"A\"A\"".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = (new OALParser(
        new BufferedTokenStream(tokenSource))).attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    Token stop = actualAttributeExpressionSegmentResult.getStop();
    assertTrue(stop instanceof CommonToken);
    assertEquals("([] \"\" A)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("\"\"", start.getText());
    assertEquals("\"\"A", actualAttributeExpressionSegmentResult.getText());
    assertEquals(2, stop.getCharPositionInLine());
    assertEquals(2, stop.getStartIndex());
    assertEquals(2, stop.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input, stop.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(tokenSource, stop.getTokenSource());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange and Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualAttributeExpressionSegmentResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    assertTrue(getResult instanceof TerminalNodeImpl);
    TokenSource tokenSource = start.getTokenSource();
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("Input", ((OALLexer) tokenSource).getText());
    assertEquals("Input", actualAttributeExpressionSegmentResult.getText());
    assertEquals("Input", start.getText());
    assertEquals("Input", getResult.getText());
    assertEquals("Input", getResult.toStringTree());
    assertEquals(5, tokenSource.getCharPositionInLine());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnToStringTreeIsInput2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = oalParser.attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualAttributeExpressionSegmentResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    assertTrue(getResult instanceof TerminalNodeImpl);
    TokenSource tokenSource = start.getTokenSource();
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("Input", ((OALLexer) tokenSource).getText());
    assertEquals("Input", actualAttributeExpressionSegmentResult.getText());
    assertEquals("Input", start.getText());
    assertEquals("Input", getResult.getText());
    assertEquals("Input", getResult.toStringTree());
    assertEquals(5, tokenSource.getCharPositionInLine());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] nput)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then return toStringTree is '([] nput)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenReturnToStringTreeIsNput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    input.seek(1);

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(input)))).attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualAttributeExpressionSegmentResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    assertTrue(getResult instanceof TerminalNodeImpl);
    TokenSource tokenSource = start.getTokenSource();
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] nput)", actualAttributeExpressionSegmentResult.toStringTree());
    assertEquals("nput", ((OALLexer) tokenSource).getText());
    assertEquals("nput", actualAttributeExpressionSegmentResult.getText());
    assertEquals("nput", start.getText());
    assertEquals("nput", getResult.getText());
    assertEquals("nput", getResult.toStringTree());
    assertEquals(1, ((OALLexer) tokenSource)._tokenStartCharIndex);
    assertEquals(4, tokenSource.getCharPositionInLine());
  }

  /**
   * Test {@link OALParser#attributeExpressionSegment()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpressionSegment()}
   */
  @Test
  @DisplayName("Test attributeExpressionSegment(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpressionSegment_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    AttributeExpressionSegmentContext actualAttributeExpressionSegmentResult = oalParser.attributeExpressionSegment();

    // Assert
    Token start = actualAttributeExpressionSegmentResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAttributeExpressionSegmentResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualAttributeExpressionSegmentResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <ul>
   *   <li>Given {@link OALParser#OALParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression(); given OALParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression_givenOALParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    AttributeExpressionContext actualAttributeExpressionResult = oalParser.attributeExpression();

    // Assert
    Token start = actualAttributeExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([206] Input))", actualAttributeExpressionResult.toStringTree());
    assertEquals("Input", actualAttributeExpressionResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeExpressionResult.getStop());
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} InputStream return {@link BufferedTokenStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression(); then exception InputStream return BufferedTokenStream")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression_thenExceptionInputStreamReturnBufferedTokenStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    AttributeExpressionContext actualAttributeExpressionResult = oalParser.attributeExpression();

    // Assert
    RecognitionException recognitionException = actualAttributeExpressionResult.exception;
    IntStream inputStream = recognitionException.getInputStream();
    assertTrue(inputStream instanceof BufferedTokenStream);
    Token start = actualAttributeExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualAttributeExpressionResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("([] 42)", actualAttributeExpressionResult.toStringTree());
    assertEquals("42", actualAttributeExpressionResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, inputStream);
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualAttributeExpressionResult, recognitionException.getCtx());
    assertSame(start, actualAttributeExpressionResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} Recognizer return {@link OALParser}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression(); then exception Recognizer return OALParser")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression_thenExceptionRecognizerReturnOALParser() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.attributeExpression().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(
        new int[]{OALParser.IDENTIFIER, OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT,
            OALParser.STRING_TO_INT, OALParser.STRING_TO_INT_SHORT},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    AttributeExpressionContext actualAttributeExpressionResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).attributeExpression();

    // Assert
    assertEquals("", actualAttributeExpressionResult.getText());
    assertEquals("[]", actualAttributeExpressionResult.toStringTree());
    assertNull(actualAttributeExpressionResult.children);
    assertNull(actualAttributeExpressionResult.getStop());
    assertEquals(0, actualAttributeExpressionResult.getChildCount());
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([206] A))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression(); then return toStringTree is '([] ([206] A))'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression_thenReturnToStringTreeIs206A() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A A A A ".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    AttributeExpressionContext actualAttributeExpressionResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .attributeExpression();

    // Assert
    Token start = actualAttributeExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([206] A))", actualAttributeExpressionResult.toStringTree());
    assertEquals("A", actualAttributeExpressionResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeExpressionResult.getStop());
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([206] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression(); then return toStringTree is '([] ([206] Input))'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression_thenReturnToStringTreeIs206Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    AttributeExpressionContext actualAttributeExpressionResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .attributeExpression();

    // Assert
    Token start = actualAttributeExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([206] Input))", actualAttributeExpressionResult.toStringTree());
    assertEquals("Input", actualAttributeExpressionResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeExpressionResult.getStop());
  }

  /**
   * Test {@link OALParser#attributeExpression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([206] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#attributeExpression()}
   */
  @Test
  @DisplayName("Test attributeExpression(); then return toStringTree is '([] ([206] Input))'")
  @Tag("MaintainedByDiffblue")
  void testAttributeExpression_thenReturnToStringTreeIs206Input2() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    AttributeExpressionContext actualAttributeExpressionResult = oalParser.attributeExpression();

    // Assert
    Token start = actualAttributeExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([206] Input))", actualAttributeExpressionResult.toStringTree());
    assertEquals("Input", actualAttributeExpressionResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeExpressionResult.getStop());
  }

  /**
   * Test BooleanConditionValueContext {@link BooleanConditionValueContext#BOOL_LITERAL()}.
   * <p>
   * Method under test: {@link BooleanConditionValueContext#BOOL_LITERAL()}
   */
  @Test
  @DisplayName("Test BooleanConditionValueContext BOOL_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValueContextBool_literal() {
    // Arrange, Act and Assert
    assertNull((new BooleanConditionValueContext(new ParserRuleContext(), 1)).BOOL_LITERAL());
  }

  /**
   * Test BooleanConditionValueContext {@link BooleanConditionValueContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link BooleanConditionValueContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test BooleanConditionValueContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValueContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_CILIUM_ENDPOINT,
        (new BooleanConditionValueContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test BooleanConditionValueContext {@link BooleanConditionValueContext#BooleanConditionValueContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link BooleanConditionValueContext#BooleanConditionValueContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test BooleanConditionValueContext new BooleanConditionValueContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValueContextNewBooleanConditionValueContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    BooleanConditionValueContext actualBooleanConditionValueContext = new BooleanConditionValueContext(parent, 1);

    // Assert
    assertNull(actualBooleanConditionValueContext.getStart());
    assertNull(actualBooleanConditionValueContext.getStop());
    assertSame(parent, actualBooleanConditionValueContext.getParent());
  }

  /**
   * Test {@link OALParser#booleanConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test booleanConditionValue(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.booleanConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.BOOL_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#booleanConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test booleanConditionValue(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.booleanConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.BOOL_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#booleanConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] AHAHAHAH)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test booleanConditionValue(); then return toStringTree is '([] AHAHAHAH)'")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValue_thenReturnToStringTreeIsAhahahah() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("AHAHAHAH".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    BooleanConditionValueContext actualBooleanConditionValueResult = oalParser.booleanConditionValue();

    // Assert
    Token start = actualBooleanConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBooleanConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] AHAHAHAH)", actualBooleanConditionValueResult.toStringTree());
    assertEquals("AHAHAHAH", actualBooleanConditionValueResult.getText());
    assertEquals("AHAHAHAH", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualBooleanConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualBooleanConditionValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#booleanConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test booleanConditionValue(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValue_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    BooleanConditionValueContext actualBooleanConditionValueResult = oalParser.booleanConditionValue();

    // Assert
    Token start = actualBooleanConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBooleanConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualBooleanConditionValueResult.toStringTree());
    assertEquals("Input", actualBooleanConditionValueResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualBooleanConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualBooleanConditionValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#booleanConditionValue()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test booleanConditionValue(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValue_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).booleanConditionValue().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link OALParser#booleanConditionValue()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test booleanConditionValue(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testBooleanConditionValue_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.booleanConditionValue().getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test BooleanMatchContext {@link BooleanMatchContext#booleanConditionValue()}.
   * <p>
   * Method under test: {@link BooleanMatchContext#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test BooleanMatchContext booleanConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatchContextBooleanConditionValue() {
    // Arrange, Act and Assert
    assertNull((new BooleanMatchContext(new ParserRuleContext(), 1)).booleanConditionValue());
  }

  /**
   * Test BooleanMatchContext {@link BooleanMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link BooleanMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test BooleanMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new BooleanMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test BooleanMatchContext {@link BooleanMatchContext#DUALEQUALS()}.
   * <p>
   * Method under test: {@link BooleanMatchContext#DUALEQUALS()}
   */
  @Test
  @DisplayName("Test BooleanMatchContext DUALEQUALS()")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatchContextDualequals() {
    // Arrange, Act and Assert
    assertNull((new BooleanMatchContext(new ParserRuleContext(), 1)).DUALEQUALS());
  }

  /**
   * Test BooleanMatchContext {@link BooleanMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link BooleanMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test BooleanMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_CLR_CPU,
        (new BooleanMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test BooleanMatchContext {@link BooleanMatchContext#BooleanMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link BooleanMatchContext#BooleanMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test BooleanMatchContext new BooleanMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatchContextNewBooleanMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    BooleanMatchContext actualBooleanMatchContext = new BooleanMatchContext(parent, 1);

    // Assert
    assertNull(actualBooleanMatchContext.getStart());
    assertNull(actualBooleanMatchContext.getStop());
    assertSame(parent, actualBooleanMatchContext.getParent());
  }

  /**
   * Test {@link OALParser#booleanMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanMatch()}
   */
  @Test
  @DisplayName("Test booleanMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.booleanMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.DUALEQUALS},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#booleanMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanMatch()}
   */
  @Test
  @DisplayName("Test booleanMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.booleanMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.DUALEQUALS},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#booleanMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanMatch()}
   */
  @Test
  @DisplayName("Test booleanMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    BooleanMatchContext actualBooleanMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).booleanMatch();

    // Assert
    assertEquals("", actualBooleanMatchResult.getText());
    assertEquals("([] [241])", actualBooleanMatchResult.toStringTree());
    assertNull(actualBooleanMatchResult.getStop());
    assertEquals(1, actualBooleanMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#booleanMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([241] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanMatch()}
   */
  @Test
  @DisplayName("Test booleanMatch(); then return toStringTree is '([] ([241] 42))'")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatch_thenReturnToStringTreeIs24142() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    BooleanMatchContext actualBooleanMatchResult = oalParser.booleanMatch();

    // Assert
    Token start = actualBooleanMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBooleanMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([241] 42))", actualBooleanMatchResult.toStringTree());
    assertEquals("42", actualBooleanMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualBooleanMatchResult, recognitionException.getCtx());
    assertSame(start, actualBooleanMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#booleanMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([241] ([324 241] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanMatch()}
   */
  @Test
  @DisplayName("Test booleanMatch(); then return toStringTree is '([] ([241] ([324 241] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatch_thenReturnToStringTreeIs241324241Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    BooleanMatchContext actualBooleanMatchResult = oalParser.booleanMatch();

    // Assert
    Token start = actualBooleanMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBooleanMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([241] ([324 241] Input)))", actualBooleanMatchResult.toStringTree());
    assertEquals("Input", actualBooleanMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualBooleanMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#booleanMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanMatch()}
   */
  @Test
  @DisplayName("Test booleanMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testBooleanMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    BooleanMatchContext actualBooleanMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A*A*A*A*".getBytes("UTF-8"))))))).booleanMatch();

    // Assert
    assertTrue(actualBooleanMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([241] ([324 241] A)) * A * A * A *)", actualBooleanMatchResult.toStringTree());
    assertEquals("A*A*A*A*", actualBooleanMatchResult.getText());
    assertEquals(8, actualBooleanMatchResult.children.size());
    assertEquals(8, actualBooleanMatchResult.getChildCount());
  }

  /**
   * Test BooleanNotEqualMatchContext {@link BooleanNotEqualMatchContext#booleanConditionValue()}.
   * <p>
   * Method under test: {@link BooleanNotEqualMatchContext#booleanConditionValue()}
   */
  @Test
  @DisplayName("Test BooleanNotEqualMatchContext booleanConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatchContextBooleanConditionValue() {
    // Arrange, Act and Assert
    assertNull((new BooleanNotEqualMatchContext(new ParserRuleContext(), 1)).booleanConditionValue());
  }

  /**
   * Test BooleanNotEqualMatchContext {@link BooleanNotEqualMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link BooleanNotEqualMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test BooleanNotEqualMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new BooleanNotEqualMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test BooleanNotEqualMatchContext {@link BooleanNotEqualMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link BooleanNotEqualMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test BooleanNotEqualMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_MQ_ENDPOINT_ACCESS,
        (new BooleanNotEqualMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test BooleanNotEqualMatchContext {@link BooleanNotEqualMatchContext#BooleanNotEqualMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link BooleanNotEqualMatchContext#BooleanNotEqualMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test BooleanNotEqualMatchContext new BooleanNotEqualMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatchContextNewBooleanNotEqualMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    BooleanNotEqualMatchContext actualBooleanNotEqualMatchContext = new BooleanNotEqualMatchContext(parent, 1);

    // Assert
    assertNull(actualBooleanNotEqualMatchContext.getStart());
    assertNull(actualBooleanNotEqualMatchContext.getStop());
    assertSame(parent, actualBooleanNotEqualMatchContext.getParent());
  }

  /**
   * Test BooleanNotEqualMatchContext {@link BooleanNotEqualMatchContext#NOT_EQUAL()}.
   * <p>
   * Method under test: {@link BooleanNotEqualMatchContext#NOT_EQUAL()}
   */
  @Test
  @DisplayName("Test BooleanNotEqualMatchContext NOT_EQUAL()")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatchContextNot_equal() {
    // Arrange, Act and Assert
    assertNull((new BooleanNotEqualMatchContext(new ParserRuleContext(), 1)).NOT_EQUAL());
  }

  /**
   * Test {@link OALParser#booleanNotEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanNotEqualMatch()}
   */
  @Test
  @DisplayName("Test booleanNotEqualMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.booleanNotEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.NOT_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#booleanNotEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanNotEqualMatch()}
   */
  @Test
  @DisplayName("Test booleanNotEqualMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.booleanNotEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.NOT_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#booleanNotEqualMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanNotEqualMatch()}
   */
  @Test
  @DisplayName("Test booleanNotEqualMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    BooleanNotEqualMatchContext actualBooleanNotEqualMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).booleanNotEqualMatch();

    // Assert
    assertEquals("", actualBooleanNotEqualMatchResult.getText());
    assertEquals("([] [272])", actualBooleanNotEqualMatchResult.toStringTree());
    assertNull(actualBooleanNotEqualMatchResult.getStop());
    assertEquals(1, actualBooleanNotEqualMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#booleanNotEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([272] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanNotEqualMatch()}
   */
  @Test
  @DisplayName("Test booleanNotEqualMatch(); then return toStringTree is '([] ([272] 42))'")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatch_thenReturnToStringTreeIs27242() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    BooleanNotEqualMatchContext actualBooleanNotEqualMatchResult = oalParser.booleanNotEqualMatch();

    // Assert
    Token start = actualBooleanNotEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBooleanNotEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([272] 42))", actualBooleanNotEqualMatchResult.toStringTree());
    assertEquals("42", actualBooleanNotEqualMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualBooleanNotEqualMatchResult, recognitionException.getCtx());
    assertSame(start, actualBooleanNotEqualMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#booleanNotEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([272] ([324 272] A8A8A8A8)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanNotEqualMatch()}
   */
  @Test
  @DisplayName("Test booleanNotEqualMatch(); then return toStringTree is '([] ([272] ([324 272] A8A8A8A8)))'")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatch_thenReturnToStringTreeIs272324272A8a8a8a8() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A8A8A8A8".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    BooleanNotEqualMatchContext actualBooleanNotEqualMatchResult = oalParser.booleanNotEqualMatch();

    // Assert
    Token start = actualBooleanNotEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBooleanNotEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([272] ([324 272] A8A8A8A8)))", actualBooleanNotEqualMatchResult.toStringTree());
    assertEquals("A8A8A8A8", actualBooleanNotEqualMatchResult.getText());
    assertEquals("A8A8A8A8", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualBooleanNotEqualMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#booleanNotEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([272] ([324 272] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#booleanNotEqualMatch()}
   */
  @Test
  @DisplayName("Test booleanNotEqualMatch(); then return toStringTree is '([] ([272] ([324 272] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testBooleanNotEqualMatch_thenReturnToStringTreeIs272324272Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    BooleanNotEqualMatchContext actualBooleanNotEqualMatchResult = oalParser.booleanNotEqualMatch();

    // Assert
    Token start = actualBooleanNotEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBooleanNotEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([272] ([324 272] Input)))", actualBooleanNotEqualMatchResult.toStringTree());
    assertEquals("Input", actualBooleanNotEqualMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualBooleanNotEqualMatchResult.getStop());
  }

  /**
   * Test CastStmtContext {@link CastStmtContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link CastStmtContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test CastStmtContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testCastStmtContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_BROWSER_APP_TRAFFIC, (new CastStmtContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test CastStmtContext {@link CastStmtContext#CastStmtContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link CastStmtContext#CastStmtContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test CastStmtContext new CastStmtContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testCastStmtContextNewCastStmtContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    CastStmtContext actualCastStmtContext = new CastStmtContext(parent, 1);

    // Assert
    assertNull(actualCastStmtContext.getStart());
    assertNull(actualCastStmtContext.getStop());
    assertSame(parent, actualCastStmtContext.getParent());
  }

  /**
   * Test CastStmtContext {@link CastStmtContext#STRING_TO_INT()}.
   * <p>
   * Method under test: {@link CastStmtContext#STRING_TO_INT()}
   */
  @Test
  @DisplayName("Test CastStmtContext STRING_TO_INT()")
  @Tag("MaintainedByDiffblue")
  void testCastStmtContextString_to_int() {
    // Arrange, Act and Assert
    assertNull((new CastStmtContext(new ParserRuleContext(), 1)).STRING_TO_INT());
  }

  /**
   * Test CastStmtContext {@link CastStmtContext#STRING_TO_INT_SHORT()}.
   * <p>
   * Method under test: {@link CastStmtContext#STRING_TO_INT_SHORT()}
   */
  @Test
  @DisplayName("Test CastStmtContext STRING_TO_INT_SHORT()")
  @Tag("MaintainedByDiffblue")
  void testCastStmtContextString_to_int_short() {
    // Arrange, Act and Assert
    assertNull((new CastStmtContext(new ParserRuleContext(), 1)).STRING_TO_INT_SHORT());
  }

  /**
   * Test CastStmtContext {@link CastStmtContext#STRING_TO_LONG()}.
   * <p>
   * Method under test: {@link CastStmtContext#STRING_TO_LONG()}
   */
  @Test
  @DisplayName("Test CastStmtContext STRING_TO_LONG()")
  @Tag("MaintainedByDiffblue")
  void testCastStmtContextString_to_long() {
    // Arrange, Act and Assert
    assertNull((new CastStmtContext(new ParserRuleContext(), 1)).STRING_TO_LONG());
  }

  /**
   * Test CastStmtContext {@link CastStmtContext#STRING_TO_LONG_SHORT()}.
   * <p>
   * Method under test: {@link CastStmtContext#STRING_TO_LONG_SHORT()}
   */
  @Test
  @DisplayName("Test CastStmtContext STRING_TO_LONG_SHORT()")
  @Tag("MaintainedByDiffblue")
  void testCastStmtContextString_to_long_short() {
    // Arrange, Act and Assert
    assertNull((new CastStmtContext(new ParserRuleContext(), 1)).STRING_TO_LONG_SHORT());
  }

  /**
   * Test {@link OALParser#castStmt()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#castStmt()}
   */
  @Test
  @DisplayName("Test castStmt(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testCastStmt_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.castStmt().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#castStmt()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#castStmt()}
   */
  @Test
  @DisplayName("Test castStmt(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testCastStmt_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.castStmt().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#castStmt()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#castStmt()}
   */
  @Test
  @DisplayName("Test castStmt(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testCastStmt_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.castStmt().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#castStmt()}.
   * <ul>
   *   <li>Then return not {@link ParserRuleContext#exception} Recognizer Trace.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#castStmt()}
   */
  @Test
  @DisplayName("Test castStmt(); then return not exception Recognizer Trace")
  @Tag("MaintainedByDiffblue")
  void testCastStmt_thenReturnNotExceptionRecognizerTrace() throws RecognitionException {
    // Arrange, Act and Assert
    RecognitionException recognitionException = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).castStmt().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertFalse(((OALParser) recognizer).isTrace());
    assertTrue(((OALParser) recognizer).getParseListeners().isEmpty());
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#castStmt()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#castStmt()}
   */
  @Test
  @DisplayName("Test castStmt(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testCastStmt_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).castStmt().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualConditionAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualConditionAttributeResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setErrorHandler(new BailErrorStrategy());
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualConditionAttributeResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test ConditionAttributeContext {@link ConditionAttributeContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link ConditionAttributeContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test ConditionAttributeContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_CILIUM_SERVICE,
        (new ConditionAttributeContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test ConditionAttributeContext {@link ConditionAttributeContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link ConditionAttributeContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test ConditionAttributeContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeContextIdentifier() {
    // Arrange, Act and Assert
    assertNull((new ConditionAttributeContext(new ParserRuleContext(), 1)).IDENTIFIER());
  }

  /**
   * Test ConditionAttributeContext {@link ConditionAttributeContext#mapAttribute()}.
   * <p>
   * Method under test: {@link ConditionAttributeContext#mapAttribute()}
   */
  @Test
  @DisplayName("Test ConditionAttributeContext mapAttribute()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeContextMapAttribute() {
    // Arrange, Act and Assert
    assertNull((new ConditionAttributeContext(new ParserRuleContext(), 1)).mapAttribute());
  }

  /**
   * Test ConditionAttributeContext {@link ConditionAttributeContext#ConditionAttributeContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link ConditionAttributeContext#ConditionAttributeContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test ConditionAttributeContext new ConditionAttributeContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeContextNewConditionAttributeContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    ConditionAttributeContext actualConditionAttributeContext = new ConditionAttributeContext(parent, 1);

    // Assert
    assertNull(actualConditionAttributeContext.getStart());
    assertNull(actualConditionAttributeContext.getStop());
    assertSame(parent, actualConditionAttributeContext.getParent());
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.setErrorHandler(new BailErrorStrategy());
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ConditionAttributeStmtContext actualConditionAttributeStmtResult = oalParser.conditionAttributeStmt();

    // Assert
    Token start = actualConditionAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([324] Input))", actualConditionAttributeStmtResult.toStringTree());
    assertEquals("Input", actualConditionAttributeStmtResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualConditionAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt2() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ConditionAttributeStmtContext actualConditionAttributeStmtResult = oalParser.conditionAttributeStmt();

    // Assert
    Token start = actualConditionAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([324] Input))", actualConditionAttributeStmtResult.toStringTree());
    assertEquals("Input", actualConditionAttributeStmtResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualConditionAttributeStmtResult.getStop());
  }

  /**
   * Test ConditionAttributeStmtContext {@link ConditionAttributeStmtContext#conditionAttribute()}.
   * <p>
   * Method under test: {@link ConditionAttributeStmtContext#conditionAttribute()}
   */
  @Test
  @DisplayName("Test ConditionAttributeStmtContext conditionAttribute()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmtContextConditionAttribute() {
    // Arrange, Act and Assert
    assertTrue((new ConditionAttributeStmtContext(new ParserRuleContext(), 1)).conditionAttribute().isEmpty());
  }

  /**
   * Test ConditionAttributeStmtContext {@link ConditionAttributeStmtContext#conditionAttribute(int)} with {@code int}.
   * <p>
   * Method under test: {@link ConditionAttributeStmtContext#conditionAttribute(int)}
   */
  @Test
  @DisplayName("Test ConditionAttributeStmtContext conditionAttribute(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmtContextConditionAttributeWithInt() {
    // Arrange, Act and Assert
    assertNull((new ConditionAttributeStmtContext(new ParserRuleContext(), 1)).conditionAttribute(1));
  }

  /**
   * Test ConditionAttributeStmtContext {@link ConditionAttributeStmtContext#DOT()}.
   * <p>
   * Method under test: {@link ConditionAttributeStmtContext#DOT()}
   */
  @Test
  @DisplayName("Test ConditionAttributeStmtContext DOT()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmtContextDot() {
    // Arrange, Act and Assert
    assertTrue((new ConditionAttributeStmtContext(new ParserRuleContext(), 1)).DOT().isEmpty());
  }

  /**
   * Test ConditionAttributeStmtContext {@link ConditionAttributeStmtContext#DOT(int)} with {@code int}.
   * <p>
   * Method under test: {@link ConditionAttributeStmtContext#DOT(int)}
   */
  @Test
  @DisplayName("Test ConditionAttributeStmtContext DOT(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmtContextDotWithInt() {
    // Arrange, Act and Assert
    assertNull((new ConditionAttributeStmtContext(new ParserRuleContext(), 1)).DOT(1));
  }

  /**
   * Test ConditionAttributeStmtContext {@link ConditionAttributeStmtContext#expressionAttrCast()}.
   * <p>
   * Method under test: {@link ConditionAttributeStmtContext#expressionAttrCast()}
   */
  @Test
  @DisplayName("Test ConditionAttributeStmtContext expressionAttrCast()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmtContextExpressionAttrCast() {
    // Arrange, Act and Assert
    assertNull((new ConditionAttributeStmtContext(new ParserRuleContext(), 1)).expressionAttrCast());
  }

  /**
   * Test ConditionAttributeStmtContext {@link ConditionAttributeStmtContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link ConditionAttributeStmtContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test ConditionAttributeStmtContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmtContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_K8S_SERVICE_INSTANCE_RELATION,
        (new ConditionAttributeStmtContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test ConditionAttributeStmtContext {@link ConditionAttributeStmtContext#ConditionAttributeStmtContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link ConditionAttributeStmtContext#ConditionAttributeStmtContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test ConditionAttributeStmtContext new ConditionAttributeStmtContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmtContextNewConditionAttributeStmtContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    ConditionAttributeStmtContext actualConditionAttributeStmtContext = new ConditionAttributeStmtContext(parent, 1);

    // Assert
    assertNull(actualConditionAttributeStmtContext.getStart());
    assertNull(actualConditionAttributeStmtContext.getStop());
    assertSame(parent, actualConditionAttributeStmtContext.getParent());
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} InputStream return {@link BufferedTokenStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt(); then exception InputStream return BufferedTokenStream")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt_thenExceptionInputStreamReturnBufferedTokenStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ConditionAttributeStmtContext actualConditionAttributeStmtResult = oalParser.conditionAttributeStmt();

    // Assert
    RecognitionException recognitionException = actualConditionAttributeStmtResult.exception;
    IntStream inputStream = recognitionException.getInputStream();
    assertTrue(inputStream instanceof BufferedTokenStream);
    Token start = actualConditionAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualConditionAttributeStmtResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("([] 42)", actualConditionAttributeStmtResult.toStringTree());
    assertEquals("42", actualConditionAttributeStmtResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, inputStream);
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualConditionAttributeStmtResult, recognitionException.getCtx());
    assertSame(start, actualConditionAttributeStmtResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} Recognizer return {@link OALParser}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt(); then exception Recognizer return OALParser")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt_thenExceptionRecognizerReturnOALParser() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.conditionAttributeStmt().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(
        new int[]{OALParser.IDENTIFIER, OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT,
            OALParser.STRING_TO_INT, OALParser.STRING_TO_INT_SHORT},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    ConditionAttributeStmtContext actualConditionAttributeStmtResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).conditionAttributeStmt();

    // Assert
    assertEquals("", actualConditionAttributeStmtResult.getText());
    assertEquals("[]", actualConditionAttributeStmtResult.toStringTree());
    assertNull(actualConditionAttributeStmtResult.children);
    assertNull(actualConditionAttributeStmtResult.getStop());
    assertEquals(0, actualConditionAttributeStmtResult.getChildCount());
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([324] ABABABAB))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt(); then return toStringTree is '([] ([324] ABABABAB))'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt_thenReturnToStringTreeIs324Abababab() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("ABABABAB".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    ConditionAttributeStmtContext actualConditionAttributeStmtResult = (new OALParser(
        new BufferedTokenStream(tokenSource))).conditionAttributeStmt();

    // Assert
    Token start = actualConditionAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([324] ABABABAB))", actualConditionAttributeStmtResult.toStringTree());
    assertEquals("ABABABAB", actualConditionAttributeStmtResult.getText());
    assertEquals("ABABABAB", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualConditionAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([324] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt(); then return toStringTree is '([] ([324] Input))'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt_thenReturnToStringTreeIs324Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    ConditionAttributeStmtContext actualConditionAttributeStmtResult = (new OALParser(
        new BufferedTokenStream(tokenSource))).conditionAttributeStmt();

    // Assert
    Token start = actualConditionAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([324] Input))", actualConditionAttributeStmtResult.toStringTree());
    assertEquals("Input", actualConditionAttributeStmtResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualConditionAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#conditionAttributeStmt()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([324] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test conditionAttributeStmt(); then return toStringTree is '([] ([324] Input))'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttributeStmt_thenReturnToStringTreeIs324Input2() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ConditionAttributeStmtContext actualConditionAttributeStmtResult = oalParser.conditionAttributeStmt();

    // Assert
    Token start = actualConditionAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([324] Input))", actualConditionAttributeStmtResult.toStringTree());
    assertEquals("Input", actualConditionAttributeStmtResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualConditionAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Given {@link OALParser#OALParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); given OALParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_givenOALParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualConditionAttributeResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenFourthElementAtnStartStateReturnBasicBlockStartState() throws RecognitionException {
    // Arrange and Act
    ConditionAttributeContext actualConditionAttributeResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualConditionAttributeResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenFourthElementAtnStartStateReturnBasicBlockStartState2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualConditionAttributeResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualConditionAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualConditionAttributeResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("([] 42)", actualConditionAttributeResult.toStringTree());
    assertEquals("42", actualConditionAttributeResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualConditionAttributeResult, recognitionException.getCtx());
    assertSame(start, actualConditionAttributeResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then return Start CharPositionInLine is eight.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then return Start CharPositionInLine is eight")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenReturnStartCharPositionInLineIsEight() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("\"DADADAD".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualConditionAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals(8, start.getCharPositionInLine());
    assertEquals(8, start.getStartIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualConditionAttributeResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([68] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then return toStringTree is '([68] Input)'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenReturnToStringTreeIs68Input() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    ParserRuleContext localctx = new ParserRuleContext();
    oalParser.enterRule(localctx, OALParser.NOT_CONTAIN, 1);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    assertEquals("([68] Input)", actualConditionAttributeResult.toStringTree());
    assertEquals(2, actualConditionAttributeResult.depth());
    assertFalse(actualConditionAttributeResult.isEmpty());
    assertEquals(OALParser.NOT_CONTAIN, actualConditionAttributeResult.invokingState);
    assertSame(localctx, actualConditionAttributeResult.getParent());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ADADADAD)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then return toStringTree is '([] ADADADAD)'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenReturnToStringTreeIsAdadadad() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("ADADADAD".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    ConditionAttributeContext actualConditionAttributeResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ADADADAD)", actualConditionAttributeResult.toStringTree());
    assertEquals("ADADADAD", actualConditionAttributeResult.getText());
    assertEquals("ADADADAD", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualConditionAttributeResult.getStop());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] DDADADAD)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then return toStringTree is '([] DDADADAD)'")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenReturnToStringTreeIsDdadadad() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("DDADADAD".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    ConditionAttributeContext actualConditionAttributeResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] DDADADAD)", actualConditionAttributeResult.toStringTree());
    assertEquals("DDADADAD", actualConditionAttributeResult.getText());
    assertEquals("DDADADAD", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualConditionAttributeResult.getStop());
  }

  /**
   * Test {@link OALParser#conditionAttribute()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#conditionAttribute()}
   */
  @Test
  @DisplayName("Test conditionAttribute(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testConditionAttribute_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    ConditionAttributeContext actualConditionAttributeResult = oalParser.conditionAttribute();

    // Assert
    Token start = actualConditionAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualConditionAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualConditionAttributeResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test ContainMatchContext {@link ContainMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link ContainMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test ContainMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testContainMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new ContainMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test ContainMatchContext {@link ContainMatchContext#CONTAIN()}.
   * <p>
   * Method under test: {@link ContainMatchContext#CONTAIN()}
   */
  @Test
  @DisplayName("Test ContainMatchContext CONTAIN()")
  @Tag("MaintainedByDiffblue")
  void testContainMatchContextContain() {
    // Arrange, Act and Assert
    assertNull((new ContainMatchContext(new ParserRuleContext(), 1)).CONTAIN());
  }

  /**
   * Test ContainMatchContext {@link ContainMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link ContainMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test ContainMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testContainMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_JVM_CLASS,
        (new ContainMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test ContainMatchContext {@link ContainMatchContext#ContainMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link ContainMatchContext#ContainMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test ContainMatchContext new ContainMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testContainMatchContextNewContainMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    ContainMatchContext actualContainMatchContext = new ContainMatchContext(parent, 1);

    // Assert
    assertNull(actualContainMatchContext.getStart());
    assertNull(actualContainMatchContext.getStop());
    assertSame(parent, actualContainMatchContext.getParent());
  }

  /**
   * Test ContainMatchContext {@link ContainMatchContext#stringConditionValue()}.
   * <p>
   * Method under test: {@link ContainMatchContext#stringConditionValue()}
   */
  @Test
  @DisplayName("Test ContainMatchContext stringConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testContainMatchContextStringConditionValue() {
    // Arrange, Act and Assert
    assertNull((new ContainMatchContext(new ParserRuleContext(), 1)).stringConditionValue());
  }

  /**
   * Test {@link OALParser#containMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#containMatch()}
   */
  @Test
  @DisplayName("Test containMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testContainMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.containMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.CONTAIN}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#containMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#containMatch()}
   */
  @Test
  @DisplayName("Test containMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testContainMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.containMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.CONTAIN}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#containMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#containMatch()}
   */
  @Test
  @DisplayName("Test containMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testContainMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    ContainMatchContext actualContainMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).containMatch();

    // Assert
    assertEquals("", actualContainMatchResult.getText());
    assertEquals("([] [233])", actualContainMatchResult.toStringTree());
    assertNull(actualContainMatchResult.getStop());
    assertEquals(1, actualContainMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#containMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([233] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#containMatch()}
   */
  @Test
  @DisplayName("Test containMatch(); then return toStringTree is '([] ([233] 42))'")
  @Tag("MaintainedByDiffblue")
  void testContainMatch_thenReturnToStringTreeIs23342() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ContainMatchContext actualContainMatchResult = oalParser.containMatch();

    // Assert
    Token start = actualContainMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualContainMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([233] 42))", actualContainMatchResult.toStringTree());
    assertEquals("42", actualContainMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualContainMatchResult, recognitionException.getCtx());
    assertSame(start, actualContainMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#containMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([233] ([324 233] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#containMatch()}
   */
  @Test
  @DisplayName("Test containMatch(); then return toStringTree is '([] ([233] ([324 233] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testContainMatch_thenReturnToStringTreeIs233324233Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ContainMatchContext actualContainMatchResult = oalParser.containMatch();

    // Assert
    Token start = actualContainMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualContainMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([233] ([324 233] Input)))", actualContainMatchResult.toStringTree());
    assertEquals("Input", actualContainMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualContainMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#containMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#containMatch()}
   */
  @Test
  @DisplayName("Test containMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testContainMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    ContainMatchContext actualContainMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A&A&A&A&".getBytes("UTF-8"))))))).containMatch();

    // Assert
    assertTrue(actualContainMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([233] ([324 233] A)) A A A)", actualContainMatchResult.toStringTree());
    assertEquals("AAAA", actualContainMatchResult.getText());
    assertEquals(4, actualContainMatchResult.children.size());
    assertEquals(4, actualContainMatchResult.getChildCount());
  }

  /**
   * Test DecorateSourceContext {@link DecorateSourceContext#DECORATOR()}.
   * <p>
   * Method under test: {@link DecorateSourceContext#DECORATOR()}
   */
  @Test
  @DisplayName("Test DecorateSourceContext DECORATOR()")
  @Tag("MaintainedByDiffblue")
  void testDecorateSourceContextDecorator() {
    // Arrange, Act and Assert
    assertNull((new DecorateSourceContext(new ParserRuleContext(), 1)).DECORATOR());
  }

  /**
   * Test DecorateSourceContext {@link DecorateSourceContext#DOT()}.
   * <p>
   * Method under test: {@link DecorateSourceContext#DOT()}
   */
  @Test
  @DisplayName("Test DecorateSourceContext DOT()")
  @Tag("MaintainedByDiffblue")
  void testDecorateSourceContextDot() {
    // Arrange, Act and Assert
    assertNull((new DecorateSourceContext(new ParserRuleContext(), 1)).DOT());
  }

  /**
   * Test DecorateSourceContext {@link DecorateSourceContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link DecorateSourceContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test DecorateSourceContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testDecorateSourceContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(6, (new DecorateSourceContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test DecorateSourceContext {@link DecorateSourceContext#LR_BRACKET()}.
   * <p>
   * Method under test: {@link DecorateSourceContext#LR_BRACKET()}
   */
  @Test
  @DisplayName("Test DecorateSourceContext LR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testDecorateSourceContextLr_bracket() {
    // Arrange, Act and Assert
    assertNull((new DecorateSourceContext(new ParserRuleContext(), 1)).LR_BRACKET());
  }

  /**
   * Test DecorateSourceContext {@link DecorateSourceContext#DecorateSourceContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link DecorateSourceContext#DecorateSourceContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test DecorateSourceContext new DecorateSourceContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testDecorateSourceContextNewDecorateSourceContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    DecorateSourceContext actualDecorateSourceContext = new DecorateSourceContext(parent, 1);

    // Assert
    assertNull(actualDecorateSourceContext.getStart());
    assertNull(actualDecorateSourceContext.getStop());
    assertSame(parent, actualDecorateSourceContext.getParent());
  }

  /**
   * Test DecorateSourceContext {@link DecorateSourceContext#RR_BRACKET()}.
   * <p>
   * Method under test: {@link DecorateSourceContext#RR_BRACKET()}
   */
  @Test
  @DisplayName("Test DecorateSourceContext RR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testDecorateSourceContextRr_bracket() {
    // Arrange, Act and Assert
    assertNull((new DecorateSourceContext(new ParserRuleContext(), 1)).RR_BRACKET());
  }

  /**
   * Test DecorateSourceContext {@link DecorateSourceContext#STRING_LITERAL()}.
   * <p>
   * Method under test: {@link DecorateSourceContext#STRING_LITERAL()}
   */
  @Test
  @DisplayName("Test DecorateSourceContext STRING_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testDecorateSourceContextString_literal() {
    // Arrange, Act and Assert
    assertNull((new DecorateSourceContext(new ParserRuleContext(), 1)).STRING_LITERAL());
  }

  /**
   * Test {@link OALParser#decorateSource()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#decorateSource()}
   */
  @Test
  @DisplayName("Test decorateSource(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testDecorateSource_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange and Act
    DecorateSourceContext actualDecorateSourceResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).decorateSource();

    // Assert
    assertEquals("([] Input)", actualDecorateSourceResult.toStringTree());
    assertEquals("Input", actualDecorateSourceResult.getText());
    assertEquals(1, actualDecorateSourceResult.children.size());
    assertEquals(1, actualDecorateSourceResult.getChildCount());
  }

  /**
   * Test {@link OALParser#decorateSource()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#decorateSource()}
   */
  @Test
  @DisplayName("Test decorateSource(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testDecorateSource_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.decorateSource().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#decorateSource()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#decorateSource()}
   */
  @Test
  @DisplayName("Test decorateSource(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testDecorateSource_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.decorateSource().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#decorateSource()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#decorateSource()}
   */
  @Test
  @DisplayName("Test decorateSource(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testDecorateSource_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    DecorateSourceContext actualDecorateSourceResult = oalParser.decorateSource();

    // Assert
    Token start = actualDecorateSourceResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualDecorateSourceResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualDecorateSourceResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#decorateSource()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#decorateSource()}
   */
  @Test
  @DisplayName("Test decorateSource(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testDecorateSource_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    DecorateSourceContext actualDecorateSourceResult = oalParser.decorateSource();

    // Assert
    Token start = actualDecorateSourceResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualDecorateSourceResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualDecorateSourceResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#decorateSource()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#decorateSource()}
   */
  @Test
  @DisplayName("Test decorateSource(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testDecorateSource_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    DecorateSourceContext actualDecorateSourceResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A\fA\fA\fA\f".getBytes("UTF-8")))))))
            .decorateSource();

    // Assert
    assertTrue(actualDecorateSourceResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualDecorateSourceResult.toStringTree());
    assertEquals("AAAA", actualDecorateSourceResult.getText());
    assertEquals(4, actualDecorateSourceResult.children.size());
    assertEquals(4, actualDecorateSourceResult.getChildCount());
  }

  /**
   * Test DisableSourceContext {@link DisableSourceContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link DisableSourceContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test DisableSourceContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testDisableSourceContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(8, (new DisableSourceContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test DisableSourceContext {@link DisableSourceContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link DisableSourceContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test DisableSourceContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testDisableSourceContextIdentifier() {
    // Arrange, Act and Assert
    assertNull((new DisableSourceContext(new ParserRuleContext(), 1)).IDENTIFIER());
  }

  /**
   * Test DisableSourceContext {@link DisableSourceContext#DisableSourceContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link DisableSourceContext#DisableSourceContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test DisableSourceContext new DisableSourceContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testDisableSourceContextNewDisableSourceContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    DisableSourceContext actualDisableSourceContext = new DisableSourceContext(parent, 1);

    // Assert
    assertNull(actualDisableSourceContext.getStart());
    assertNull(actualDisableSourceContext.getStop());
    assertSame(parent, actualDisableSourceContext.getParent());
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then {@link ParserRuleContext#children} first return {@link TerminalNodeImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); given 'A'; then children first return TerminalNodeImpl")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_givenA_thenChildrenFirstReturnTerminalNodeImpl() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 16, 'A', 16, 'A', 16, 'A', 16}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    DisableSourceContext actualDisableSourceResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .disableSource();

    // Assert
    Token start = actualDisableSourceResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualDisableSourceResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof TerminalNodeImpl);
    assertEquals("([] A)", actualDisableSourceResult.toStringTree());
    assertEquals("A", actualDisableSourceResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualDisableSourceResult.getStop());
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Given {@link OALParser#OALParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); given OALParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_givenOALParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    DisableSourceContext actualDisableSourceResult = oalParser.disableSource();

    // Assert
    Token start = actualDisableSourceResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualDisableSourceResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_thenFourthElementAtnStartStateReturnBasicBlockStartState() throws RecognitionException {
    // Arrange and Act
    DisableSourceContext actualDisableSourceResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).disableSource();

    // Assert
    Token start = actualDisableSourceResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualDisableSourceResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_thenFourthElementAtnStartStateReturnBasicBlockStartState2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    DisableSourceContext actualDisableSourceResult = oalParser.disableSource();

    // Assert
    Token start = actualDisableSourceResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualDisableSourceResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); then return exception Recognizer ParseListeners Empty")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_thenReturnExceptionRecognizerParseListenersEmpty() throws RecognitionException {
    // Arrange, Act and Assert
    RecognitionException recognitionException = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).disableSource().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(((OALParser) recognizer).getParseListeners().isEmpty());
    assertEquals(OALParser.SRC_BROWSER_APP_PAGE_TRAFFIC, recognizer.getATN().ruleToStartState.length);
    assertArrayEquals(new int[]{OALParser.IDENTIFIER}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([16] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); then return toStringTree is '([16] Input)'")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_thenReturnToStringTreeIs16Input() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    ParserRuleContext localctx = new ParserRuleContext();
    oalParser.enterRule(localctx, OALParser.RULE_attributeExpression, 1);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    DisableSourceContext actualDisableSourceResult = oalParser.disableSource();

    // Assert
    assertEquals("([16] Input)", actualDisableSourceResult.toStringTree());
    assertEquals(2, actualDisableSourceResult.depth());
    assertFalse(actualDisableSourceResult.isEmpty());
    assertEquals(OALParser.RULE_attributeExpression, actualDisableSourceResult.invokingState);
    assertSame(localctx, actualDisableSourceResult.getParent());
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    DisableSourceContext actualDisableSourceResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .disableSource();

    // Assert
    Token start = actualDisableSourceResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualDisableSourceResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#disableSource()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableSource()}
   */
  @Test
  @DisplayName("Test disableSource(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testDisableSource_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.disableSource().getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#DelimitedComment()}.
   * <p>
   * Method under test: {@link DisableStatementContext#DelimitedComment()}
   */
  @Test
  @DisplayName("Test DisableStatementContext DelimitedComment()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextDelimitedComment() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).DelimitedComment());
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#DISABLE()}.
   * <p>
   * Method under test: {@link DisableStatementContext#DISABLE()}
   */
  @Test
  @DisplayName("Test DisableStatementContext DISABLE()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextDisable() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).DISABLE());
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#disableSource()}.
   * <p>
   * Method under test: {@link DisableStatementContext#disableSource()}
   */
  @Test
  @DisplayName("Test DisableStatementContext disableSource()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextDisableSource() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).disableSource());
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#EOF()}.
   * <p>
   * Method under test: {@link DisableStatementContext#EOF()}
   */
  @Test
  @DisplayName("Test DisableStatementContext EOF()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextEof() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).EOF());
  }

  /**
   * Test DisableStatementContext getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DisableStatementContext#DisableStatementContext(ParserRuleContext, int)}
   *   <li>{@link DisableStatementContext#getRuleIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test DisableStatementContext getters and setters")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextGettersAndSetters() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    DisableStatementContext actualDisableStatementContext = new DisableStatementContext(parent, 1);
    int actualRuleIndex = actualDisableStatementContext.getRuleIndex();

    // Assert
    assertNull(actualDisableStatementContext.getStart());
    assertNull(actualDisableStatementContext.getStop());
    assertEquals(2, actualRuleIndex);
    assertSame(parent, actualDisableStatementContext.getParent());
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#LineComment()}.
   * <p>
   * Method under test: {@link DisableStatementContext#LineComment()}
   */
  @Test
  @DisplayName("Test DisableStatementContext LineComment()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextLineComment() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).LineComment());
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#LR_BRACKET()}.
   * <p>
   * Method under test: {@link DisableStatementContext#LR_BRACKET()}
   */
  @Test
  @DisplayName("Test DisableStatementContext LR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextLr_bracket() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).LR_BRACKET());
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#RR_BRACKET()}.
   * <p>
   * Method under test: {@link DisableStatementContext#RR_BRACKET()}
   */
  @Test
  @DisplayName("Test DisableStatementContext RR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextRr_bracket() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).RR_BRACKET());
  }

  /**
   * Test DisableStatementContext {@link DisableStatementContext#SEMI()}.
   * <p>
   * Method under test: {@link DisableStatementContext#SEMI()}
   */
  @Test
  @DisplayName("Test DisableStatementContext SEMI()")
  @Tag("MaintainedByDiffblue")
  void testDisableStatementContextSemi() {
    // Arrange, Act and Assert
    assertNull((new DisableStatementContext(new ParserRuleContext(), 1)).SEMI());
  }

  /**
   * Test {@link OALParser#disableStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableStatement()}
   */
  @Test
  @DisplayName("Test disableStatement(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testDisableStatement_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.disableStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{3}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#disableStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableStatement()}
   */
  @Test
  @DisplayName("Test disableStatement(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testDisableStatement_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.disableStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{3}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#disableStatement()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableStatement()}
   */
  @Test
  @DisplayName("Test disableStatement(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testDisableStatement_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange and Act
    DisableStatementContext actualDisableStatementResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).disableStatement();

    // Assert
    assertEquals("([] Input)", actualDisableStatementResult.toStringTree());
    assertEquals("Input", actualDisableStatementResult.getText());
    assertEquals(1, actualDisableStatementResult.children.size());
    assertEquals(1, actualDisableStatementResult.getChildCount());
  }

  /**
   * Test {@link OALParser#disableStatement()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableStatement()}
   */
  @Test
  @DisplayName("Test disableStatement(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testDisableStatement_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    DisableStatementContext actualDisableStatementResult = oalParser.disableStatement();

    // Assert
    Token start = actualDisableStatementResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualDisableStatementResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualDisableStatementResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#disableStatement()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableStatement()}
   */
  @Test
  @DisplayName("Test disableStatement(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testDisableStatement_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    DisableStatementContext actualDisableStatementResult = oalParser.disableStatement();

    // Assert
    Token start = actualDisableStatementResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualDisableStatementResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualDisableStatementResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#disableStatement()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#disableStatement()}
   */
  @Test
  @DisplayName("Test disableStatement(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testDisableStatement_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    DisableStatementContext actualDisableStatementResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 4, 'A', 4, 'A', 4, 'A', 4}))))))
            .disableStatement();

    // Assert
    assertTrue(actualDisableStatementResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualDisableStatementResult.toStringTree());
    assertEquals("AAAA", actualDisableStatementResult.getText());
    assertEquals(4, actualDisableStatementResult.children.size());
    assertEquals(4, actualDisableStatementResult.getChildCount());
  }

  /**
   * Test EnumConditionValueContext {@link EnumConditionValueContext#DOT()}.
   * <p>
   * Method under test: {@link EnumConditionValueContext#DOT()}
   */
  @Test
  @DisplayName("Test EnumConditionValueContext DOT()")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValueContextDot() {
    // Arrange, Act and Assert
    assertNull((new EnumConditionValueContext(new ParserRuleContext(), 1)).DOT());
  }

  /**
   * Test EnumConditionValueContext {@link EnumConditionValueContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link EnumConditionValueContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test EnumConditionValueContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValueContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_CILIUM_SERVICE_INSTANCE_RELATION,
        (new EnumConditionValueContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test EnumConditionValueContext {@link EnumConditionValueContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link EnumConditionValueContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test EnumConditionValueContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValueContextIdentifier() {
    // Arrange, Act and Assert
    assertTrue((new EnumConditionValueContext(new ParserRuleContext(), 1)).IDENTIFIER().isEmpty());
  }

  /**
   * Test EnumConditionValueContext {@link EnumConditionValueContext#IDENTIFIER(int)} with {@code int}.
   * <p>
   * Method under test: {@link EnumConditionValueContext#IDENTIFIER(int)}
   */
  @Test
  @DisplayName("Test EnumConditionValueContext IDENTIFIER(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValueContextIdentifierWithInt() {
    // Arrange, Act and Assert
    assertNull((new EnumConditionValueContext(new ParserRuleContext(), 1)).IDENTIFIER(1));
  }

  /**
   * Test EnumConditionValueContext {@link EnumConditionValueContext#EnumConditionValueContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link EnumConditionValueContext#EnumConditionValueContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test EnumConditionValueContext new EnumConditionValueContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValueContextNewEnumConditionValueContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    EnumConditionValueContext actualEnumConditionValueContext = new EnumConditionValueContext(parent, 1);

    // Assert
    assertNull(actualEnumConditionValueContext.getStart());
    assertNull(actualEnumConditionValueContext.getStop());
    assertSame(parent, actualEnumConditionValueContext.getParent());
  }

  /**
   * Test {@link OALParser#enumConditionValue()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#children} first return {@link ErrorNodeImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#enumConditionValue()}
   */
  @Test
  @DisplayName("Test enumConditionValue(); then children first return ErrorNodeImpl")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValue_thenChildrenFirstReturnErrorNodeImpl() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    EnumConditionValueContext actualEnumConditionValueResult = oalParser.enumConditionValue();

    // Assert
    Token start = actualEnumConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualEnumConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualEnumConditionValueResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("([] 42)", actualEnumConditionValueResult.toStringTree());
    assertEquals("42", actualEnumConditionValueResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualEnumConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualEnumConditionValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#enumConditionValue()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} OffendingToken return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#enumConditionValue()}
   */
  @Test
  @DisplayName("Test enumConditionValue(); then exception OffendingToken return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValue_thenExceptionOffendingTokenReturnCommonToken() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    EnumConditionValueContext actualEnumConditionValueResult = oalParser.enumConditionValue();

    // Assert
    Token start = actualEnumConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualEnumConditionValueResult.exception;
    assertTrue(recognitionException.getOffendingToken() instanceof CommonToken);
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualEnumConditionValueResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof TerminalNodeImpl);
    assertEquals("([] Input)", actualEnumConditionValueResult.toStringTree());
    assertEquals("Input", actualEnumConditionValueResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(346, recognitionException.getOffendingState());
    assertEquals(4, start.getStopIndex());
    assertEquals(OALParser.IDENTIFIER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualEnumConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualEnumConditionValueResult.getStop());
  }

  /**
   * Test {@link OALParser#enumConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#enumConditionValue()}
   */
  @Test
  @DisplayName("Test enumConditionValue(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.enumConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#enumConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#enumConditionValue()}
   */
  @Test
  @DisplayName("Test enumConditionValue(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.enumConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#enumConditionValue()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#enumConditionValue()}
   */
  @Test
  @DisplayName("Test enumConditionValue(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValue_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    EnumConditionValueContext actualEnumConditionValueResult = oalParser.enumConditionValue();

    // Assert
    Token start = actualEnumConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualEnumConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualEnumConditionValueResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#enumConditionValue()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#enumConditionValue()}
   */
  @Test
  @DisplayName("Test enumConditionValue(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testEnumConditionValue_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    EnumConditionValueContext actualEnumConditionValueResult = oalParser.enumConditionValue();

    // Assert
    Token start = actualEnumConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualEnumConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualEnumConditionValueResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#expressionAttrCast()}.
   * <p>
   * Method under test: {@link OALParser#expressionAttrCast()}
   */
  @Test
  @DisplayName("Test expressionAttrCast()")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCast() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.expressionAttrCast().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((CastStmtContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof CastStmtContext);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult2 = parseListeners.get(0);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#expressionAttrCast()}.
   * <p>
   * Method under test: {@link OALParser#expressionAttrCast()}
   */
  @Test
  @DisplayName("Test expressionAttrCast()")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCast2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.expressionAttrCast().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((CastStmtContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof CastStmtContext);
    ParseTreeListener getResult2 = parseListeners.get(1);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test ExpressionAttrCastContext {@link ExpressionAttrCastContext#castStmt()}.
   * <p>
   * Method under test: {@link ExpressionAttrCastContext#castStmt()}
   */
  @Test
  @DisplayName("Test ExpressionAttrCastContext castStmt()")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCastContextCastStmt() {
    // Arrange, Act and Assert
    assertNull((new ExpressionAttrCastContext(new ParserRuleContext(), 1)).castStmt());
  }

  /**
   * Test ExpressionAttrCastContext {@link ExpressionAttrCastContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link ExpressionAttrCastContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test ExpressionAttrCastContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCastContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_BROWSER_APP_PAGE_PERF,
        (new ExpressionAttrCastContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test ExpressionAttrCastContext {@link ExpressionAttrCastContext#ExpressionAttrCastContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link ExpressionAttrCastContext#ExpressionAttrCastContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test ExpressionAttrCastContext new ExpressionAttrCastContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCastContextNewExpressionAttrCastContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    ExpressionAttrCastContext actualExpressionAttrCastContext = new ExpressionAttrCastContext(parent, 1);

    // Assert
    assertNull(actualExpressionAttrCastContext.getStart());
    assertNull(actualExpressionAttrCastContext.getStop());
    assertSame(parent, actualExpressionAttrCastContext.getParent());
  }

  /**
   * Test {@link OALParser#expressionAttrCast()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expressionAttrCast()}
   */
  @Test
  @DisplayName("Test expressionAttrCast(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCast_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    ExpressionAttrCastContext actualExpressionAttrCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .expressionAttrCast();

    // Assert
    Token start = actualExpressionAttrCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("", actualExpressionAttrCastResult.getText());
    assertEquals("([] [355])", actualExpressionAttrCastResult.toStringTree());
    assertEquals("<EOF>", start.getText());
    assertNull(actualExpressionAttrCastResult.getStop());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    Interval sourceInterval = actualExpressionAttrCastResult.getSourceInterval();
    assertEquals(-1, sourceInterval.b);
    assertEquals(0, sourceInterval.length());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#expressionAttrCast()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([355] ATATATAT))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expressionAttrCast()}
   */
  @Test
  @DisplayName("Test expressionAttrCast(); then return toStringTree is '([] ([355] ATATATAT))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCast_thenReturnToStringTreeIs355Atatatat() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("ATATATAT".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    ExpressionAttrCastContext actualExpressionAttrCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .expressionAttrCast();

    // Assert
    Token start = actualExpressionAttrCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([355] ATATATAT))", actualExpressionAttrCastResult.toStringTree());
    assertEquals("ATATATAT", actualExpressionAttrCastResult.getText());
    assertEquals("ATATATAT", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionAttrCastResult.getStop());
  }

  /**
   * Test {@link OALParser#expressionAttrCast()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([355] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expressionAttrCast()}
   */
  @Test
  @DisplayName("Test expressionAttrCast(); then return toStringTree is '([] ([355] Input))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionAttrCast_thenReturnToStringTreeIs355Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    ExpressionAttrCastContext actualExpressionAttrCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .expressionAttrCast();

    // Assert
    Token start = actualExpressionAttrCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([355] Input))", actualExpressionAttrCastResult.toStringTree());
    assertEquals("Input", actualExpressionAttrCastResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionAttrCastResult.getStop());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#booleanMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#booleanMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext booleanMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextBooleanMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).booleanMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#booleanNotEqualMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#booleanNotEqualMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext booleanNotEqualMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextBooleanNotEqualMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).booleanNotEqualMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#containMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#containMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext containMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextContainMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).containMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link ExpressionContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test ExpressionContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_JVM_THREAD,
        (new ExpressionContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#greaterEqualMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#greaterEqualMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext greaterEqualMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextGreaterEqualMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).greaterEqualMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#greaterMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#greaterMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext greaterMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextGreaterMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).greaterMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#inMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#inMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext inMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextInMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).inMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#lessEqualMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#lessEqualMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext lessEqualMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextLessEqualMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).lessEqualMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#lessMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#lessMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext lessMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextLessMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).lessMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#likeMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#likeMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext likeMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextLikeMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).likeMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#ExpressionContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link ExpressionContext#ExpressionContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test ExpressionContext new ExpressionContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextNewExpressionContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    ExpressionContext actualExpressionContext = new ExpressionContext(parent, 1);

    // Assert
    assertNull(actualExpressionContext.getStart());
    assertNull(actualExpressionContext.getStop());
    assertSame(parent, actualExpressionContext.getParent());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#notContainMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#notContainMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext notContainMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextNotContainMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).notContainMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#notEqualMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#notEqualMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext notEqualMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextNotEqualMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).notEqualMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#numberMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#numberMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext numberMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextNumberMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).numberMatch());
  }

  /**
   * Test ExpressionContext {@link ExpressionContext#stringMatch()}.
   * <p>
   * Method under test: {@link ExpressionContext#stringMatch()}
   */
  @Test
  @DisplayName("Test ExpressionContext stringMatch()")
  @Tag("MaintainedByDiffblue")
  void testExpressionContextStringMatch() {
    // Arrange, Act and Assert
    assertNull((new ExpressionContext(new ParserRuleContext(), 1)).stringMatch());
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testExpression_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ExpressionContext actualExpressionResult = oalParser.expression();

    // Assert
    Token start = actualExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualExpressionResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualExpressionResult.toStringTree());
    assertEquals("42", actualExpressionResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualExpressionResult, recognitionException.getCtx());
    assertSame(start, actualExpressionResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return array length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); given ANTLRInputStream(String) with 'Input'; then return array length is two")
  @Tag("MaintainedByDiffblue")
  void testExpression_givenANTLRInputStreamWithInput_thenReturnArrayLengthIsTwo() throws RecognitionException {
    // Arrange, Act and Assert
    RecognitionException recognitionException = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).expression().exception;
    assertTrue(recognitionException instanceof NoViableAltException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    RuleStopState[] ruleStopStateArray = recognizer.getATN().ruleToStopState;
    assertEquals(2, (ruleStopStateArray[OALParser.DECORATOR]).getTransitions().length);
    assertEquals(4, (ruleStopStateArray[OALParser.RULE_enumConditionValue]).getTransitions().length);
    assertEquals(8, (ruleStopStateArray[OALParser.RULE_numberConditionValue]).getTransitions().length);
    assertEquals(OALParser.SRC_BROWSER_APP_PAGE_TRAFFIC, ruleStopStateArray.length);
    assertArrayEquals(
        new int[]{OALParser.IDENTIFIER, OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT,
            OALParser.STRING_TO_INT, OALParser.STRING_TO_INT_SHORT},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} OffendingToken return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then exception OffendingToken return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenExceptionOffendingTokenReturnCommonToken() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A$A$A$A$".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ExpressionContext actualExpressionResult = oalParser.expression();

    // Assert
    Token start = actualExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualExpressionResult.exception;
    assertTrue(recognitionException.getOffendingToken() instanceof CommonToken);
    assertTrue(recognitionException instanceof NoViableAltException);
    assertEquals("([] A$A$A$A$)", actualExpressionResult.toStringTree());
    assertEquals("A$A$A$A$", actualExpressionResult.getText());
    assertEquals("A$A$A$A$", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualExpressionResult, recognitionException.getCtx());
    assertSame(start, ((NoViableAltException) recognitionException).getStartToken());
    assertSame(start, actualExpressionResult.getStop());
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Then return depth is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then return depth is two")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenReturnDepthIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    ParserRuleContext ctx = new ParserRuleContext();
    oalParser.setContext(ctx);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    ExpressionContext actualExpressionResult = oalParser.expression();

    // Assert
    assertEquals(2, actualExpressionResult.depth());
    assertSame(ctx, actualExpressionResult.getParent());
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.expression().exception;
    assertTrue(recognitionException instanceof NoViableAltException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(
        new int[]{OALParser.IDENTIFIER, OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT,
            OALParser.STRING_TO_INT, OALParser.STRING_TO_INT_SHORT},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.expression().exception;
    assertTrue(recognitionException instanceof NoViableAltException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(
        new int[]{OALParser.IDENTIFIER, OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT,
            OALParser.STRING_TO_INT, OALParser.STRING_TO_INT_SHORT},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    ExpressionContext actualExpressionResult = oalParser.expression();

    // Assert
    Token start = actualExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualExpressionResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualExpressionResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#expression()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    ExpressionContext actualExpressionResult = oalParser.expression();

    // Assert
    Token start = actualExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualExpressionResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualExpressionResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test FilterExpressionContext {@link FilterExpressionContext#expression()}.
   * <p>
   * Method under test: {@link FilterExpressionContext#expression()}
   */
  @Test
  @DisplayName("Test FilterExpressionContext expression()")
  @Tag("MaintainedByDiffblue")
  void testFilterExpressionContextExpression() {
    // Arrange, Act and Assert
    assertNull((new FilterExpressionContext(new ParserRuleContext(), 1)).expression());
  }

  /**
   * Test FilterExpressionContext getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FilterExpressionContext#FilterExpressionContext(ParserRuleContext, int)}
   *   <li>{@link FilterExpressionContext#getRuleIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test FilterExpressionContext getters and setters")
  @Tag("MaintainedByDiffblue")
  void testFilterExpressionContextGettersAndSetters() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    FilterExpressionContext actualFilterExpressionContext = new FilterExpressionContext(parent, 1);
    int actualRuleIndex = actualFilterExpressionContext.getRuleIndex();

    // Assert
    assertNull(actualFilterExpressionContext.getStart());
    assertNull(actualFilterExpressionContext.getStop());
    assertEquals(5, actualRuleIndex);
    assertSame(parent, actualFilterExpressionContext.getParent());
  }

  /**
   * Test {@link OALParser#filterExpression()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterExpression()}
   */
  @Test
  @DisplayName("Test filterExpression(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testFilterExpression_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    FilterExpressionContext actualFilterExpressionResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).filterExpression();

    // Assert
    assertTrue(actualFilterExpressionResult.getStart() instanceof CommonToken);
    assertEquals("", actualFilterExpressionResult.getText());
    assertEquals("([] [156])", actualFilterExpressionResult.toStringTree());
    assertNull(actualFilterExpressionResult.getStop());
    assertEquals(1, actualFilterExpressionResult.children.size());
  }

  /**
   * Test {@link OALParser#filterExpression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([156] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterExpression()}
   */
  @Test
  @DisplayName("Test filterExpression(); then return toStringTree is '([] ([156] 42))'")
  @Tag("MaintainedByDiffblue")
  void testFilterExpression_thenReturnToStringTreeIs15642() throws RecognitionException {
    // Arrange and Act
    FilterExpressionContext actualFilterExpressionResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).filterExpression();

    // Assert
    Token start = actualFilterExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([156] 42))", actualFilterExpressionResult.toStringTree());
    assertEquals("42", actualFilterExpressionResult.getText());
    assertEquals(1, actualFilterExpressionResult.children.size());
    assertSame(start, actualFilterExpressionResult.getStop());
  }

  /**
   * Test {@link OALParser#filterExpression()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterExpression()}
   */
  @Test
  @DisplayName("Test filterExpression(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testFilterExpression_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    FilterExpressionContext actualFilterExpressionResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .filterExpression();

    // Assert
    Token start = actualFilterExpressionResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    ExpressionContext expressionResult = actualFilterExpressionResult.expression();
    assertTrue(expressionResult.exception instanceof NoViableAltException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("([156] Input)", expressionResult.toStringTree());
    assertEquals("([] ([156] Input))", actualFilterExpressionResult.toStringTree());
    assertEquals("Input", actualFilterExpressionResult.getText());
    assertEquals("Input", expressionResult.getText());
    assertEquals("Input", start.getText());
    List<ParseTree> parseTreeList = actualFilterExpressionResult.children;
    assertEquals(1, parseTreeList.size());
    assertEquals(1, expressionResult.children.size());
    assertEquals(4, start.getStopIndex());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
    assertSame(actualFilterExpressionResult, expressionResult.getParent());
    assertSame(expressionResult, parseTreeList.get(0));
    assertSame(start, expressionResult.getStart());
    assertSame(start, actualFilterExpressionResult.getStop());
    assertSame(start, expressionResult.getStop());
  }

  /**
   * Test {@link OALParser#filterExpression()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterExpression()}
   */
  @Test
  @DisplayName("Test filterExpression(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testFilterExpression_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    FilterExpressionContext actualFilterExpressionResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A\nA\nA\nA\n".getBytes("UTF-8")))))))
            .filterExpression();

    // Assert
    assertTrue(actualFilterExpressionResult.getStart() instanceof CommonToken);
    assertTrue(actualFilterExpressionResult.getStop() instanceof CommonToken);
    assertEquals("([] ([156] A \\n A \\n A \\n A \\n))", actualFilterExpressionResult.toStringTree());
    assertEquals("A\nA\nA\nA\n", actualFilterExpressionResult.getText());
    assertEquals(1, actualFilterExpressionResult.children.size());
  }

  /**
   * Test FilterStatementContext {@link FilterStatementContext#DOT()}.
   * <p>
   * Method under test: {@link FilterStatementContext#DOT()}
   */
  @Test
  @DisplayName("Test FilterStatementContext DOT()")
  @Tag("MaintainedByDiffblue")
  void testFilterStatementContextDot() {
    // Arrange, Act and Assert
    assertNull((new FilterStatementContext(new ParserRuleContext(), 1)).DOT());
  }

  /**
   * Test FilterStatementContext {@link FilterStatementContext#FILTER()}.
   * <p>
   * Method under test: {@link FilterStatementContext#FILTER()}
   */
  @Test
  @DisplayName("Test FilterStatementContext FILTER()")
  @Tag("MaintainedByDiffblue")
  void testFilterStatementContextFilter() {
    // Arrange, Act and Assert
    assertNull((new FilterStatementContext(new ParserRuleContext(), 1)).FILTER());
  }

  /**
   * Test FilterStatementContext {@link FilterStatementContext#filterExpression()}.
   * <p>
   * Method under test: {@link FilterStatementContext#filterExpression()}
   */
  @Test
  @DisplayName("Test FilterStatementContext filterExpression()")
  @Tag("MaintainedByDiffblue")
  void testFilterStatementContextFilterExpression() {
    // Arrange, Act and Assert
    assertNull((new FilterStatementContext(new ParserRuleContext(), 1)).filterExpression());
  }

  /**
   * Test FilterStatementContext getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FilterStatementContext#FilterStatementContext(ParserRuleContext, int)}
   *   <li>{@link FilterStatementContext#getRuleIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test FilterStatementContext getters and setters")
  @Tag("MaintainedByDiffblue")
  void testFilterStatementContextGettersAndSetters() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    FilterStatementContext actualFilterStatementContext = new FilterStatementContext(parent, 1);
    int actualRuleIndex = actualFilterStatementContext.getRuleIndex();

    // Assert
    assertNull(actualFilterStatementContext.getStart());
    assertNull(actualFilterStatementContext.getStop());
    assertEquals(4, actualRuleIndex);
    assertSame(parent, actualFilterStatementContext.getParent());
  }

  /**
   * Test FilterStatementContext {@link FilterStatementContext#LR_BRACKET()}.
   * <p>
   * Method under test: {@link FilterStatementContext#LR_BRACKET()}
   */
  @Test
  @DisplayName("Test FilterStatementContext LR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testFilterStatementContextLr_bracket() {
    // Arrange, Act and Assert
    assertNull((new FilterStatementContext(new ParserRuleContext(), 1)).LR_BRACKET());
  }

  /**
   * Test FilterStatementContext {@link FilterStatementContext#RR_BRACKET()}.
   * <p>
   * Method under test: {@link FilterStatementContext#RR_BRACKET()}
   */
  @Test
  @DisplayName("Test FilterStatementContext RR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testFilterStatementContextRr_bracket() {
    // Arrange, Act and Assert
    assertNull((new FilterStatementContext(new ParserRuleContext(), 1)).RR_BRACKET());
  }

  /**
   * Test {@link OALParser#filterStatement()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterStatement()}
   */
  @Test
  @DisplayName("Test filterStatement(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testFilterStatement_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange and Act
    FilterStatementContext actualFilterStatementResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).filterStatement();

    // Assert
    assertEquals("([] Input)", actualFilterStatementResult.toStringTree());
    assertEquals("Input", actualFilterStatementResult.getText());
    assertEquals(1, actualFilterStatementResult.children.size());
    assertEquals(1, actualFilterStatementResult.getChildCount());
  }

  /**
   * Test {@link OALParser#filterStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterStatement()}
   */
  @Test
  @DisplayName("Test filterStatement(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testFilterStatement_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.filterStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#filterStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterStatement()}
   */
  @Test
  @DisplayName("Test filterStatement(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testFilterStatement_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.filterStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#filterStatement()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterStatement()}
   */
  @Test
  @DisplayName("Test filterStatement(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testFilterStatement_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    FilterStatementContext actualFilterStatementResult = oalParser.filterStatement();

    // Assert
    Token start = actualFilterStatementResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualFilterStatementResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualFilterStatementResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#filterStatement()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterStatement()}
   */
  @Test
  @DisplayName("Test filterStatement(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testFilterStatement_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    FilterStatementContext actualFilterStatementResult = oalParser.filterStatement();

    // Assert
    Token start = actualFilterStatementResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualFilterStatementResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualFilterStatementResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#filterStatement()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#filterStatement()}
   */
  @Test
  @DisplayName("Test filterStatement(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testFilterStatement_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    FilterStatementContext actualFilterStatementResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A\bA\bA\bA\b".getBytes("UTF-8")))))))
            .filterStatement();

    // Assert
    assertTrue(actualFilterStatementResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualFilterStatementResult.toStringTree());
    assertEquals("AAAA", actualFilterStatementResult.getText());
    assertEquals(4, actualFilterStatementResult.children.size());
    assertEquals(4, actualFilterStatementResult.getChildCount());
  }

  /**
   * Test FuncParamExpressionContext {@link FuncParamExpressionContext#expression()}.
   * <p>
   * Method under test: {@link FuncParamExpressionContext#expression()}
   */
  @Test
  @DisplayName("Test FuncParamExpressionContext expression()")
  @Tag("MaintainedByDiffblue")
  void testFuncParamExpressionContextExpression() {
    // Arrange, Act and Assert
    assertNull((new FuncParamExpressionContext(new ParserRuleContext(), 1)).expression());
  }

  /**
   * Test FuncParamExpressionContext {@link FuncParamExpressionContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link FuncParamExpressionContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test FuncParamExpressionContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testFuncParamExpressionContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_JVM_CPU,
        (new FuncParamExpressionContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test FuncParamExpressionContext {@link FuncParamExpressionContext#FuncParamExpressionContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link FuncParamExpressionContext#FuncParamExpressionContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test FuncParamExpressionContext new FuncParamExpressionContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testFuncParamExpressionContextNewFuncParamExpressionContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    FuncParamExpressionContext actualFuncParamExpressionContext = new FuncParamExpressionContext(parent, 1);

    // Assert
    assertNull(actualFuncParamExpressionContext.getStart());
    assertNull(actualFuncParamExpressionContext.getStop());
    assertSame(parent, actualFuncParamExpressionContext.getParent());
  }

  /**
   * Test {@link OALParser#funcParamExpression()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#funcParamExpression()}
   */
  @Test
  @DisplayName("Test funcParamExpression(); given 'A'; then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testFuncParamExpression_givenA_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    FuncParamExpressionContext actualFuncParamExpressionResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 28, 'A', 28, 'A', 28, 'A', 28}))))))
            .funcParamExpression();

    // Assert
    assertTrue(actualFuncParamExpressionResult.getStart() instanceof CommonToken);
    assertTrue(actualFuncParamExpressionResult.getStop() instanceof CommonToken);
    assertEquals("([] ([199] A A A A))", actualFuncParamExpressionResult.toStringTree());
    assertEquals("AAAA", actualFuncParamExpressionResult.getText());
    assertEquals(1, actualFuncParamExpressionResult.children.size());
  }

  /**
   * Test {@link OALParser#funcParamExpression()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#funcParamExpression()}
   */
  @Test
  @DisplayName("Test funcParamExpression(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testFuncParamExpression_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    FuncParamExpressionContext actualFuncParamExpressionResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).funcParamExpression();

    // Assert
    assertTrue(actualFuncParamExpressionResult.getStart() instanceof CommonToken);
    assertEquals("", actualFuncParamExpressionResult.getText());
    assertEquals("([] [199])", actualFuncParamExpressionResult.toStringTree());
    assertNull(actualFuncParamExpressionResult.getStop());
    assertEquals(1, actualFuncParamExpressionResult.children.size());
  }

  /**
   * Test {@link OALParser#funcParamExpression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([199] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#funcParamExpression()}
   */
  @Test
  @DisplayName("Test funcParamExpression(); then return toStringTree is '([] ([199] 42))'")
  @Tag("MaintainedByDiffblue")
  void testFuncParamExpression_thenReturnToStringTreeIs19942() throws RecognitionException {
    // Arrange and Act
    FuncParamExpressionContext actualFuncParamExpressionResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).funcParamExpression();

    // Assert
    Token start = actualFuncParamExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([199] 42))", actualFuncParamExpressionResult.toStringTree());
    assertEquals("42", actualFuncParamExpressionResult.getText());
    assertEquals(1, actualFuncParamExpressionResult.children.size());
    assertSame(start, actualFuncParamExpressionResult.getStop());
  }

  /**
   * Test {@link OALParser#funcParamExpression()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#funcParamExpression()}
   */
  @Test
  @DisplayName("Test funcParamExpression(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testFuncParamExpression_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    FuncParamExpressionContext actualFuncParamExpressionResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .funcParamExpression();

    // Assert
    Token start = actualFuncParamExpressionResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    ExpressionContext expressionResult = actualFuncParamExpressionResult.expression();
    assertTrue(expressionResult.exception instanceof NoViableAltException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("([199] Input)", expressionResult.toStringTree());
    assertEquals("([] ([199] Input))", actualFuncParamExpressionResult.toStringTree());
    assertEquals("Input", actualFuncParamExpressionResult.getText());
    assertEquals("Input", expressionResult.getText());
    assertEquals("Input", start.getText());
    List<ParseTree> parseTreeList = actualFuncParamExpressionResult.children;
    assertEquals(1, parseTreeList.size());
    assertEquals(1, expressionResult.children.size());
    assertEquals(4, start.getStopIndex());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
    assertSame(actualFuncParamExpressionResult, expressionResult.getParent());
    assertSame(expressionResult, parseTreeList.get(0));
    assertSame(start, expressionResult.getStart());
    assertSame(start, actualFuncParamExpressionResult.getStop());
    assertSame(start, expressionResult.getStop());
  }

  /**
   * Test {@link OALParser#functionArgCast()}.
   * <p>
   * Method under test: {@link OALParser#functionArgCast()}
   */
  @Test
  @DisplayName("Test functionArgCast()")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCast() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.functionArgCast().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((CastStmtContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof CastStmtContext);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult2 = parseListeners.get(0);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#functionArgCast()}.
   * <p>
   * Method under test: {@link OALParser#functionArgCast()}
   */
  @Test
  @DisplayName("Test functionArgCast()")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCast2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.functionArgCast().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((CastStmtContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof CastStmtContext);
    ParseTreeListener getResult2 = parseListeners.get(1);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test FunctionArgCastContext {@link FunctionArgCastContext#castStmt()}.
   * <p>
   * Method under test: {@link FunctionArgCastContext#castStmt()}
   */
  @Test
  @DisplayName("Test FunctionArgCastContext castStmt()")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCastContextCastStmt() {
    // Arrange, Act and Assert
    assertNull((new FunctionArgCastContext(new ParserRuleContext(), 1)).castStmt());
  }

  /**
   * Test FunctionArgCastContext {@link FunctionArgCastContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link FunctionArgCastContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test FunctionArgCastContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCastContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_BROWSER_APP_SINGLE_VERSION_PERF,
        (new FunctionArgCastContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test FunctionArgCastContext {@link FunctionArgCastContext#FunctionArgCastContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link FunctionArgCastContext#FunctionArgCastContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test FunctionArgCastContext new FunctionArgCastContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCastContextNewFunctionArgCastContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    FunctionArgCastContext actualFunctionArgCastContext = new FunctionArgCastContext(parent, 1);

    // Assert
    assertNull(actualFunctionArgCastContext.getStart());
    assertNull(actualFunctionArgCastContext.getStop());
    assertSame(parent, actualFunctionArgCastContext.getParent());
  }

  /**
   * Test {@link OALParser#functionArgCast()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionArgCast()}
   */
  @Test
  @DisplayName("Test functionArgCast(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCast_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    FunctionArgCastContext actualFunctionArgCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .functionArgCast();

    // Assert
    Token start = actualFunctionArgCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("", actualFunctionArgCastResult.getText());
    assertEquals("([] [357])", actualFunctionArgCastResult.toStringTree());
    assertEquals("<EOF>", start.getText());
    assertNull(actualFunctionArgCastResult.getStop());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    Interval sourceInterval = actualFunctionArgCastResult.getSourceInterval();
    assertEquals(-1, sourceInterval.b);
    assertEquals(0, sourceInterval.length());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#functionArgCast()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([357] AVAVAVAV))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionArgCast()}
   */
  @Test
  @DisplayName("Test functionArgCast(); then return toStringTree is '([] ([357] AVAVAVAV))'")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCast_thenReturnToStringTreeIs357Avavavav() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("AVAVAVAV".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    FunctionArgCastContext actualFunctionArgCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .functionArgCast();

    // Assert
    Token start = actualFunctionArgCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([357] AVAVAVAV))", actualFunctionArgCastResult.toStringTree());
    assertEquals("AVAVAVAV", actualFunctionArgCastResult.getText());
    assertEquals("AVAVAVAV", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualFunctionArgCastResult.getStop());
  }

  /**
   * Test {@link OALParser#functionArgCast()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([357] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionArgCast()}
   */
  @Test
  @DisplayName("Test functionArgCast(); then return toStringTree is '([] ([357] Input))'")
  @Tag("MaintainedByDiffblue")
  void testFunctionArgCast_thenReturnToStringTreeIs357Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    FunctionArgCastContext actualFunctionArgCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .functionArgCast();

    // Assert
    Token start = actualFunctionArgCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([357] Input))", actualFunctionArgCastResult.toStringTree());
    assertEquals("Input", actualFunctionArgCastResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualFunctionArgCastResult.getStop());
  }

  /**
   * Test FunctionNameContext {@link FunctionNameContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link FunctionNameContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test FunctionNameContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testFunctionNameContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_ENDPOINT_RELATION, (new FunctionNameContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test FunctionNameContext {@link FunctionNameContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link FunctionNameContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test FunctionNameContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testFunctionNameContextIdentifier() {
    // Arrange, Act and Assert
    assertNull((new FunctionNameContext(new ParserRuleContext(), 1)).IDENTIFIER());
  }

  /**
   * Test FunctionNameContext {@link FunctionNameContext#FunctionNameContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link FunctionNameContext#FunctionNameContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test FunctionNameContext new FunctionNameContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testFunctionNameContextNewFunctionNameContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    FunctionNameContext actualFunctionNameContext = new FunctionNameContext(parent, 1);

    // Assert
    assertNull(actualFunctionNameContext.getStart());
    assertNull(actualFunctionNameContext.getStop());
    assertSame(parent, actualFunctionNameContext.getParent());
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then {@link ParserRuleContext#children} first return {@link TerminalNodeImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); given 'A'; then children first return TerminalNodeImpl")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_givenA_thenChildrenFirstReturnTerminalNodeImpl() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 26, 'A', 26, 'A', 26, 'A', 26}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    FunctionNameContext actualFunctionNameResult = (new OALParser(new BufferedTokenStream(tokenSource))).functionName();

    // Assert
    Token start = actualFunctionNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualFunctionNameResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof TerminalNodeImpl);
    assertEquals("([] A)", actualFunctionNameResult.toStringTree());
    assertEquals("A", actualFunctionNameResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualFunctionNameResult.getStop());
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Given {@link OALParser#OALParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); given OALParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_givenOALParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    FunctionNameContext actualFunctionNameResult = oalParser.functionName();

    // Assert
    Token start = actualFunctionNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualFunctionNameResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_thenFourthElementAtnStartStateReturnBasicBlockStartState() throws RecognitionException {
    // Arrange and Act
    FunctionNameContext actualFunctionNameResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).functionName();

    // Assert
    Token start = actualFunctionNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualFunctionNameResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_thenFourthElementAtnStartStateReturnBasicBlockStartState2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    FunctionNameContext actualFunctionNameResult = oalParser.functionName();

    // Assert
    Token start = actualFunctionNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualFunctionNameResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); then return exception Recognizer ParseListeners Empty")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_thenReturnExceptionRecognizerParseListenersEmpty() throws RecognitionException {
    // Arrange, Act and Assert
    RecognitionException recognitionException = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).functionName().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(((OALParser) recognizer).getParseListeners().isEmpty());
    assertEquals(OALParser.SRC_BROWSER_APP_PAGE_TRAFFIC, recognizer.getATN().ruleToStartState.length);
    assertArrayEquals(new int[]{OALParser.IDENTIFIER}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([26] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); then return toStringTree is '([26] Input)'")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_thenReturnToStringTreeIs26Input() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    ParserRuleContext localctx = new ParserRuleContext();
    oalParser.enterRule(localctx, OALParser.RULE_greaterEqualMatch, 1);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    FunctionNameContext actualFunctionNameResult = oalParser.functionName();

    // Assert
    assertEquals("([26] Input)", actualFunctionNameResult.toStringTree());
    assertEquals(2, actualFunctionNameResult.depth());
    assertFalse(actualFunctionNameResult.isEmpty());
    assertEquals(OALParser.RULE_greaterEqualMatch, actualFunctionNameResult.invokingState);
    assertSame(localctx, actualFunctionNameResult.getParent());
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    FunctionNameContext actualFunctionNameResult = (new OALParser(new BufferedTokenStream(tokenSource))).functionName();

    // Assert
    Token start = actualFunctionNameResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualFunctionNameResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#functionName()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#functionName()}
   */
  @Test
  @DisplayName("Test functionName(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testFunctionName_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.functionName().getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link OALParser#getATN()}
   *   <li>{@link OALParser#getGrammarFileName()}
   *   <li>{@link OALParser#getRuleNames()}
   *   <li>{@link OALParser#getSerializedATN()}
   *   <li>{@link OALParser#getTokenNames()}
   *   <li>{@link OALParser#getVocabulary()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));

    // Act
    ATN actualATN = oalParser.getATN();
    String actualGrammarFileName = oalParser.getGrammarFileName();
    String[] actualRuleNames = oalParser.getRuleNames();
    String actualSerializedATN = oalParser.getSerializedATN();
    String[] actualTokenNames = oalParser.getTokenNames();

    // Assert
    assertTrue(oalParser.getVocabulary() instanceof VocabularyImpl);
    assertEquals("java-escape", actualGrammarFileName);
    assertEquals(OALParser._serializedATN, actualSerializedATN);
    assertSame(oalParser._ATN, actualATN);
    assertSame(oalParser.ruleNames, actualRuleNames);
    assertSame(oalParser.tokenNames, actualTokenNames);
  }

  /**
   * Test GreaterEqualMatchContext {@link GreaterEqualMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link GreaterEqualMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test GreaterEqualMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new GreaterEqualMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test GreaterEqualMatchContext {@link GreaterEqualMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link GreaterEqualMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test GreaterEqualMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_CACHE_ACCESS, (new GreaterEqualMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test GreaterEqualMatchContext {@link GreaterEqualMatchContext#GREATER_EQUAL()}.
   * <p>
   * Method under test: {@link GreaterEqualMatchContext#GREATER_EQUAL()}
   */
  @Test
  @DisplayName("Test GreaterEqualMatchContext GREATER_EQUAL()")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatchContextGreater_equal() {
    // Arrange, Act and Assert
    assertNull((new GreaterEqualMatchContext(new ParserRuleContext(), 1)).GREATER_EQUAL());
  }

  /**
   * Test GreaterEqualMatchContext {@link GreaterEqualMatchContext#GreaterEqualMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link GreaterEqualMatchContext#GreaterEqualMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test GreaterEqualMatchContext new GreaterEqualMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatchContextNewGreaterEqualMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    GreaterEqualMatchContext actualGreaterEqualMatchContext = new GreaterEqualMatchContext(parent, 1);

    // Assert
    assertNull(actualGreaterEqualMatchContext.getStart());
    assertNull(actualGreaterEqualMatchContext.getStop());
    assertSame(parent, actualGreaterEqualMatchContext.getParent());
  }

  /**
   * Test GreaterEqualMatchContext {@link GreaterEqualMatchContext#numberConditionValue()}.
   * <p>
   * Method under test: {@link GreaterEqualMatchContext#numberConditionValue()}
   */
  @Test
  @DisplayName("Test GreaterEqualMatchContext numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatchContextNumberConditionValue() {
    // Arrange, Act and Assert
    assertNull((new GreaterEqualMatchContext(new ParserRuleContext(), 1)).numberConditionValue());
  }

  /**
   * Test {@link OALParser#greaterEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterEqualMatch()}
   */
  @Test
  @DisplayName("Test greaterEqualMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.greaterEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.GREATER_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#greaterEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterEqualMatch()}
   */
  @Test
  @DisplayName("Test greaterEqualMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.greaterEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.GREATER_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#greaterEqualMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterEqualMatch()}
   */
  @Test
  @DisplayName("Test greaterEqualMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    GreaterEqualMatchContext actualGreaterEqualMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).greaterEqualMatch();

    // Assert
    assertEquals("", actualGreaterEqualMatchResult.getText());
    assertEquals("([] [264])", actualGreaterEqualMatchResult.toStringTree());
    assertNull(actualGreaterEqualMatchResult.getStop());
    assertEquals(1, actualGreaterEqualMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#greaterEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([264] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterEqualMatch()}
   */
  @Test
  @DisplayName("Test greaterEqualMatch(); then return toStringTree is '([] ([264] 42))'")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatch_thenReturnToStringTreeIs26442() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    GreaterEqualMatchContext actualGreaterEqualMatchResult = oalParser.greaterEqualMatch();

    // Assert
    Token start = actualGreaterEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualGreaterEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([264] 42))", actualGreaterEqualMatchResult.toStringTree());
    assertEquals("42", actualGreaterEqualMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualGreaterEqualMatchResult, recognitionException.getCtx());
    assertSame(start, actualGreaterEqualMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#greaterEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([264] ([324 264] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterEqualMatch()}
   */
  @Test
  @DisplayName("Test greaterEqualMatch(); then return toStringTree is '([] ([264] ([324 264] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testGreaterEqualMatch_thenReturnToStringTreeIs264324264Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    GreaterEqualMatchContext actualGreaterEqualMatchResult = oalParser.greaterEqualMatch();

    // Assert
    Token start = actualGreaterEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualGreaterEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([264] ([324 264] Input)))", actualGreaterEqualMatchResult.toStringTree());
    assertEquals("Input", actualGreaterEqualMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(329, recognitionException.getOffendingState());
    assertEquals(4, start.getStopIndex());
    assertEquals(OALParser.IDENTIFIER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualGreaterEqualMatchResult.getStop());
  }

  /**
   * Test GreaterMatchContext {@link GreaterMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link GreaterMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test GreaterMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new GreaterMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test GreaterMatchContext {@link GreaterMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link GreaterMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test GreaterMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_ENVOY_INSTANCE_METRIC,
        (new GreaterMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test GreaterMatchContext {@link GreaterMatchContext#GREATER()}.
   * <p>
   * Method under test: {@link GreaterMatchContext#GREATER()}
   */
  @Test
  @DisplayName("Test GreaterMatchContext GREATER()")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatchContextGreater() {
    // Arrange, Act and Assert
    assertNull((new GreaterMatchContext(new ParserRuleContext(), 1)).GREATER());
  }

  /**
   * Test GreaterMatchContext {@link GreaterMatchContext#GreaterMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link GreaterMatchContext#GreaterMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test GreaterMatchContext new GreaterMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatchContextNewGreaterMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    GreaterMatchContext actualGreaterMatchContext = new GreaterMatchContext(parent, 1);

    // Assert
    assertNull(actualGreaterMatchContext.getStart());
    assertNull(actualGreaterMatchContext.getStop());
    assertSame(parent, actualGreaterMatchContext.getParent());
  }

  /**
   * Test GreaterMatchContext {@link GreaterMatchContext#numberConditionValue()}.
   * <p>
   * Method under test: {@link GreaterMatchContext#numberConditionValue()}
   */
  @Test
  @DisplayName("Test GreaterMatchContext numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatchContextNumberConditionValue() {
    // Arrange, Act and Assert
    assertNull((new GreaterMatchContext(new ParserRuleContext(), 1)).numberConditionValue());
  }

  /**
   * Test {@link OALParser#greaterMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterMatch()}
   */
  @Test
  @DisplayName("Test greaterMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.greaterMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.GREATER}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#greaterMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterMatch()}
   */
  @Test
  @DisplayName("Test greaterMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.greaterMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.GREATER}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#greaterMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterMatch()}
   */
  @Test
  @DisplayName("Test greaterMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    GreaterMatchContext actualGreaterMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).greaterMatch();

    // Assert
    assertEquals("", actualGreaterMatchResult.getText());
    assertEquals("([] [256])", actualGreaterMatchResult.toStringTree());
    assertNull(actualGreaterMatchResult.getStop());
    assertEquals(1, actualGreaterMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#greaterMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([256] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterMatch()}
   */
  @Test
  @DisplayName("Test greaterMatch(); then return toStringTree is '([] ([256] 42))'")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatch_thenReturnToStringTreeIs25642() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    GreaterMatchContext actualGreaterMatchResult = oalParser.greaterMatch();

    // Assert
    Token start = actualGreaterMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualGreaterMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([256] 42))", actualGreaterMatchResult.toStringTree());
    assertEquals("42", actualGreaterMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualGreaterMatchResult, recognitionException.getCtx());
    assertSame(start, actualGreaterMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#greaterMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([256] ([324 256] A0A0A0A0)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterMatch()}
   */
  @Test
  @DisplayName("Test greaterMatch(); then return toStringTree is '([] ([256] ([324 256] A0A0A0A0)))'")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatch_thenReturnToStringTreeIs256324256A0a0a0a0() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A0A0A0A0".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    GreaterMatchContext actualGreaterMatchResult = oalParser.greaterMatch();

    // Assert
    Token start = actualGreaterMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualGreaterMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([256] ([324 256] A0A0A0A0)))", actualGreaterMatchResult.toStringTree());
    assertEquals("A0A0A0A0", actualGreaterMatchResult.getText());
    assertEquals("A0A0A0A0", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualGreaterMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#greaterMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([256] ([324 256] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#greaterMatch()}
   */
  @Test
  @DisplayName("Test greaterMatch(); then return toStringTree is '([] ([256] ([324 256] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testGreaterMatch_thenReturnToStringTreeIs256324256Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    GreaterMatchContext actualGreaterMatchResult = oalParser.greaterMatch();

    // Assert
    Token start = actualGreaterMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualGreaterMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([256] ([324 256] Input)))", actualGreaterMatchResult.toStringTree());
    assertEquals("Input", actualGreaterMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualGreaterMatchResult.getStop());
  }

  /**
   * Test InMatchContext {@link InMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link InMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test InMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testInMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new InMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test InMatchContext {@link InMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link InMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test InMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testInMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_K8S_ENDPOINT, (new InMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test InMatchContext {@link InMatchContext#IN()}.
   * <p>
   * Method under test: {@link InMatchContext#IN()}
   */
  @Test
  @DisplayName("Test InMatchContext IN()")
  @Tag("MaintainedByDiffblue")
  void testInMatchContextIn() {
    // Arrange, Act and Assert
    assertNull((new InMatchContext(new ParserRuleContext(), 1)).IN());
  }

  /**
   * Test InMatchContext {@link InMatchContext#multiConditionValue()}.
   * <p>
   * Method under test: {@link InMatchContext#multiConditionValue()}
   */
  @Test
  @DisplayName("Test InMatchContext multiConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testInMatchContextMultiConditionValue() {
    // Arrange, Act and Assert
    assertNull((new InMatchContext(new ParserRuleContext(), 1)).multiConditionValue());
  }

  /**
   * Test InMatchContext {@link InMatchContext#InMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link InMatchContext#InMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test InMatchContext new InMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testInMatchContextNewInMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    InMatchContext actualInMatchContext = new InMatchContext(parent, 1);

    // Assert
    assertNull(actualInMatchContext.getStart());
    assertNull(actualInMatchContext.getStop());
    assertSame(parent, actualInMatchContext.getParent());
  }

  /**
   * Test {@link OALParser#inMatch()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] ([288] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#inMatch()}
   */
  @Test
  @DisplayName("Test inMatch(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] ([288] 42))'")
  @Tag("MaintainedByDiffblue")
  void testInMatch_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs28842() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    InMatchContext actualInMatchResult = oalParser.inMatch();

    // Assert
    Token start = actualInMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualInMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([288] 42))", actualInMatchResult.toStringTree());
    assertEquals("42", actualInMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualInMatchResult, recognitionException.getCtx());
    assertSame(start, actualInMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#inMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#inMatch()}
   */
  @Test
  @DisplayName("Test inMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testInMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.inMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.IN}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#inMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#inMatch()}
   */
  @Test
  @DisplayName("Test inMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testInMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.inMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.IN}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#inMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#inMatch()}
   */
  @Test
  @DisplayName("Test inMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testInMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    InMatchContext actualInMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).inMatch();

    // Assert
    assertEquals("", actualInMatchResult.getText());
    assertEquals("([] [288])", actualInMatchResult.toStringTree());
    assertNull(actualInMatchResult.getStop());
    assertEquals(1, actualInMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#inMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([288] ([324 288] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#inMatch()}
   */
  @Test
  @DisplayName("Test inMatch(); then return toStringTree is '([] ([288] ([324 288] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testInMatch_thenReturnToStringTreeIs288324288Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    InMatchContext actualInMatchResult = oalParser.inMatch();

    // Assert
    Token start = actualInMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualInMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([288] ([324 288] Input)))", actualInMatchResult.toStringTree());
    assertEquals("Input", actualInMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualInMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#inMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#inMatch()}
   */
  @Test
  @DisplayName("Test inMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testInMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    InMatchContext actualInMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A>A>A>A>".getBytes("UTF-8"))))))).inMatch();

    // Assert
    assertTrue(actualInMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([288] ([324 288] A)) > A > A > A >)", actualInMatchResult.toStringTree());
    assertEquals("A>A>A>A>", actualInMatchResult.getText());
    assertEquals(8, actualInMatchResult.children.size());
    assertEquals(8, actualInMatchResult.getChildCount());
  }

  /**
   * Test LessEqualMatchContext {@link LessEqualMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link LessEqualMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test LessEqualMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new LessEqualMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test LessEqualMatchContext {@link LessEqualMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link LessEqualMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test LessEqualMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_MQ_ACCESS, (new LessEqualMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test LessEqualMatchContext {@link LessEqualMatchContext#LESS_EQUAL()}.
   * <p>
   * Method under test: {@link LessEqualMatchContext#LESS_EQUAL()}
   */
  @Test
  @DisplayName("Test LessEqualMatchContext LESS_EQUAL()")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatchContextLess_equal() {
    // Arrange, Act and Assert
    assertNull((new LessEqualMatchContext(new ParserRuleContext(), 1)).LESS_EQUAL());
  }

  /**
   * Test LessEqualMatchContext {@link LessEqualMatchContext#LessEqualMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link LessEqualMatchContext#LessEqualMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test LessEqualMatchContext new LessEqualMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatchContextNewLessEqualMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    LessEqualMatchContext actualLessEqualMatchContext = new LessEqualMatchContext(parent, 1);

    // Assert
    assertNull(actualLessEqualMatchContext.getStart());
    assertNull(actualLessEqualMatchContext.getStop());
    assertSame(parent, actualLessEqualMatchContext.getParent());
  }

  /**
   * Test LessEqualMatchContext {@link LessEqualMatchContext#numberConditionValue()}.
   * <p>
   * Method under test: {@link LessEqualMatchContext#numberConditionValue()}
   */
  @Test
  @DisplayName("Test LessEqualMatchContext numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatchContextNumberConditionValue() {
    // Arrange, Act and Assert
    assertNull((new LessEqualMatchContext(new ParserRuleContext(), 1)).numberConditionValue());
  }

  /**
   * Test {@link OALParser#lessEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessEqualMatch()}
   */
  @Test
  @DisplayName("Test lessEqualMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.lessEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.LESS_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#lessEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessEqualMatch()}
   */
  @Test
  @DisplayName("Test lessEqualMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.lessEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.LESS_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#lessEqualMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessEqualMatch()}
   */
  @Test
  @DisplayName("Test lessEqualMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    LessEqualMatchContext actualLessEqualMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).lessEqualMatch();

    // Assert
    assertEquals("", actualLessEqualMatchResult.getText());
    assertEquals("([] [268])", actualLessEqualMatchResult.toStringTree());
    assertNull(actualLessEqualMatchResult.getStop());
    assertEquals(1, actualLessEqualMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#lessEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([268] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessEqualMatch()}
   */
  @Test
  @DisplayName("Test lessEqualMatch(); then return toStringTree is '([] ([268] 42))'")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatch_thenReturnToStringTreeIs26842() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LessEqualMatchContext actualLessEqualMatchResult = oalParser.lessEqualMatch();

    // Assert
    Token start = actualLessEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLessEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([268] 42))", actualLessEqualMatchResult.toStringTree());
    assertEquals("42", actualLessEqualMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualLessEqualMatchResult, recognitionException.getCtx());
    assertSame(start, actualLessEqualMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#lessEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([268] ([324 268] A6A6A6A6)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessEqualMatch()}
   */
  @Test
  @DisplayName("Test lessEqualMatch(); then return toStringTree is '([] ([268] ([324 268] A6A6A6A6)))'")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatch_thenReturnToStringTreeIs268324268A6a6a6a6() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A6A6A6A6".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LessEqualMatchContext actualLessEqualMatchResult = oalParser.lessEqualMatch();

    // Assert
    Token start = actualLessEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLessEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([268] ([324 268] A6A6A6A6)))", actualLessEqualMatchResult.toStringTree());
    assertEquals("A6A6A6A6", actualLessEqualMatchResult.getText());
    assertEquals("A6A6A6A6", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualLessEqualMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#lessEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([268] ([324 268] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessEqualMatch()}
   */
  @Test
  @DisplayName("Test lessEqualMatch(); then return toStringTree is '([] ([268] ([324 268] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testLessEqualMatch_thenReturnToStringTreeIs268324268Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LessEqualMatchContext actualLessEqualMatchResult = oalParser.lessEqualMatch();

    // Assert
    Token start = actualLessEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLessEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([268] ([324 268] Input)))", actualLessEqualMatchResult.toStringTree());
    assertEquals("Input", actualLessEqualMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualLessEqualMatchResult.getStop());
  }

  /**
   * Test LessMatchContext {@link LessMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link LessMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test LessMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testLessMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new LessMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test LessMatchContext {@link LessMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link LessMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test LessMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testLessMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_EVENT, (new LessMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test LessMatchContext {@link LessMatchContext#LESS()}.
   * <p>
   * Method under test: {@link LessMatchContext#LESS()}
   */
  @Test
  @DisplayName("Test LessMatchContext LESS()")
  @Tag("MaintainedByDiffblue")
  void testLessMatchContextLess() {
    // Arrange, Act and Assert
    assertNull((new LessMatchContext(new ParserRuleContext(), 1)).LESS());
  }

  /**
   * Test LessMatchContext {@link LessMatchContext#LessMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link LessMatchContext#LessMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test LessMatchContext new LessMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testLessMatchContextNewLessMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    LessMatchContext actualLessMatchContext = new LessMatchContext(parent, 1);

    // Assert
    assertNull(actualLessMatchContext.getStart());
    assertNull(actualLessMatchContext.getStop());
    assertSame(parent, actualLessMatchContext.getParent());
  }

  /**
   * Test LessMatchContext {@link LessMatchContext#numberConditionValue()}.
   * <p>
   * Method under test: {@link LessMatchContext#numberConditionValue()}
   */
  @Test
  @DisplayName("Test LessMatchContext numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testLessMatchContextNumberConditionValue() {
    // Arrange, Act and Assert
    assertNull((new LessMatchContext(new ParserRuleContext(), 1)).numberConditionValue());
  }

  /**
   * Test {@link OALParser#lessMatch()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] ([260] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessMatch()}
   */
  @Test
  @DisplayName("Test lessMatch(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] ([260] 42))'")
  @Tag("MaintainedByDiffblue")
  void testLessMatch_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs26042() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LessMatchContext actualLessMatchResult = oalParser.lessMatch();

    // Assert
    Token start = actualLessMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLessMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([260] 42))", actualLessMatchResult.toStringTree());
    assertEquals("42", actualLessMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualLessMatchResult, recognitionException.getCtx());
    assertSame(start, actualLessMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#lessMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessMatch()}
   */
  @Test
  @DisplayName("Test lessMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testLessMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.lessMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.LESS}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#lessMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessMatch()}
   */
  @Test
  @DisplayName("Test lessMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testLessMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.lessMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.LESS}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#lessMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessMatch()}
   */
  @Test
  @DisplayName("Test lessMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testLessMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    LessMatchContext actualLessMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).lessMatch();

    // Assert
    assertEquals("", actualLessMatchResult.getText());
    assertEquals("([] [260])", actualLessMatchResult.toStringTree());
    assertNull(actualLessMatchResult.getStop());
    assertEquals(1, actualLessMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#lessMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([260] ([324 260] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#lessMatch()}
   */
  @Test
  @DisplayName("Test lessMatch(); then return toStringTree is '([] ([260] ([324 260] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testLessMatch_thenReturnToStringTreeIs260324260Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LessMatchContext actualLessMatchResult = oalParser.lessMatch();

    // Assert
    Token start = actualLessMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLessMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([260] ([324 260] Input)))", actualLessMatchResult.toStringTree());
    assertEquals("Input", actualLessMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(329, recognitionException.getOffendingState());
    assertEquals(4, start.getStopIndex());
    assertEquals(OALParser.IDENTIFIER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualLessMatchResult.getStop());
  }

  /**
   * Test LikeMatchContext {@link LikeMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link LikeMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test LikeMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testLikeMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new LikeMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test LikeMatchContext {@link LikeMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link LikeMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test LikeMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testLikeMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_K8S_SERVICE_INSTANCE, (new LikeMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test LikeMatchContext {@link LikeMatchContext#LIKE()}.
   * <p>
   * Method under test: {@link LikeMatchContext#LIKE()}
   */
  @Test
  @DisplayName("Test LikeMatchContext LIKE()")
  @Tag("MaintainedByDiffblue")
  void testLikeMatchContextLike() {
    // Arrange, Act and Assert
    assertNull((new LikeMatchContext(new ParserRuleContext(), 1)).LIKE());
  }

  /**
   * Test LikeMatchContext {@link LikeMatchContext#LikeMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link LikeMatchContext#LikeMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test LikeMatchContext new LikeMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testLikeMatchContextNewLikeMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    LikeMatchContext actualLikeMatchContext = new LikeMatchContext(parent, 1);

    // Assert
    assertNull(actualLikeMatchContext.getStart());
    assertNull(actualLikeMatchContext.getStop());
    assertSame(parent, actualLikeMatchContext.getParent());
  }

  /**
   * Test LikeMatchContext {@link LikeMatchContext#stringConditionValue()}.
   * <p>
   * Method under test: {@link LikeMatchContext#stringConditionValue()}
   */
  @Test
  @DisplayName("Test LikeMatchContext stringConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testLikeMatchContextStringConditionValue() {
    // Arrange, Act and Assert
    assertNull((new LikeMatchContext(new ParserRuleContext(), 1)).stringConditionValue());
  }

  /**
   * Test {@link OALParser#likeMatch()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] ([284] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#likeMatch()}
   */
  @Test
  @DisplayName("Test likeMatch(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] ([284] 42))'")
  @Tag("MaintainedByDiffblue")
  void testLikeMatch_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs28442() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LikeMatchContext actualLikeMatchResult = oalParser.likeMatch();

    // Assert
    Token start = actualLikeMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLikeMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([284] 42))", actualLikeMatchResult.toStringTree());
    assertEquals("42", actualLikeMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualLikeMatchResult, recognitionException.getCtx());
    assertSame(start, actualLikeMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#likeMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#likeMatch()}
   */
  @Test
  @DisplayName("Test likeMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testLikeMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.likeMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.LIKE}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#likeMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#likeMatch()}
   */
  @Test
  @DisplayName("Test likeMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testLikeMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.likeMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.LIKE}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#likeMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#likeMatch()}
   */
  @Test
  @DisplayName("Test likeMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testLikeMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    LikeMatchContext actualLikeMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).likeMatch();

    // Assert
    assertEquals("", actualLikeMatchResult.getText());
    assertEquals("([] [284])", actualLikeMatchResult.toStringTree());
    assertNull(actualLikeMatchResult.getStop());
    assertEquals(1, actualLikeMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#likeMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([284] ([324 284] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#likeMatch()}
   */
  @Test
  @DisplayName("Test likeMatch(); then return toStringTree is '([] ([284] ([324 284] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testLikeMatch_thenReturnToStringTreeIs284324284Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LikeMatchContext actualLikeMatchResult = oalParser.likeMatch();

    // Assert
    Token start = actualLikeMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLikeMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([284] ([324 284] Input)))", actualLikeMatchResult.toStringTree());
    assertEquals("Input", actualLikeMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualLikeMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#likeMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#likeMatch()}
   */
  @Test
  @DisplayName("Test likeMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testLikeMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    LikeMatchContext actualLikeMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A<A<A<A<".getBytes("UTF-8"))))))).likeMatch();

    // Assert
    assertTrue(actualLikeMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([284] ([324 284] A)) < A < A < A <)", actualLikeMatchResult.toStringTree());
    assertEquals("A<A<A<A<", actualLikeMatchResult.getText());
    assertEquals(8, actualLikeMatchResult.children.size());
    assertEquals(8, actualLikeMatchResult.getChildCount());
  }

  /**
   * Test LiteralExpressionContext {@link LiteralExpressionContext#BOOL_LITERAL()}.
   * <p>
   * Method under test: {@link LiteralExpressionContext#BOOL_LITERAL()}
   */
  @Test
  @DisplayName("Test LiteralExpressionContext BOOL_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpressionContextBool_literal() {
    // Arrange, Act and Assert
    assertNull((new LiteralExpressionContext(new ParserRuleContext(), 1)).BOOL_LITERAL());
  }

  /**
   * Test LiteralExpressionContext {@link LiteralExpressionContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link LiteralExpressionContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test LiteralExpressionContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpressionContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_JVM_MEMORY,
        (new LiteralExpressionContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test LiteralExpressionContext {@link LiteralExpressionContext#LiteralExpressionContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link LiteralExpressionContext#LiteralExpressionContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test LiteralExpressionContext new LiteralExpressionContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpressionContextNewLiteralExpressionContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    LiteralExpressionContext actualLiteralExpressionContext = new LiteralExpressionContext(parent, 1);

    // Assert
    assertNull(actualLiteralExpressionContext.getStart());
    assertNull(actualLiteralExpressionContext.getStop());
    assertSame(parent, actualLiteralExpressionContext.getParent());
  }

  /**
   * Test LiteralExpressionContext {@link LiteralExpressionContext#NUMBER_LITERAL()}.
   * <p>
   * Method under test: {@link LiteralExpressionContext#NUMBER_LITERAL()}
   */
  @Test
  @DisplayName("Test LiteralExpressionContext NUMBER_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpressionContextNumber_literal() {
    // Arrange, Act and Assert
    assertNull((new LiteralExpressionContext(new ParserRuleContext(), 1)).NUMBER_LITERAL());
  }

  /**
   * Test LiteralExpressionContext {@link LiteralExpressionContext#STRING_LITERAL()}.
   * <p>
   * Method under test: {@link LiteralExpressionContext#STRING_LITERAL()}
   */
  @Test
  @DisplayName("Test LiteralExpressionContext STRING_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpressionContextString_literal() {
    // Arrange, Act and Assert
    assertNull((new LiteralExpressionContext(new ParserRuleContext(), 1)).STRING_LITERAL());
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} InputStream return {@link BufferedTokenStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then exception InputStream return BufferedTokenStream")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenExceptionInputStreamReturnBufferedTokenStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    LiteralExpressionContext actualLiteralExpressionResult = oalParser.literalExpression();

    // Assert
    RecognitionException recognitionException = actualLiteralExpressionResult.exception;
    IntStream inputStream = recognitionException.getInputStream();
    assertTrue(inputStream instanceof BufferedTokenStream);
    Token start = actualLiteralExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualLiteralExpressionResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("([] Input)", actualLiteralExpressionResult.toStringTree());
    assertEquals("Input", actualLiteralExpressionResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, inputStream);
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualLiteralExpressionResult, recognitionException.getCtx());
    assertSame(start, actualLiteralExpressionResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenFourthElementAtnStartStateReturnBasicBlockStartState() throws RecognitionException {
    // Arrange and Act
    LiteralExpressionContext actualLiteralExpressionResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).literalExpression();

    // Assert
    Token start = actualLiteralExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] 42)", actualLiteralExpressionResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.literalExpression().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.BOOL_LITERAL, OALParser.NUMBER_LITERAL, OALParser.STRING_LITERAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.literalExpression().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.BOOL_LITERAL, OALParser.NUMBER_LITERAL, OALParser.STRING_LITERAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).literalExpression().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenReturnStartTextIsEof2() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.literalExpression().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([30] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then return toStringTree is '([30] 42)'")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenReturnToStringTreeIs3042() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))));
    ParserRuleContext localctx = new ParserRuleContext();
    oalParser.enterRule(localctx, OALParser.RULE_likeMatch, 1);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    LiteralExpressionContext actualLiteralExpressionResult = oalParser.literalExpression();

    // Assert
    assertEquals("([30] 42)", actualLiteralExpressionResult.toStringTree());
    assertEquals(2, actualLiteralExpressionResult.depth());
    assertFalse(actualLiteralExpressionResult.isEmpty());
    assertEquals(OALParser.RULE_likeMatch, actualLiteralExpressionResult.invokingState);
    assertSame(localctx, actualLiteralExpressionResult.getParent());
  }

  /**
   * Test {@link OALParser#literalExpression()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#literalExpression()}
   */
  @Test
  @DisplayName("Test literalExpression(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testLiteralExpression_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    LiteralExpressionContext actualLiteralExpressionResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 30, 'A', 30, 'A', 30, 'A', 30}))))))
            .literalExpression();

    // Assert
    assertTrue(actualLiteralExpressionResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualLiteralExpressionResult.toStringTree());
    assertEquals("AAAA", actualLiteralExpressionResult.getText());
    assertEquals(4, actualLiteralExpressionResult.children.size());
    assertEquals(4, actualLiteralExpressionResult.getChildCount());
  }

  /**
   * Test MapAttributeContext {@link MapAttributeContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link MapAttributeContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test MapAttributeContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testMapAttributeContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_CILIUM_SERVICE_INSTANCE,
        (new MapAttributeContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test MapAttributeContext {@link MapAttributeContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link MapAttributeContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test MapAttributeContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testMapAttributeContextIdentifier() {
    // Arrange, Act and Assert
    assertNull((new MapAttributeContext(new ParserRuleContext(), 1)).IDENTIFIER());
  }

  /**
   * Test MapAttributeContext {@link MapAttributeContext#LS_BRACKET()}.
   * <p>
   * Method under test: {@link MapAttributeContext#LS_BRACKET()}
   */
  @Test
  @DisplayName("Test MapAttributeContext LS_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testMapAttributeContextLs_bracket() {
    // Arrange, Act and Assert
    assertNull((new MapAttributeContext(new ParserRuleContext(), 1)).LS_BRACKET());
  }

  /**
   * Test MapAttributeContext {@link MapAttributeContext#MapAttributeContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link MapAttributeContext#MapAttributeContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test MapAttributeContext new MapAttributeContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testMapAttributeContextNewMapAttributeContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    MapAttributeContext actualMapAttributeContext = new MapAttributeContext(parent, 1);

    // Assert
    assertNull(actualMapAttributeContext.getStart());
    assertNull(actualMapAttributeContext.getStop());
    assertSame(parent, actualMapAttributeContext.getParent());
  }

  /**
   * Test MapAttributeContext {@link MapAttributeContext#RS_BRACKET()}.
   * <p>
   * Method under test: {@link MapAttributeContext#RS_BRACKET()}
   */
  @Test
  @DisplayName("Test MapAttributeContext RS_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testMapAttributeContextRs_bracket() {
    // Arrange, Act and Assert
    assertNull((new MapAttributeContext(new ParserRuleContext(), 1)).RS_BRACKET());
  }

  /**
   * Test MapAttributeContext {@link MapAttributeContext#STRING_LITERAL()}.
   * <p>
   * Method under test: {@link MapAttributeContext#STRING_LITERAL()}
   */
  @Test
  @DisplayName("Test MapAttributeContext STRING_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testMapAttributeContextString_literal() {
    // Arrange, Act and Assert
    assertNull((new MapAttributeContext(new ParserRuleContext(), 1)).STRING_LITERAL());
  }

  /**
   * Test {@link OALParser#mapAttribute()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#mapAttribute()}
   */
  @Test
  @DisplayName("Test mapAttribute(); given ANTLRInputStream(String) with input is '42'; then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testMapAttribute_givenANTLRInputStreamWithInputIs42_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    MapAttributeContext actualMapAttributeResult = oalParser.mapAttribute();

    // Assert
    Token start = actualMapAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMapAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualMapAttributeResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("([] 42)", actualMapAttributeResult.toStringTree());
    assertEquals("42", actualMapAttributeResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMapAttributeResult, recognitionException.getCtx());
    assertSame(start, actualMapAttributeResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#mapAttribute()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#mapAttribute()}
   */
  @Test
  @DisplayName("Test mapAttribute(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testMapAttribute_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    MapAttributeContext actualMapAttributeResult = oalParser.mapAttribute();

    // Assert
    Token start = actualMapAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMapAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualMapAttributeResult.toStringTree());
    assertEquals("Input", actualMapAttributeResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMapAttributeResult, recognitionException.getCtx());
    assertSame(start, actualMapAttributeResult.getStop());
  }

  /**
   * Test {@link OALParser#mapAttribute()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#mapAttribute()}
   */
  @Test
  @DisplayName("Test mapAttribute(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testMapAttribute_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.mapAttribute().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.LS_BRACKET}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#mapAttribute()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#mapAttribute()}
   */
  @Test
  @DisplayName("Test mapAttribute(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testMapAttribute_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.mapAttribute().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.LS_BRACKET}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#mapAttribute()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#mapAttribute()}
   */
  @Test
  @DisplayName("Test mapAttribute(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testMapAttribute_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    MapAttributeContext actualMapAttributeResult = oalParser.mapAttribute();

    // Assert
    Token start = actualMapAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMapAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMapAttributeResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#mapAttribute()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] AFAFAFAF)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#mapAttribute()}
   */
  @Test
  @DisplayName("Test mapAttribute(); then return toStringTree is '([] AFAFAFAF)'")
  @Tag("MaintainedByDiffblue")
  void testMapAttribute_thenReturnToStringTreeIsAfafafaf() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("AFAFAFAF".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    MapAttributeContext actualMapAttributeResult = oalParser.mapAttribute();

    // Assert
    Token start = actualMapAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMapAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] AFAFAFAF)", actualMapAttributeResult.toStringTree());
    assertEquals("AFAFAFAF", actualMapAttributeResult.getText());
    assertEquals("AFAFAFAF", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMapAttributeResult, recognitionException.getCtx());
    assertSame(start, actualMapAttributeResult.getStop());
  }

  /**
   * Test {@link OALParser#mapAttribute()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#mapAttribute()}
   */
  @Test
  @DisplayName("Test mapAttribute(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testMapAttribute_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    MapAttributeContext actualMapAttributeResult = oalParser.mapAttribute();

    // Assert
    Token start = actualMapAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMapAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMapAttributeResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#aggregateFunction()}.
   * <p>
   * Method under test: {@link MetricStatementContext#aggregateFunction()}
   */
  @Test
  @DisplayName("Test MetricStatementContext aggregateFunction()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextAggregateFunction() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).aggregateFunction());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#decorateSource()}.
   * <p>
   * Method under test: {@link MetricStatementContext#decorateSource()}
   */
  @Test
  @DisplayName("Test MetricStatementContext decorateSource()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextDecorateSource() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).decorateSource());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#DOT()}.
   * <p>
   * Method under test: {@link MetricStatementContext#DOT()}
   */
  @Test
  @DisplayName("Test MetricStatementContext DOT()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextDot() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).DOT());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#filterStatement()}.
   * <p>
   * Method under test: {@link MetricStatementContext#filterStatement()}
   */
  @Test
  @DisplayName("Test MetricStatementContext filterStatement()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextFilterStatement() {
    // Arrange, Act and Assert
    assertTrue((new MetricStatementContext(new ParserRuleContext(), 1)).filterStatement().isEmpty());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#filterStatement(int)} with {@code int}.
   * <p>
   * Method under test: {@link MetricStatementContext#filterStatement(int)}
   */
  @Test
  @DisplayName("Test MetricStatementContext filterStatement(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextFilterStatementWithInt() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).filterStatement(1));
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#FROM()}.
   * <p>
   * Method under test: {@link MetricStatementContext#FROM()}
   */
  @Test
  @DisplayName("Test MetricStatementContext FROM()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextFrom() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).FROM());
  }

  /**
   * Test MetricStatementContext getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MetricStatementContext#MetricStatementContext(ParserRuleContext, int)}
   *   <li>{@link MetricStatementContext#getRuleIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test MetricStatementContext getters and setters")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextGettersAndSetters() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    MetricStatementContext actualMetricStatementContext = new MetricStatementContext(parent, 1);
    int actualRuleIndex = actualMetricStatementContext.getRuleIndex();

    // Assert
    assertNull(actualMetricStatementContext.getStart());
    assertNull(actualMetricStatementContext.getStop());
    assertEquals(3, actualRuleIndex);
    assertSame(parent, actualMetricStatementContext.getParent());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#LR_BRACKET()}.
   * <p>
   * Method under test: {@link MetricStatementContext#LR_BRACKET()}
   */
  @Test
  @DisplayName("Test MetricStatementContext LR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextLr_bracket() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).LR_BRACKET());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#RR_BRACKET()}.
   * <p>
   * Method under test: {@link MetricStatementContext#RR_BRACKET()}
   */
  @Test
  @DisplayName("Test MetricStatementContext RR_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextRr_bracket() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).RR_BRACKET());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#source()}.
   * <p>
   * Method under test: {@link MetricStatementContext#source()}
   */
  @Test
  @DisplayName("Test MetricStatementContext source()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextSource() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).source());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#sourceAttrCast()}.
   * <p>
   * Method under test: {@link MetricStatementContext#sourceAttrCast()}
   */
  @Test
  @DisplayName("Test MetricStatementContext sourceAttrCast()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextSourceAttrCast() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).sourceAttrCast());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#sourceAttributeStmt()}.
   * <p>
   * Method under test: {@link MetricStatementContext#sourceAttributeStmt()}
   */
  @Test
  @DisplayName("Test MetricStatementContext sourceAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextSourceAttributeStmt() {
    // Arrange, Act and Assert
    assertTrue((new MetricStatementContext(new ParserRuleContext(), 1)).sourceAttributeStmt().isEmpty());
  }

  /**
   * Test MetricStatementContext {@link MetricStatementContext#sourceAttributeStmt(int)} with {@code int}.
   * <p>
   * Method under test: {@link MetricStatementContext#sourceAttributeStmt(int)}
   */
  @Test
  @DisplayName("Test MetricStatementContext sourceAttributeStmt(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testMetricStatementContextSourceAttributeStmtWithInt() {
    // Arrange, Act and Assert
    assertNull((new MetricStatementContext(new ParserRuleContext(), 1)).sourceAttributeStmt(1));
  }

  /**
   * Test {@link OALParser#metricStatement()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#children} first return {@link ErrorNodeImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#metricStatement()}
   */
  @Test
  @DisplayName("Test metricStatement(); then children first return ErrorNodeImpl")
  @Tag("MaintainedByDiffblue")
  void testMetricStatement_thenChildrenFirstReturnErrorNodeImpl() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    MetricStatementContext actualMetricStatementResult = oalParser.metricStatement();

    // Assert
    Token start = actualMetricStatementResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMetricStatementResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualMetricStatementResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMetricStatementResult, recognitionException.getCtx());
    assertSame(start, actualMetricStatementResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#metricStatement()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} OffendingToken return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#metricStatement()}
   */
  @Test
  @DisplayName("Test metricStatement(); then exception OffendingToken return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testMetricStatement_thenExceptionOffendingTokenReturnCommonToken() throws RecognitionException {
    // Arrange
    BufferedTokenStream input = new BufferedTokenStream(new XPathLexer(new ANTLRInputStream("Input")));
    OALParser oalParser = new OALParser(input);

    // Act
    MetricStatementContext actualMetricStatementResult = oalParser.metricStatement();

    // Assert
    Token start = actualMetricStatementResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMetricStatementResult.exception;
    assertTrue(recognitionException.getOffendingToken() instanceof CommonToken);
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualMetricStatementResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof TerminalNodeImpl);
    assertNull(start.getTokenSource());
    assertEquals(1, start.getType());
    assertEquals(Float.MAX_EXPONENT, recognitionException.getOffendingState());
    assertSame(input, recognitionException.getInputStream());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMetricStatementResult, recognitionException.getCtx());
    assertSame(start, actualMetricStatementResult.getStop());
  }

  /**
   * Test {@link OALParser#metricStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#metricStatement()}
   */
  @Test
  @DisplayName("Test metricStatement(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testMetricStatement_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.metricStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{1}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#metricStatement()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#metricStatement()}
   */
  @Test
  @DisplayName("Test metricStatement(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testMetricStatement_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.metricStatement().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{1}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#metricStatement()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#metricStatement()}
   */
  @Test
  @DisplayName("Test metricStatement(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testMetricStatement_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    MetricStatementContext actualMetricStatementResult = oalParser.metricStatement();

    // Assert
    Token start = actualMetricStatementResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMetricStatementResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMetricStatementResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#metricStatement()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#metricStatement()}
   */
  @Test
  @DisplayName("Test metricStatement(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testMetricStatement_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input);

    // Act
    MetricStatementContext actualMetricStatementResult = oalParser.metricStatement();

    // Assert
    Token start = actualMetricStatementResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMetricStatementResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertEquals(-1, start.getStartIndex());
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualMetricStatementResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#metricStatement()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#metricStatement()}
   */
  @Test
  @DisplayName("Test metricStatement(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testMetricStatement_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    MetricStatementContext actualMetricStatementResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 6, 'A', 6, 'A', 6, 'A', 6}))))))
            .metricStatement();

    // Assert
    assertTrue(actualMetricStatementResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualMetricStatementResult.toStringTree());
    assertEquals("AAAA", actualMetricStatementResult.getText());
    assertEquals(4, actualMetricStatementResult.children.size());
    assertEquals(4, actualMetricStatementResult.getChildCount());
  }

  /**
   * Test {@link OALParser#multiConditionValue()}.
   * <p>
   * Method under test: {@link OALParser#multiConditionValue()}
   */
  @Test
  @DisplayName("Test multiConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValue() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.multiConditionValue().children;
    assertEquals(3, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(1);
    RecognitionException recognitionException = ((EnumConditionValueContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof EnumConditionValueContext);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult2 = parseListeners.get(0);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#multiConditionValue()}.
   * <p>
   * Method under test: {@link OALParser#multiConditionValue()}
   */
  @Test
  @DisplayName("Test multiConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValue2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.multiConditionValue().children;
    assertEquals(3, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(1);
    RecognitionException recognitionException = ((EnumConditionValueContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof EnumConditionValueContext);
    ParseTreeListener getResult2 = parseListeners.get(1);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#COMMA()}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#COMMA()}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext COMMA()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextComma() {
    // Arrange, Act and Assert
    assertTrue((new MultiConditionValueContext(new ParserRuleContext(), 1)).COMMA().isEmpty());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#COMMA(int)} with {@code int}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#COMMA(int)}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext COMMA(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextCommaWithInt() {
    // Arrange, Act and Assert
    assertNull((new MultiConditionValueContext(new ParserRuleContext(), 1)).COMMA(1));
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#enumConditionValue()}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#enumConditionValue()}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext enumConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextEnumConditionValue() {
    // Arrange, Act and Assert
    assertTrue((new MultiConditionValueContext(new ParserRuleContext(), 1)).enumConditionValue().isEmpty());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#enumConditionValue(int)} with {@code int}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#enumConditionValue(int)}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext enumConditionValue(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextEnumConditionValueWithInt() {
    // Arrange, Act and Assert
    assertNull((new MultiConditionValueContext(new ParserRuleContext(), 1)).enumConditionValue(1));
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_K8S_SERVICE_RELATION,
        (new MultiConditionValueContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#LS_BRACKET()}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#LS_BRACKET()}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext LS_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextLs_bracket() {
    // Arrange, Act and Assert
    assertNull((new MultiConditionValueContext(new ParserRuleContext(), 1)).LS_BRACKET());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#MultiConditionValueContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#MultiConditionValueContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext new MultiConditionValueContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextNewMultiConditionValueContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    MultiConditionValueContext actualMultiConditionValueContext = new MultiConditionValueContext(parent, 1);

    // Assert
    assertNull(actualMultiConditionValueContext.getStart());
    assertNull(actualMultiConditionValueContext.getStop());
    assertSame(parent, actualMultiConditionValueContext.getParent());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#numberConditionValue()}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#numberConditionValue()}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextNumberConditionValue() {
    // Arrange, Act and Assert
    assertTrue((new MultiConditionValueContext(new ParserRuleContext(), 1)).numberConditionValue().isEmpty());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#numberConditionValue(int)} with {@code int}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#numberConditionValue(int)}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext numberConditionValue(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextNumberConditionValueWithInt() {
    // Arrange, Act and Assert
    assertNull((new MultiConditionValueContext(new ParserRuleContext(), 1)).numberConditionValue(1));
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#RS_BRACKET()}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#RS_BRACKET()}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext RS_BRACKET()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextRs_bracket() {
    // Arrange, Act and Assert
    assertNull((new MultiConditionValueContext(new ParserRuleContext(), 1)).RS_BRACKET());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#stringConditionValue()}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#stringConditionValue()}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext stringConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextStringConditionValue() {
    // Arrange, Act and Assert
    assertTrue((new MultiConditionValueContext(new ParserRuleContext(), 1)).stringConditionValue().isEmpty());
  }

  /**
   * Test MultiConditionValueContext {@link MultiConditionValueContext#stringConditionValue(int)} with {@code int}.
   * <p>
   * Method under test: {@link MultiConditionValueContext#stringConditionValue(int)}
   */
  @Test
  @DisplayName("Test MultiConditionValueContext stringConditionValue(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValueContextStringConditionValueWithInt() {
    // Arrange, Act and Assert
    assertNull((new MultiConditionValueContext(new ParserRuleContext(), 1)).stringConditionValue(1));
  }

  /**
   * Test {@link OALParser#multiConditionValue()}.
   * <ul>
   *   <li>Then return not {@link ParserRuleContext#children} second {@link ParserRuleContext#exception} Recognizer Trace.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#multiConditionValue()}
   */
  @Test
  @DisplayName("Test multiConditionValue(); then return not children second exception Recognizer Trace")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValue_thenReturnNotChildrenSecondExceptionRecognizerTrace() throws RecognitionException {
    // Arrange, Act and Assert
    List<ParseTree> parseTreeList = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).multiConditionValue().children;
    assertEquals(3, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(1);
    RecognitionException recognitionException = ((EnumConditionValueContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof EnumConditionValueContext);
    assertFalse(((OALParser) recognizer).isTrace());
    assertTrue(((OALParser) recognizer).getParseListeners().isEmpty());
    assertArrayEquals(new int[]{OALParser.DOT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#multiConditionValue()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#multiConditionValue()}
   */
  @Test
  @DisplayName("Test multiConditionValue(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValue_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    MultiConditionValueContext actualMultiConditionValueResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).multiConditionValue();

    // Assert
    assertEquals("", actualMultiConditionValueResult.getText());
    assertEquals("[]", actualMultiConditionValueResult.toStringTree());
    assertNull(actualMultiConditionValueResult.children);
    assertNull(actualMultiConditionValueResult.getStop());
    assertEquals(0, actualMultiConditionValueResult.getChildCount());
  }

  /**
   * Test {@link OALParser#multiConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] <missing '['> ([293] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#multiConditionValue()}
   */
  @Test
  @DisplayName("Test multiConditionValue(); then return toStringTree is '([] <missing '['> ([293] 42))'")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValue_thenReturnToStringTreeIsMissing29342() throws RecognitionException {
    // Arrange and Act
    MultiConditionValueContext actualMultiConditionValueResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).multiConditionValue();

    // Assert
    assertEquals("([] <missing '['> ([293] 42))", actualMultiConditionValueResult.toStringTree());
    assertEquals("<missing '['>42", actualMultiConditionValueResult.getText());
    assertEquals(2, actualMultiConditionValueResult.children.size());
    assertEquals(2, actualMultiConditionValueResult.getChildCount());
  }

  /**
   * Test {@link OALParser#multiConditionValue()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#multiConditionValue()}
   */
  @Test
  @DisplayName("Test multiConditionValue(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testMultiConditionValue_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    MultiConditionValueContext actualMultiConditionValueResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A@A@A@A@".getBytes("UTF-8")))))))
            .multiConditionValue();

    // Assert
    assertTrue(actualMultiConditionValueResult.getStop() instanceof CommonToken);
    assertEquals("([] <missing '['> ([309] A <missing '.'> A) A A)", actualMultiConditionValueResult.toStringTree());
    assertEquals("<missing '['>A<missing '.'>AAA", actualMultiConditionValueResult.getText());
    assertEquals(4, actualMultiConditionValueResult.children.size());
    assertEquals(4, actualMultiConditionValueResult.getChildCount());
  }

  /**
   * Test {@link OALParser#OALParser(TokenStream)}.
   * <p>
   * Method under test: {@link OALParser#OALParser(TokenStream)}
   */
  @Test
  @DisplayName("Test new OALParser(TokenStream)")
  @Tag("MaintainedByDiffblue")
  void testNewOALParser() {
    // Arrange
    BufferedTokenStream input = new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input")));

    // Act
    OALParser actualOalParser = new OALParser(input);

    // Assert
    TokenStream inputStream = actualOalParser.getInputStream();
    assertTrue(inputStream instanceof BufferedTokenStream);
    assertTrue(actualOalParser.getCurrentToken() instanceof CommonToken);
    assertTrue(actualOalParser.getTokenFactory() instanceof CommonTokenFactory);
    assertTrue(actualOalParser.getErrorHandler() instanceof DefaultErrorStrategy);
    assertTrue(actualOalParser.getErrorListenerDispatch() instanceof ProxyErrorListener);
    Vocabulary vocabulary = actualOalParser.getVocabulary();
    assertTrue(vocabulary instanceof VocabularyImpl);
    assertEquals("<unknown>", actualOalParser.getSourceName());
    assertEquals("java-escape", actualOalParser.getGrammarFileName());
    assertNull(actualOalParser.getContext());
    assertNull(actualOalParser.getRuleContext());
    assertNull(actualOalParser.getParseInfo());
    assertEquals(-1, actualOalParser.getState());
    assertEquals(0, actualOalParser.getNumberOfSyntaxErrors());
    assertEquals(0, actualOalParser.getPrecedence());
    assertEquals(1, actualOalParser.getErrorListeners().size());
    Map<String, Integer> tokenTypeMap = actualOalParser.getTokenTypeMap();
    assertEquals(155, tokenTypeMap.size());
    assertFalse(actualOalParser.getTrimParseTree());
    assertFalse(actualOalParser.isMatchedEOF());
    assertFalse(actualOalParser.isTrace());
    assertTrue(actualOalParser.getParseListeners().isEmpty());
    assertTrue(actualOalParser.getRuleInvocationStack().isEmpty());
    Map<String, Integer> ruleIndexMap = actualOalParser.getRuleIndexMap();
    assertEquals(OALParser.SRC_BROWSER_APP_PAGE_TRAFFIC, ruleIndexMap.size());
    assertTrue(ruleIndexMap.containsKey("funcParamExpression"));
    assertTrue(ruleIndexMap.containsKey("functionArgCast"));
    assertTrue(ruleIndexMap.containsKey("lessEqualMatch"));
    assertTrue(ruleIndexMap.containsKey("mapAttribute"));
    assertTrue(ruleIndexMap.containsKey("multiConditionValue"));
    assertTrue(ruleIndexMap.containsKey("stringMatch"));
    assertTrue(tokenTypeMap.containsKey("'BrowserAppWebInteractionPerf'"));
    assertTrue(tokenTypeMap.containsKey("'CacheAccess'"));
    assertTrue(tokenTypeMap.containsKey("'CiliumEndpoint'"));
    assertTrue(tokenTypeMap.containsKey("'ServiceInstanceCLRThread'"));
    assertTrue(tokenTypeMap.containsKey("'in'"));
    assertTrue(tokenTypeMap.containsKey("SRC_K8S_SERVICE_INSTANCE"));
    assertTrue(actualOalParser.getBuildParseTree());
    assertEquals(OALParser.RULE_inMatch, actualOalParser.getDFAStrings().size());
    assertEquals(OALParser._serializedATN, actualOalParser.getSerializedATN());
    assertSame(input, inputStream);
    assertSame(input, actualOalParser.getTokenStream());
    assertSame(actualOalParser.VOCABULARY, vocabulary);
    ATN expectedATN = actualOalParser._ATN;
    assertSame(expectedATN, actualOalParser.getATN());
    String[] expectedRuleNames = actualOalParser.ruleNames;
    assertSame(expectedRuleNames, actualOalParser.getRuleNames());
    String[] expectedTokenNames = actualOalParser.tokenNames;
    assertSame(expectedTokenNames, actualOalParser.getTokenNames());
  }

  /**
   * Test NotContainMatchContext {@link NotContainMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link NotContainMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test NotContainMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new NotContainMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test NotContainMatchContext {@link NotContainMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link NotContainMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test NotContainMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_DATABASE_ACCESS, (new NotContainMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test NotContainMatchContext {@link NotContainMatchContext#NotContainMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link NotContainMatchContext#NotContainMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test NotContainMatchContext new NotContainMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatchContextNewNotContainMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    NotContainMatchContext actualNotContainMatchContext = new NotContainMatchContext(parent, 1);

    // Assert
    assertNull(actualNotContainMatchContext.getStart());
    assertNull(actualNotContainMatchContext.getStop());
    assertSame(parent, actualNotContainMatchContext.getParent());
  }

  /**
   * Test NotContainMatchContext {@link NotContainMatchContext#NOT_CONTAIN()}.
   * <p>
   * Method under test: {@link NotContainMatchContext#NOT_CONTAIN()}
   */
  @Test
  @DisplayName("Test NotContainMatchContext NOT_CONTAIN()")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatchContextNot_contain() {
    // Arrange, Act and Assert
    assertNull((new NotContainMatchContext(new ParserRuleContext(), 1)).NOT_CONTAIN());
  }

  /**
   * Test NotContainMatchContext {@link NotContainMatchContext#stringConditionValue()}.
   * <p>
   * Method under test: {@link NotContainMatchContext#stringConditionValue()}
   */
  @Test
  @DisplayName("Test NotContainMatchContext stringConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatchContextStringConditionValue() {
    // Arrange, Act and Assert
    assertNull((new NotContainMatchContext(new ParserRuleContext(), 1)).stringConditionValue());
  }

  /**
   * Test {@link OALParser#notContainMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notContainMatch()}
   */
  @Test
  @DisplayName("Test notContainMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.notContainMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.NOT_CONTAIN},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#notContainMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notContainMatch()}
   */
  @Test
  @DisplayName("Test notContainMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.notContainMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.NOT_CONTAIN},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#notContainMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notContainMatch()}
   */
  @Test
  @DisplayName("Test notContainMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    NotContainMatchContext actualNotContainMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).notContainMatch();

    // Assert
    assertEquals("", actualNotContainMatchResult.getText());
    assertEquals("([] [237])", actualNotContainMatchResult.toStringTree());
    assertNull(actualNotContainMatchResult.getStop());
    assertEquals(1, actualNotContainMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#notContainMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([237] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notContainMatch()}
   */
  @Test
  @DisplayName("Test notContainMatch(); then return toStringTree is '([] ([237] 42))'")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatch_thenReturnToStringTreeIs23742() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NotContainMatchContext actualNotContainMatchResult = oalParser.notContainMatch();

    // Assert
    Token start = actualNotContainMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNotContainMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([237] 42))", actualNotContainMatchResult.toStringTree());
    assertEquals("42", actualNotContainMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualNotContainMatchResult, recognitionException.getCtx());
    assertSame(start, actualNotContainMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#notContainMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([237] ([324 237] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notContainMatch()}
   */
  @Test
  @DisplayName("Test notContainMatch(); then return toStringTree is '([] ([237] ([324 237] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatch_thenReturnToStringTreeIs237324237Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NotContainMatchContext actualNotContainMatchResult = oalParser.notContainMatch();

    // Assert
    Token start = actualNotContainMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNotContainMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([237] ([324 237] Input)))", actualNotContainMatchResult.toStringTree());
    assertEquals("Input", actualNotContainMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualNotContainMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#notContainMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notContainMatch()}
   */
  @Test
  @DisplayName("Test notContainMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testNotContainMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    NotContainMatchContext actualNotContainMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A(A(A(A(".getBytes("UTF-8"))))))).notContainMatch();

    // Assert
    assertTrue(actualNotContainMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([237] ([324 237] A)) ( A ( A ( A ()", actualNotContainMatchResult.toStringTree());
    assertEquals("A(A(A(A(", actualNotContainMatchResult.getText());
    assertEquals(8, actualNotContainMatchResult.children.size());
    assertEquals(8, actualNotContainMatchResult.getChildCount());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new NotEqualMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#enumConditionValue()}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#enumConditionValue()}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext enumConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextEnumConditionValue() {
    // Arrange, Act and Assert
    assertNull((new NotEqualMatchContext(new ParserRuleContext(), 1)).enumConditionValue());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_K8S_SERVICE, (new NotEqualMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#NotEqualMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#NotEqualMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext new NotEqualMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextNewNotEqualMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    NotEqualMatchContext actualNotEqualMatchContext = new NotEqualMatchContext(parent, 1);

    // Assert
    assertNull(actualNotEqualMatchContext.getStart());
    assertNull(actualNotEqualMatchContext.getStop());
    assertSame(parent, actualNotEqualMatchContext.getParent());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#NOT_EQUAL()}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#NOT_EQUAL()}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext NOT_EQUAL()")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextNot_equal() {
    // Arrange, Act and Assert
    assertNull((new NotEqualMatchContext(new ParserRuleContext(), 1)).NOT_EQUAL());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#nullConditionValue()}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#nullConditionValue()}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext nullConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextNullConditionValue() {
    // Arrange, Act and Assert
    assertNull((new NotEqualMatchContext(new ParserRuleContext(), 1)).nullConditionValue());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#numberConditionValue()}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#numberConditionValue()}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextNumberConditionValue() {
    // Arrange, Act and Assert
    assertNull((new NotEqualMatchContext(new ParserRuleContext(), 1)).numberConditionValue());
  }

  /**
   * Test NotEqualMatchContext {@link NotEqualMatchContext#stringConditionValue()}.
   * <p>
   * Method under test: {@link NotEqualMatchContext#stringConditionValue()}
   */
  @Test
  @DisplayName("Test NotEqualMatchContext stringConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatchContextStringConditionValue() {
    // Arrange, Act and Assert
    assertNull((new NotEqualMatchContext(new ParserRuleContext(), 1)).stringConditionValue());
  }

  /**
   * Test {@link OALParser#notEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notEqualMatch()}
   */
  @Test
  @DisplayName("Test notEqualMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.notEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.NOT_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#notEqualMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notEqualMatch()}
   */
  @Test
  @DisplayName("Test notEqualMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.notEqualMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.NOT_EQUAL},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#notEqualMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notEqualMatch()}
   */
  @Test
  @DisplayName("Test notEqualMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    NotEqualMatchContext actualNotEqualMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).notEqualMatch();

    // Assert
    assertEquals("", actualNotEqualMatchResult.getText());
    assertEquals("([] [276])", actualNotEqualMatchResult.toStringTree());
    assertNull(actualNotEqualMatchResult.getStop());
    assertEquals(1, actualNotEqualMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#notEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([276] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notEqualMatch()}
   */
  @Test
  @DisplayName("Test notEqualMatch(); then return toStringTree is '([] ([276] 42))'")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatch_thenReturnToStringTreeIs27642() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NotEqualMatchContext actualNotEqualMatchResult = oalParser.notEqualMatch();

    // Assert
    Token start = actualNotEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNotEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([276] 42))", actualNotEqualMatchResult.toStringTree());
    assertEquals("42", actualNotEqualMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualNotEqualMatchResult, recognitionException.getCtx());
    assertSame(start, actualNotEqualMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#notEqualMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([276] ([324 276] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notEqualMatch()}
   */
  @Test
  @DisplayName("Test notEqualMatch(); then return toStringTree is '([] ([276] ([324 276] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatch_thenReturnToStringTreeIs276324276Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NotEqualMatchContext actualNotEqualMatchResult = oalParser.notEqualMatch();

    // Assert
    Token start = actualNotEqualMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNotEqualMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([276] ([324 276] Input)))", actualNotEqualMatchResult.toStringTree());
    assertEquals("Input", actualNotEqualMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(329, recognitionException.getOffendingState());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualNotEqualMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#notEqualMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#notEqualMatch()}
   */
  @Test
  @DisplayName("Test notEqualMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testNotEqualMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    NotEqualMatchContext actualNotEqualMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A:A:A:A:".getBytes("UTF-8"))))))).notEqualMatch();

    // Assert
    assertTrue(actualNotEqualMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([276] ([324 276] A)) <missing '!='> ([280] A <missing '.'> A))",
        actualNotEqualMatchResult.toStringTree());
    assertEquals("A<missing '!='>A<missing '.'>A", actualNotEqualMatchResult.getText());
    assertNull(actualNotEqualMatchResult.exception);
    assertEquals(3, actualNotEqualMatchResult.children.size());
    assertEquals(3, actualNotEqualMatchResult.getChildCount());
  }

  /**
   * Test NullConditionValueContext {@link NullConditionValueContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link NullConditionValueContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test NullConditionValueContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValueContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.DECORATOR, (new NullConditionValueContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test NullConditionValueContext {@link NullConditionValueContext#NullConditionValueContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link NullConditionValueContext#NullConditionValueContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test NullConditionValueContext new NullConditionValueContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValueContextNewNullConditionValueContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    NullConditionValueContext actualNullConditionValueContext = new NullConditionValueContext(parent, 1);

    // Assert
    assertNull(actualNullConditionValueContext.getStart());
    assertNull(actualNullConditionValueContext.getStop());
    assertSame(parent, actualNullConditionValueContext.getParent());
  }

  /**
   * Test NullConditionValueContext {@link NullConditionValueContext#NULL_LITERAL()}.
   * <p>
   * Method under test: {@link NullConditionValueContext#NULL_LITERAL()}
   */
  @Test
  @DisplayName("Test NullConditionValueContext NULL_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValueContextNull_literal() {
    // Arrange, Act and Assert
    assertNull((new NullConditionValueContext(new ParserRuleContext(), 1)).NULL_LITERAL());
  }

  /**
   * Test {@link OALParser#nullConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#nullConditionValue()}
   */
  @Test
  @DisplayName("Test nullConditionValue(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.nullConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.NULL_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#nullConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#nullConditionValue()}
   */
  @Test
  @DisplayName("Test nullConditionValue(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.nullConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.NULL_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#nullConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] APAPAPAP)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#nullConditionValue()}
   */
  @Test
  @DisplayName("Test nullConditionValue(); then return toStringTree is '([] APAPAPAP)'")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValue_thenReturnToStringTreeIsApapapap() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("APAPAPAP".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NullConditionValueContext actualNullConditionValueResult = oalParser.nullConditionValue();

    // Assert
    Token start = actualNullConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNullConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] APAPAPAP)", actualNullConditionValueResult.toStringTree());
    assertEquals("APAPAPAP", actualNullConditionValueResult.getText());
    assertEquals("APAPAPAP", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualNullConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualNullConditionValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#nullConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#nullConditionValue()}
   */
  @Test
  @DisplayName("Test nullConditionValue(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValue_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NullConditionValueContext actualNullConditionValueResult = oalParser.nullConditionValue();

    // Assert
    Token start = actualNullConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNullConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualNullConditionValueResult.toStringTree());
    assertEquals("Input", actualNullConditionValueResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualNullConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualNullConditionValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#nullConditionValue()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#nullConditionValue()}
   */
  @Test
  @DisplayName("Test nullConditionValue(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValue_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).nullConditionValue().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link OALParser#nullConditionValue()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#nullConditionValue()}
   */
  @Test
  @DisplayName("Test nullConditionValue(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testNullConditionValue_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.nullConditionValue().getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link OALParser#numberConditionValue()}.
   * <p>
   * Method under test: {@link OALParser#numberConditionValue()}
   */
  @Test
  @DisplayName("Test numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValue() throws RecognitionException {
    // Arrange and Act
    NumberConditionValueContext actualNumberConditionValueResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).numberConditionValue();

    // Assert
    Token start = actualNumberConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] 42)", actualNumberConditionValueResult.toStringTree());
    BlockEndState blockEndState = ((BasicBlockStartState) decisionState).endState;
    assertEquals(1, blockEndState.getNumberOfTransitions());
    BlockEndState blockEndState2 = ((StarBlockStartState) decisionState2).endState;
    assertEquals(1, blockEndState2.getNumberOfTransitions());
    assertEquals(1, blockEndState.getTransitions().length);
    assertEquals(1, blockEndState2.getTransitions().length);
    assertEquals(8, blockEndState.getStateType());
    assertEquals(8, blockEndState2.getStateType());
    assertFalse(blockEndState.isNonGreedyExitState());
    assertFalse(blockEndState2.isNonGreedyExitState());
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test NumberConditionValueContext {@link NumberConditionValueContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link NumberConditionValueContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test NumberConditionValueContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValueContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_CILIUM_ENDPOINT_RELATION,
        (new NumberConditionValueContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test NumberConditionValueContext {@link NumberConditionValueContext#NumberConditionValueContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link NumberConditionValueContext#NumberConditionValueContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test NumberConditionValueContext new NumberConditionValueContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValueContextNewNumberConditionValueContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    NumberConditionValueContext actualNumberConditionValueContext = new NumberConditionValueContext(parent, 1);

    // Assert
    assertNull(actualNumberConditionValueContext.getStart());
    assertNull(actualNumberConditionValueContext.getStop());
    assertSame(parent, actualNumberConditionValueContext.getParent());
  }

  /**
   * Test NumberConditionValueContext {@link NumberConditionValueContext#NUMBER_LITERAL()}.
   * <p>
   * Method under test: {@link NumberConditionValueContext#NUMBER_LITERAL()}
   */
  @Test
  @DisplayName("Test NumberConditionValueContext NUMBER_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValueContextNumber_literal() {
    // Arrange, Act and Assert
    assertNull((new NumberConditionValueContext(new ParserRuleContext(), 1)).NUMBER_LITERAL());
  }

  /**
   * Test {@link OALParser#numberConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberConditionValue()}
   */
  @Test
  @DisplayName("Test numberConditionValue(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.numberConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.NUMBER_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#numberConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberConditionValue()}
   */
  @Test
  @DisplayName("Test numberConditionValue(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.numberConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.NUMBER_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#numberConditionValue()}.
   * <ul>
   *   <li>Then return not {@link ParserRuleContext#exception} Recognizer Trace.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberConditionValue()}
   */
  @Test
  @DisplayName("Test numberConditionValue(); then return not exception Recognizer Trace")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValue_thenReturnNotExceptionRecognizerTrace() throws RecognitionException {
    // Arrange, Act and Assert
    RecognitionException recognitionException = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).numberConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertFalse(((OALParser) recognizer).isTrace());
    assertTrue(((OALParser) recognizer).getParseListeners().isEmpty());
    assertArrayEquals(new int[]{OALParser.NUMBER_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#numberConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([78] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberConditionValue()}
   */
  @Test
  @DisplayName("Test numberConditionValue(); then return toStringTree is '([78] 42)'")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValue_thenReturnToStringTreeIs7842() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))));
    ParserRuleContext localctx = new ParserRuleContext();
    oalParser.enterRule(localctx, OALParser.STRING_TO_LONG, 1);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    NumberConditionValueContext actualNumberConditionValueResult = oalParser.numberConditionValue();

    // Assert
    assertEquals("([78] 42)", actualNumberConditionValueResult.toStringTree());
    assertEquals(2, actualNumberConditionValueResult.depth());
    assertFalse(actualNumberConditionValueResult.isEmpty());
    assertEquals(OALParser.STRING_TO_LONG, actualNumberConditionValueResult.invokingState);
    assertSame(localctx, actualNumberConditionValueResult.getParent());
  }

  /**
   * Test {@link OALParser#numberConditionValue()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberConditionValue()}
   */
  @Test
  @DisplayName("Test numberConditionValue(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValue_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).numberConditionValue().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#numberConditionValue()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberConditionValue()}
   */
  @Test
  @DisplayName("Test numberConditionValue(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testNumberConditionValue_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.numberConditionValue().getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test NumberMatchContext {@link NumberMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link NumberMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test NumberMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testNumberMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new NumberMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test NumberMatchContext {@link NumberMatchContext#DUALEQUALS()}.
   * <p>
   * Method under test: {@link NumberMatchContext#DUALEQUALS()}
   */
  @Test
  @DisplayName("Test NumberMatchContext DUALEQUALS()")
  @Tag("MaintainedByDiffblue")
  void testNumberMatchContextDualequals() {
    // Arrange, Act and Assert
    assertNull((new NumberMatchContext(new ParserRuleContext(), 1)).DUALEQUALS());
  }

  /**
   * Test NumberMatchContext {@link NumberMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link NumberMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test NumberMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testNumberMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_CLR_GC,
        (new NumberMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test NumberMatchContext {@link NumberMatchContext#NumberMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link NumberMatchContext#NumberMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test NumberMatchContext new NumberMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testNumberMatchContextNewNumberMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    NumberMatchContext actualNumberMatchContext = new NumberMatchContext(parent, 1);

    // Assert
    assertNull(actualNumberMatchContext.getStart());
    assertNull(actualNumberMatchContext.getStop());
    assertSame(parent, actualNumberMatchContext.getParent());
  }

  /**
   * Test NumberMatchContext {@link NumberMatchContext#numberConditionValue()}.
   * <p>
   * Method under test: {@link NumberMatchContext#numberConditionValue()}
   */
  @Test
  @DisplayName("Test NumberMatchContext numberConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testNumberMatchContextNumberConditionValue() {
    // Arrange, Act and Assert
    assertNull((new NumberMatchContext(new ParserRuleContext(), 1)).numberConditionValue());
  }

  /**
   * Test {@link OALParser#numberMatch()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] ([245] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberMatch()}
   */
  @Test
  @DisplayName("Test numberMatch(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] ([245] 42))'")
  @Tag("MaintainedByDiffblue")
  void testNumberMatch_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs24542() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NumberMatchContext actualNumberMatchResult = oalParser.numberMatch();

    // Assert
    Token start = actualNumberMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNumberMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([245] 42))", actualNumberMatchResult.toStringTree());
    assertEquals("42", actualNumberMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualNumberMatchResult, recognitionException.getCtx());
    assertSame(start, actualNumberMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#numberMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberMatch()}
   */
  @Test
  @DisplayName("Test numberMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testNumberMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.numberMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.DUALEQUALS},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#numberMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberMatch()}
   */
  @Test
  @DisplayName("Test numberMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testNumberMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.numberMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.DUALEQUALS},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#numberMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberMatch()}
   */
  @Test
  @DisplayName("Test numberMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testNumberMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    NumberMatchContext actualNumberMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).numberMatch();

    // Assert
    assertEquals("", actualNumberMatchResult.getText());
    assertEquals("([] [245])", actualNumberMatchResult.toStringTree());
    assertNull(actualNumberMatchResult.getStop());
    assertEquals(1, actualNumberMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#numberMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([245] ([324 245] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberMatch()}
   */
  @Test
  @DisplayName("Test numberMatch(); then return toStringTree is '([] ([245] ([324 245] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testNumberMatch_thenReturnToStringTreeIs245324245Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    NumberMatchContext actualNumberMatchResult = oalParser.numberMatch();

    // Assert
    Token start = actualNumberMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualNumberMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([245] ([324 245] Input)))", actualNumberMatchResult.toStringTree());
    assertEquals("Input", actualNumberMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualNumberMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#numberMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#numberMatch()}
   */
  @Test
  @DisplayName("Test numberMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testNumberMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    NumberMatchContext actualNumberMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A,A,A,A,".getBytes("UTF-8"))))))).numberMatch();

    // Assert
    assertTrue(actualNumberMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([245] ([324 245] A)) , A , A , A ,)", actualNumberMatchResult.toStringTree());
    assertEquals("A,A,A,A,", actualNumberMatchResult.getText());
    assertEquals(8, actualNumberMatchResult.children.size());
    assertEquals(8, actualNumberMatchResult.getChildCount());
  }

  /**
   * Test RootContext {@link RootContext#aggregationStatement()}.
   * <p>
   * Method under test: {@link RootContext#aggregationStatement()}
   */
  @Test
  @DisplayName("Test RootContext aggregationStatement()")
  @Tag("MaintainedByDiffblue")
  void testRootContextAggregationStatement() {
    // Arrange, Act and Assert
    assertTrue((new RootContext(new ParserRuleContext(), 1)).aggregationStatement().isEmpty());
  }

  /**
   * Test RootContext {@link RootContext#aggregationStatement(int)} with {@code int}.
   * <p>
   * Method under test: {@link RootContext#aggregationStatement(int)}
   */
  @Test
  @DisplayName("Test RootContext aggregationStatement(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testRootContextAggregationStatementWithInt() {
    // Arrange, Act and Assert
    assertNull((new RootContext(new ParserRuleContext(), 1)).aggregationStatement(1));
  }

  /**
   * Test RootContext {@link RootContext#disableStatement()}.
   * <p>
   * Method under test: {@link RootContext#disableStatement()}
   */
  @Test
  @DisplayName("Test RootContext disableStatement()")
  @Tag("MaintainedByDiffblue")
  void testRootContextDisableStatement() {
    // Arrange, Act and Assert
    assertTrue((new RootContext(new ParserRuleContext(), 1)).disableStatement().isEmpty());
  }

  /**
   * Test RootContext {@link RootContext#disableStatement(int)} with {@code int}.
   * <p>
   * Method under test: {@link RootContext#disableStatement(int)}
   */
  @Test
  @DisplayName("Test RootContext disableStatement(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testRootContextDisableStatementWithInt() {
    // Arrange, Act and Assert
    assertNull((new RootContext(new ParserRuleContext(), 1)).disableStatement(1));
  }

  /**
   * Test RootContext getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RootContext#RootContext(ParserRuleContext, int)}
   *   <li>{@link RootContext#getRuleIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test RootContext getters and setters")
  @Tag("MaintainedByDiffblue")
  void testRootContextGettersAndSetters() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    RootContext actualRootContext = new RootContext(parent, 1);
    int actualRuleIndex = actualRootContext.getRuleIndex();

    // Assert
    assertNull(actualRootContext.getStart());
    assertNull(actualRootContext.getStop());
    assertEquals(0, actualRuleIndex);
    assertSame(parent, actualRootContext.getParent());
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return Start Text is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); given ANTLRInputStream(String) with input is '42'; then return Start Text is '42'")
  @Tag("MaintainedByDiffblue")
  void testRoot_givenANTLRInputStreamWithInputIs42_thenReturnStartTextIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).root().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is empty string.</li>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); given ANTLRInputStream(String) with input is empty string; then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testRoot_givenANTLRInputStreamWithInputIsEmptyString_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).root().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] ([90] ([97 90] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] ([90] ([97 90] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testRoot_givenANTLRInputStreamWithInput_thenReturnToStringTreeIs909790Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    RootContext actualRootResult = (new OALParser(new BufferedTokenStream(tokenSource))).root();

    // Assert
    Token start = actualRootResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([90] ([97 90] Input)))", actualRootResult.toStringTree());
    assertEquals("Input", actualRootResult.getText());
    assertEquals("Input", start.getText());
    Interval sourceInterval = actualRootResult.getSourceInterval();
    assertEquals(0, sourceInterval.b);
    assertEquals(1, actualRootResult.getChildCount());
    assertEquals(1, sourceInterval.length());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualRootResult.getStop());
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} first {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return children first exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnChildrenFirstExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.root().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((AggregationStatementContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof AggregationStatementContext);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult2 = parseListeners.get(0);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.EQUAL, OALParser.SPACE}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} first {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return children first exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnChildrenFirstExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.root().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((AggregationStatementContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof AggregationStatementContext);
    ParseTreeListener getResult2 = parseListeners.get(1);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.EQUAL, OALParser.SPACE}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return toStringTree is '([] ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))'")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnToStringTreeIs909790A909790A909790A() throws IOException, RecognitionException {
    // Arrange and Act
    RootContext actualRootResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'^', 1, 'A', 1, 'A', 1, 'A', 1}))))))
            .root();

    // Assert
    assertEquals("([] ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))", actualRootResult.toStringTree());
    assertEquals("AAA", actualRootResult.getText());
    assertEquals(3, actualRootResult.children.size());
    assertEquals(3, actualRootResult.getChildCount());
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return toStringTree is '([] ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))'")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnToStringTreeIs909790A909790A909790A909790A() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 1, 'A', 1, 'A', 1, 'A', 1}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    RootContext actualRootResult = (new OALParser(new BufferedTokenStream(tokenSource))).root();

    // Assert
    Token start = actualRootResult.getStart();
    assertTrue(start instanceof CommonToken);
    Token stop = actualRootResult.getStop();
    assertTrue(stop instanceof CommonToken);
    assertEquals("([] ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))",
        actualRootResult.toStringTree());
    assertEquals("AAAA", actualRootResult.getText());
    assertSame(input, start.getInputStream());
    assertSame(input, stop.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(tokenSource, stop.getTokenSource());
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([90] ([97 90] Z)) ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return toStringTree is '([] ([90] ([97 90] Z)) ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))'")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnToStringTreeIs909790Z909790A909790A909790A() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'Z', 1, 'A', 1, 'A', 1, 'A', 1}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    RootContext actualRootResult = (new OALParser(new BufferedTokenStream(tokenSource))).root();

    // Assert
    Token start = actualRootResult.getStart();
    assertTrue(start instanceof CommonToken);
    Token stop = actualRootResult.getStop();
    assertTrue(stop instanceof CommonToken);
    assertEquals("([] ([90] ([97 90] Z)) ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))",
        actualRootResult.toStringTree());
    assertEquals("Z", start.getText());
    assertEquals("ZAAA", actualRootResult.getText());
    assertSame(input, start.getInputStream());
    assertSame(input, stop.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(tokenSource, stop.getTokenSource());
  }

  /**
   * Test {@link OALParser#root()}.
   * <ul>
   *   <li>Then return toStringTree is a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return toStringTree is a string")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnToStringTreeIsAString() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(new byte[]{-1, 1, 'A', 1, 'A', 1, 'A', 1}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    RootContext actualRootResult = (new OALParser(new BufferedTokenStream(tokenSource))).root();

    // Assert
    Token start = actualRootResult.getStart();
    assertTrue(start instanceof CommonToken);
    Token stop = actualRootResult.getStop();
    assertTrue(stop instanceof CommonToken);
    assertEquals("([] ([90] ([97 90] �)) ([90] ([97 90] A)) ([90] ([97 90] A)) ([90] ([97 90] A)))",
        actualRootResult.toStringTree());
    assertEquals("�", start.getText());
    assertEquals("�AAA", actualRootResult.getText());
    assertSame(input, start.getInputStream());
    assertSame(input, stop.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(tokenSource, stop.getTokenSource());
  }

  /**
   * Test {@link OALParser#sourceAttrCast()}.
   * <p>
   * Method under test: {@link OALParser#sourceAttrCast()}
   */
  @Test
  @DisplayName("Test sourceAttrCast()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCast() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.sourceAttrCast().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((CastStmtContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof CastStmtContext);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult2 = parseListeners.get(0);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#sourceAttrCast()}.
   * <p>
   * Method under test: {@link OALParser#sourceAttrCast()}
   */
  @Test
  @DisplayName("Test sourceAttrCast()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCast2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    List<ParseTree> parseTreeList = oalParser.sourceAttrCast().children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    RecognitionException recognitionException = ((CastStmtContext) getResult).exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    assertTrue(getResult instanceof CastStmtContext);
    ParseTreeListener getResult2 = parseListeners.get(1);
    assertTrue(getResult2 instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult2);
    assertArrayEquals(new int[]{OALParser.STRING_TO_LONG, OALParser.STRING_TO_LONG_SHORT, OALParser.STRING_TO_INT,
        OALParser.STRING_TO_INT_SHORT}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test SourceAttrCastContext {@link SourceAttrCastContext#castStmt()}.
   * <p>
   * Method under test: {@link SourceAttrCastContext#castStmt()}
   */
  @Test
  @DisplayName("Test SourceAttrCastContext castStmt()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCastContextCastStmt() {
    // Arrange, Act and Assert
    assertNull((new SourceAttrCastContext(new ParserRuleContext(), 1)).castStmt());
  }

  /**
   * Test SourceAttrCastContext {@link SourceAttrCastContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link SourceAttrCastContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test SourceAttrCastContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCastContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_BROWSER_APP_PERF, (new SourceAttrCastContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test SourceAttrCastContext {@link SourceAttrCastContext#SourceAttrCastContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link SourceAttrCastContext#SourceAttrCastContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test SourceAttrCastContext new SourceAttrCastContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCastContextNewSourceAttrCastContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    SourceAttrCastContext actualSourceAttrCastContext = new SourceAttrCastContext(parent, 1);

    // Assert
    assertNull(actualSourceAttrCastContext.getStart());
    assertNull(actualSourceAttrCastContext.getStop());
    assertSame(parent, actualSourceAttrCastContext.getParent());
  }

  /**
   * Test {@link OALParser#sourceAttrCast()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttrCast()}
   */
  @Test
  @DisplayName("Test sourceAttrCast(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCast_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    SourceAttrCastContext actualSourceAttrCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .sourceAttrCast();

    // Assert
    Token start = actualSourceAttrCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("", actualSourceAttrCastResult.getText());
    assertEquals("([] [353])", actualSourceAttrCastResult.toStringTree());
    assertEquals("<EOF>", start.getText());
    assertNull(actualSourceAttrCastResult.getStop());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    Interval sourceInterval = actualSourceAttrCastResult.getSourceInterval();
    assertEquals(-1, sourceInterval.b);
    assertEquals(0, sourceInterval.length());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#sourceAttrCast()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([353] ARARARAR))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttrCast()}
   */
  @Test
  @DisplayName("Test sourceAttrCast(); then return toStringTree is '([] ([353] ARARARAR))'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCast_thenReturnToStringTreeIs353Arararar() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("ARARARAR".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    SourceAttrCastContext actualSourceAttrCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .sourceAttrCast();

    // Assert
    Token start = actualSourceAttrCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([353] ARARARAR))", actualSourceAttrCastResult.toStringTree());
    assertEquals("ARARARAR", actualSourceAttrCastResult.getText());
    assertEquals("ARARARAR", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttrCastResult.getStop());
  }

  /**
   * Test {@link OALParser#sourceAttrCast()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([353] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttrCast()}
   */
  @Test
  @DisplayName("Test sourceAttrCast(); then return toStringTree is '([] ([353] Input))'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttrCast_thenReturnToStringTreeIs353Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    SourceAttrCastContext actualSourceAttrCastResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .sourceAttrCast();

    // Assert
    Token start = actualSourceAttrCastResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([353] Input))", actualSourceAttrCastResult.toStringTree());
    assertEquals("Input", actualSourceAttrCastResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttrCastResult.getStop());
  }

  /**
   * Test SourceAttributeContext {@link SourceAttributeContext#ALL()}.
   * <p>
   * Method under test: {@link SourceAttributeContext#ALL()}
   */
  @Test
  @DisplayName("Test SourceAttributeContext ALL()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeContextAll() {
    // Arrange, Act and Assert
    assertNull((new SourceAttributeContext(new ParserRuleContext(), 1)).ALL());
  }

  /**
   * Test SourceAttributeContext {@link SourceAttributeContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link SourceAttributeContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test SourceAttributeContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_TCP_SERVICE_RELATION,
        (new SourceAttributeContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test SourceAttributeContext {@link SourceAttributeContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link SourceAttributeContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test SourceAttributeContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeContextIdentifier() {
    // Arrange, Act and Assert
    assertNull((new SourceAttributeContext(new ParserRuleContext(), 1)).IDENTIFIER());
  }

  /**
   * Test SourceAttributeContext {@link SourceAttributeContext#mapAttribute()}.
   * <p>
   * Method under test: {@link SourceAttributeContext#mapAttribute()}
   */
  @Test
  @DisplayName("Test SourceAttributeContext mapAttribute()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeContextMapAttribute() {
    // Arrange, Act and Assert
    assertNull((new SourceAttributeContext(new ParserRuleContext(), 1)).mapAttribute());
  }

  /**
   * Test SourceAttributeContext {@link SourceAttributeContext#SourceAttributeContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link SourceAttributeContext#SourceAttributeContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test SourceAttributeContext new SourceAttributeContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeContextNewSourceAttributeContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    SourceAttributeContext actualSourceAttributeContext = new SourceAttributeContext(parent, 1);

    // Assert
    assertNull(actualSourceAttributeContext.getStart());
    assertNull(actualSourceAttributeContext.getStop());
    assertSame(parent, actualSourceAttributeContext.getParent());
  }

  /**
   * Test SourceAttributeStmtContext {@link SourceAttributeStmtContext#DOT()}.
   * <p>
   * Method under test: {@link SourceAttributeStmtContext#DOT()}
   */
  @Test
  @DisplayName("Test SourceAttributeStmtContext DOT()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmtContextDot() {
    // Arrange, Act and Assert
    assertNull((new SourceAttributeStmtContext(new ParserRuleContext(), 1)).DOT());
  }

  /**
   * Test SourceAttributeStmtContext {@link SourceAttributeStmtContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link SourceAttributeStmtContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test SourceAttributeStmtContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmtContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_RELATION,
        (new SourceAttributeStmtContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test SourceAttributeStmtContext {@link SourceAttributeStmtContext#SourceAttributeStmtContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link SourceAttributeStmtContext#SourceAttributeStmtContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test SourceAttributeStmtContext new SourceAttributeStmtContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmtContextNewSourceAttributeStmtContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    SourceAttributeStmtContext actualSourceAttributeStmtContext = new SourceAttributeStmtContext(parent, 1);

    // Assert
    assertNull(actualSourceAttributeStmtContext.getStart());
    assertNull(actualSourceAttributeStmtContext.getStop());
    assertSame(parent, actualSourceAttributeStmtContext.getParent());
  }

  /**
   * Test SourceAttributeStmtContext {@link SourceAttributeStmtContext#sourceAttribute()}.
   * <p>
   * Method under test: {@link SourceAttributeStmtContext#sourceAttribute()}
   */
  @Test
  @DisplayName("Test SourceAttributeStmtContext sourceAttribute()")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmtContextSourceAttribute() {
    // Arrange, Act and Assert
    assertNull((new SourceAttributeStmtContext(new ParserRuleContext(), 1)).sourceAttribute());
  }

  /**
   * Test {@link OALParser#sourceAttributeStmt()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] <missing '.'> ([169] A))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttributeStmt()}
   */
  @Test
  @DisplayName("Test sourceAttributeStmt(); given 'A'; then return toStringTree is '([] <missing '.'> ([169] A))'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmt_givenA_thenReturnToStringTreeIsMissing169A() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 18, 'A', 18, 'A', 18, 'A', 18}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    SourceAttributeStmtContext actualSourceAttributeStmtResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .sourceAttributeStmt();

    // Assert
    Token start = actualSourceAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] <missing '.'> ([169] A))", actualSourceAttributeStmtResult.toStringTree());
    assertEquals("<missing '.'>A", actualSourceAttributeStmtResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#sourceAttributeStmt()}.
   * <ul>
   *   <li>Given {@link OALParser#OALParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttributeStmt()}
   */
  @Test
  @DisplayName("Test sourceAttributeStmt(); given OALParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmt_givenOALParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    SourceAttributeStmtContext actualSourceAttributeStmtResult = oalParser.sourceAttributeStmt();

    // Assert
    Token start = actualSourceAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] <missing '.'> ([169] Input))", actualSourceAttributeStmtResult.toStringTree());
    assertEquals("<missing '.'>Input", actualSourceAttributeStmtResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#sourceAttributeStmt()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttributeStmt()}
   */
  @Test
  @DisplayName("Test sourceAttributeStmt(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmt_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    SourceAttributeStmtContext actualSourceAttributeStmtResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).sourceAttributeStmt();

    // Assert
    assertEquals("", actualSourceAttributeStmtResult.getText());
    assertEquals("[]", actualSourceAttributeStmtResult.toStringTree());
    assertNull(actualSourceAttributeStmtResult.children);
    assertNull(actualSourceAttributeStmtResult.getStop());
    assertEquals(0, actualSourceAttributeStmtResult.getChildCount());
  }

  /**
   * Test {@link OALParser#sourceAttributeStmt()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttributeStmt()}
   */
  @Test
  @DisplayName("Test sourceAttributeStmt(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmt_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange and Act
    SourceAttributeStmtContext actualSourceAttributeStmtResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).sourceAttributeStmt();

    // Assert
    assertEquals("([] 42)", actualSourceAttributeStmtResult.toStringTree());
    assertEquals("42", actualSourceAttributeStmtResult.getText());
    assertEquals(1, actualSourceAttributeStmtResult.children.size());
    assertEquals(1, actualSourceAttributeStmtResult.getChildCount());
  }

  /**
   * Test {@link OALParser#sourceAttributeStmt()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] <missing '.'> ([169] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttributeStmt()}
   */
  @Test
  @DisplayName("Test sourceAttributeStmt(); then return toStringTree is '([] <missing '.'> ([169] Input))'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmt_thenReturnToStringTreeIsMissing169Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    SourceAttributeStmtContext actualSourceAttributeStmtResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .sourceAttributeStmt();

    // Assert
    Token start = actualSourceAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] <missing '.'> ([169] Input))", actualSourceAttributeStmtResult.toStringTree());
    assertEquals("<missing '.'>Input", actualSourceAttributeStmtResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#sourceAttributeStmt()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] <missing '.'> ([169] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttributeStmt()}
   */
  @Test
  @DisplayName("Test sourceAttributeStmt(); then return toStringTree is '([] <missing '.'> ([169] Input))'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttributeStmt_thenReturnToStringTreeIsMissing169Input2() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    SourceAttributeStmtContext actualSourceAttributeStmtResult = oalParser.sourceAttributeStmt();

    // Assert
    Token start = actualSourceAttributeStmtResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] <missing '.'> ([169] Input))", actualSourceAttributeStmtResult.toStringTree());
    assertEquals("<missing '.'>Input", actualSourceAttributeStmtResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttributeStmtResult.getStop());
  }

  /**
   * Test {@link OALParser#sourceAttribute()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttribute()}
   */
  @Test
  @DisplayName("Test sourceAttribute(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttribute_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    SourceAttributeContext actualSourceAttributeResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .sourceAttribute();

    // Assert
    Token start = actualSourceAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualSourceAttributeResult.toStringTree());
    assertEquals("Input", actualSourceAttributeResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttributeResult.getStop());
  }

  /**
   * Test {@link OALParser#sourceAttribute()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] A)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttribute()}
   */
  @Test
  @DisplayName("Test sourceAttribute(); given 'A'; then return toStringTree is '([] A)'")
  @Tag("MaintainedByDiffblue")
  void testSourceAttribute_givenA_thenReturnToStringTreeIsA() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 20, 'A', 20, 'A', 20, 'A', 20}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    SourceAttributeContext actualSourceAttributeResult = (new OALParser(new BufferedTokenStream(tokenSource)))
        .sourceAttribute();

    // Assert
    Token start = actualSourceAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] A)", actualSourceAttributeResult.toStringTree());
    assertEquals("A", actualSourceAttributeResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualSourceAttributeResult.getStop());
  }

  /**
   * Test {@link OALParser#sourceAttribute()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} InputStream return {@link BufferedTokenStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttribute()}
   */
  @Test
  @DisplayName("Test sourceAttribute(); then exception InputStream return BufferedTokenStream")
  @Tag("MaintainedByDiffblue")
  void testSourceAttribute_thenExceptionInputStreamReturnBufferedTokenStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    SourceAttributeContext actualSourceAttributeResult = oalParser.sourceAttribute();

    // Assert
    RecognitionException recognitionException = actualSourceAttributeResult.exception;
    IntStream inputStream = recognitionException.getInputStream();
    assertTrue(inputStream instanceof BufferedTokenStream);
    Token start = actualSourceAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualSourceAttributeResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertEquals("([] 42)", actualSourceAttributeResult.toStringTree());
    assertEquals("42", actualSourceAttributeResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, inputStream);
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognizer);
    assertSame(actualSourceAttributeResult, recognitionException.getCtx());
    assertSame(start, actualSourceAttributeResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#sourceAttribute()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#sourceAttribute()}
   */
  @Test
  @DisplayName("Test sourceAttribute(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testSourceAttribute_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    SourceAttributeContext actualSourceAttributeResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).sourceAttribute();

    // Assert
    assertEquals("", actualSourceAttributeResult.getText());
    assertEquals("[]", actualSourceAttributeResult.toStringTree());
    assertNull(actualSourceAttributeResult.children);
    assertNull(actualSourceAttributeResult.getStop());
    assertEquals(0, actualSourceAttributeResult.getChildCount());
  }

  /**
   * Test SourceContext {@link SourceContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link SourceContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test SourceContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(7, (new SourceContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test SourceContext {@link SourceContext#SourceContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link SourceContext#SourceContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test SourceContext new SourceContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testSourceContextNewSourceContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    SourceContext actualSourceContext = new SourceContext(parent, 1);

    // Assert
    assertNull(actualSourceContext.getStart());
    assertNull(actualSourceContext.getStop());
    assertSame(parent, actualSourceContext.getParent());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_PAGE_PERF()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_PAGE_PERF()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_PAGE_PERF()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_page_perf() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_PAGE_PERF());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_PAGE_TRAFFIC()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_PAGE_TRAFFIC()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_PAGE_TRAFFIC()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_page_traffic() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_PAGE_TRAFFIC());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_PERF()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_PERF()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_PERF()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_perf() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_PERF());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_RESOURCE_PERF()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_RESOURCE_PERF()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_RESOURCE_PERF()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_resource_perf() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_RESOURCE_PERF());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_SINGLE_VERSION_PERF()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_SINGLE_VERSION_PERF()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_SINGLE_VERSION_PERF()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_single_version_perf() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_SINGLE_VERSION_PERF());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_SINGLE_VERSION_TRAFFIC()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_SINGLE_VERSION_TRAFFIC()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_SINGLE_VERSION_TRAFFIC()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_single_version_traffic() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_SINGLE_VERSION_TRAFFIC());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_TRAFFIC()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_TRAFFIC()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_TRAFFIC()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_traffic() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_TRAFFIC());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_WEB_INTERACTION_PERF()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_WEB_INTERACTION_PERF()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_WEB_INTERACTION_PERF()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_web_interaction_perf() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_WEB_INTERACTION_PERF());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_BROWSER_APP_WEB_VITALS_PERF()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_BROWSER_APP_WEB_VITALS_PERF()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_BROWSER_APP_WEB_VITALS_PERF()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_browser_app_web_vitals_perf() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_BROWSER_APP_WEB_VITALS_PERF());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_CACHE_ACCESS()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_CACHE_ACCESS()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_CACHE_ACCESS()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_cache_access() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_CACHE_ACCESS());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_CILIUM_ENDPOINT()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_CILIUM_ENDPOINT()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_CILIUM_ENDPOINT()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_cilium_endpoint() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_CILIUM_ENDPOINT());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_CILIUM_ENDPOINT_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_CILIUM_ENDPOINT_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_CILIUM_ENDPOINT_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_cilium_endpoint_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_CILIUM_ENDPOINT_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_CILIUM_SERVICE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_CILIUM_SERVICE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_CILIUM_SERVICE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_cilium_service() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_CILIUM_SERVICE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_CILIUM_SERVICE_INSTANCE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_CILIUM_SERVICE_INSTANCE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_CILIUM_SERVICE_INSTANCE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_cilium_service_instance() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_CILIUM_SERVICE_INSTANCE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_CILIUM_SERVICE_INSTANCE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_CILIUM_SERVICE_INSTANCE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_CILIUM_SERVICE_INSTANCE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_cilium_service_instance_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_CILIUM_SERVICE_INSTANCE_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_CILIUM_SERVICE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_CILIUM_SERVICE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_CILIUM_SERVICE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_cilium_service_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_CILIUM_SERVICE_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_DATABASE_ACCESS()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_DATABASE_ACCESS()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_DATABASE_ACCESS()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_database_access() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_DATABASE_ACCESS());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_ENDPOINT()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_ENDPOINT()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_ENDPOINT()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_endpoint() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_ENDPOINT());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_ENDPOINT_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_ENDPOINT_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_ENDPOINT_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_endpoint_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_ENDPOINT_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_ENVOY_INSTANCE_METRIC()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_ENVOY_INSTANCE_METRIC()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_ENVOY_INSTANCE_METRIC()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_envoy_instance_metric() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_ENVOY_INSTANCE_METRIC());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_EVENT()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_EVENT()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_EVENT()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_event() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_EVENT());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_K8S_ENDPOINT()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_K8S_ENDPOINT()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_K8S_ENDPOINT()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_k8s_endpoint() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_K8S_ENDPOINT());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_K8S_SERVICE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_K8S_SERVICE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_K8S_SERVICE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_k8s_service() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_K8S_SERVICE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_K8S_SERVICE_INSTANCE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_K8S_SERVICE_INSTANCE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_K8S_SERVICE_INSTANCE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_k8s_service_instance() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_K8S_SERVICE_INSTANCE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_K8S_SERVICE_INSTANCE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_K8S_SERVICE_INSTANCE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_K8S_SERVICE_INSTANCE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_k8s_service_instance_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_K8S_SERVICE_INSTANCE_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_K8S_SERVICE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_K8S_SERVICE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_K8S_SERVICE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_k8s_service_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_K8S_SERVICE_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_MQ_ACCESS()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_MQ_ACCESS()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_MQ_ACCESS()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_mq_access() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_MQ_ACCESS());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_MQ_ENDPOINT_ACCESS()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_MQ_ENDPOINT_ACCESS()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_MQ_ENDPOINT_ACCESS()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_mq_endpoint_access() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_MQ_ENDPOINT_ACCESS());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_CLR_CPU()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_CLR_CPU()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_CLR_CPU()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_clr_cpu() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_CLR_CPU());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_CLR_GC()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_CLR_GC()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_CLR_GC()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_clr_gc() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_CLR_GC());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_CLR_THREAD()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_CLR_THREAD()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_CLR_THREAD()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_clr_thread() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_CLR_THREAD());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_CLASS()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_CLASS()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_JVM_CLASS()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_jvm_class() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_JVM_CLASS());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_CPU()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_CPU()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_JVM_CPU()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_jvm_cpu() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_JVM_CPU());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_GC()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_GC()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_JVM_GC()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_jvm_gc() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_JVM_GC());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_MEMORY()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_MEMORY()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_JVM_MEMORY()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_jvm_memory() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_JVM_MEMORY());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_MEMORY_POOL()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_MEMORY_POOL()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_JVM_MEMORY_POOL()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_jvm_memory_pool() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_JVM_MEMORY_POOL());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_THREAD()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_JVM_THREAD()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_JVM_THREAD()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_jvm_thread() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_JVM_THREAD());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_INSTANCE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_INSTANCE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_INSTANCE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_instance_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_INSTANCE_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_SERVICE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_SERVICE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_SERVICE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_service_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_SERVICE_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_TCP_SERVICE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_TCP_SERVICE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_TCP_SERVICE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_tcp_service() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_TCP_SERVICE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_TCP_SERVICE_INSTANCE()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_TCP_SERVICE_INSTANCE()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_TCP_SERVICE_INSTANCE()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_tcp_service_instance() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_TCP_SERVICE_INSTANCE());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_TCP_SERVICE_INSTANCE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_TCP_SERVICE_INSTANCE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_TCP_SERVICE_INSTANCE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_tcp_service_instance_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_TCP_SERVICE_INSTANCE_RELATION());
  }

  /**
   * Test SourceContext {@link SourceContext#SRC_TCP_SERVICE_RELATION()}.
   * <p>
   * Method under test: {@link SourceContext#SRC_TCP_SERVICE_RELATION()}
   */
  @Test
  @DisplayName("Test SourceContext SRC_TCP_SERVICE_RELATION()")
  @Tag("MaintainedByDiffblue")
  void testSourceContextSrc_tcp_service_relation() {
    // Arrange, Act and Assert
    assertNull((new SourceContext(new ParserRuleContext(), 1)).SRC_TCP_SERVICE_RELATION());
  }

  /**
   * Test {@link OALParser#source()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is empty string.</li>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#source()}
   */
  @Test
  @DisplayName("Test source(); given ANTLRInputStream(String) with input is empty string; then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testSource_givenANTLRInputStreamWithInputIsEmptyString_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).source().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#source()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return Start StopIndex is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#source()}
   */
  @Test
  @DisplayName("Test source(); given ANTLRInputStream(String) with 'Input'; then return Start StopIndex is four")
  @Tag("MaintainedByDiffblue")
  void testSource_givenANTLRInputStreamWithInput_thenReturnStartStopIndexIsFour() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    SourceContext actualSourceResult = oalParser.source();

    // Assert
    Token start = actualSourceResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualSourceResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualSourceResult, recognitionException.getCtx());
    assertSame(start, actualSourceResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#source()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#source()}
   */
  @Test
  @DisplayName("Test source(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testSource_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.source().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#source()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#source()}
   */
  @Test
  @DisplayName("Test source(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testSource_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.source().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(
        new int[]{4, 5, 6, 7, 8, OALParser.RULE_sourceAttributeStmt, OALParser.RULE_sourceAttribute,
            OALParser.RULE_variable, OALParser.RULE_aggregateFunction, OALParser.RULE_functionName,
            OALParser.RULE_funcParamExpression, OALParser.RULE_literalExpression, OALParser.RULE_attributeExpression,
            OALParser.RULE_attributeExpressionSegment, OALParser.RULE_expression, OALParser.RULE_containMatch,
            OALParser.RULE_notContainMatch, OALParser.RULE_booleanMatch, OALParser.RULE_numberMatch,
            OALParser.RULE_stringMatch, OALParser.RULE_greaterMatch, OALParser.RULE_lessMatch,
            OALParser.RULE_greaterEqualMatch, OALParser.RULE_lessEqualMatch, OALParser.RULE_booleanNotEqualMatch,
            OALParser.RULE_notEqualMatch, OALParser.RULE_likeMatch, OALParser.RULE_inMatch,
            OALParser.RULE_multiConditionValue, OALParser.RULE_conditionAttributeStmt,
            OALParser.RULE_conditionAttribute, OALParser.RULE_mapAttribute, OALParser.RULE_booleanConditionValue,
            OALParser.RULE_stringConditionValue, OALParser.RULE_enumConditionValue, OALParser.RULE_numberConditionValue,
            OALParser.RULE_sourceAttrCast, OALParser.RULE_expressionAttrCast, OALParser.RULE_functionArgCast,
            OALParser.RULE_castStmt, OALParser.SRC_BROWSER_APP_PAGE_TRAFFIC,
            OALParser.SRC_BROWSER_APP_SINGLE_VERSION_TRAFFIC, OALParser.SRC_BROWSER_APP_RESOURCE_PERF,
            OALParser.SRC_BROWSER_APP_WEB_VITALS_PERF, OALParser.SRC_BROWSER_APP_WEB_INTERACTION_PERF},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#source()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#source()}
   */
  @Test
  @DisplayName("Test source(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testSource_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.source().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(
        new int[]{4, 5, 6, 7, 8, OALParser.RULE_sourceAttributeStmt, OALParser.RULE_sourceAttribute,
            OALParser.RULE_variable, OALParser.RULE_aggregateFunction, OALParser.RULE_functionName,
            OALParser.RULE_funcParamExpression, OALParser.RULE_literalExpression, OALParser.RULE_attributeExpression,
            OALParser.RULE_attributeExpressionSegment, OALParser.RULE_expression, OALParser.RULE_containMatch,
            OALParser.RULE_notContainMatch, OALParser.RULE_booleanMatch, OALParser.RULE_numberMatch,
            OALParser.RULE_stringMatch, OALParser.RULE_greaterMatch, OALParser.RULE_lessMatch,
            OALParser.RULE_greaterEqualMatch, OALParser.RULE_lessEqualMatch, OALParser.RULE_booleanNotEqualMatch,
            OALParser.RULE_notEqualMatch, OALParser.RULE_likeMatch, OALParser.RULE_inMatch,
            OALParser.RULE_multiConditionValue, OALParser.RULE_conditionAttributeStmt,
            OALParser.RULE_conditionAttribute, OALParser.RULE_mapAttribute, OALParser.RULE_booleanConditionValue,
            OALParser.RULE_stringConditionValue, OALParser.RULE_enumConditionValue, OALParser.RULE_numberConditionValue,
            OALParser.RULE_sourceAttrCast, OALParser.RULE_expressionAttrCast, OALParser.RULE_functionArgCast,
            OALParser.RULE_castStmt, OALParser.SRC_BROWSER_APP_PAGE_TRAFFIC,
            OALParser.SRC_BROWSER_APP_SINGLE_VERSION_TRAFFIC, OALParser.SRC_BROWSER_APP_RESOURCE_PERF,
            OALParser.SRC_BROWSER_APP_WEB_VITALS_PERF, OALParser.SRC_BROWSER_APP_WEB_INTERACTION_PERF},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#source()}.
   * <ul>
   *   <li>Then return Start TokenSource is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#source()}
   */
  @Test
  @DisplayName("Test source(); then return Start TokenSource is 'null'")
  @Tag("MaintainedByDiffblue")
  void testSource_thenReturnStartTokenSourceIsNull() throws RecognitionException {
    // Arrange
    BufferedTokenStream input = new BufferedTokenStream(new XPathLexer(new ANTLRInputStream("Input")));
    OALParser oalParser = new OALParser(input);

    // Act
    SourceContext actualSourceResult = oalParser.source();

    // Assert
    Token start = actualSourceResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualSourceResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertNull(start.getTokenSource());
    assertEquals(1, start.getType());
    assertSame(input, recognitionException.getInputStream());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualSourceResult, recognitionException.getCtx());
    assertSame(start, actualSourceResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#source()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#source()}
   */
  @Test
  @DisplayName("Test source(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testSource_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    SourceContext actualSourceResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 14, 'A', 14, 'A', 14, 'A', 14}))))))
            .source();

    // Assert
    assertTrue(actualSourceResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualSourceResult.toStringTree());
    assertEquals("AAAA", actualSourceResult.getText());
    assertEquals(4, actualSourceResult.children.size());
    assertEquals(4, actualSourceResult.getChildCount());
  }

  /**
   * Test StringConditionValueContext {@link StringConditionValueContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link StringConditionValueContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test StringConditionValueContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValueContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_CILIUM_SERVICE_RELATION,
        (new StringConditionValueContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test StringConditionValueContext {@link StringConditionValueContext#StringConditionValueContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link StringConditionValueContext#StringConditionValueContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test StringConditionValueContext new StringConditionValueContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValueContextNewStringConditionValueContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    StringConditionValueContext actualStringConditionValueContext = new StringConditionValueContext(parent, 1);

    // Assert
    assertNull(actualStringConditionValueContext.getStart());
    assertNull(actualStringConditionValueContext.getStop());
    assertSame(parent, actualStringConditionValueContext.getParent());
  }

  /**
   * Test StringConditionValueContext {@link StringConditionValueContext#STRING_LITERAL()}.
   * <p>
   * Method under test: {@link StringConditionValueContext#STRING_LITERAL()}
   */
  @Test
  @DisplayName("Test StringConditionValueContext STRING_LITERAL()")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValueContextString_literal() {
    // Arrange, Act and Assert
    assertNull((new StringConditionValueContext(new ParserRuleContext(), 1)).STRING_LITERAL());
  }

  /**
   * Test {@link OALParser#stringConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringConditionValue()}
   */
  @Test
  @DisplayName("Test stringConditionValue(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.stringConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.STRING_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#stringConditionValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringConditionValue()}
   */
  @Test
  @DisplayName("Test stringConditionValue(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValue_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.stringConditionValue().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.STRING_LITERAL}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#stringConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] AJAJAJAJ)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringConditionValue()}
   */
  @Test
  @DisplayName("Test stringConditionValue(); then return toStringTree is '([] AJAJAJAJ)'")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValue_thenReturnToStringTreeIsAjajajaj() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("AJAJAJAJ".getBytes("UTF-8")));
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    StringConditionValueContext actualStringConditionValueResult = oalParser.stringConditionValue();

    // Assert
    Token start = actualStringConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualStringConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] AJAJAJAJ)", actualStringConditionValueResult.toStringTree());
    assertEquals("AJAJAJAJ", actualStringConditionValueResult.getText());
    assertEquals("AJAJAJAJ", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualStringConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualStringConditionValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#stringConditionValue()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringConditionValue()}
   */
  @Test
  @DisplayName("Test stringConditionValue(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValue_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    StringConditionValueContext actualStringConditionValueResult = oalParser.stringConditionValue();

    // Assert
    Token start = actualStringConditionValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualStringConditionValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualStringConditionValueResult.toStringTree());
    assertEquals("Input", actualStringConditionValueResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualStringConditionValueResult, recognitionException.getCtx());
    assertSame(start, actualStringConditionValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link OALParser#stringConditionValue()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringConditionValue()}
   */
  @Test
  @DisplayName("Test stringConditionValue(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValue_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act and Assert
    Token start = (new OALParser(new BufferedTokenStream(tokenSource))).stringConditionValue().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof OALLexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link OALParser#stringConditionValue()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringConditionValue()}
   */
  @Test
  @DisplayName("Test stringConditionValue(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testStringConditionValue_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.stringConditionValue().getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test StringMatchContext {@link StringMatchContext#conditionAttributeStmt()}.
   * <p>
   * Method under test: {@link StringMatchContext#conditionAttributeStmt()}
   */
  @Test
  @DisplayName("Test StringMatchContext conditionAttributeStmt()")
  @Tag("MaintainedByDiffblue")
  void testStringMatchContextConditionAttributeStmt() {
    // Arrange, Act and Assert
    assertNull((new StringMatchContext(new ParserRuleContext(), 1)).conditionAttributeStmt());
  }

  /**
   * Test StringMatchContext {@link StringMatchContext#DUALEQUALS()}.
   * <p>
   * Method under test: {@link StringMatchContext#DUALEQUALS()}
   */
  @Test
  @DisplayName("Test StringMatchContext DUALEQUALS()")
  @Tag("MaintainedByDiffblue")
  void testStringMatchContextDualequals() {
    // Arrange, Act and Assert
    assertNull((new StringMatchContext(new ParserRuleContext(), 1)).DUALEQUALS());
  }

  /**
   * Test StringMatchContext {@link StringMatchContext#enumConditionValue()}.
   * <p>
   * Method under test: {@link StringMatchContext#enumConditionValue()}
   */
  @Test
  @DisplayName("Test StringMatchContext enumConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testStringMatchContextEnumConditionValue() {
    // Arrange, Act and Assert
    assertNull((new StringMatchContext(new ParserRuleContext(), 1)).enumConditionValue());
  }

  /**
   * Test StringMatchContext {@link StringMatchContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link StringMatchContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test StringMatchContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testStringMatchContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_CLR_THREAD,
        (new StringMatchContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test StringMatchContext {@link StringMatchContext#StringMatchContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link StringMatchContext#StringMatchContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test StringMatchContext new StringMatchContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testStringMatchContextNewStringMatchContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    StringMatchContext actualStringMatchContext = new StringMatchContext(parent, 1);

    // Assert
    assertNull(actualStringMatchContext.getStart());
    assertNull(actualStringMatchContext.getStop());
    assertSame(parent, actualStringMatchContext.getParent());
  }

  /**
   * Test StringMatchContext {@link StringMatchContext#nullConditionValue()}.
   * <p>
   * Method under test: {@link StringMatchContext#nullConditionValue()}
   */
  @Test
  @DisplayName("Test StringMatchContext nullConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testStringMatchContextNullConditionValue() {
    // Arrange, Act and Assert
    assertNull((new StringMatchContext(new ParserRuleContext(), 1)).nullConditionValue());
  }

  /**
   * Test StringMatchContext {@link StringMatchContext#stringConditionValue()}.
   * <p>
   * Method under test: {@link StringMatchContext#stringConditionValue()}
   */
  @Test
  @DisplayName("Test StringMatchContext stringConditionValue()")
  @Tag("MaintainedByDiffblue")
  void testStringMatchContextStringConditionValue() {
    // Arrange, Act and Assert
    assertNull((new StringMatchContext(new ParserRuleContext(), 1)).stringConditionValue());
  }

  /**
   * Test {@link OALParser#stringMatch()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] ([249] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringMatch()}
   */
  @Test
  @DisplayName("Test stringMatch(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] ([249] 42))'")
  @Tag("MaintainedByDiffblue")
  void testStringMatch_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs24942() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    StringMatchContext actualStringMatchResult = oalParser.stringMatch();

    // Assert
    Token start = actualStringMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualStringMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([249] 42))", actualStringMatchResult.toStringTree());
    assertEquals("42", actualStringMatchResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(OALParser.NUMBER_LITERAL, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(actualStringMatchResult, recognitionException.getCtx());
    assertSame(start, actualStringMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#stringMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringMatch()}
   */
  @Test
  @DisplayName("Test stringMatch(); then return exception Recognizer ParseListeners size is one")
  @Tag("MaintainedByDiffblue")
  void testStringMatch_thenReturnExceptionRecognizerParseListenersSizeIsOne() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.stringMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(1, parseListeners.size());
    ParseTreeListener getResult = parseListeners.get(0);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.DUALEQUALS},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#stringMatch()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringMatch()}
   */
  @Test
  @DisplayName("Test stringMatch(); then return exception Recognizer ParseListeners size is two")
  @Tag("MaintainedByDiffblue")
  void testStringMatch_thenReturnExceptionRecognizerParseListenersSizeIsTwo() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    OALParserBaseListener listener = new OALParserBaseListener();
    oalParser.addParseListener(listener);

    // Act and Assert
    RecognitionException recognitionException = oalParser.stringMatch().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    List<ParseTreeListener> parseListeners = ((OALParser) recognizer).getParseListeners();
    assertEquals(2, parseListeners.size());
    assertTrue(parseListeners.get(0) instanceof TraceListener);
    assertTrue(recognizer instanceof OALParser);
    ParseTreeListener getResult = parseListeners.get(1);
    assertTrue(getResult instanceof OALParserBaseListener);
    assertTrue(((OALParser) recognizer).isTrace());
    assertSame(listener, getResult);
    assertArrayEquals(new int[]{OALParser.DOT, OALParser.DUALEQUALS},
        recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#stringMatch()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringMatch()}
   */
  @Test
  @DisplayName("Test stringMatch(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testStringMatch_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    StringMatchContext actualStringMatchResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream(""))))).stringMatch();

    // Assert
    assertEquals("", actualStringMatchResult.getText());
    assertEquals("([] [249])", actualStringMatchResult.toStringTree());
    assertNull(actualStringMatchResult.getStop());
    assertEquals(1, actualStringMatchResult.children.size());
  }

  /**
   * Test {@link OALParser#stringMatch()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([249] ([324 249] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringMatch()}
   */
  @Test
  @DisplayName("Test stringMatch(); then return toStringTree is '([] ([249] ([324 249] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testStringMatch_thenReturnToStringTreeIs249324249Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    OALLexer tokenSource = new OALLexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    OALParser oalParser = new OALParser(input2);

    // Act
    StringMatchContext actualStringMatchResult = oalParser.stringMatch();

    // Assert
    Token start = actualStringMatchResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualStringMatchResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([249] ([324 249] Input)))", actualStringMatchResult.toStringTree());
    assertEquals("Input", actualStringMatchResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(329, recognitionException.getOffendingState());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(oalParser, recognitionException.getRecognizer());
    assertSame(start, actualStringMatchResult.getStop());
  }

  /**
   * Test {@link OALParser#stringMatch()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#stringMatch()}
   */
  @Test
  @DisplayName("Test stringMatch(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testStringMatch_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    StringMatchContext actualStringMatchResult = (new OALParser(new BufferedTokenStream(
        new OALLexer(new ANTLRInputStream(new ByteArrayInputStream("A.A.A.A.".getBytes("UTF-8"))))))).stringMatch();

    // Assert
    assertTrue(actualStringMatchResult.getStop() instanceof CommonToken);
    assertEquals("([] ([249] ([324 249] A) . ([326 249] A) . ([326 249] A) . ([326 249] A) . [326 249]))",
        actualStringMatchResult.toStringTree());
    assertEquals("A.A.A.A.", actualStringMatchResult.getText());
    assertEquals(1, actualStringMatchResult.children.size());
  }

  /**
   * Test VariableContext {@link VariableContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link VariableContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test VariableContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testVariableContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(OALLexer.SRC_SERVICE_INSTANCE_RELATION,
        (new VariableContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test VariableContext {@link VariableContext#IDENTIFIER()}.
   * <p>
   * Method under test: {@link VariableContext#IDENTIFIER()}
   */
  @Test
  @DisplayName("Test VariableContext IDENTIFIER()")
  @Tag("MaintainedByDiffblue")
  void testVariableContextIdentifier() {
    // Arrange, Act and Assert
    assertNull((new VariableContext(new ParserRuleContext(), 1)).IDENTIFIER());
  }

  /**
   * Test VariableContext {@link VariableContext#VariableContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link VariableContext#VariableContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test VariableContext new VariableContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testVariableContextNewVariableContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    VariableContext actualVariableContext = new VariableContext(parent, 1);

    // Assert
    assertNull(actualVariableContext.getStart());
    assertNull(actualVariableContext.getStop());
    assertSame(parent, actualVariableContext.getParent());
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then {@link ParserRuleContext#children} first return {@link TerminalNodeImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); given 'A'; then children first return TerminalNodeImpl")
  @Tag("MaintainedByDiffblue")
  void testVariable_givenA_thenChildrenFirstReturnTerminalNodeImpl() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 22, 'A', 22, 'A', 22, 'A', 22}));
    OALLexer tokenSource = new OALLexer(input);

    // Act
    VariableContext actualVariableResult = (new OALParser(new BufferedTokenStream(tokenSource))).variable();

    // Assert
    Token start = actualVariableResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualVariableResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof TerminalNodeImpl);
    assertEquals("([] A)", actualVariableResult.toStringTree());
    assertEquals("A", actualVariableResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualVariableResult.getStop());
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Given {@link OALParser#OALParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); given OALParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testVariable_givenOALParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.setTrace(true);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    VariableContext actualVariableResult = oalParser.variable();

    // Assert
    Token start = actualVariableResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualVariableResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testVariable_thenFourthElementAtnStartStateReturnBasicBlockStartState() throws RecognitionException {
    // Arrange and Act
    VariableContext actualVariableResult = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))))).variable();

    // Assert
    Token start = actualVariableResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualVariableResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Then fourth element {@link DFA#atnStartState} return {@link BasicBlockStartState}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); then fourth element atnStartState return BasicBlockStartState")
  @Tag("MaintainedByDiffblue")
  void testVariable_thenFourthElementAtnStartStateReturnBasicBlockStartState2() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    VariableContext actualVariableResult = oalParser.variable();

    // Assert
    Token start = actualVariableResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    DFA[] dfaArray = ((OALLexer) tokenSource).getInterpreter().decisionToDFA;
    DecisionState decisionState = (dfaArray[3]).atnStartState;
    assertTrue(decisionState instanceof BasicBlockStartState);
    Transition[] transitions = ((BasicBlockStartState) decisionState).endState.getTransitions();
    assertTrue(transitions[0] instanceof EpsilonTransition);
    DecisionState decisionState2 = (dfaArray[4]).atnStartState;
    Transition[] transitions2 = ((StarBlockStartState) decisionState2).endState.getTransitions();
    assertTrue(transitions2[0] instanceof EpsilonTransition);
    assertTrue(decisionState2 instanceof StarBlockStartState);
    DecisionState decisionState3 = (dfaArray[5]).atnStartState;
    assertTrue(decisionState3 instanceof StarLoopEntryState);
    assertTrue(tokenSource instanceof OALLexer);
    assertEquals("([] Input)", actualVariableResult.toStringTree());
    assertEquals(1, transitions.length);
    assertEquals(1, transitions2.length);
    assertEquals(OALParser.RULE_stringMatch, dfaArray.length);
    assertSame(decisionState3, ((StarLoopEntryState) decisionState3).loopBackState.getLoopEntryState());
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#exception} Recognizer ParseListeners Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); then return exception Recognizer ParseListeners Empty")
  @Tag("MaintainedByDiffblue")
  void testVariable_thenReturnExceptionRecognizerParseListenersEmpty() throws RecognitionException {
    // Arrange, Act and Assert
    RecognitionException recognitionException = (new OALParser(
        new BufferedTokenStream(new OALLexer(new ANTLRInputStream("42"))))).variable().exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    Recognizer<?, ?> recognizer = recognitionException.getRecognizer();
    assertTrue(recognizer instanceof OALParser);
    assertTrue(((OALParser) recognizer).getParseListeners().isEmpty());
    assertEquals(OALParser.SRC_BROWSER_APP_PAGE_TRAFFIC, recognizer.getATN().ruleToStartState.length);
    assertArrayEquals(new int[]{OALParser.IDENTIFIER}, recognitionException.getExpectedTokens().toArray());
    assertArrayEquals(
        new int[]{82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105,
            106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126},
        ((OALParser) recognizer).getATNWithBypassAlts().ruleToTokenType);
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([22] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); then return toStringTree is '([22] Input)'")
  @Tag("MaintainedByDiffblue")
  void testVariable_thenReturnToStringTreeIs22Input() throws RecognitionException {
    // Arrange
    OALParser oalParser = new OALParser(new BufferedTokenStream(new OALLexer(new ANTLRInputStream("Input"))));
    ParserRuleContext localctx = new ParserRuleContext();
    oalParser.enterRule(localctx, OALParser.RULE_numberMatch, 1);
    oalParser.addParseListener(new OALParserBaseListener());

    // Act
    VariableContext actualVariableResult = oalParser.variable();

    // Assert
    assertEquals("([22] Input)", actualVariableResult.toStringTree());
    assertEquals(2, actualVariableResult.depth());
    assertFalse(actualVariableResult.isEmpty());
    assertEquals(OALParser.RULE_numberMatch, actualVariableResult.invokingState);
    assertSame(localctx, actualVariableResult.getParent());
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testVariable_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    OALLexer tokenSource = new OALLexer(input);

    // Act
    VariableContext actualVariableResult = (new OALParser(new BufferedTokenStream(tokenSource))).variable();

    // Assert
    Token start = actualVariableResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualVariableResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link OALParser#variable()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OALParser#variable()}
   */
  @Test
  @DisplayName("Test variable(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testVariable_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    OALParser oalParser = new OALParser(new BufferedTokenStream(tokenSource));

    // Act and Assert
    Token start = oalParser.variable().getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertEquals(1, oalParser.getNumberOfSyntaxErrors());
    assertSame(tokenSource, tokenSource2);
  }
}
