package com.cleware.commandline.parser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public class CommandLineParserTest {

    @Mock
    private ArgumentParser helpParser;

    @Mock
    private ArgumentParser otherParser;

    private CommandLineParser commandLineParser;

    @Before
    public void setup() {
        initMocks(this);
        when(otherParser.isResponsible(any(ArgumentBuffer.class))).thenReturn(true);
        commandLineParser = new CommandLineParser(Arrays.asList(otherParser), helpParser);
    }

    @Test
    public void testEmptyArgumentBuffer() throws Exception {
        commandLineParser.execute(new ArgumentBuffer(new String[]{}));
        verifyZeroInteractions(otherParser);
        verify(helpParser, times(1)).execute(any(ArgumentBuffer.class));
    }

    @Test
    public void testChainExecution() throws Exception {
        commandLineParser.execute(new ArgumentBuffer(new String[]{"--argument"}));
        verifyZeroInteractions(helpParser);
        verify(otherParser, times(1)).execute(any(ArgumentBuffer.class));
    }
}
