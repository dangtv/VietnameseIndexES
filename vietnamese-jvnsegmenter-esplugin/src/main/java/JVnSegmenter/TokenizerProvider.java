package JVnSegmenter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * @author LE Hong Phuong
 * @editor Vandang TRAN
 *         <p>
 *         8 janv. 07
 *         </p>
 *         A provider of tokenizer. It creates a tokenizer for Vietnamese.
 */
public final class TokenizerProvider {

    /**
     * The tokenizer
     */
    private Tokenizer tokenizer;
    /**
     * An instance flag
     */
    private static boolean instanceFlag = false;

    private static TokenizerProvider provider;

    /**
     * Private constructor
     */
    private TokenizerProvider() {
            tokenizer = new Tokenizer();
            // Do not resolve the ambiguity.
//			tokenizer.setAmbiguitiesResolved(false);

    }



    /**
     * Instantiate a tokenizer provider object.
     *
     * @return a provider object
     */
    public static TokenizerProvider getInstance() {
        if (!instanceFlag) {
            instanceFlag = true;
            provider = new TokenizerProvider();
        }
        return provider;
    }

    /**
     * Returns the tokenizer
     *
     * @return
     */
    public Tokenizer getTokenizer() {
        return tokenizer;
    }
}
