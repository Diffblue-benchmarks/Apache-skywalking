package org.apache.skywalking.oap.server.library.util;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.apache.skywalking.oap.server.library.util.RunnableWithExceptionProtection.CallbackWhenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RunnableWithExceptionProtectionDiffblueTest {
  /**
   * Test {@link RunnableWithExceptionProtection#run()}.
   * <p>
   * Method under test: {@link RunnableWithExceptionProtection#run()}
   */
  @Test
  @DisplayName("Test run()")
  @Tag("MaintainedByDiffblue")
  void testRun() {
    // Arrange
    Runnable run = mock(Runnable.class);
    doNothing().when(run).run();

    // Act
    (new RunnableWithExceptionProtection(run, mock(CallbackWhenException.class))).run();

    // Assert
    verify(run).run();
  }
}
