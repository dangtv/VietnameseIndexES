package org.elasticsearch;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws IOException
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException
    {
        VietTokenizer.main(new String[]{});
        TestTokenizer.main(new String[]{});
        assertTrue( true );
    }
}
