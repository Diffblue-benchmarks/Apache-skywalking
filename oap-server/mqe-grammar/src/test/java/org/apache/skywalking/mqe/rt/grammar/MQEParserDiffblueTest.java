package org.apache.skywalking.mqe.rt.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.ListTokenSource;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.DecisionState;
import org.antlr.v4.runtime.atn.EpsilonTransition;
import org.antlr.v4.runtime.atn.TokensStartState;
import org.antlr.v4.runtime.atn.Transition;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ErrorNodeImpl;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.xpath.XPathLexer;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AddSubContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AddSubOpContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AggregateLabelsContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AggregateLabelsFuncContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AggregateLabelsFuncNameContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AggregateLabelsOpContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AggregationContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AggregationOpContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AttributeContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AttributeListContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.AttributeNameContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.BaselineContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.Baseline_typeContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.Bool_operatorContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.CompareContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.ExprNodeContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.ExpressionContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.ExpressionListContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.ExpressionNodeContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.LabelContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.LabelListContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.LabelNameContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.LabelNameListContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.LabelValueContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.Logical_operatorContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.Mathematical_operator0Context;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.Mathematical_operator1Context;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.MetricContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.MetricNameContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.MulDivModContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.OrderContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.ParameterContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.RelabelsContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.ReplaceLabelContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.RootContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.ScalarContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.Sort_label_valuesContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.Sort_valuesContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.TopNContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.TopNOfContext;
import org.apache.skywalking.mqe.rt.grammar.MQEParser.TrendContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MQEParserDiffblueTest {
  /**
   * Test AddSubContext {@link AddSubContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddSubContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AddSubContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAddSubContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AddSubContext addSubContext = new AddSubContext(new ParserRuleContext(), 1);

    // Act and Assert
    assertNull(addSubContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AddSubContext {@link AddSubContext#ADD()}.
   * <p>
   * Method under test: {@link AddSubContext#ADD()}
   */
  @Test
  @DisplayName("Test AddSubContext ADD()")
  @Tag("MaintainedByDiffblue")
  void testAddSubContextAdd() {
    // Arrange, Act and Assert
    assertNull((new AddSubContext(new ParserRuleContext(), 1)).ADD());
  }

  /**
   * Test AddSubContext getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddSubContext#AddSubContext(ParserRuleContext, int)}
   *   <li>{@link AddSubContext#getRuleIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test AddSubContext getters and setters")
  @Tag("MaintainedByDiffblue")
  void testAddSubContextGettersAndSetters() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AddSubContext actualAddSubContext = new AddSubContext(parent, 1);
    int actualRuleIndex = actualAddSubContext.getRuleIndex();

    // Assert
    assertNull(actualAddSubContext.getStart());
    assertNull(actualAddSubContext.getStop());
    assertEquals(4, actualRuleIndex);
    assertSame(parent, actualAddSubContext.getParent());
  }

  /**
   * Test AddSubContext {@link AddSubContext#SUB()}.
   * <p>
   * Method under test: {@link AddSubContext#SUB()}
   */
  @Test
  @DisplayName("Test AddSubContext SUB()")
  @Tag("MaintainedByDiffblue")
  void testAddSubContextSub() {
    // Arrange, Act and Assert
    assertNull((new AddSubContext(new ParserRuleContext(), 1)).SUB());
  }

  /**
   * Test AddSubOpContext {@link AddSubOpContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddSubOpContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AddSubOpContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAddSubOpContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AddSubOpContext addSubOpContext = new AddSubOpContext(new ExpressionContext());

    // Act and Assert
    assertNull(addSubOpContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AddSubOpContext {@link AddSubOpContext#addSub()}.
   * <p>
   * Method under test: {@link AddSubOpContext#addSub()}
   */
  @Test
  @DisplayName("Test AddSubOpContext addSub()")
  @Tag("MaintainedByDiffblue")
  void testAddSubOpContextAddSub() {
    // Arrange, Act and Assert
    assertNull((new AddSubOpContext(new ExpressionContext())).addSub());
  }

  /**
   * Test AddSubOpContext {@link AddSubOpContext#expression()}.
   * <p>
   * Method under test: {@link AddSubOpContext#expression()}
   */
  @Test
  @DisplayName("Test AddSubOpContext expression()")
  @Tag("MaintainedByDiffblue")
  void testAddSubOpContextExpression() {
    // Arrange, Act and Assert
    assertTrue((new AddSubOpContext(new ExpressionContext())).expression().isEmpty());
  }

  /**
   * Test AddSubOpContext {@link AddSubOpContext#expression(int)} with {@code int}.
   * <p>
   * Method under test: {@link AddSubOpContext#expression(int)}
   */
  @Test
  @DisplayName("Test AddSubOpContext expression(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAddSubOpContextExpressionWithInt() {
    // Arrange, Act and Assert
    assertNull((new AddSubOpContext(new ExpressionContext())).expression(1));
  }

  /**
   * Test AddSubOpContext {@link AddSubOpContext#AddSubOpContext(ExpressionContext)}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddSubOpContext#AddSubOpContext(ExpressionContext)}
   */
  @Test
  @DisplayName("Test AddSubOpContext new AddSubOpContext(ExpressionContext); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAddSubOpContextNewAddSubOpContext_thenReturnTextIsEmptyString() {
    // Arrange and Act
    AddSubOpContext actualAddSubOpContext = new AddSubOpContext(new ExpressionContext());

    // Assert
    assertEquals("", actualAddSubOpContext.getText());
    assertEquals("[]", actualAddSubOpContext.toStringTree());
    assertNull(actualAddSubOpContext.children);
    assertNull(actualAddSubOpContext.getParent());
    assertNull(actualAddSubOpContext.exception);
    assertNull(actualAddSubOpContext.getStart());
    assertNull(actualAddSubOpContext.getStop());
    assertEquals(-1, actualAddSubOpContext.invokingState);
    assertEquals(0, actualAddSubOpContext.getChildCount());
    assertEquals(0, actualAddSubOpContext.getAltNumber());
    assertEquals(1, actualAddSubOpContext.depth());
    assertEquals(1, actualAddSubOpContext.getRuleIndex());
    assertTrue(actualAddSubOpContext.expression().isEmpty());
    assertTrue(actualAddSubOpContext.isEmpty());
  }

  /**
   * Test {@link MQEParser#addSub()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#addSub()}
   */
  @Test
  @DisplayName("Test addSub(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testAddSub_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AddSubContext actualAddSubResult = mqeParser.addSub();

    // Assert
    Token start = actualAddSubResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAddSubResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualAddSubResult.toStringTree());
    assertEquals("42", actualAddSubResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAddSubResult, recognitionException.getCtx());
    assertSame(start, actualAddSubResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#addSub()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is empty string.</li>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#addSub()}
   */
  @Test
  @DisplayName("Test addSub(); given ANTLRInputStream(String) with input is empty string; then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testAddSub_givenANTLRInputStreamWithInputIsEmptyString_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).addSub().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#addSub()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#addSub()}
   */
  @Test
  @DisplayName("Test addSub(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testAddSub_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AddSubContext actualAddSubResult = mqeParser.addSub();

    // Assert
    Token start = actualAddSubResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAddSubResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualAddSubResult.toStringTree());
    assertEquals("Input", actualAddSubResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAddSubResult, recognitionException.getCtx());
    assertSame(start, actualAddSubResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#addSub()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#addSub()}
   */
  @Test
  @DisplayName("Test addSub(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testAddSub_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).addSub().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#addSub()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#addSub()}
   */
  @Test
  @DisplayName("Test addSub(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testAddSub_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    AddSubContext actualAddSubResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A\bA\bA\bA\b".getBytes("UTF-8"))))))).addSub();

    // Assert
    assertTrue(actualAddSubResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualAddSubResult.toStringTree());
    assertEquals("AAAA", actualAddSubResult.getText());
    assertEquals(4, actualAddSubResult.children.size());
    assertEquals(4, actualAddSubResult.getChildCount());
  }

  /**
   * Test AggregateLabelsContext {@link AggregateLabelsContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AggregateLabelsContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AggregateLabelsContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AggregateLabelsContext aggregateLabelsContext = new AggregateLabelsContext(new ParserRuleContext(), 1);

    // Act and Assert
    assertNull(aggregateLabelsContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AggregateLabelsContext {@link AggregateLabelsContext#AGGREGATE_LABELS()}.
   * <p>
   * Method under test: {@link AggregateLabelsContext#AGGREGATE_LABELS()}
   */
  @Test
  @DisplayName("Test AggregateLabelsContext AGGREGATE_LABELS()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsContextAggregate_labels() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsContext(new ParserRuleContext(), 1)).AGGREGATE_LABELS());
  }

  /**
   * Test AggregateLabelsContext {@link AggregateLabelsContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AggregateLabelsContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AggregateLabelsContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(MQELexer.LATEST, (new AggregateLabelsContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AggregateLabelsContext {@link AggregateLabelsContext#AggregateLabelsContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AggregateLabelsContext#AggregateLabelsContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AggregateLabelsContext new AggregateLabelsContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsContextNewAggregateLabelsContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AggregateLabelsContext actualAggregateLabelsContext = new AggregateLabelsContext(parent, 1);

    // Assert
    assertNull(actualAggregateLabelsContext.getStart());
    assertNull(actualAggregateLabelsContext.getStop());
    assertSame(parent, actualAggregateLabelsContext.getParent());
  }

  /**
   * Test AggregateLabelsFuncContext {@link AggregateLabelsFuncContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AggregateLabelsFuncContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AggregateLabelsFuncContext aggregateLabelsFuncContext = new AggregateLabelsFuncContext(new ParserRuleContext(), 1);

    // Act and Assert
    assertNull(aggregateLabelsFuncContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AggregateLabelsFuncContext {@link AggregateLabelsFuncContext#aggregateLabelsFuncName()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncContext#aggregateLabelsFuncName()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncContext aggregateLabelsFuncName()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncContextAggregateLabelsFuncName() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncContext(new ParserRuleContext(), 1)).aggregateLabelsFuncName());
  }

  /**
   * Test AggregateLabelsFuncContext {@link AggregateLabelsFuncContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(MQELexer.MAX, (new AggregateLabelsFuncContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AggregateLabelsFuncContext {@link AggregateLabelsFuncContext#L_PAREN()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncContext#L_PAREN()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncContext L_PAREN()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncContextL_paren() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncContext(new ParserRuleContext(), 1)).L_PAREN());
  }

  /**
   * Test AggregateLabelsFuncContext {@link AggregateLabelsFuncContext#labelNameList()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncContext#labelNameList()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncContext labelNameList()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncContextLabelNameList() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncContext(new ParserRuleContext(), 1)).labelNameList());
  }

  /**
   * Test AggregateLabelsFuncContext {@link AggregateLabelsFuncContext#AggregateLabelsFuncContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncContext#AggregateLabelsFuncContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncContext new AggregateLabelsFuncContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncContextNewAggregateLabelsFuncContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AggregateLabelsFuncContext actualAggregateLabelsFuncContext = new AggregateLabelsFuncContext(parent, 1);

    // Assert
    assertNull(actualAggregateLabelsFuncContext.getStart());
    assertNull(actualAggregateLabelsFuncContext.getStop());
    assertSame(parent, actualAggregateLabelsFuncContext.getParent());
  }

  /**
   * Test AggregateLabelsFuncContext {@link AggregateLabelsFuncContext#R_PAREN()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncContext#R_PAREN()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncContext R_PAREN()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncContextR_paren() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncContext(new ParserRuleContext(), 1)).R_PAREN());
  }

  /**
   * Test AggregateLabelsFuncNameContext {@link AggregateLabelsFuncNameContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AggregateLabelsFuncNameContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncNameContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncNameContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AggregateLabelsFuncNameContext aggregateLabelsFuncNameContext = new AggregateLabelsFuncNameContext(
        new ParserRuleContext(), 1);

    // Act and Assert
    assertNull(aggregateLabelsFuncNameContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AggregateLabelsFuncNameContext {@link AggregateLabelsFuncNameContext#AVG()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncNameContext#AVG()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncNameContext AVG()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncNameContextAvg() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncNameContext(new ParserRuleContext(), 1)).AVG());
  }

  /**
   * Test AggregateLabelsFuncNameContext {@link AggregateLabelsFuncNameContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncNameContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncNameContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncNameContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(MQELexer.MIN, (new AggregateLabelsFuncNameContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AggregateLabelsFuncNameContext {@link AggregateLabelsFuncNameContext#MAX()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncNameContext#MAX()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncNameContext MAX()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncNameContextMax() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncNameContext(new ParserRuleContext(), 1)).MAX());
  }

  /**
   * Test AggregateLabelsFuncNameContext {@link AggregateLabelsFuncNameContext#MIN()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncNameContext#MIN()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncNameContext MIN()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncNameContextMin() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncNameContext(new ParserRuleContext(), 1)).MIN());
  }

  /**
   * Test AggregateLabelsFuncNameContext {@link AggregateLabelsFuncNameContext#AggregateLabelsFuncNameContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncNameContext#AggregateLabelsFuncNameContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncNameContext new AggregateLabelsFuncNameContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncNameContextNewAggregateLabelsFuncNameContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AggregateLabelsFuncNameContext actualAggregateLabelsFuncNameContext = new AggregateLabelsFuncNameContext(parent, 1);

    // Assert
    assertNull(actualAggregateLabelsFuncNameContext.getStart());
    assertNull(actualAggregateLabelsFuncNameContext.getStop());
    assertSame(parent, actualAggregateLabelsFuncNameContext.getParent());
  }

  /**
   * Test AggregateLabelsFuncNameContext {@link AggregateLabelsFuncNameContext#SUM()}.
   * <p>
   * Method under test: {@link AggregateLabelsFuncNameContext#SUM()}
   */
  @Test
  @DisplayName("Test AggregateLabelsFuncNameContext SUM()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncNameContextSum() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsFuncNameContext(new ParserRuleContext(), 1)).SUM());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFuncName()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFuncName()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFuncName(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncName_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).aggregateLabelsFuncName().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFuncName()}.
   * <ul>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFuncName()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFuncName(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncName_thenReturnStartTextIsEof2() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).aggregateLabelsFuncName().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFuncName()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFuncName()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFuncName(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncName_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AggregateLabelsFuncNameContext actualAggregateLabelsFuncNameResult = mqeParser.aggregateLabelsFuncName();

    // Assert
    Token start = actualAggregateLabelsFuncNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregateLabelsFuncNameResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualAggregateLabelsFuncNameResult.toStringTree());
    assertEquals("42", actualAggregateLabelsFuncNameResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregateLabelsFuncNameResult, recognitionException.getCtx());
    assertSame(start, actualAggregateLabelsFuncNameResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFuncName()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFuncName()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFuncName(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncName_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AggregateLabelsFuncNameContext actualAggregateLabelsFuncNameResult = mqeParser.aggregateLabelsFuncName();

    // Assert
    Token start = actualAggregateLabelsFuncNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregateLabelsFuncNameResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualAggregateLabelsFuncNameResult.toStringTree());
    assertEquals("Input", actualAggregateLabelsFuncNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregateLabelsFuncNameResult, recognitionException.getCtx());
    assertSame(start, actualAggregateLabelsFuncNameResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFuncName()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFuncName()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFuncName(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFuncName_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    AggregateLabelsFuncNameContext actualAggregateLabelsFuncNameResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A:A:A:A:".getBytes("UTF-8")))))))
            .aggregateLabelsFuncName();

    // Assert
    assertTrue(actualAggregateLabelsFuncNameResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualAggregateLabelsFuncNameResult.toStringTree());
    assertEquals("AAAA", actualAggregateLabelsFuncNameResult.getText());
    assertEquals(4, actualAggregateLabelsFuncNameResult.children.size());
    assertEquals(4, actualAggregateLabelsFuncNameResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFunc()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFunc()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFunc(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFunc_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    AggregateLabelsFuncContext actualAggregateLabelsFuncResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .aggregateLabelsFunc();

    // Assert
    Token start = actualAggregateLabelsFuncResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("", actualAggregateLabelsFuncResult.getText());
    assertEquals("([] [297])", actualAggregateLabelsFuncResult.toStringTree());
    assertEquals("<EOF>", start.getText());
    assertNull(actualAggregateLabelsFuncResult.getStop());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    Interval sourceInterval = actualAggregateLabelsFuncResult.getSourceInterval();
    assertEquals(-1, sourceInterval.b);
    assertEquals(0, sourceInterval.length());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFunc()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([297] A8A8A8A8))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFunc()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFunc(); then return toStringTree is '([] ([297] A8A8A8A8))'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFunc_thenReturnToStringTreeIs297A8a8a8a8() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A8A8A8A8".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    AggregateLabelsFuncContext actualAggregateLabelsFuncResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .aggregateLabelsFunc();

    // Assert
    Token start = actualAggregateLabelsFuncResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([297] A8A8A8A8))", actualAggregateLabelsFuncResult.toStringTree());
    assertEquals("A8A8A8A8", actualAggregateLabelsFuncResult.getText());
    assertEquals("A8A8A8A8", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAggregateLabelsFuncResult.getStop());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFunc()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([297] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFunc()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFunc(); then return toStringTree is '([] ([297] Input))'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFunc_thenReturnToStringTreeIs297Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    AggregateLabelsFuncContext actualAggregateLabelsFuncResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .aggregateLabelsFunc();

    // Assert
    Token start = actualAggregateLabelsFuncResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([297] Input))", actualAggregateLabelsFuncResult.toStringTree());
    assertEquals("Input", actualAggregateLabelsFuncResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAggregateLabelsFuncResult.getStop());
  }

  /**
   * Test {@link MQEParser#aggregateLabelsFunc()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([297] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabelsFunc()}
   */
  @Test
  @DisplayName("Test aggregateLabelsFunc(); then return toStringTree is '([] ([297] 42))'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsFunc_thenReturnToStringTreeIs29742() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    AggregateLabelsFuncContext actualAggregateLabelsFuncResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .aggregateLabelsFunc();

    // Assert
    Token start = actualAggregateLabelsFuncResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([297] 42))", actualAggregateLabelsFuncResult.toStringTree());
    assertEquals("42", actualAggregateLabelsFuncResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAggregateLabelsFuncResult.getStop());
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AggregateLabelsOpContext aggregateLabelsOpContext = new AggregateLabelsOpContext(new ExpressionContext());

    // Act and Assert
    assertNull(aggregateLabelsOpContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#aggregateLabels()}.
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#aggregateLabels()}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext aggregateLabels()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextAggregateLabels() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsOpContext(new ExpressionContext())).aggregateLabels());
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#aggregateLabelsFunc()}.
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#aggregateLabelsFunc()}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext aggregateLabelsFunc()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextAggregateLabelsFunc() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsOpContext(new ExpressionContext())).aggregateLabelsFunc());
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#COMMA()}.
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#COMMA()}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext COMMA()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextComma() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsOpContext(new ExpressionContext())).COMMA());
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#expression()}.
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#expression()}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext expression()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextExpression() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsOpContext(new ExpressionContext())).expression());
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#L_PAREN()}.
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#L_PAREN()}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext L_PAREN()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextL_paren() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsOpContext(new ExpressionContext())).L_PAREN());
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#AggregateLabelsOpContext(ExpressionContext)}.
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#AggregateLabelsOpContext(ExpressionContext)}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext new AggregateLabelsOpContext(ExpressionContext)")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextNewAggregateLabelsOpContext() {
    // Arrange and Act
    AggregateLabelsOpContext actualAggregateLabelsOpContext = new AggregateLabelsOpContext(new ExpressionContext());

    // Assert
    assertEquals("", actualAggregateLabelsOpContext.getText());
    assertEquals("[]", actualAggregateLabelsOpContext.toStringTree());
    assertNull(actualAggregateLabelsOpContext.children);
    assertNull(actualAggregateLabelsOpContext.getParent());
    assertNull(actualAggregateLabelsOpContext.exception);
    assertNull(actualAggregateLabelsOpContext.getStart());
    assertNull(actualAggregateLabelsOpContext.getStop());
    assertNull(actualAggregateLabelsOpContext.expression());
    assertEquals(-1, actualAggregateLabelsOpContext.invokingState);
    assertEquals(0, actualAggregateLabelsOpContext.getChildCount());
    assertEquals(0, actualAggregateLabelsOpContext.getAltNumber());
    assertEquals(1, actualAggregateLabelsOpContext.depth());
    assertEquals(1, actualAggregateLabelsOpContext.getRuleIndex());
    assertTrue(actualAggregateLabelsOpContext.isEmpty());
  }

  /**
   * Test AggregateLabelsOpContext {@link AggregateLabelsOpContext#R_PAREN()}.
   * <p>
   * Method under test: {@link AggregateLabelsOpContext#R_PAREN()}
   */
  @Test
  @DisplayName("Test AggregateLabelsOpContext R_PAREN()")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabelsOpContextR_paren() {
    // Arrange, Act and Assert
    assertNull((new AggregateLabelsOpContext(new ExpressionContext())).R_PAREN());
  }

  /**
   * Test {@link MQEParser#aggregateLabels()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabels()}
   */
  @Test
  @DisplayName("Test aggregateLabels(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabels_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AggregateLabelsContext actualAggregateLabelsResult = mqeParser.aggregateLabels();

    // Assert
    Token start = actualAggregateLabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregateLabelsResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualAggregateLabelsResult.toStringTree());
    assertEquals("Input", actualAggregateLabelsResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregateLabelsResult, recognitionException.getCtx());
    assertSame(start, actualAggregateLabelsResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregateLabels()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabels()}
   */
  @Test
  @DisplayName("Test aggregateLabels(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabels_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    AggregateLabelsContext actualAggregateLabelsResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .aggregateLabels();

    // Assert
    Token start = actualAggregateLabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualAggregateLabelsResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualAggregateLabelsResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#aggregateLabels()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabels()}
   */
  @Test
  @DisplayName("Test aggregateLabels(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabels_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AggregateLabelsContext actualAggregateLabelsResult = mqeParser.aggregateLabels();

    // Assert
    Token start = actualAggregateLabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregateLabelsResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualAggregateLabelsResult.toStringTree());
    assertEquals("42", actualAggregateLabelsResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregateLabelsResult, recognitionException.getCtx());
    assertSame(start, actualAggregateLabelsResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregateLabels()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] A6A6A6A6)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabels()}
   */
  @Test
  @DisplayName("Test aggregateLabels(); then return toStringTree is '([] A6A6A6A6)'")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabels_thenReturnToStringTreeIsA6a6a6a6() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A6A6A6A6".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AggregateLabelsContext actualAggregateLabelsResult = mqeParser.aggregateLabels();

    // Assert
    Token start = actualAggregateLabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregateLabelsResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] A6A6A6A6)", actualAggregateLabelsResult.toStringTree());
    assertEquals("A6A6A6A6", actualAggregateLabelsResult.getText());
    assertEquals("A6A6A6A6", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregateLabelsResult, recognitionException.getCtx());
    assertSame(start, actualAggregateLabelsResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregateLabels()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregateLabels()}
   */
  @Test
  @DisplayName("Test aggregateLabels(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testAggregateLabels_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).aggregateLabels().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test AggregationContext {@link AggregationContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AggregationContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AggregationContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AggregationContext aggregationContext = new AggregationContext(new ParserRuleContext(), 1);

    // Act and Assert
    assertNull(aggregationContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AggregationContext {@link AggregationContext#AVG()}.
   * <p>
   * Method under test: {@link AggregationContext#AVG()}
   */
  @Test
  @DisplayName("Test AggregationContext AVG()")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextAvg() {
    // Arrange, Act and Assert
    assertNull((new AggregationContext(new ParserRuleContext(), 1)).AVG());
  }

  /**
   * Test AggregationContext {@link AggregationContext#COUNT()}.
   * <p>
   * Method under test: {@link AggregationContext#COUNT()}
   */
  @Test
  @DisplayName("Test AggregationContext COUNT()")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextCount() {
    // Arrange, Act and Assert
    assertNull((new AggregationContext(new ParserRuleContext(), 1)).COUNT());
  }

  /**
   * Test AggregationContext {@link AggregationContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AggregationContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AggregationContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(MQELexer.MOD, (new AggregationContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AggregationContext {@link AggregationContext#LATEST()}.
   * <p>
   * Method under test: {@link AggregationContext#LATEST()}
   */
  @Test
  @DisplayName("Test AggregationContext LATEST()")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextLatest() {
    // Arrange, Act and Assert
    assertNull((new AggregationContext(new ParserRuleContext(), 1)).LATEST());
  }

  /**
   * Test AggregationContext {@link AggregationContext#MAX()}.
   * <p>
   * Method under test: {@link AggregationContext#MAX()}
   */
  @Test
  @DisplayName("Test AggregationContext MAX()")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextMax() {
    // Arrange, Act and Assert
    assertNull((new AggregationContext(new ParserRuleContext(), 1)).MAX());
  }

  /**
   * Test AggregationContext {@link AggregationContext#MIN()}.
   * <p>
   * Method under test: {@link AggregationContext#MIN()}
   */
  @Test
  @DisplayName("Test AggregationContext MIN()")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextMin() {
    // Arrange, Act and Assert
    assertNull((new AggregationContext(new ParserRuleContext(), 1)).MIN());
  }

  /**
   * Test AggregationContext {@link AggregationContext#AggregationContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AggregationContext#AggregationContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AggregationContext new AggregationContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextNewAggregationContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AggregationContext actualAggregationContext = new AggregationContext(parent, 1);

    // Assert
    assertNull(actualAggregationContext.getStart());
    assertNull(actualAggregationContext.getStop());
    assertSame(parent, actualAggregationContext.getParent());
  }

  /**
   * Test AggregationContext {@link AggregationContext#SUM()}.
   * <p>
   * Method under test: {@link AggregationContext#SUM()}
   */
  @Test
  @DisplayName("Test AggregationContext SUM()")
  @Tag("MaintainedByDiffblue")
  void testAggregationContextSum() {
    // Arrange, Act and Assert
    assertNull((new AggregationContext(new ParserRuleContext(), 1)).SUM());
  }

  /**
   * Test AggregationOpContext {@link AggregationOpContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AggregationOpContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AggregationOpContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAggregationOpContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AggregationOpContext aggregationOpContext = new AggregationOpContext(new ExpressionContext());

    // Act and Assert
    assertNull(aggregationOpContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AggregationOpContext {@link AggregationOpContext#aggregation()}.
   * <p>
   * Method under test: {@link AggregationOpContext#aggregation()}
   */
  @Test
  @DisplayName("Test AggregationOpContext aggregation()")
  @Tag("MaintainedByDiffblue")
  void testAggregationOpContextAggregation() {
    // Arrange, Act and Assert
    assertNull((new AggregationOpContext(new ExpressionContext())).aggregation());
  }

  /**
   * Test AggregationOpContext {@link AggregationOpContext#expression()}.
   * <p>
   * Method under test: {@link AggregationOpContext#expression()}
   */
  @Test
  @DisplayName("Test AggregationOpContext expression()")
  @Tag("MaintainedByDiffblue")
  void testAggregationOpContextExpression() {
    // Arrange, Act and Assert
    assertNull((new AggregationOpContext(new ExpressionContext())).expression());
  }

  /**
   * Test AggregationOpContext {@link AggregationOpContext#L_PAREN()}.
   * <p>
   * Method under test: {@link AggregationOpContext#L_PAREN()}
   */
  @Test
  @DisplayName("Test AggregationOpContext L_PAREN()")
  @Tag("MaintainedByDiffblue")
  void testAggregationOpContextL_paren() {
    // Arrange, Act and Assert
    assertNull((new AggregationOpContext(new ExpressionContext())).L_PAREN());
  }

  /**
   * Test AggregationOpContext {@link AggregationOpContext#AggregationOpContext(ExpressionContext)}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AggregationOpContext#AggregationOpContext(ExpressionContext)}
   */
  @Test
  @DisplayName("Test AggregationOpContext new AggregationOpContext(ExpressionContext); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAggregationOpContextNewAggregationOpContext_thenReturnTextIsEmptyString() {
    // Arrange and Act
    AggregationOpContext actualAggregationOpContext = new AggregationOpContext(new ExpressionContext());

    // Assert
    assertEquals("", actualAggregationOpContext.getText());
    assertEquals("[]", actualAggregationOpContext.toStringTree());
    assertNull(actualAggregationOpContext.children);
    assertNull(actualAggregationOpContext.getParent());
    assertNull(actualAggregationOpContext.exception);
    assertNull(actualAggregationOpContext.getStart());
    assertNull(actualAggregationOpContext.getStop());
    assertNull(actualAggregationOpContext.aggregation());
    assertNull(actualAggregationOpContext.expression());
    assertEquals(-1, actualAggregationOpContext.invokingState);
    assertEquals(0, actualAggregationOpContext.getChildCount());
    assertEquals(0, actualAggregationOpContext.getAltNumber());
    assertEquals(1, actualAggregationOpContext.depth());
    assertEquals(1, actualAggregationOpContext.getRuleIndex());
    assertTrue(actualAggregationOpContext.isEmpty());
  }

  /**
   * Test AggregationOpContext {@link AggregationOpContext#R_PAREN()}.
   * <p>
   * Method under test: {@link AggregationOpContext#R_PAREN()}
   */
  @Test
  @DisplayName("Test AggregationOpContext R_PAREN()")
  @Tag("MaintainedByDiffblue")
  void testAggregationOpContextR_paren() {
    // Arrange, Act and Assert
    assertNull((new AggregationOpContext(new ExpressionContext())).R_PAREN());
  }

  /**
   * Test {@link MQEParser#aggregation()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregation()}
   */
  @Test
  @DisplayName("Test aggregation(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testAggregation_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AggregationContext actualAggregationResult = mqeParser.aggregation();

    // Assert
    Token start = actualAggregationResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregationResult.exception;
    assertTrue(recognitionException instanceof NoViableAltException);
    assertEquals("([] 42)", actualAggregationResult.toStringTree());
    assertEquals("42", actualAggregationResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregationResult, recognitionException.getCtx());
    assertSame(start, ((NoViableAltException) recognitionException).getStartToken());
    assertSame(start, actualAggregationResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregation()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return Start StopIndex is four.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregation()}
   */
  @Test
  @DisplayName("Test aggregation(); given ANTLRInputStream(String) with 'Input'; then return Start StopIndex is four")
  @Tag("MaintainedByDiffblue")
  void testAggregation_givenANTLRInputStreamWithInput_thenReturnStartStopIndexIsFour() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AggregationContext actualAggregationResult = mqeParser.aggregation();

    // Assert
    Token start = actualAggregationResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregationResult.exception;
    assertTrue(recognitionException instanceof NoViableAltException);
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregationResult, recognitionException.getCtx());
    assertSame(start, ((NoViableAltException) recognitionException).getStartToken());
    assertSame(start, actualAggregationResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregation()}.
   * <ul>
   *   <li>Then return Start TokenSource is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregation()}
   */
  @Test
  @DisplayName("Test aggregation(); then return Start TokenSource is 'null'")
  @Tag("MaintainedByDiffblue")
  void testAggregation_thenReturnStartTokenSourceIsNull() throws RecognitionException {
    // Arrange
    BufferedTokenStream input = new BufferedTokenStream(new XPathLexer(new ANTLRInputStream("Input")));
    MQEParser mqeParser = new MQEParser(input);

    // Act
    AggregationContext actualAggregationResult = mqeParser.aggregation();

    // Assert
    Token start = actualAggregationResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAggregationResult.exception;
    assertTrue(recognitionException instanceof NoViableAltException);
    assertNull(start.getTokenSource());
    assertEquals(1, start.getType());
    assertSame(input, recognitionException.getInputStream());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAggregationResult, recognitionException.getCtx());
    assertSame(start, ((NoViableAltException) recognitionException).getStartToken());
    assertSame(start, actualAggregationResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#aggregation()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregation()}
   */
  @Test
  @DisplayName("Test aggregation(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAggregation_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    AggregationContext actualAggregationResult = (new MQEParser(
        new BufferedTokenStream(new ListTokenSource(new ArrayList<>())))).aggregation();

    // Assert
    assertEquals("", actualAggregationResult.getText());
    assertEquals("[]", actualAggregationResult.toStringTree());
    assertNull(actualAggregationResult.children);
    assertNull(actualAggregationResult.getStop());
    assertEquals(0, actualAggregationResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#aggregation()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#aggregation()}
   */
  @Test
  @DisplayName("Test aggregation(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testAggregation_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    AggregationContext actualAggregationResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A A A A ".getBytes("UTF-8"))))))).aggregation();

    // Assert
    assertTrue(actualAggregationResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualAggregationResult.toStringTree());
    assertEquals("AAAA", actualAggregationResult.getText());
    assertEquals(4, actualAggregationResult.children.size());
    assertEquals(4, actualAggregationResult.getChildCount());
  }

  /**
   * Test AttributeContext {@link AttributeContext#accept(ParseTreeVisitor)}.
   * <ul>
   *   <li>When {@link MQEParserBaseVisitor} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AttributeContext#accept(ParseTreeVisitor)}
   */
  @Test
  @DisplayName("Test AttributeContext accept(ParseTreeVisitor); when MQEParserBaseVisitor (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testAttributeContextAccept_whenMQEParserBaseVisitor_thenReturnNull() {
    // Arrange
    AttributeContext attributeContext = new AttributeContext(new ParserRuleContext(), 1);

    // Act and Assert
    assertNull(attributeContext.accept(new MQEParserBaseVisitor<>()));
  }

  /**
   * Test AttributeContext {@link AttributeContext#attributeName()}.
   * <p>
   * Method under test: {@link AttributeContext#attributeName()}
   */
  @Test
  @DisplayName("Test AttributeContext attributeName()")
  @Tag("MaintainedByDiffblue")
  void testAttributeContextAttributeName() {
    // Arrange, Act and Assert
    assertNull((new AttributeContext(new ParserRuleContext(), 1)).attributeName());
  }

  /**
   * Test AttributeContext {@link AttributeContext#EQ()}.
   * <p>
   * Method under test: {@link AttributeContext#EQ()}
   */
  @Test
  @DisplayName("Test AttributeContext EQ()")
  @Tag("MaintainedByDiffblue")
  void testAttributeContextEq() {
    // Arrange, Act and Assert
    assertNull((new AttributeContext(new ParserRuleContext(), 1)).EQ());
  }

  /**
   * Test AttributeContext {@link AttributeContext#getRuleIndex()}.
   * <p>
   * Method under test: {@link AttributeContext#getRuleIndex()}
   */
  @Test
  @DisplayName("Test AttributeContext getRuleIndex()")
  @Tag("MaintainedByDiffblue")
  void testAttributeContextGetRuleIndex() {
    // Arrange, Act and Assert
    assertEquals(MQELexer.FLOOR, (new AttributeContext(new ParserRuleContext(), 1)).getRuleIndex());
  }

  /**
   * Test AttributeContext {@link AttributeContext#NEQ()}.
   * <p>
   * Method under test: {@link AttributeContext#NEQ()}
   */
  @Test
  @DisplayName("Test AttributeContext NEQ()")
  @Tag("MaintainedByDiffblue")
  void testAttributeContextNeq() {
    // Arrange, Act and Assert
    assertNull((new AttributeContext(new ParserRuleContext(), 1)).NEQ());
  }

  /**
   * Test AttributeContext {@link AttributeContext#AttributeContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AttributeContext#AttributeContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AttributeContext new AttributeContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAttributeContextNewAttributeContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AttributeContext actualAttributeContext = new AttributeContext(parent, 1);

    // Assert
    assertNull(actualAttributeContext.getStart());
    assertNull(actualAttributeContext.getStop());
    assertSame(parent, actualAttributeContext.getParent());
  }

  /**
   * Test AttributeContext {@link AttributeContext#VALUE_STRING()}.
   * <p>
   * Method under test: {@link AttributeContext#VALUE_STRING()}
   */
  @Test
  @DisplayName("Test AttributeContext VALUE_STRING()")
  @Tag("MaintainedByDiffblue")
  void testAttributeContextValue_string() {
    // Arrange, Act and Assert
    assertNull((new AttributeContext(new ParserRuleContext(), 1)).VALUE_STRING());
  }

  /**
   * Test AttributeListContext {@link AttributeListContext#COMMA()}.
   * <p>
   * Method under test: {@link AttributeListContext#COMMA()}
   */
  @Test
  @DisplayName("Test AttributeListContext COMMA()")
  @Tag("MaintainedByDiffblue")
  void testAttributeListContextComma() {
    // Arrange, Act and Assert
    assertTrue((new AttributeListContext(new ParserRuleContext(), 1)).COMMA().isEmpty());
  }

  /**
   * Test AttributeListContext {@link AttributeListContext#COMMA(int)} with {@code int}.
   * <p>
   * Method under test: {@link AttributeListContext#COMMA(int)}
   */
  @Test
  @DisplayName("Test AttributeListContext COMMA(int) with 'int'")
  @Tag("MaintainedByDiffblue")
  void testAttributeListContextCommaWithInt() {
    // Arrange, Act and Assert
    assertNull((new AttributeListContext(new ParserRuleContext(), 1)).COMMA(1));
  }

  /**
   * Test AttributeListContext {@link AttributeListContext#AttributeListContext(ParserRuleContext, int)}.
   * <p>
   * Method under test: {@link AttributeListContext#AttributeListContext(ParserRuleContext, int)}
   */
  @Test
  @DisplayName("Test AttributeListContext new AttributeListContext(ParserRuleContext, int)")
  @Tag("MaintainedByDiffblue")
  void testAttributeListContextNewAttributeListContext() {
    // Arrange
    ParserRuleContext parent = new ParserRuleContext();

    // Act
    AttributeListContext actualAttributeListContext = new AttributeListContext(parent, 1);

    // Assert
    assertNull(actualAttributeListContext.getStart());
    assertNull(actualAttributeListContext.getStop());
    assertSame(parent, actualAttributeListContext.getParent());
  }

  /**
   * Test {@link MQEParser#attributeList()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeList()}
   */
  @Test
  @DisplayName("Test attributeList(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAttributeList_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    AttributeListContext actualAttributeListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .attributeList();

    // Assert
    Token start = actualAttributeListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("", actualAttributeListResult.getText());
    assertEquals("([] ([316] [312 316]))", actualAttributeListResult.toStringTree());
    assertEquals("<EOF>", start.getText());
    assertNull(actualAttributeListResult.getStop());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    Interval sourceInterval = actualAttributeListResult.getSourceInterval();
    assertEquals(-1, sourceInterval.b);
    assertEquals(0, sourceInterval.length());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#attributeList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([316] ([312 316] ADADADAD)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeList()}
   */
  @Test
  @DisplayName("Test attributeList(); then return toStringTree is '([] ([316] ([312 316] ADADADAD)))'")
  @Tag("MaintainedByDiffblue")
  void testAttributeList_thenReturnToStringTreeIs316312316Adadadad() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("ADADADAD".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    AttributeListContext actualAttributeListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .attributeList();

    // Assert
    Token start = actualAttributeListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([316] ([312 316] ADADADAD)))", actualAttributeListResult.toStringTree());
    assertEquals("ADADADAD", actualAttributeListResult.getText());
    assertEquals("ADADADAD", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeListResult.getStop());
  }

  /**
   * Test {@link MQEParser#attributeList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([316] ([312 316] 42)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeList()}
   */
  @Test
  @DisplayName("Test attributeList(); then return toStringTree is '([] ([316] ([312 316] 42)))'")
  @Tag("MaintainedByDiffblue")
  void testAttributeList_thenReturnToStringTreeIs31631231642() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    AttributeListContext actualAttributeListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .attributeList();

    // Assert
    Token start = actualAttributeListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([316] ([312 316] 42)))", actualAttributeListResult.toStringTree());
    assertEquals("42", actualAttributeListResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualAttributeListResult.getStop());
  }

  /**
   * Test {@link MQEParser#attributeName()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeName()}
   */
  @Test
  @DisplayName("Test attributeName(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeName_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AttributeNameContext actualAttributeNameResult = mqeParser.attributeName();

    // Assert
    Token start = actualAttributeNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAttributeNameResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualAttributeNameResult.toStringTree());
    assertEquals("42", actualAttributeNameResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAttributeNameResult, recognitionException.getCtx());
    assertSame(start, actualAttributeNameResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#attributeName()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeName()}
   */
  @Test
  @DisplayName("Test attributeName(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testAttributeName_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AttributeNameContext actualAttributeNameResult = mqeParser.attributeName();

    // Assert
    Token start = actualAttributeNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAttributeNameResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualAttributeNameResult.toStringTree());
    assertEquals("Input", actualAttributeNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAttributeNameResult, recognitionException.getCtx());
    assertSame(start, actualAttributeNameResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#attributeName()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeName()}
   */
  @Test
  @DisplayName("Test attributeName(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testAttributeName_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).attributeName().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#attributeName()}.
   * <ul>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeName()}
   */
  @Test
  @DisplayName("Test attributeName(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testAttributeName_thenReturnStartTextIsEof2() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).attributeName().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#attributeName()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attributeName()}
   */
  @Test
  @DisplayName("Test attributeName(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testAttributeName_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    AttributeNameContext actualAttributeNameResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A@A@A@A@".getBytes("UTF-8"))))))).attributeName();

    // Assert
    assertTrue(actualAttributeNameResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualAttributeNameResult.toStringTree());
    assertEquals("AAAA", actualAttributeNameResult.getText());
    assertEquals(4, actualAttributeNameResult.children.size());
    assertEquals(4, actualAttributeNameResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#attribute()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] ([312] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attribute()}
   */
  @Test
  @DisplayName("Test attribute(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] ([312] 42))'")
  @Tag("MaintainedByDiffblue")
  void testAttribute_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs31242() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AttributeContext actualAttributeResult = mqeParser.attribute();

    // Assert
    Token start = actualAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([312] 42))", actualAttributeResult.toStringTree());
    assertEquals("42", actualAttributeResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAttributeResult, recognitionException.getCtx());
    assertSame(start, actualAttributeResult.getStop());
  }

  /**
   * Test {@link MQEParser#attribute()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attribute()}
   */
  @Test
  @DisplayName("Test attribute(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testAttribute_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange and Act
    AttributeContext actualAttributeResult = (new MQEParser(
        new BufferedTokenStream(new MQELexer(new ANTLRInputStream(""))))).attribute();

    // Assert
    assertTrue(actualAttributeResult.getStart() instanceof CommonToken);
    assertEquals("", actualAttributeResult.getText());
    assertEquals("([] [312])", actualAttributeResult.toStringTree());
    assertNull(actualAttributeResult.getStop());
  }

  /**
   * Test {@link MQEParser#attribute()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([312] ABABABAB))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#attribute()}
   */
  @Test
  @DisplayName("Test attribute(); then return toStringTree is '([] ([312] ABABABAB))'")
  @Tag("MaintainedByDiffblue")
  void testAttribute_thenReturnToStringTreeIs312Abababab() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("ABABABAB".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    AttributeContext actualAttributeResult = mqeParser.attribute();

    // Assert
    Token start = actualAttributeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualAttributeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([312] ABABABAB))", actualAttributeResult.toStringTree());
    assertEquals("ABABABAB", actualAttributeResult.getText());
    assertEquals("ABABABAB", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualAttributeResult, recognitionException.getCtx());
    assertSame(start, actualAttributeResult.getStop());
  }

  /**
   * Test {@link MQEParser#baseline()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline()}
   */
  @Test
  @DisplayName("Test baseline(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    BaselineContext actualBaselineResult = mqeParser.baseline();

    // Assert
    Token start = actualBaselineResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBaselineResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualBaselineResult.toStringTree());
    assertEquals("42", actualBaselineResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBaselineResult, recognitionException.getCtx());
    assertSame(start, actualBaselineResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#baseline()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline()}
   */
  @Test
  @DisplayName("Test baseline(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    BaselineContext actualBaselineResult = mqeParser.baseline();

    // Assert
    Token start = actualBaselineResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBaselineResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualBaselineResult.toStringTree());
    assertEquals("Input", actualBaselineResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBaselineResult, recognitionException.getCtx());
    assertSame(start, actualBaselineResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#baseline()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline()}
   */
  @Test
  @DisplayName("Test baseline(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testBaseline_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    BaselineContext actualBaselineResult = (new MQEParser(new BufferedTokenStream(tokenSource))).baseline();

    // Assert
    Token start = actualBaselineResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualBaselineResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualBaselineResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#baseline()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] AFAFAFAF)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline()}
   */
  @Test
  @DisplayName("Test baseline(); then return toStringTree is '([] AFAFAFAF)'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_thenReturnToStringTreeIsAfafafaf() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("AFAFAFAF".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    BaselineContext actualBaselineResult = mqeParser.baseline();

    // Assert
    Token start = actualBaselineResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBaselineResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] AFAFAFAF)", actualBaselineResult.toStringTree());
    assertEquals("AFAFAFAF", actualBaselineResult.getText());
    assertEquals("AFAFAFAF", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBaselineResult, recognitionException.getCtx());
    assertSame(start, actualBaselineResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#baseline()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline()}
   */
  @Test
  @DisplayName("Test baseline(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testBaseline_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).baseline().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link MQEParser#baseline_type()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline_type()}
   */
  @Test
  @DisplayName("Test baseline_type(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_type_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Baseline_typeContext actualBaseline_typeResult = mqeParser.baseline_type();

    // Assert
    Token start = actualBaseline_typeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBaseline_typeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualBaseline_typeResult.toStringTree());
    assertEquals("42", actualBaseline_typeResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBaseline_typeResult, recognitionException.getCtx());
    assertSame(start, actualBaseline_typeResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#baseline_type()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline_type()}
   */
  @Test
  @DisplayName("Test baseline_type(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_type_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Baseline_typeContext actualBaseline_typeResult = mqeParser.baseline_type();

    // Assert
    Token start = actualBaseline_typeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBaseline_typeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualBaseline_typeResult.toStringTree());
    assertEquals("Input", actualBaseline_typeResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBaseline_typeResult, recognitionException.getCtx());
    assertSame(start, actualBaseline_typeResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#baseline_type()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline_type()}
   */
  @Test
  @DisplayName("Test baseline_type(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_type_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).baseline_type().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#baseline_type()}.
   * <ul>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline_type()}
   */
  @Test
  @DisplayName("Test baseline_type(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_type_thenReturnStartTextIsEof2() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).baseline_type().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#baseline_type()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] AHAHAHAH)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#baseline_type()}
   */
  @Test
  @DisplayName("Test baseline_type(); then return toStringTree is '([] AHAHAHAH)'")
  @Tag("MaintainedByDiffblue")
  void testBaseline_type_thenReturnToStringTreeIsAhahahah() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("AHAHAHAH".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Baseline_typeContext actualBaseline_typeResult = mqeParser.baseline_type();

    // Assert
    Token start = actualBaseline_typeResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBaseline_typeResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] AHAHAHAH)", actualBaseline_typeResult.toStringTree());
    assertEquals("AHAHAHAH", actualBaseline_typeResult.getText());
    assertEquals("AHAHAHAH", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBaseline_typeResult, recognitionException.getCtx());
    assertSame(start, actualBaseline_typeResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#bool_operator()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#bool_operator()}
   */
  @Test
  @DisplayName("Test bool_operator(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testBool_operator_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Bool_operatorContext actualBool_operatorResult = mqeParser.bool_operator();

    // Assert
    Token start = actualBool_operatorResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBool_operatorResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualBool_operatorResult.toStringTree());
    assertEquals("42", actualBool_operatorResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBool_operatorResult, recognitionException.getCtx());
    assertSame(start, actualBool_operatorResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#bool_operator()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#bool_operator()}
   */
  @Test
  @DisplayName("Test bool_operator(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testBool_operator_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Bool_operatorContext actualBool_operatorResult = mqeParser.bool_operator();

    // Assert
    Token start = actualBool_operatorResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualBool_operatorResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualBool_operatorResult.toStringTree());
    assertEquals("Input", actualBool_operatorResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualBool_operatorResult, recognitionException.getCtx());
    assertSame(start, actualBool_operatorResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#bool_operator()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#bool_operator()}
   */
  @Test
  @DisplayName("Test bool_operator(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testBool_operator_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).bool_operator().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#bool_operator()}.
   * <ul>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#bool_operator()}
   */
  @Test
  @DisplayName("Test bool_operator(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testBool_operator_thenReturnStartTextIsEof2() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).bool_operator().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#bool_operator()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#bool_operator()}
   */
  @Test
  @DisplayName("Test bool_operator(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testBool_operator_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    Bool_operatorContext actualBool_operatorResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A.A.A.A.".getBytes("UTF-8"))))))).bool_operator();

    // Assert
    assertTrue(actualBool_operatorResult.getStop() instanceof CommonToken);
    assertEquals("([] A . A . A . A .)", actualBool_operatorResult.toStringTree());
    assertEquals("A.A.A.A.", actualBool_operatorResult.getText());
    assertEquals(8, actualBool_operatorResult.children.size());
    assertEquals(8, actualBool_operatorResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#compare()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#compare()}
   */
  @Test
  @DisplayName("Test compare(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testCompare_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    CompareContext actualCompareResult = mqeParser.compare();

    // Assert
    Token start = actualCompareResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualCompareResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualCompareResult.toStringTree());
    assertEquals("42", actualCompareResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualCompareResult, recognitionException.getCtx());
    assertSame(start, actualCompareResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#compare()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is empty string.</li>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#compare()}
   */
  @Test
  @DisplayName("Test compare(); given ANTLRInputStream(String) with input is empty string; then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testCompare_givenANTLRInputStreamWithInputIsEmptyString_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).compare().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#compare()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#compare()}
   */
  @Test
  @DisplayName("Test compare(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testCompare_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    CompareContext actualCompareResult = mqeParser.compare();

    // Assert
    Token start = actualCompareResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualCompareResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualCompareResult.toStringTree());
    assertEquals("Input", actualCompareResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualCompareResult, recognitionException.getCtx());
    assertSame(start, actualCompareResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#compare()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#compare()}
   */
  @Test
  @DisplayName("Test compare(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testCompare_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).compare().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#compare()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#compare()}
   */
  @Test
  @DisplayName("Test compare(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testCompare_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    CompareContext actualCompareResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A\fA\fA\fA\f".getBytes("UTF-8"))))))).compare();

    // Assert
    assertTrue(actualCompareResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualCompareResult.toStringTree());
    assertEquals("AAAA", actualCompareResult.getText());
    assertEquals(4, actualCompareResult.children.size());
    assertEquals(4, actualCompareResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#expressionList()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] ([195] ([78 195] ([203 78 195] ([217 203 78 195] A)))))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionList()}
   */
  @Test
  @DisplayName("Test expressionList(); given 'A'; then return toStringTree is '([] ([195] ([78 195] ([203 78 195] ([217 203 78 195] A)))))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionList_givenA_thenReturnToStringTreeIs195781952037819521720378195A()
      throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 4, 'A', 4, 'A', 4, 'A', 4}));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionListContext actualExpressionListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .expressionList();

    // Assert
    Token start = actualExpressionListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([195] ([78 195] ([203 78 195] ([217 203 78 195] A)))))",
        actualExpressionListResult.toStringTree());
    assertEquals("A", actualExpressionListResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertEquals(1, actualExpressionListResult.children.size());
    List<ParseTree> expectedExpressionResult = actualExpressionListResult.children;
    assertEquals(expectedExpressionResult, actualExpressionListResult.expression());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionListResult.getStop());
  }

  /**
   * Test {@link MQEParser#expressionList()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionList()}
   */
  @Test
  @DisplayName("Test expressionList(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testExpressionList_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionListContext actualExpressionListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .expressionList();

    // Assert
    Token start = actualExpressionListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("", actualExpressionListResult.getText());
    assertEquals("([] [195])", actualExpressionListResult.toStringTree());
    assertEquals("<EOF>", start.getText());
    assertNull(actualExpressionListResult.getStop());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    Interval sourceInterval = actualExpressionListResult.getSourceInterval();
    assertEquals(-1, sourceInterval.b);
    assertEquals(0, sourceInterval.length());
    assertEquals(1, actualExpressionListResult.children.size());
    List<ParseTree> expectedExpressionResult = actualExpressionListResult.children;
    assertEquals(expectedExpressionResult, actualExpressionListResult.expression());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#expressionList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([195] ([78 195] ([204 78 195] 42))))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionList()}
   */
  @Test
  @DisplayName("Test expressionList(); then return toStringTree is '([] ([195] ([78 195] ([204 78 195] 42))))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionList_thenReturnToStringTreeIs195781952047819542() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionListContext actualExpressionListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .expressionList();

    // Assert
    Token start = actualExpressionListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([195] ([78 195] ([204 78 195] 42))))", actualExpressionListResult.toStringTree());
    assertEquals("42", actualExpressionListResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, actualExpressionListResult.children.size());
    assertEquals(1, start.getStopIndex());
    List<ParseTree> expectedExpressionResult = actualExpressionListResult.children;
    assertEquals(expectedExpressionResult, actualExpressionListResult.expression());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionListResult.getStop());
  }

  /**
   * Test {@link MQEParser#expressionList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([195] ([78 195] ([203 78 195] ([217 203 78 195] Input)))))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionList()}
   */
  @Test
  @DisplayName("Test expressionList(); then return toStringTree is '([] ([195] ([78 195] ([203 78 195] ([217 203 78 195] Input)))))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionList_thenReturnToStringTreeIs195781952037819521720378195Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionListContext actualExpressionListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .expressionList();

    // Assert
    Token start = actualExpressionListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([195] ([78 195] ([203 78 195] ([217 203 78 195] Input)))))",
        actualExpressionListResult.toStringTree());
    assertEquals("Input", actualExpressionListResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(1, actualExpressionListResult.children.size());
    assertEquals(4, start.getStopIndex());
    List<ParseTree> expectedExpressionResult = actualExpressionListResult.children;
    assertEquals(expectedExpressionResult, actualExpressionListResult.expression());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionListResult.getStop());
  }

  /**
   * Test {@link MQEParser#expressionNode()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return metric toStringTree is {@code ([203] ([217 203] A))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionNode()}
   */
  @Test
  @DisplayName("Test expressionNode(); given 'A'; then return metric toStringTree is '([203] ([217 203] A))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionNode_givenA_thenReturnMetricToStringTreeIs203217203A() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 6, 'A', 6, 'A', 6, 'A', 6}));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionNodeContext actualExpressionNodeResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .expressionNode();

    // Assert
    Token start = actualExpressionNodeResult.getStart();
    assertTrue(start instanceof CommonToken);
    MetricContext metricResult = actualExpressionNodeResult.metric();
    assertEquals("([203] ([217 203] A))", metricResult.toStringTree());
    assertEquals("([] ([203] ([217 203] A)))", actualExpressionNodeResult.toStringTree());
    assertEquals("A", actualExpressionNodeResult.getText());
    assertEquals("A", metricResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    List<ParseTree> parseTreeList = actualExpressionNodeResult.children;
    assertEquals(1, parseTreeList.size());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualExpressionNodeResult, metricResult.getParent());
    assertSame(metricResult, parseTreeList.get(0));
    assertSame(start, metricResult.getStart());
    assertSame(start, actualExpressionNodeResult.getStop());
    assertSame(start, metricResult.getStop());
  }

  /**
   * Test {@link MQEParser#expressionNode()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} return {@link InputMismatchException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionNode()}
   */
  @Test
  @DisplayName("Test expressionNode(); then exception return InputMismatchException")
  @Tag("MaintainedByDiffblue")
  void testExpressionNode_thenExceptionReturnInputMismatchException() throws RecognitionException {
    // Arrange and Act
    ExpressionNodeContext actualExpressionNodeResult = (new MQEParser(
        new BufferedTokenStream(new MQELexer(new ANTLRInputStream(""))))).expressionNode();

    // Assert
    assertTrue(actualExpressionNodeResult.exception instanceof InputMismatchException);
    assertEquals("", actualExpressionNodeResult.getText());
    assertEquals("[]", actualExpressionNodeResult.toStringTree());
    assertNull(actualExpressionNodeResult.children);
    assertNull(actualExpressionNodeResult.getStop());
    assertEquals(0, actualExpressionNodeResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#expressionNode()}.
   * <ul>
   *   <li>Then return metric toStringTree is {@code ([203] ([217 203] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionNode()}
   */
  @Test
  @DisplayName("Test expressionNode(); then return metric toStringTree is '([203] ([217 203] Input))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionNode_thenReturnMetricToStringTreeIs203217203Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionNodeContext actualExpressionNodeResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .expressionNode();

    // Assert
    Token start = actualExpressionNodeResult.getStart();
    assertTrue(start instanceof CommonToken);
    MetricContext metricResult = actualExpressionNodeResult.metric();
    assertEquals("([203] ([217 203] Input))", metricResult.toStringTree());
    assertEquals("([] ([203] ([217 203] Input)))", actualExpressionNodeResult.toStringTree());
    assertEquals("Input", actualExpressionNodeResult.getText());
    assertEquals("Input", metricResult.getText());
    assertEquals("Input", start.getText());
    List<ParseTree> parseTreeList = actualExpressionNodeResult.children;
    assertEquals(1, parseTreeList.size());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualExpressionNodeResult, metricResult.getParent());
    assertSame(metricResult, parseTreeList.get(0));
    assertSame(start, metricResult.getStart());
    assertSame(start, actualExpressionNodeResult.getStop());
    assertSame(start, metricResult.getStop());
  }

  /**
   * Test {@link MQEParser#expressionNode()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([204] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expressionNode()}
   */
  @Test
  @DisplayName("Test expressionNode(); then return toStringTree is '([] ([204] 42))'")
  @Tag("MaintainedByDiffblue")
  void testExpressionNode_thenReturnToStringTreeIs20442() throws RecognitionException {
    // Arrange and Act
    ExpressionNodeContext actualExpressionNodeResult = (new MQEParser(
        new BufferedTokenStream(new MQELexer(new ANTLRInputStream("42"))))).expressionNode();

    // Assert
    assertEquals("([] ([204] 42))", actualExpressionNodeResult.toStringTree());
    assertEquals("42", actualExpressionNodeResult.getText());
    assertEquals(1, actualExpressionNodeResult.children.size());
  }

  /**
   * Test {@link MQEParser#expression()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] ([78] ([203 78] ([217 203 78] A))))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); given 'A'; then return toStringTree is '([] ([78] ([203 78] ([217 203 78] A))))'")
  @Tag("MaintainedByDiffblue")
  void testExpression_givenA_thenReturnToStringTreeIs782037821720378A() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 2, 'A', 2, 'A', 2, 'A', 2}));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionContext actualExpressionResult = (new MQEParser(new BufferedTokenStream(tokenSource))).expression();

    // Assert
    Token start = actualExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(actualExpressionResult instanceof ExprNodeContext);
    assertEquals("([] ([78] ([203 78] ([217 203 78] A))))", actualExpressionResult.toStringTree());
    assertEquals("A", actualExpressionResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionResult.getStop());
  }

  /**
   * Test {@link MQEParser#expression()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} return {@link InputMismatchException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then exception return InputMismatchException")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenExceptionReturnInputMismatchException() throws RecognitionException {
    // Arrange and Act
    ExpressionContext actualExpressionResult = (new MQEParser(
        new BufferedTokenStream(new MQELexer(new ANTLRInputStream(""))))).expression();

    // Assert
    assertTrue(actualExpressionResult.exception instanceof InputMismatchException);
    assertEquals("", actualExpressionResult.getText());
    assertEquals("[]", actualExpressionResult.toStringTree());
    assertNull(actualExpressionResult.children);
    assertNull(actualExpressionResult.getStop());
    assertEquals(0, actualExpressionResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#expression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([78] ([204 78] 42)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then return toStringTree is '([] ([78] ([204 78] 42)))'")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenReturnToStringTreeIs782047842() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionContext actualExpressionResult = (new MQEParser(new BufferedTokenStream(tokenSource))).expression();

    // Assert
    Token start = actualExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(actualExpressionResult instanceof ExprNodeContext);
    assertEquals("([] ([78] ([204 78] 42)))", actualExpressionResult.toStringTree());
    assertEquals("42", actualExpressionResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionResult.getStop());
  }

  /**
   * Test {@link MQEParser#expression()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([78] ([203 78] ([217 203 78] Input))))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#expression()}
   */
  @Test
  @DisplayName("Test expression(); then return toStringTree is '([] ([78] ([203 78] ([217 203 78] Input))))'")
  @Tag("MaintainedByDiffblue")
  void testExpression_thenReturnToStringTreeIs782037821720378Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ExpressionContext actualExpressionResult = (new MQEParser(new BufferedTokenStream(tokenSource))).expression();

    // Assert
    Token start = actualExpressionResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(actualExpressionResult instanceof ExprNodeContext);
    assertEquals("([] ([78] ([203 78] ([217 203 78] Input))))", actualExpressionResult.toStringTree());
    assertEquals("Input", actualExpressionResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualExpressionResult.getStop());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MQEParser#getATN()}
   *   <li>{@link MQEParser#getGrammarFileName()}
   *   <li>{@link MQEParser#getRuleNames()}
   *   <li>{@link MQEParser#getSerializedATN()}
   *   <li>{@link MQEParser#getTokenNames()}
   *   <li>{@link MQEParser#getVocabulary()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  void testGettersAndSetters() {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));

    // Act
    ATN actualATN = mqeParser.getATN();
    String actualGrammarFileName = mqeParser.getGrammarFileName();
    String[] actualRuleNames = mqeParser.getRuleNames();
    String actualSerializedATN = mqeParser.getSerializedATN();
    String[] actualTokenNames = mqeParser.getTokenNames();

    // Assert
    assertTrue(mqeParser.getVocabulary() instanceof VocabularyImpl);
    assertEquals("java-escape", actualGrammarFileName);
    assertEquals(MQEParser._serializedATN, actualSerializedATN);
    assertSame(mqeParser._ATN, actualATN);
    assertSame(mqeParser.ruleNames, actualRuleNames);
    assertSame(mqeParser.tokenNames, actualTokenNames);
  }

  /**
   * Test {@link MQEParser#labelList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([243] ([239 243] Input)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelList()}
   */
  @Test
  @DisplayName("Test labelList(); then return toStringTree is '([] ([243] ([239 243] Input)))'")
  @Tag("MaintainedByDiffblue")
  void testLabelList_thenReturnToStringTreeIs243239243Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    LabelListContext actualLabelListResult = (new MQEParser(new BufferedTokenStream(tokenSource))).labelList();

    // Assert
    Token start = actualLabelListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([243] ([239 243] Input)))", actualLabelListResult.toStringTree());
    assertEquals("Input", actualLabelListResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(1, actualLabelListResult.children.size());
    assertEquals(4, start.getStopIndex());
    List<ParseTree> expectedLabelResult = actualLabelListResult.children;
    assertEquals(expectedLabelResult, actualLabelListResult.label());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelListResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([243] ([239 243] 42)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelList()}
   */
  @Test
  @DisplayName("Test labelList(); then return toStringTree is '([] ([243] ([239 243] 42)))'")
  @Tag("MaintainedByDiffblue")
  void testLabelList_thenReturnToStringTreeIs24323924342() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    LabelListContext actualLabelListResult = (new MQEParser(new BufferedTokenStream(tokenSource))).labelList();

    // Assert
    Token start = actualLabelListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([243] ([239 243] 42)))", actualLabelListResult.toStringTree());
    assertEquals("42", actualLabelListResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, actualLabelListResult.children.size());
    assertEquals(1, start.getStopIndex());
    List<ParseTree> expectedLabelResult = actualLabelListResult.children;
    assertEquals(expectedLabelResult, actualLabelListResult.label());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelListResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelList()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelList()}
   */
  @Test
  @DisplayName("Test labelList(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testLabelList_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    LabelListContext actualLabelListResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 26, 'A', 26, 'A', 26, 'A', 26}))))))
            .labelList();

    // Assert
    assertTrue(actualLabelListResult.getStop() instanceof CommonToken);
    assertEquals("([] ([243] ([239 243] A) A A A))", actualLabelListResult.toStringTree());
    assertEquals("AAAA", actualLabelListResult.getText());
    List<ParseTree> expectedLabelResult = actualLabelListResult.children;
    assertEquals(expectedLabelResult, actualLabelListResult.label());
  }

  /**
   * Test {@link MQEParser#labelNameList()}.
   * <ul>
   *   <li>Then return Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelNameList()}
   */
  @Test
  @DisplayName("Test labelNameList(); then return Text is empty string")
  @Tag("MaintainedByDiffblue")
  void testLabelNameList_thenReturnTextIsEmptyString() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    LabelNameListContext actualLabelNameListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .labelNameList();

    // Assert
    Token start = actualLabelNameListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("", actualLabelNameListResult.getText());
    assertEquals("([] [229])", actualLabelNameListResult.toStringTree());
    assertEquals("<EOF>", start.getText());
    assertNull(actualLabelNameListResult.getStop());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, start.getType());
    Interval sourceInterval = actualLabelNameListResult.getSourceInterval();
    assertEquals(-1, sourceInterval.b);
    assertEquals(0, sourceInterval.length());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#labelNameList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([229] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelNameList()}
   */
  @Test
  @DisplayName("Test labelNameList(); then return toStringTree is '([] ([229] Input))'")
  @Tag("MaintainedByDiffblue")
  void testLabelNameList_thenReturnToStringTreeIs229Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    LabelNameListContext actualLabelNameListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .labelNameList();

    // Assert
    Token start = actualLabelNameListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([229] Input))", actualLabelNameListResult.toStringTree());
    assertEquals("Input", actualLabelNameListResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertEquals(MQEParser.NAME_STRING, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelNameListResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelNameList()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([229] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelNameList()}
   */
  @Test
  @DisplayName("Test labelNameList(); then return toStringTree is '([] ([229] 42))'")
  @Tag("MaintainedByDiffblue")
  void testLabelNameList_thenReturnToStringTreeIs22942() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    LabelNameListContext actualLabelNameListResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .labelNameList();

    // Assert
    Token start = actualLabelNameListResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([229] 42))", actualLabelNameListResult.toStringTree());
    assertEquals("42", actualLabelNameListResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelNameListResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelName()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelName()}
   */
  @Test
  @DisplayName("Test labelName(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testLabelName_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    LabelNameContext actualLabelNameResult = (new MQEParser(new BufferedTokenStream(tokenSource))).labelName();

    // Assert
    Token start = actualLabelNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualLabelNameResult.toStringTree());
    assertEquals("Input", actualLabelNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelName()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelName()}
   */
  @Test
  @DisplayName("Test labelName(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testLabelName_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput2() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(tokenSource));
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    LabelNameContext actualLabelNameResult = mqeParser.labelName();

    // Assert
    Token start = actualLabelNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualLabelNameResult.toStringTree());
    assertEquals("Input", actualLabelNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelName()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] A)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelName()}
   */
  @Test
  @DisplayName("Test labelName(); given 'A'; then return toStringTree is '([] A)'")
  @Tag("MaintainedByDiffblue")
  void testLabelName_givenA_thenReturnToStringTreeIsA() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 18, 'A', 18, 'A', 18, 'A', 18}));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    LabelNameContext actualLabelNameResult = (new MQEParser(new BufferedTokenStream(tokenSource))).labelName();

    // Assert
    Token start = actualLabelNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] A)", actualLabelNameResult.toStringTree());
    assertEquals("A", actualLabelNameResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelName()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelName()}
   */
  @Test
  @DisplayName("Test labelName(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testLabelName_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).labelName().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#labelName()}.
   * <ul>
   *   <li>Given {@link MQEParser#MQEParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelName()}
   */
  @Test
  @DisplayName("Test labelName(); given MQEParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testLabelName_givenMQEParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(tokenSource));
    mqeParser.setTrace(true);
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    LabelNameContext actualLabelNameResult = mqeParser.labelName();

    // Assert
    Token start = actualLabelNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualLabelNameResult.toStringTree());
    assertEquals("Input", actualLabelNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualLabelNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#labelName()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelName()}
   */
  @Test
  @DisplayName("Test labelName(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testLabelName_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).labelName().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#labelName()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([18] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelName()}
   */
  @Test
  @DisplayName("Test labelName(); then return toStringTree is '([18] Input)'")
  @Tag("MaintainedByDiffblue")
  void testLabelName_thenReturnToStringTreeIs18Input() throws RecognitionException {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));
    ParserRuleContext localctx = new ParserRuleContext();
    mqeParser.enterRule(localctx, MQEParser.NEQ, 1);
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    LabelNameContext actualLabelNameResult = mqeParser.labelName();

    // Assert
    assertEquals("([18] Input)", actualLabelNameResult.toStringTree());
    assertEquals(2, actualLabelNameResult.depth());
    assertFalse(actualLabelNameResult.isEmpty());
    assertEquals(MQEParser.NEQ, actualLabelNameResult.invokingState);
    assertSame(localctx, actualLabelNameResult.getParent());
  }

  /**
   * Test {@link MQEParser#labelValue()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelValue()}
   */
  @Test
  @DisplayName("Test labelValue(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testLabelValue_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    LabelValueContext actualLabelValueResult = mqeParser.labelValue();

    // Assert
    Token start = actualLabelValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLabelValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualLabelValueResult.toStringTree());
    assertEquals("42", actualLabelValueResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualLabelValueResult, recognitionException.getCtx());
    assertSame(start, actualLabelValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#labelValue()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelValue()}
   */
  @Test
  @DisplayName("Test labelValue(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testLabelValue_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    LabelValueContext actualLabelValueResult = mqeParser.labelValue();

    // Assert
    Token start = actualLabelValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLabelValueResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualLabelValueResult.toStringTree());
    assertEquals("Input", actualLabelValueResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualLabelValueResult, recognitionException.getCtx());
    assertSame(start, actualLabelValueResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#labelValue()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelValue()}
   */
  @Test
  @DisplayName("Test labelValue(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testLabelValue_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    LabelValueContext actualLabelValueResult = (new MQEParser(new BufferedTokenStream(tokenSource))).labelValue();

    // Assert
    Token start = actualLabelValueResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualLabelValueResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualLabelValueResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#labelValue()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelValue()}
   */
  @Test
  @DisplayName("Test labelValue(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testLabelValue_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).labelValue().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link MQEParser#labelValue()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#labelValue()}
   */
  @Test
  @DisplayName("Test labelValue(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testLabelValue_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    LabelValueContext actualLabelValueResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 22, 'A', 22, 'A', 22, 'A', 22}))))))
            .labelValue();

    // Assert
    assertTrue(actualLabelValueResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualLabelValueResult.toStringTree());
    assertEquals("AAAA", actualLabelValueResult.getText());
    assertEquals(4, actualLabelValueResult.children.size());
    assertEquals(4, actualLabelValueResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#label()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] ([239] 42))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#label()}
   */
  @Test
  @DisplayName("Test label(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] ([239] 42))'")
  @Tag("MaintainedByDiffblue")
  void testLabel_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs23942() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    LabelContext actualLabelResult = mqeParser.label();

    // Assert
    Token start = actualLabelResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLabelResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([239] 42))", actualLabelResult.toStringTree());
    assertEquals("42", actualLabelResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualLabelResult, recognitionException.getCtx());
    assertSame(start, actualLabelResult.getStop());
  }

  /**
   * Test {@link MQEParser#label()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] ([239] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#label()}
   */
  @Test
  @DisplayName("Test label(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] ([239] Input))'")
  @Tag("MaintainedByDiffblue")
  void testLabel_givenANTLRInputStreamWithInput_thenReturnToStringTreeIs239Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    LabelContext actualLabelResult = mqeParser.label();

    // Assert
    Token start = actualLabelResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLabelResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] ([239] Input))", actualLabelResult.toStringTree());
    assertEquals("Input", actualLabelResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualLabelResult, recognitionException.getCtx());
    assertSame(start, actualLabelResult.getStop());
  }

  /**
   * Test {@link MQEParser#label()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#label()}
   */
  @Test
  @DisplayName("Test label(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testLabel_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    LabelContext actualLabelResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 24, 'A', 24, 'A', 24, 'A', 24}))))))
            .label();

    // Assert
    assertTrue(actualLabelResult.getStop() instanceof CommonToken);
    assertEquals("([] ([239] A) A A A)", actualLabelResult.toStringTree());
    assertEquals("AAAA", actualLabelResult.getText());
    assertEquals(4, actualLabelResult.children.size());
    assertEquals(4, actualLabelResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#logical_operator()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#logical_operator()}
   */
  @Test
  @DisplayName("Test logical_operator(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testLogical_operator_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).logical_operator().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#logical_operator()}.
   * <ul>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#logical_operator()}
   */
  @Test
  @DisplayName("Test logical_operator(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testLogical_operator_thenReturnStartTextIsEof2() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).logical_operator().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#logical_operator()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#logical_operator()}
   */
  @Test
  @DisplayName("Test logical_operator(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testLogical_operator_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Logical_operatorContext actualLogical_operatorResult = mqeParser.logical_operator();

    // Assert
    Token start = actualLogical_operatorResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLogical_operatorResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualLogical_operatorResult.toStringTree());
    assertEquals("42", actualLogical_operatorResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualLogical_operatorResult, recognitionException.getCtx());
    assertSame(start, actualLogical_operatorResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#logical_operator()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#logical_operator()}
   */
  @Test
  @DisplayName("Test logical_operator(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testLogical_operator_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Logical_operatorContext actualLogical_operatorResult = mqeParser.logical_operator();

    // Assert
    Token start = actualLogical_operatorResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualLogical_operatorResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualLogical_operatorResult.toStringTree());
    assertEquals("Input", actualLogical_operatorResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualLogical_operatorResult, recognitionException.getCtx());
    assertSame(start, actualLogical_operatorResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#logical_operator()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#logical_operator()}
   */
  @Test
  @DisplayName("Test logical_operator(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testLogical_operator_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    Logical_operatorContext actualLogical_operatorResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A,A,A,A,".getBytes("UTF-8")))))))
            .logical_operator();

    // Assert
    assertTrue(actualLogical_operatorResult.getStop() instanceof CommonToken);
    assertEquals("([] A , A , A , A ,)", actualLogical_operatorResult.toStringTree());
    assertEquals("A,A,A,A,", actualLogical_operatorResult.getText());
    assertEquals(8, actualLogical_operatorResult.children.size());
    assertEquals(8, actualLogical_operatorResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#mathematical_operator0()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator0()}
   */
  @Test
  @DisplayName("Test mathematical_operator0(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator0_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).mathematical_operator0().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#mathematical_operator0()}.
   * <ul>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator0()}
   */
  @Test
  @DisplayName("Test mathematical_operator0(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator0_thenReturnStartTextIsEof2() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).mathematical_operator0().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#mathematical_operator0()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator0()}
   */
  @Test
  @DisplayName("Test mathematical_operator0(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator0_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Mathematical_operator0Context actualMathematical_operator0Result = mqeParser.mathematical_operator0();

    // Assert
    Token start = actualMathematical_operator0Result.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMathematical_operator0Result.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualMathematical_operator0Result.toStringTree());
    assertEquals("42", actualMathematical_operator0Result.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualMathematical_operator0Result, recognitionException.getCtx());
    assertSame(start, actualMathematical_operator0Result.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#mathematical_operator0()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator0()}
   */
  @Test
  @DisplayName("Test mathematical_operator0(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator0_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Mathematical_operator0Context actualMathematical_operator0Result = mqeParser.mathematical_operator0();

    // Assert
    Token start = actualMathematical_operator0Result.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMathematical_operator0Result.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualMathematical_operator0Result.toStringTree());
    assertEquals("Input", actualMathematical_operator0Result.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualMathematical_operator0Result, recognitionException.getCtx());
    assertSame(start, actualMathematical_operator0Result.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#mathematical_operator0()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator0()}
   */
  @Test
  @DisplayName("Test mathematical_operator0(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator0_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    Mathematical_operator0Context actualMathematical_operator0Result = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A\"A\"A\"A\"".getBytes("UTF-8")))))))
            .mathematical_operator0();

    // Assert
    assertTrue(actualMathematical_operator0Result.getStop() instanceof CommonToken);
    assertEquals("([] A \"A\" A \"A\")", actualMathematical_operator0Result.toStringTree());
    assertEquals("A\"A\"A\"A\"", actualMathematical_operator0Result.getText());
    assertEquals(4, actualMathematical_operator0Result.children.size());
    assertEquals(4, actualMathematical_operator0Result.getChildCount());
  }

  /**
   * Test {@link MQEParser#mathematical_operator1()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator1()}
   */
  @Test
  @DisplayName("Test mathematical_operator1(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator1_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    Mathematical_operator1Context actualMathematical_operator1Result = (new MQEParser(
        new BufferedTokenStream(tokenSource))).mathematical_operator1();

    // Assert
    Token start = actualMathematical_operator1Result.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualMathematical_operator1Result.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualMathematical_operator1Result, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#mathematical_operator1()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator1()}
   */
  @Test
  @DisplayName("Test mathematical_operator1(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator1_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Mathematical_operator1Context actualMathematical_operator1Result = mqeParser.mathematical_operator1();

    // Assert
    Token start = actualMathematical_operator1Result.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMathematical_operator1Result.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualMathematical_operator1Result.toStringTree());
    assertEquals("42", actualMathematical_operator1Result.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualMathematical_operator1Result, recognitionException.getCtx());
    assertSame(start, actualMathematical_operator1Result.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#mathematical_operator1()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator1()}
   */
  @Test
  @DisplayName("Test mathematical_operator1(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator1_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Mathematical_operator1Context actualMathematical_operator1Result = mqeParser.mathematical_operator1();

    // Assert
    Token start = actualMathematical_operator1Result.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMathematical_operator1Result.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualMathematical_operator1Result.toStringTree());
    assertEquals("Input", actualMathematical_operator1Result.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualMathematical_operator1Result, recognitionException.getCtx());
    assertSame(start, actualMathematical_operator1Result.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#mathematical_operator1()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator1()}
   */
  @Test
  @DisplayName("Test mathematical_operator1(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator1_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).mathematical_operator1().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link MQEParser#mathematical_operator1()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mathematical_operator1()}
   */
  @Test
  @DisplayName("Test mathematical_operator1(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testMathematical_operator1_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    Mathematical_operator1Context actualMathematical_operator1Result = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A$A$A$A$".getBytes("UTF-8")))))))
            .mathematical_operator1();

    // Assert
    assertTrue(actualMathematical_operator1Result.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualMathematical_operator1Result.toStringTree());
    assertEquals("AAAA", actualMathematical_operator1Result.getText());
    assertEquals(4, actualMathematical_operator1Result.children.size());
    assertEquals(4, actualMathematical_operator1Result.getChildCount());
  }

  /**
   * Test {@link MQEParser#metricName()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metricName()}
   */
  @Test
  @DisplayName("Test metricName(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testMetricName_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    MetricNameContext actualMetricNameResult = (new MQEParser(new BufferedTokenStream(tokenSource))).metricName();

    // Assert
    Token start = actualMetricNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualMetricNameResult.toStringTree());
    assertEquals("Input", actualMetricNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualMetricNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#metricName()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metricName()}
   */
  @Test
  @DisplayName("Test metricName(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testMetricName_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput2() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(tokenSource));
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    MetricNameContext actualMetricNameResult = mqeParser.metricName();

    // Assert
    Token start = actualMetricNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualMetricNameResult.toStringTree());
    assertEquals("Input", actualMetricNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualMetricNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#metricName()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] A)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metricName()}
   */
  @Test
  @DisplayName("Test metricName(); given 'A'; then return toStringTree is '([] A)'")
  @Tag("MaintainedByDiffblue")
  void testMetricName_givenA_thenReturnToStringTreeIsA() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 14, 'A', 14, 'A', 14, 'A', 14}));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    MetricNameContext actualMetricNameResult = (new MQEParser(new BufferedTokenStream(tokenSource))).metricName();

    // Assert
    Token start = actualMetricNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] A)", actualMetricNameResult.toStringTree());
    assertEquals("A", actualMetricNameResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualMetricNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#metricName()}.
   * <ul>
   *   <li>Given {@link MQEParser#MQEParser(TokenStream)} with input is {@link BufferedTokenStream#BufferedTokenStream(TokenSource)} Trace is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metricName()}
   */
  @Test
  @DisplayName("Test metricName(); given MQEParser(TokenStream) with input is BufferedTokenStream(TokenSource) Trace is 'true'")
  @Tag("MaintainedByDiffblue")
  void testMetricName_givenMQEParserWithInputIsBufferedTokenStreamTraceIsTrue() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(tokenSource));
    mqeParser.setTrace(true);
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    MetricNameContext actualMetricNameResult = mqeParser.metricName();

    // Assert
    Token start = actualMetricNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualMetricNameResult.toStringTree());
    assertEquals("Input", actualMetricNameResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualMetricNameResult.getStop());
  }

  /**
   * Test {@link MQEParser#metricName()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#children} first Payload return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metricName()}
   */
  @Test
  @DisplayName("Test metricName(); then children first Payload return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testMetricName_thenChildrenFirstPayloadReturnCommonToken() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    MetricNameContext actualMetricNameResult = (new MQEParser(new BufferedTokenStream(tokenSource))).metricName();

    // Assert
    Token start = actualMetricNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualMetricNameResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualMetricNameResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#metricName()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metricName()}
   */
  @Test
  @DisplayName("Test metricName(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testMetricName_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    MetricNameContext actualMetricNameResult = (new MQEParser(new BufferedTokenStream(tokenSource))).metricName();

    // Assert
    Token start = actualMetricNameResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualMetricNameResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#metricName()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([14] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metricName()}
   */
  @Test
  @DisplayName("Test metricName(); then return toStringTree is '([14] Input)'")
  @Tag("MaintainedByDiffblue")
  void testMetricName_thenReturnToStringTreeIs14Input() throws RecognitionException {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));
    ParserRuleContext localctx = new ParserRuleContext();
    mqeParser.enterRule(localctx, MQEParser.MUL, 1);
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    MetricNameContext actualMetricNameResult = mqeParser.metricName();

    // Assert
    assertEquals("([14] Input)", actualMetricNameResult.toStringTree());
    assertEquals(2, actualMetricNameResult.depth());
    assertFalse(actualMetricNameResult.isEmpty());
    assertEquals(MQEParser.MUL, actualMetricNameResult.invokingState);
    assertSame(localctx, actualMetricNameResult.getParent());
  }

  /**
   * Test {@link MQEParser#metric()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] ([217] Input))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metric()}
   */
  @Test
  @DisplayName("Test metric(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] ([217] Input))'")
  @Tag("MaintainedByDiffblue")
  void testMetric_givenANTLRInputStreamWithInput_thenReturnToStringTreeIs217Input() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    MetricContext actualMetricResult = (new MQEParser(new BufferedTokenStream(tokenSource))).metric();

    // Assert
    Token start = actualMetricResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([217] Input))", actualMetricResult.toStringTree());
    assertEquals("Input", actualMetricResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualMetricResult.getStop());
  }

  /**
   * Test {@link MQEParser#metric()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then return toStringTree is {@code ([] ([217] A))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metric()}
   */
  @Test
  @DisplayName("Test metric(); given 'A'; then return toStringTree is '([] ([217] A))'")
  @Tag("MaintainedByDiffblue")
  void testMetric_givenA_thenReturnToStringTreeIs217A() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(
        new ByteArrayInputStream(new byte[]{'A', 16, 'A', 16, 'A', 16, 'A', 16}));
    MQELexer tokenSource = new MQELexer(input);

    // Act
    MetricContext actualMetricResult = (new MQEParser(new BufferedTokenStream(tokenSource))).metric();

    // Assert
    Token start = actualMetricResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([217] A))", actualMetricResult.toStringTree());
    assertEquals("A", actualMetricResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualMetricResult.getStop());
  }

  /**
   * Test {@link MQEParser#metric()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} return {@link InputMismatchException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#metric()}
   */
  @Test
  @DisplayName("Test metric(); then exception return InputMismatchException")
  @Tag("MaintainedByDiffblue")
  void testMetric_thenExceptionReturnInputMismatchException() throws RecognitionException {
    // Arrange and Act
    MetricContext actualMetricResult = (new MQEParser(
        new BufferedTokenStream(new MQELexer(new ANTLRInputStream("42"))))).metric();

    // Assert
    assertTrue(actualMetricResult.exception instanceof InputMismatchException);
    assertEquals("([] 42)", actualMetricResult.toStringTree());
    assertEquals("42", actualMetricResult.getText());
  }

  /**
   * Test {@link MQEParser#mulDivMod()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mulDivMod()}
   */
  @Test
  @DisplayName("Test mulDivMod(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testMulDivMod_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    MulDivModContext actualMulDivModResult = mqeParser.mulDivMod();

    // Assert
    Token start = actualMulDivModResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMulDivModResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualMulDivModResult.toStringTree());
    assertEquals("42", actualMulDivModResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualMulDivModResult, recognitionException.getCtx());
    assertSame(start, actualMulDivModResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#mulDivMod()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mulDivMod()}
   */
  @Test
  @DisplayName("Test mulDivMod(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testMulDivMod_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    MulDivModContext actualMulDivModResult = mqeParser.mulDivMod();

    // Assert
    Token start = actualMulDivModResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualMulDivModResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualMulDivModResult.toStringTree());
    assertEquals("Input", actualMulDivModResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualMulDivModResult, recognitionException.getCtx());
    assertSame(start, actualMulDivModResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#mulDivMod()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mulDivMod()}
   */
  @Test
  @DisplayName("Test mulDivMod(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testMulDivMod_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).mulDivMod().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#mulDivMod()}.
   * <ul>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mulDivMod()}
   */
  @Test
  @DisplayName("Test mulDivMod(); then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testMulDivMod_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).mulDivMod().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#mulDivMod()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#mulDivMod()}
   */
  @Test
  @DisplayName("Test mulDivMod(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testMulDivMod_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    MulDivModContext actualMulDivModResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A\nA\nA\nA\n".getBytes("UTF-8"))))))).mulDivMod();

    // Assert
    assertTrue(actualMulDivModResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualMulDivModResult.toStringTree());
    assertEquals("AAAA", actualMulDivModResult.getText());
    assertEquals(4, actualMulDivModResult.children.size());
    assertEquals(4, actualMulDivModResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#order()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#order()}
   */
  @Test
  @DisplayName("Test order(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testOrder_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    OrderContext actualOrderResult = mqeParser.order();

    // Assert
    Token start = actualOrderResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualOrderResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualOrderResult.toStringTree());
    assertEquals("42", actualOrderResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualOrderResult, recognitionException.getCtx());
    assertSame(start, actualOrderResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#order()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is empty string.</li>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#order()}
   */
  @Test
  @DisplayName("Test order(); given ANTLRInputStream(String) with input is empty string; then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testOrder_givenANTLRInputStreamWithInputIsEmptyString_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).order().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#order()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#order()}
   */
  @Test
  @DisplayName("Test order(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testOrder_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    OrderContext actualOrderResult = mqeParser.order();

    // Assert
    Token start = actualOrderResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualOrderResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualOrderResult.toStringTree());
    assertEquals("Input", actualOrderResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualOrderResult, recognitionException.getCtx());
    assertSame(start, actualOrderResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#order()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#order()}
   */
  @Test
  @DisplayName("Test order(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testOrder_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).order().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#order()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] A4A4A4A4)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#order()}
   */
  @Test
  @DisplayName("Test order(); then return toStringTree is '([] A4A4A4A4)'")
  @Tag("MaintainedByDiffblue")
  void testOrder_thenReturnToStringTreeIsA4a4a4a4() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A4A4A4A4".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    OrderContext actualOrderResult = mqeParser.order();

    // Assert
    Token start = actualOrderResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualOrderResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] A4A4A4A4)", actualOrderResult.toStringTree());
    assertEquals("A4A4A4A4", actualOrderResult.getText());
    assertEquals("A4A4A4A4", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualOrderResult, recognitionException.getCtx());
    assertSame(start, actualOrderResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#parameter()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#parameter()}
   */
  @Test
  @DisplayName("Test parameter(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testParameter_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    ParameterContext actualParameterResult = mqeParser.parameter();

    // Assert
    Token start = actualParameterResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualParameterResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualParameterResult.toStringTree());
    assertEquals("Input", actualParameterResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualParameterResult, recognitionException.getCtx());
    assertSame(start, actualParameterResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#parameter()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#parameter()}
   */
  @Test
  @DisplayName("Test parameter(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testParameter_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    ParameterContext actualParameterResult = (new MQEParser(new BufferedTokenStream(tokenSource))).parameter();

    // Assert
    Token start = actualParameterResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualParameterResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualParameterResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#parameter()}.
   * <ul>
   *   <li>Then return Start TokenSource ATN {@link ATN#decisionToState} size is {@link MQEParser#L_BRACE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#parameter()}
   */
  @Test
  @DisplayName("Test parameter(); then return Start TokenSource ATN decisionToState size is L_BRACE")
  @Tag("MaintainedByDiffblue")
  void testParameter_thenReturnStartTokenSourceAtnDecisionToStateSizeIsL_brace() throws RecognitionException {
    // Arrange and Act
    ParameterContext actualParameterResult = (new MQEParser(
        new BufferedTokenStream(new MQELexer(new ANTLRInputStream("42"))))).parameter();

    // Assert
    Token start = actualParameterResult.getStart();
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource = start.getTokenSource();
    List<DecisionState> decisionStateList = ((MQELexer) tokenSource).getATN().decisionToState;
    assertEquals(MQEParser.L_BRACE, decisionStateList.size());
    DecisionState getResult = decisionStateList.get(0);
    Transition[] transitions = getResult.getTransitions();
    assertTrue(transitions[MQEParser.ATTR4] instanceof EpsilonTransition);
    assertTrue(transitions[MQEParser.ATTR5] instanceof EpsilonTransition);
    assertTrue(transitions[MQEParser.BASELINE] instanceof EpsilonTransition);
    assertTrue(getResult instanceof TokensStartState);
    assertTrue(tokenSource instanceof MQELexer);
    assertEquals("([] 42)", actualParameterResult.toStringTree());
    assertEquals(MQEParser.WS, transitions.length);
  }

  /**
   * Test {@link MQEParser#parameter()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([50] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#parameter()}
   */
  @Test
  @DisplayName("Test parameter(); then return toStringTree is '([50] 42)'")
  @Tag("MaintainedByDiffblue")
  void testParameter_thenReturnToStringTreeIs5042() throws RecognitionException {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("42"))));
    ParserRuleContext localctx = new ParserRuleContext();
    mqeParser.enterRule(localctx, MQEParser.ATTR3, 1);
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    ParameterContext actualParameterResult = mqeParser.parameter();

    // Assert
    assertEquals("([50] 42)", actualParameterResult.toStringTree());
    assertEquals(2, actualParameterResult.depth());
    assertFalse(actualParameterResult.isEmpty());
    assertEquals(MQEParser.ATTR3, actualParameterResult.invokingState);
    assertSame(localctx, actualParameterResult.getParent());
  }

  /**
   * Test {@link MQEParser#parameter()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] A2A2A2A2)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#parameter()}
   */
  @Test
  @DisplayName("Test parameter(); then return toStringTree is '([] A2A2A2A2)'")
  @Tag("MaintainedByDiffblue")
  void testParameter_thenReturnToStringTreeIsA2a2a2a2() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A2A2A2A2".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    ParameterContext actualParameterResult = mqeParser.parameter();

    // Assert
    Token start = actualParameterResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualParameterResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] A2A2A2A2)", actualParameterResult.toStringTree());
    assertEquals("A2A2A2A2", actualParameterResult.getText());
    assertEquals("A2A2A2A2", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualParameterResult, recognitionException.getCtx());
    assertSame(start, actualParameterResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#parameter()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#parameter()}
   */
  @Test
  @DisplayName("Test parameter(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testParameter_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).parameter().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#relabels()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#relabels()}
   */
  @Test
  @DisplayName("Test relabels(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testRelabels_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    RelabelsContext actualRelabelsResult = mqeParser.relabels();

    // Assert
    Token start = actualRelabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualRelabelsResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualRelabelsResult.toStringTree());
    assertEquals("42", actualRelabelsResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualRelabelsResult, recognitionException.getCtx());
    assertSame(start, actualRelabelsResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#relabels()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#relabels()}
   */
  @Test
  @DisplayName("Test relabels(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testRelabels_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    RelabelsContext actualRelabelsResult = mqeParser.relabels();

    // Assert
    Token start = actualRelabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualRelabelsResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualRelabelsResult.toStringTree());
    assertEquals("Input", actualRelabelsResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualRelabelsResult, recognitionException.getCtx());
    assertSame(start, actualRelabelsResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#relabels()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#relabels()}
   */
  @Test
  @DisplayName("Test relabels(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testRelabels_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    RelabelsContext actualRelabelsResult = (new MQEParser(new BufferedTokenStream(tokenSource))).relabels();

    // Assert
    Token start = actualRelabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualRelabelsResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualRelabelsResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#relabels()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] A0A0A0A0)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#relabels()}
   */
  @Test
  @DisplayName("Test relabels(); then return toStringTree is '([] A0A0A0A0)'")
  @Tag("MaintainedByDiffblue")
  void testRelabels_thenReturnToStringTreeIsA0a0a0a0() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A0A0A0A0".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    RelabelsContext actualRelabelsResult = mqeParser.relabels();

    // Assert
    Token start = actualRelabelsResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualRelabelsResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] A0A0A0A0)", actualRelabelsResult.toStringTree());
    assertEquals("A0A0A0A0", actualRelabelsResult.getText());
    assertEquals("A0A0A0A0", start.getText());
    assertEquals(7, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualRelabelsResult, recognitionException.getCtx());
    assertSame(start, actualRelabelsResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#relabels()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#relabels()}
   */
  @Test
  @DisplayName("Test relabels(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testRelabels_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).relabels().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link MQEParser#replaceLabel()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([251] ([239 251] 42)))}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#replaceLabel()}
   */
  @Test
  @DisplayName("Test replaceLabel(); then return toStringTree is '([] ([251] ([239 251] 42)))'")
  @Tag("MaintainedByDiffblue")
  void testReplaceLabel_thenReturnToStringTreeIs25123925142() throws RecognitionException {
    // Arrange and Act
    ReplaceLabelContext actualReplaceLabelResult = (new MQEParser(
        new BufferedTokenStream(new MQELexer(new ANTLRInputStream("42"))))).replaceLabel();

    // Assert
    Token start = actualReplaceLabelResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] ([251] ([239 251] 42)))", actualReplaceLabelResult.toStringTree());
    assertEquals("42", actualReplaceLabelResult.getText());
    assertEquals(1, actualReplaceLabelResult.children.size());
    assertSame(start, actualReplaceLabelResult.getStop());
  }

  /**
   * Test {@link MQEParser#replaceLabel()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#replaceLabel()}
   */
  @Test
  @DisplayName("Test replaceLabel(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testReplaceLabel_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ReplaceLabelContext actualReplaceLabelResult = (new MQEParser(new BufferedTokenStream(tokenSource))).replaceLabel();

    // Assert
    Token start = actualReplaceLabelResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    LabelContext labelResult = actualReplaceLabelResult.label();
    assertEquals("([251] ([239 251] Input))", labelResult.toStringTree());
    assertEquals("([] ([251] ([239 251] Input)))", actualReplaceLabelResult.toStringTree());
    assertEquals("Input", actualReplaceLabelResult.getText());
    assertEquals("Input", labelResult.getText());
    assertEquals("Input", start.getText());
    List<ParseTree> parseTreeList = actualReplaceLabelResult.children;
    assertEquals(1, parseTreeList.size());
    assertEquals(1, labelResult.children.size());
    assertEquals(4, start.getStopIndex());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
    assertSame(actualReplaceLabelResult, labelResult.getParent());
    assertSame(labelResult, parseTreeList.get(0));
    assertSame(start, labelResult.getStart());
    assertSame(start, actualReplaceLabelResult.getStop());
    assertSame(start, labelResult.getStop());
  }

  /**
   * Test {@link MQEParser#replaceLabel()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#replaceLabel()}
   */
  @Test
  @DisplayName("Test replaceLabel(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testReplaceLabel_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    ReplaceLabelContext actualReplaceLabelResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 28, 'A', 28, 'A', 28, 'A', 28}))))))
            .replaceLabel();

    // Assert
    assertTrue(actualReplaceLabelResult.getStart() instanceof CommonToken);
    assertTrue(actualReplaceLabelResult.getStop() instanceof CommonToken);
    assertEquals("([] ([251] ([239 251] A) A A A))", actualReplaceLabelResult.toStringTree());
    assertEquals("AAAA", actualReplaceLabelResult.getText());
    assertEquals(1, actualReplaceLabelResult.children.size());
  }

  /**
   * Test {@link MQEParser#root()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then {@link ParserRuleContext#exception} return {@link InputMismatchException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#root()}
   */
  @Test
  @DisplayName("Test root(); given 'A'; then exception return InputMismatchException")
  @Tag("MaintainedByDiffblue")
  void testRoot_givenA_thenExceptionReturnInputMismatchException() throws IOException, RecognitionException {
    // Arrange and Act
    RootContext actualRootResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 1, 'A', 1, 'A', 1, 'A', 1}))))))
            .root();

    // Assert
    assertTrue(actualRootResult.exception instanceof InputMismatchException);
    assertEquals("([] ([74] ([78 74] ([203 78 74] ([217 203 78 74] A)))) A A A)", actualRootResult.toStringTree());
    assertEquals("AAAA", actualRootResult.getText());
    assertEquals(4, actualRootResult.children.size());
    assertEquals(4, actualRootResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#root()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] [74] <EOF>)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return toStringTree is '([] [74] <EOF>)'")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnToStringTreeIs74Eof() throws RecognitionException {
    // Arrange and Act
    RootContext actualRootResult = (new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("")))))
        .root();

    // Assert
    assertEquals("([] [74] <EOF>)", actualRootResult.toStringTree());
    assertEquals("<EOF>", actualRootResult.getText());
    assertEquals(2, actualRootResult.children.size());
  }

  /**
   * Test {@link MQEParser#root()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([74] ([78 74] ([204 78 74] 42))) <EOF>)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return toStringTree is '([] ([74] ([78 74] ([204 78 74] 42))) <EOF>)'")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnToStringTreeIs747874204787442Eof() throws RecognitionException {
    // Arrange and Act
    RootContext actualRootResult = (new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("42")))))
        .root();

    // Assert
    assertTrue(actualRootResult.getStop() instanceof CommonToken);
    assertTrue(actualRootResult.expression() instanceof ExprNodeContext);
    assertEquals("([] ([74] ([78 74] ([204 78 74] 42))) <EOF>)", actualRootResult.toStringTree());
    assertEquals("42<EOF>", actualRootResult.getText());
  }

  /**
   * Test {@link MQEParser#root()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] ([74] ([78 74] ([203 78 74] ([217 203 78 74] Input)))) <EOF>)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#root()}
   */
  @Test
  @DisplayName("Test root(); then return toStringTree is '([] ([74] ([78 74] ([203 78 74] ([217 203 78 74] Input)))) <EOF>)'")
  @Tag("MaintainedByDiffblue")
  void testRoot_thenReturnToStringTreeIs74787420378742172037874InputEof() throws RecognitionException {
    // Arrange and Act
    RootContext actualRootResult = (new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input")))))
        .root();

    // Assert
    assertTrue(actualRootResult.getStop() instanceof CommonToken);
    assertTrue(actualRootResult.expression() instanceof ExprNodeContext);
    assertEquals("([] ([74] ([78 74] ([203 78 74] ([217 203 78 74] Input)))) <EOF>)", actualRootResult.toStringTree());
    assertEquals("Input<EOF>", actualRootResult.getText());
  }

  /**
   * Test {@link MQEParser#scalar()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is empty string.</li>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#scalar()}
   */
  @Test
  @DisplayName("Test scalar(); given ANTLRInputStream(String) with input is empty string; then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testScalar_givenANTLRInputStreamWithInputIsEmptyString_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).scalar().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#scalar()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#scalar()}
   */
  @Test
  @DisplayName("Test scalar(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testScalar_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).scalar().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#scalar()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#children} first return {@link TerminalNodeImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#scalar()}
   */
  @Test
  @DisplayName("Test scalar(); then children first return TerminalNodeImpl")
  @Tag("MaintainedByDiffblue")
  void testScalar_thenChildrenFirstReturnTerminalNodeImpl() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);

    // Act
    ScalarContext actualScalarResult = (new MQEParser(new BufferedTokenStream(tokenSource))).scalar();

    // Assert
    Token start = actualScalarResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualScalarResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof TerminalNodeImpl);
    assertEquals("([] 42)", actualScalarResult.toStringTree());
    assertEquals("42", actualScalarResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(start, actualScalarResult.getStop());
  }

  /**
   * Test {@link MQEParser#scalar()}.
   * <ul>
   *   <li>Then {@link ParserRuleContext#exception} InputStream return {@link BufferedTokenStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#scalar()}
   */
  @Test
  @DisplayName("Test scalar(); then exception InputStream return BufferedTokenStream")
  @Tag("MaintainedByDiffblue")
  void testScalar_thenExceptionInputStreamReturnBufferedTokenStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    ScalarContext actualScalarResult = mqeParser.scalar();

    // Assert
    RecognitionException recognitionException = actualScalarResult.exception;
    IntStream inputStream = recognitionException.getInputStream();
    assertTrue(inputStream instanceof BufferedTokenStream);
    Token start = actualScalarResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertTrue(recognitionException instanceof InputMismatchException);
    List<ParseTree> parseTreeList = actualScalarResult.children;
    assertEquals(1, parseTreeList.size());
    assertTrue(parseTreeList.get(0) instanceof ErrorNodeImpl);
    assertEquals("([] Input)", actualScalarResult.toStringTree());
    assertEquals("Input", actualScalarResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, inputStream);
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualScalarResult, recognitionException.getCtx());
    assertSame(start, actualScalarResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#scalar()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([30] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#scalar()}
   */
  @Test
  @DisplayName("Test scalar(); then return toStringTree is '([30] 42)'")
  @Tag("MaintainedByDiffblue")
  void testScalar_thenReturnToStringTreeIs3042() throws RecognitionException {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("42"))));
    ParserRuleContext localctx = new ParserRuleContext();
    mqeParser.enterRule(localctx, MQEParser.RULE_sort_values, 1);
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    ScalarContext actualScalarResult = mqeParser.scalar();

    // Assert
    assertEquals("([30] 42)", actualScalarResult.toStringTree());
    assertEquals(2, actualScalarResult.depth());
    assertFalse(actualScalarResult.isEmpty());
    assertEquals(MQEParser.RULE_sort_values, actualScalarResult.invokingState);
    assertSame(localctx, actualScalarResult.getParent());
  }

  /**
   * Test {@link MQEParser#scalar()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#scalar()}
   */
  @Test
  @DisplayName("Test scalar(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testScalar_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    ScalarContext actualScalarResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream(new byte[]{'A', 30, 'A', 30, 'A', 30, 'A', 30}))))))
            .scalar();

    // Assert
    assertTrue(actualScalarResult.getStop() instanceof CommonToken);
    assertEquals("([] A A A A)", actualScalarResult.toStringTree());
    assertEquals("AAAA", actualScalarResult.getText());
    assertEquals(4, actualScalarResult.children.size());
    assertEquals(4, actualScalarResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#sempred(RuleContext, int, int)}.
   * <ul>
   *   <li>When {@link MQEParser#MOD}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sempred(RuleContext, int, int)}
   */
  @Test
  @DisplayName("Test sempred(RuleContext, int, int); when MOD")
  @Tag("MaintainedByDiffblue")
  void testSempred_whenMod() {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));

    // Act and Assert
    assertTrue(mqeParser.sempred(new ExpressionContext(), 1, MQEParser.MOD));
  }

  /**
   * Test {@link MQEParser#sempred(RuleContext, int, int)}.
   * <ul>
   *   <li>When one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sempred(RuleContext, int, int)}
   */
  @Test
  @DisplayName("Test sempred(RuleContext, int, int); when one")
  @Tag("MaintainedByDiffblue")
  void testSempred_whenOne() {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));

    // Act and Assert
    assertTrue(mqeParser.sempred(new ExpressionContext(), 1, 1));
  }

  /**
   * Test {@link MQEParser#sempred(RuleContext, int, int)}.
   * <ul>
   *   <li>When three.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sempred(RuleContext, int, int)}
   */
  @Test
  @DisplayName("Test sempred(RuleContext, int, int); when three")
  @Tag("MaintainedByDiffblue")
  void testSempred_whenThree() {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));

    // Act and Assert
    assertTrue(mqeParser.sempred(new ExpressionContext(), 1, 3));
  }

  /**
   * Test {@link MQEParser#sempred(RuleContext, int, int)}.
   * <ul>
   *   <li>When two.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sempred(RuleContext, int, int)}
   */
  @Test
  @DisplayName("Test sempred(RuleContext, int, int); when two")
  @Tag("MaintainedByDiffblue")
  void testSempred_whenTwo() {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));

    // Act and Assert
    assertTrue(mqeParser.sempred(new ExpressionContext(), 1, 2));
  }

  /**
   * Test {@link MQEParser#sempred(RuleContext, int, int)}.
   * <ul>
   *   <li>When two.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sempred(RuleContext, int, int)}
   */
  @Test
  @DisplayName("Test sempred(RuleContext, int, int); when two")
  @Tag("MaintainedByDiffblue")
  void testSempred_whenTwo2() {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));

    // Act and Assert
    assertTrue(mqeParser.sempred(new ExpressionContext(), 2, 0));
  }

  /**
   * Test {@link MQEParser#sempred(RuleContext, int, int)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sempred(RuleContext, int, int)}
   */
  @Test
  @DisplayName("Test sempred(RuleContext, int, int); when zero")
  @Tag("MaintainedByDiffblue")
  void testSempred_whenZero() {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));

    // Act and Assert
    assertTrue(mqeParser.sempred(new ExpressionContext(), 1, 0));
  }

  /**
   * Test {@link MQEParser#sort_label_values()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_label_values()}
   */
  @Test
  @DisplayName("Test sort_label_values(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testSort_label_values_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    Sort_label_valuesContext actualSort_label_valuesResult = (new MQEParser(new BufferedTokenStream(tokenSource)))
        .sort_label_values();

    // Assert
    Token start = actualSort_label_valuesResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualSort_label_valuesResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualSort_label_valuesResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#sort_label_values()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_label_values()}
   */
  @Test
  @DisplayName("Test sort_label_values(); then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testSort_label_values_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Sort_label_valuesContext actualSort_label_valuesResult = mqeParser.sort_label_values();

    // Assert
    Token start = actualSort_label_valuesResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualSort_label_valuesResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualSort_label_valuesResult.toStringTree());
    assertEquals("42", actualSort_label_valuesResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualSort_label_valuesResult, recognitionException.getCtx());
    assertSame(start, actualSort_label_valuesResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#sort_label_values()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_label_values()}
   */
  @Test
  @DisplayName("Test sort_label_values(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testSort_label_values_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Sort_label_valuesContext actualSort_label_valuesResult = mqeParser.sort_label_values();

    // Assert
    Token start = actualSort_label_valuesResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualSort_label_valuesResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualSort_label_valuesResult.toStringTree());
    assertEquals("Input", actualSort_label_valuesResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualSort_label_valuesResult, recognitionException.getCtx());
    assertSame(start, actualSort_label_valuesResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#sort_label_values()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_label_values()}
   */
  @Test
  @DisplayName("Test sort_label_values(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testSort_label_values_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).sort_label_values().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link MQEParser#sort_label_values()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_label_values()}
   */
  @Test
  @DisplayName("Test sort_label_values(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testSort_label_values_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    Sort_label_valuesContext actualSort_label_valuesResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A>A>A>A>".getBytes("UTF-8")))))))
            .sort_label_values();

    // Assert
    assertTrue(actualSort_label_valuesResult.getStop() instanceof CommonToken);
    assertEquals("([] A > A > A > A >)", actualSort_label_valuesResult.toStringTree());
    assertEquals("A>A>A>A>", actualSort_label_valuesResult.getText());
    assertEquals(8, actualSort_label_valuesResult.children.size());
    assertEquals(8, actualSort_label_valuesResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#sort_values()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_values()}
   */
  @Test
  @DisplayName("Test sort_values(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testSort_values_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Sort_valuesContext actualSort_valuesResult = mqeParser.sort_values();

    // Assert
    Token start = actualSort_valuesResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualSort_valuesResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualSort_valuesResult.toStringTree());
    assertEquals("42", actualSort_valuesResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualSort_valuesResult, recognitionException.getCtx());
    assertSame(start, actualSort_valuesResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#sort_values()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_values()}
   */
  @Test
  @DisplayName("Test sort_values(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testSort_values_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    Sort_valuesContext actualSort_valuesResult = mqeParser.sort_values();

    // Assert
    Token start = actualSort_valuesResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualSort_valuesResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualSort_valuesResult.toStringTree());
    assertEquals("Input", actualSort_valuesResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualSort_valuesResult, recognitionException.getCtx());
    assertSame(start, actualSort_valuesResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#sort_values()}.
   * <ul>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_values()}
   */
  @Test
  @DisplayName("Test sort_values(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testSort_values_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    Sort_valuesContext actualSort_valuesResult = (new MQEParser(new BufferedTokenStream(tokenSource))).sort_values();

    // Assert
    Token start = actualSort_valuesResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualSort_valuesResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualSort_valuesResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#sort_values()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_values()}
   */
  @Test
  @DisplayName("Test sort_values(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testSort_values_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).sort_values().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link MQEParser#sort_values()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#sort_values()}
   */
  @Test
  @DisplayName("Test sort_values(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testSort_values_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    Sort_valuesContext actualSort_valuesResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A<A<A<A<".getBytes("UTF-8"))))))).sort_values();

    // Assert
    assertTrue(actualSort_valuesResult.getStop() instanceof CommonToken);
    assertEquals("([] A < A < A < A <)", actualSort_valuesResult.toStringTree());
    assertEquals("A<A<A<A<", actualSort_valuesResult.getText());
    assertEquals(8, actualSort_valuesResult.children.size());
    assertEquals(8, actualSort_valuesResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#topNOf()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topNOf()}
   */
  @Test
  @DisplayName("Test topNOf(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testTopNOf_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    TopNOfContext actualTopNOfResult = mqeParser.topNOf();

    // Assert
    Token start = actualTopNOfResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualTopNOfResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualTopNOfResult.toStringTree());
    assertEquals("42", actualTopNOfResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualTopNOfResult, recognitionException.getCtx());
    assertSame(start, actualTopNOfResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#topNOf()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topNOf()}
   */
  @Test
  @DisplayName("Test topNOf(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testTopNOf_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    TopNOfContext actualTopNOfResult = mqeParser.topNOf();

    // Assert
    Token start = actualTopNOfResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualTopNOfResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualTopNOfResult.toStringTree());
    assertEquals("Input", actualTopNOfResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualTopNOfResult, recognitionException.getCtx());
    assertSame(start, actualTopNOfResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#topNOf()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@link ParserRuleContext#children} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topNOf()}
   */
  @Test
  @DisplayName("Test topNOf(); given ListTokenSource(List) with tokens is ArrayList(); then return children size is one")
  @Tag("MaintainedByDiffblue")
  void testTopNOf_givenListTokenSourceWithTokensIsArrayList_thenReturnChildrenSizeIsOne() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act
    TopNOfContext actualTopNOfResult = (new MQEParser(new BufferedTokenStream(tokenSource))).topNOf();

    // Assert
    Token start = actualTopNOfResult.getStart();
    assertTrue(start instanceof CommonToken);
    List<ParseTree> parseTreeList = actualTopNOfResult.children;
    assertEquals(1, parseTreeList.size());
    ParseTree getResult = parseTreeList.get(0);
    Object payload = getResult.getPayload();
    assertTrue(payload instanceof CommonToken);
    assertTrue(getResult instanceof ErrorNodeImpl);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(actualTopNOfResult, getResult.getParent());
    assertSame(payload, ((ErrorNodeImpl) getResult).getSymbol());
  }

  /**
   * Test {@link MQEParser#topNOf()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topNOf()}
   */
  @Test
  @DisplayName("Test topNOf(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testTopNOf_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).topNOf().getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("<EOF>", start.getText());
    assertSame(input, inputStream);
    assertSame(tokenSource, tokenSource2);
  }

  /**
   * Test {@link MQEParser#topNOf()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topNOf()}
   */
  @Test
  @DisplayName("Test topNOf(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testTopNOf_thenStopReturnCommonToken() throws IOException, RecognitionException {
    // Arrange and Act
    TopNOfContext actualTopNOfResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A*A*A*A*".getBytes("UTF-8"))))))).topNOf();

    // Assert
    assertTrue(actualTopNOfResult.getStop() instanceof CommonToken);
    assertEquals("([] A * A * A * A *)", actualTopNOfResult.toStringTree());
    assertEquals("A*A*A*A*", actualTopNOfResult.getText());
    assertEquals(8, actualTopNOfResult.children.size());
    assertEquals(8, actualTopNOfResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#topN()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topN()}
   */
  @Test
  @DisplayName("Test topN(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testTopN_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange and Act
    TopNContext actualTopNResult = (new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input")))))
        .topN();

    // Assert
    Token start = actualTopNResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualTopNResult.toStringTree());
    assertEquals("Input", actualTopNResult.getText());
    assertEquals("Input", start.getText());
    assertSame(start, actualTopNResult.getStop());
  }

  /**
   * Test {@link MQEParser#topN()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] A ( A ( A ( A ()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topN()}
   */
  @Test
  @DisplayName("Test topN(); then return toStringTree is '([] A ( A ( A ( A ()'")
  @Tag("MaintainedByDiffblue")
  void testTopN_thenReturnToStringTreeIsAAAA() throws IOException, RecognitionException {
    // Arrange and Act
    TopNContext actualTopNResult = (new MQEParser(new BufferedTokenStream(
        new MQELexer(new ANTLRInputStream(new ByteArrayInputStream("A(A(A(A(".getBytes("UTF-8"))))))).topN();

    // Assert
    assertEquals("([] A ( A ( A ( A ()", actualTopNResult.toStringTree());
    assertEquals("A(A(A(A(", actualTopNResult.getText());
    assertEquals(8, actualTopNResult.children.size());
    assertEquals(8, actualTopNResult.getChildCount());
  }

  /**
   * Test {@link MQEParser#topN()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topN()}
   */
  @Test
  @DisplayName("Test topN(); then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testTopN_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(new MQELexer(new ANTLRInputStream("Input"))));
    mqeParser.addParseListener(new MQEParserBaseListener());

    // Act
    TopNContext actualTopNResult = mqeParser.topN();

    // Assert
    Token start = actualTopNResult.getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("([] Input)", actualTopNResult.toStringTree());
    assertEquals("Input", actualTopNResult.getText());
    assertEquals("Input", start.getText());
    assertSame(start, actualTopNResult.getStop());
  }

  /**
   * Test {@link MQEParser#topN()}.
   * <ul>
   *   <li>Then Start InputStream return {@link ANTLRInputStream}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topN()}
   */
  @Test
  @DisplayName("Test topN(); then Start InputStream return ANTLRInputStream")
  @Tag("MaintainedByDiffblue")
  void testTopN_thenStartInputStreamReturnANTLRInputStream() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    TopNContext actualTopNResult = mqeParser.topN();

    // Assert
    Token start = actualTopNResult.getStart();
    CharStream inputStream = start.getInputStream();
    assertTrue(inputStream instanceof ANTLRInputStream);
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualTopNResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof MQELexer);
    assertEquals("([] 42)", actualTopNResult.toStringTree());
    assertEquals("42", actualTopNResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, inputStream);
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualTopNResult, recognitionException.getCtx());
    assertSame(start, actualTopNResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#topN()}.
   * <ul>
   *   <li>Then Start TokenSource return {@link ListTokenSource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topN()}
   */
  @Test
  @DisplayName("Test topN(); then Start TokenSource return ListTokenSource")
  @Tag("MaintainedByDiffblue")
  void testTopN_thenStartTokenSourceReturnListTokenSource() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());
    BufferedTokenStream input = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input);

    // Act
    TopNContext actualTopNResult = mqeParser.topN();

    // Assert
    Token start = actualTopNResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualTopNResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    TokenSource tokenSource2 = start.getTokenSource();
    assertTrue(tokenSource2 instanceof ListTokenSource);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertNull(actualTopNResult.getStop());
    assertEquals(-1, start.getStartIndex());
    assertEquals(-1, start.getStopIndex());
    assertEquals(-1, actualTopNResult.getSourceInterval().b);
    assertSame(input, recognitionException.getInputStream());
    assertSame(tokenSource, tokenSource2);
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualTopNResult, recognitionException.getCtx());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#topN()}.
   * <ul>
   *   <li>Then Stop return {@link CommonToken}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#topN()}
   */
  @Test
  @DisplayName("Test topN(); then Stop return CommonToken")
  @Tag("MaintainedByDiffblue")
  void testTopN_thenStopReturnCommonToken() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);

    MQEParser mqeParser = new MQEParser(new BufferedTokenStream(tokenSource));
    mqeParser.addSub();

    // Act
    TopNContext actualTopNResult = mqeParser.topN();

    // Assert
    Token start = actualTopNResult.getStart();
    assertTrue(start instanceof CommonToken);
    Token stop = actualTopNResult.getStop();
    assertTrue(stop instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertEquals("Input", stop.getText());
    assertEquals(0, stop.getCharPositionInLine());
    assertEquals(0, stop.getStartIndex());
    assertEquals(0, stop.getTokenIndex());
    assertEquals(1, start.getTokenIndex());
    assertEquals(1, actualTopNResult.getSourceInterval().a);
    assertEquals(4, stop.getStopIndex());
    assertEquals(5, start.getCharPositionInLine());
    assertEquals(5, start.getStartIndex());
    assertEquals(MQEParser.NAME_STRING, stop.getType());
    assertSame(input, stop.getInputStream());
    assertSame(tokenSource, stop.getTokenSource());
  }

  /**
   * Test {@link MQEParser#trend()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is {@code 42}.</li>
   *   <li>Then return toStringTree is {@code ([] 42)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#trend()}
   */
  @Test
  @DisplayName("Test trend(); given ANTLRInputStream(String) with input is '42'; then return toStringTree is '([] 42)'")
  @Tag("MaintainedByDiffblue")
  void testTrend_givenANTLRInputStreamWithInputIs42_thenReturnToStringTreeIs42() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("42");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    TrendContext actualTrendResult = mqeParser.trend();

    // Assert
    Token start = actualTrendResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualTrendResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] 42)", actualTrendResult.toStringTree());
    assertEquals("42", actualTrendResult.getText());
    assertEquals("42", start.getText());
    assertEquals(1, start.getStopIndex());
    assertEquals(MQEParser.INTEGER, start.getType());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualTrendResult, recognitionException.getCtx());
    assertSame(start, actualTrendResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#trend()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with input is empty string.</li>
   *   <li>Then return Start Text is {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#trend()}
   */
  @Test
  @DisplayName("Test trend(); given ANTLRInputStream(String) with input is empty string; then return Start Text is '<EOF>'")
  @Tag("MaintainedByDiffblue")
  void testTrend_givenANTLRInputStreamWithInputIsEmptyString_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("");
    MQELexer tokenSource = new MQELexer(input);

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).trend().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("<EOF>", start.getText());
    assertSame(input, start.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#trend()}.
   * <ul>
   *   <li>Given {@link ANTLRInputStream#ANTLRInputStream(String)} with {@code Input}.</li>
   *   <li>Then return toStringTree is {@code ([] Input)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#trend()}
   */
  @Test
  @DisplayName("Test trend(); given ANTLRInputStream(String) with 'Input'; then return toStringTree is '([] Input)'")
  @Tag("MaintainedByDiffblue")
  void testTrend_givenANTLRInputStreamWithInput_thenReturnToStringTreeIsInput() throws RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream("Input");
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    TrendContext actualTrendResult = mqeParser.trend();

    // Assert
    Token start = actualTrendResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualTrendResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] Input)", actualTrendResult.toStringTree());
    assertEquals("Input", actualTrendResult.getText());
    assertEquals("Input", start.getText());
    assertEquals(4, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualTrendResult, recognitionException.getCtx());
    assertSame(start, actualTrendResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }

  /**
   * Test {@link MQEParser#trend()}.
   * <ul>
   *   <li>Given {@link ListTokenSource#ListTokenSource(List)} with tokens is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Start Text is {@code EOF}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#trend()}
   */
  @Test
  @DisplayName("Test trend(); given ListTokenSource(List) with tokens is ArrayList(); then return Start Text is 'EOF'")
  @Tag("MaintainedByDiffblue")
  void testTrend_givenListTokenSourceWithTokensIsArrayList_thenReturnStartTextIsEof() throws RecognitionException {
    // Arrange
    ListTokenSource tokenSource = new ListTokenSource(new ArrayList<>());

    // Act and Assert
    Token start = (new MQEParser(new BufferedTokenStream(tokenSource))).trend().getStart();
    assertTrue(start instanceof CommonToken);
    assertEquals("EOF", start.getText());
    assertNull(start.getInputStream());
    assertEquals(-1, start.getStartIndex());
    assertSame(tokenSource, start.getTokenSource());
  }

  /**
   * Test {@link MQEParser#trend()}.
   * <ul>
   *   <li>Then return toStringTree is {@code ([] A)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MQEParser#trend()}
   */
  @Test
  @DisplayName("Test trend(); then return toStringTree is '([] A)'")
  @Tag("MaintainedByDiffblue")
  void testTrend_thenReturnToStringTreeIsA() throws IOException, RecognitionException {
    // Arrange
    ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream("A&A&A&A&".getBytes("UTF-8")));
    MQELexer tokenSource = new MQELexer(input);
    BufferedTokenStream input2 = new BufferedTokenStream(tokenSource);
    MQEParser mqeParser = new MQEParser(input2);

    // Act
    TrendContext actualTrendResult = mqeParser.trend();

    // Assert
    Token start = actualTrendResult.getStart();
    assertTrue(start instanceof CommonToken);
    RecognitionException recognitionException = actualTrendResult.exception;
    assertTrue(recognitionException instanceof InputMismatchException);
    assertEquals("([] A)", actualTrendResult.toStringTree());
    assertEquals("A", actualTrendResult.getText());
    assertEquals("A", start.getText());
    assertEquals(0, start.getStopIndex());
    assertSame(input, start.getInputStream());
    assertSame(input2, recognitionException.getInputStream());
    assertSame(tokenSource, start.getTokenSource());
    assertSame(mqeParser, recognitionException.getRecognizer());
    assertSame(actualTrendResult, recognitionException.getCtx());
    assertSame(start, actualTrendResult.getStop());
    assertSame(start, recognitionException.getOffendingToken());
  }
}
