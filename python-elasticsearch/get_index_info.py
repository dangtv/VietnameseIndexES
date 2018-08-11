#!/usr/bin/env python
# -*- coding: utf-8 -*- 
# https://github.com/ernestorx/es-swapi-test/blob/master/ES%20notebook.ipynb
import requests

import glob
import sys
import json
res = requests.get('http://192.168.56.103:9200')
print(res.content)
index_stats = requests.get('http://192.168.56.103:9200/icu_vn_tokenizer/_stats')
print(index_stats.content)

# from elasticsearch import Elasticsearch
# es = Elasticsearch([{'host': '192.168.56.103', 'port': 9200}])
# res_str= json.dumps(es.get(index="icu_vi_sample", doc_type='my_type', id="AWTLWTmQSyIGGJYBJyMQ",))
# print res_str

results=open("query_result.json", "w+")
results.writelines("\n")
results.write(index_stats.content.encode("utf-8"))
results.write("\n")
results.close()