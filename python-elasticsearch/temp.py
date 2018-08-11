#!/usr/bin/env python
# -*- coding: utf-8 -*- 
# https://github.com/ernestorx/es-swapi-test/blob/master/ES%20notebook.ipynb
import requests

import glob
import sys
import json
res = requests.get('http://192.168.56.103:9200')
print(res.content)
from elasticsearch import Elasticsearch
es = Elasticsearch([{'host': '192.168.56.103', 'port': 9200}])

#====================================================
# delete the index icu_vi_sample
res=requests.delete('http://192.168.56.103:9200/my_vn')
print(res.content)

# create new index my_vn
res = requests.put('http://192.168.56.103:9200/my_vn', data=json.dumps( {
  "settings": {
    "index": {
        "number_of_shards" : 1,
        "number_of_replicas" : 1,        
      "analysis": {
        "analyzer": {
          "my_analyzer": {
            "tokenizer": "jvn_tokenizer",
            "char_filter":  [ "html_strip" ],
            "filter": [
              "icu_folding"
            ]
          }
        }
      }
    }
  },
    "mappings": {
        "my_type": {
            "properties" : {
                "user" : {
                    "type" : "text",
                    "analyzer": "my_analyzer"
                },
                "topic" : {
                    "type" : "text",
                    "analyzer": "my_analyzer"
                },
                "article" : {
                    "type" : "text",
                    "analyzer" : "my_analyzer"
                }
            }            
        }
    }
})
)
print(res.content)

#====================================================

print es.index(index='my_vn', doc_type='my_type',id=4, body={
    "user" : "Dang",
    "introduce" : "Tôi là sinh viên đại học"
    })

print json.dumps(es.get(index='my_vn', doc_type='my_type',id=4), ensure_ascii=False).encode("utf-8")

res = requests.get('http://192.168.56.103:9200/my_vn/_analyze', data=json.dumps(
{
  "analyzer": "my_analyzer",
  "text": "<p>công nghệ Việt Nam</p>"  
}))
print(res.content)
# ==============================