#!/usr/bin/env python
# -*- coding: utf-8 -*- 

# three indexes in ES:
# icu_vi_sample
# icu_jvn_tokenizer
# icu_vn_tokenizer

import requests

import glob
import sys
import json
res = requests.get('http://192.168.56.103:9200')
print(res.content)
from elasticsearch import Elasticsearch
es = Elasticsearch([{'host': '192.168.56.103', 'port': 9200}])
# print es.get(index='icu_vi_sample', doc_type='my_type',id=4)
 
results=open("vn_query_result_5.json", "w+")
res_str= json.dumps(es.search(index="icu_vn_tokenizer", doc_type='my_type', body= {
    "from" : 0, "size" : 10,
"query": {
    "match": {
        "article": {
            "query": "SEA Games 23 năm 2005 Bùi Thị Nhung huy chương vàng kỷ lục nhảy cao",
            "analyzer": "my_analyzer"
        }
    }
}
# ,
# "highlight": {
#     "fields": {
#             "introduce" : {
#                 "fragment_size" : 150,
#                 "number_of_fragments" : 3
#             }
#     }
# }
}), ensure_ascii=False  )
print res_str
results.writelines("\n")
results.write(res_str.encode("utf-8"))
results.write("\n")

# # get term vector of a document
# res = requests.get('http://192.168.56.103:9200/icu_vi_sample/my_type/AWTLWTmQSyIGGJYBJyMQ/_termvectors?fields=article')
# # print(res.content)
# results.writelines("\n")
# results.write(res.content.encode("utf-8"))
# results.write("\n")

results.close()