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

# ==============================
# delete the index icu_vn_tokenizer
res=requests.delete('http://192.168.56.103:9200/icu_vn_tokenizer')
print(res.content)

# create new index icu_vn_tokenizer
res = requests.put('http://192.168.56.103:9200/icu_vn_tokenizer', data=json.dumps( {
  "settings": {
    "index": {
        "number_of_shards" : 1,
        "number_of_replicas" : 1,        
      "analysis": {
        "analyzer": {
          "my_analyzer": {
            "tokenizer": "vn_tokenizer",
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
# ==============================


# ==============================
# delete the index icu_jvn_tokenizer
res=requests.delete('http://192.168.56.103:9200/icu_jvn_tokenizer')
print(res.content)

# create new index icu_jvn_tokenizer
res = requests.put('http://192.168.56.103:9200/icu_jvn_tokenizer', data=json.dumps( {
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
# ==============================