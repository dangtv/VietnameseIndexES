package org.elasticsearch;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.vi.VietnameseTokenizer;
import vn.hus.nlp.sd.IConstants;
import vn.hus.nlp.sd.SentenceDetector;
import vn.hus.nlp.sd.SentenceDetectorFactory;
import vn.hus.nlp.tokenizer.TokenizerProvider;
import vn.hus.nlp.tokenizer.tokens.TaggedWord;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyTest {

//    public void testSimpleVietnameseAnalysis() throws IOException {
//        TestAnalysis analysis = createTestAnalysis();
//        ESTestCase.assertNotNull(analysis); ESTestCase.assertNotNull(analysis);
//
//        TokenizerFactory tokenizerFactory = analysis.tokenizer.get("vn_tokenizer");
//        ESTestCase.assertNotNull(tokenizerFactory);
//        ESTestCase.assertThat(tokenizerFactory, instanceOf(VietnameseTokenizerFactory.class));
//
//        NamedAnalyzer analyzer = analysis.indexAnalyzers.get("vn_analyzer");
//        ESTestCase.assertNotNull(analyzer);
//        ESTestCase.assertThat(analyzer.analyzer(), instanceOf(VietnameseAnalyzer.class));
//
//        analyzer = analysis.indexAnalyzers.get("my_analyzer");
//        ESTestCase.assertNotNull(analyzer);
//        ESTestCase.assertThat(analyzer.analyzer(), instanceOf(CustomAnalyzer.class));
//        ESTestCase.assertThat(analyzer.analyzer().tokenStream(null, new StringReader("")), instanceOf(VietnameseTokenizer.class));
//
//    }

    private final boolean accept(TaggedWord word) {
        final String token = word.getText();
        if (token.length() == 1) {
            return Character.isLetterOrDigit(token.charAt(0));
        }
        return true;
    }

    @Test
    public void testVietnameseTokenizer() throws IOException {

//        Tokenizer tokenizer = new VietnameseTokenizer();
//        assertNotNull(tokenizer);
        Iterator<TaggedWord> taggedWords;
        StringReader input = new StringReader("công nghệ Việt Nam, bất kì 1 USD tăng_trưởng nào của ASEAN cũng có 6 cent xuất_khẩu của Nhật_Bản tới ASEAN ");
//        tokenizer.setReader(input);
        vn.hus.nlp.tokenizer.Tokenizer vnTokenizer = TokenizerProvider.getInstance().getTokenizer();
        vnTokenizer.setAmbiguitiesResolved(true);
        boolean sentenceDetectorEnabled = true;
        SentenceDetector sentenceDetector = SentenceDetectorFactory.create(IConstants.LANG_VIETNAMESE);
        if (sentenceDetectorEnabled) {
            final List<TaggedWord> words = new ArrayList<TaggedWord>();
            final String[] sentences = sentenceDetector.detectSentences(input);
            for (String s : sentences) {
                vnTokenizer.tokenize(new StringReader(s));
                words.addAll(vnTokenizer.getResult());
            }
            taggedWords = words.iterator();
        } else {
            vnTokenizer.tokenize(input);
            taggedWords = vnTokenizer.getResult().iterator();
        }
        while (taggedWords.hasNext()) {
            final TaggedWord word = taggedWords.next();
            if (accept(word)) {
                System.out.println(word.getText()+": "+word.getRule().getName());

            }
        }
        assertTrue(true);
//        assertTokenStreamContents(tokenizer, new String[]{"Công nghệ thông tin", "Việt","Nam"});
    }

//    public void testVietnameseAnalyzer() throws IOException {
//        TestAnalysis analysis = createTestAnalysis();
//        NamedAnalyzer analyzer = analysis.indexAnalyzers.get("vi_analyzer");
//        ESTestCase.assertNotNull(analyzer);
//
//        TokenStream ts = analyzer.analyzer().tokenStream("test", "Công nghệ ba con vịt thông tin Việt Nam");
//        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
//        ts.reset();
//        for (String expected : new String[]{"công nghệ", "ba", "con", "vịt", "thông tin", "việt", "nam"}) {
//            ESTestCase.assertThat(ts.incrementToken(), equalTo(true));
//            ESTestCase.assertThat(term.toString(), equalTo(expected));
//        }
//        ESTestCase.assertThat(ts.incrementToken(), equalTo(false));
//    }

//    public TestAnalysis createTestAnalysis() throws IOException {
//        String json = "/org/elasticsearch/index/analysis/vi_analysis.json";
//        Settings settings = Settings.builder()
//                .loadFromStream(json, Test.class.getResourceAsStream(json))
//                .put(IndexMetaData.SETTING_VERSION_CREATED, Version.CURRENT)
//                .build();
//        Settings nodeSettings = Settings.builder().put(Environment.PATH_HOME_SETTING.getKey(), ESTestCase.createTempDir()).build();
//        return createTestAnalysis(new Index("test", "_na_"), nodeSettings, settings, new AnalysisVietnamesePlugin());
//    }

//    public static void main(String[] args) {
//        System.out.print("hehe");
//    }
}

