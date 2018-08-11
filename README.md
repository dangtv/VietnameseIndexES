# Elasticsearch Plugins and python scripts for indexing Vietnamese text

## Vietnamese Analysis Plugins for Elasticsearch

In this repository, there are two Vietnamese Analysis plugins for integrating Vietnamese language analysis into Elasticsearch:

- `vietnamese-tokenizer-esplugin`: based on the paper "A hybrid approach to word segmentation of Vietnamese texts"
<!-- [https://github.com/duydo/elasticsearch-analysis-vietnamese/](https://github.com/duydo/elasticsearch-analysis-vietnamese/) -->

  - The plugin provides the `vn_analyzer` analyzer and `vn_tokenizer` tokenizer. The `vn_analyzer` is composed of the `vn_tokenizer` tokenizer, the `lowercase` and `stop` filter.

- `vietnamese-jvnsegmenter-esplugin`: based on the paper "Vietnamese word segmentation with CRFs and SVMs: An investigation"
 <!-- [JVnSegmenter](http://jvnsegmenter.sourceforge.net) -->
  - The plugin provides the `jvn_analyzer` analyzer and `jvn_tokenizer` tokenizer. The `jvn_analyzer` is composed of the `jvn_tokenizer` tokenizer, the `lowercase` and `stop` filter.

Vietnamese text dataset is from [https://github.com/duyvuleo/VNTC](https://github.com/duyvuleo/VNTC)

### Compilation and Installation

- Compilation for the plugin `vietnamese-tokenizer-esplugin` (the same way for `vietnamese-jvnsegmenter-esplugin`)

```bash
    cd vietnamese-tokenizer-esplugin
    mvn clean package
```

- Test:

```bash
    mvn test
```

- Clean:

```bash
    mvn clean
```

- Installation:
  - To install this plugin to a ElasticSearch system, the ES version has to be declared in file `pom.xml` (e.g, if `<elasticsearch.version>5.4.1</elasticsearch.version>`, only ES 5.4.1 can install this plugin)
  - After compilation the binary file for this plugin is `elasticsearch-analysis-vietnamese/target/releases/elasticsearch-analysis-vietnamese-*.zip`.
  
```bash
    sudo /usr/share/elasticsearch/bin/elasticsearch-plugin install file:<absolute_path_of_plugin_binary_file>
```

- Uninstallation: the name of installed plugin is `vietnamese-tokenizer-esplugin` (declared in file `plugin-descriptor.properties`)

```bash
    sudo /usr/share/elasticsearch/bin/elasticsearch-plugin remove vietnamese-tokenizer-esplugin
```

## Python script of index document dataset

- Create new index on ES

```bash
    python recreate_index.py
```

- Index document dataset: the directory of dataset is declared by the variable `dataset` in file index_docs.py

```bash
    python index_docs.py
```

- Query:

```bash
    python query.py
```

## License

This software is licensed under GNU GPLv3 license
