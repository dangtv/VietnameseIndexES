package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.vi.VietnameseAnalyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class VietnameseAnalyzerProvider extends AbstractIndexAnalyzerProvider<VietnameseAnalyzer> {
    private final VietnameseAnalyzer analyzer;

    public VietnameseAnalyzerProvider(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new VietnameseAnalyzer(Analysis.parseStopWords(environment, settings, VietnameseAnalyzer.getDefaultStopSet(), true));
    }

    @Override
    public VietnameseAnalyzer get() {
        return analyzer;
    }
}
