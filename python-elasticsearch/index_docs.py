#!/usr/bin/env python
# -*- coding: utf-8 -*- 
# https://github.com/ernestorx/es-swapi-test/blob/master/ES%20notebook.ipynb
import requests

import glob
import sys
import json

from elasticsearch import Elasticsearch
es = Elasticsearch([{'host': '192.168.56.103', 'port': 9200}])


# print es.index(index='icu_vi_sample', doc_type='my_type',id=4, body={
#     "user" : "Dang",
#     "introduce" : "Tôi là sinh viên đại học"
#     })

# print es.get(index='icu_vi_sample', doc_type='my_type',id=4)

# es.search(index="icu_vi_sample", doc_type='my_type', body={
#     "query": {
#         "match": {
#             "introduce": {
#                 "query": "Tôi là sinh viên đại học",
#                 "analyzer": "my_analyzer"
#             }
#         }
#     },
#     "highlight": {
#         "fields": {
#                 "introduce" : {
#                     "fragment_size" : 150,
#                     "number_of_fragments" : 3
#                 }
#         }
#     }
# })


# es.delete(index='icu_vi_sample', doc_type='my_type', id=4)
import os
dataset = 'data'
topic_lst = os.listdir(dataset)
for topic in topic_lst:
  for f_name in glob.glob(dataset+"/"+topic+"/*.txt"):
    print "reading file "+ f_name + "\n"
    f=open(f_name, "r")
    doc = f.read().decode("utf-16").encode("utf-8")
    # print doc
    try:
      es.index(index='icu_vn_tokenizer', doc_type='my_type', body={
      "user" : "Tran",
      "topic" : topic,
      "article" : doc
      })
    except Exception as ex:
      print "error when indexing file "+ f_name + ": "
      print str(ex)
      log=open("log.txt", "a+")
      log.write("error when indexing file "+ f_name + ":  ")
      log.write(str(ex))
      log.write("\n")
      log.close()
 
dataset = 'data2'
topic_lst = os.listdir(dataset)
for topic in topic_lst:
  for f_name in glob.glob(dataset+"/"+topic+"/*.txt"):
    print "reading file "+ f_name + "\n"
    f=open(f_name, "r")
    doc = f.read().decode("utf-16").encode("utf-8")
    # print doc
    try:
      es.index(index='icu_vn_tokenizer', doc_type='my_type', body={
      "user" : "Tran",
      "topic" : topic,
      "article" : doc
      })
    except Exception as ex:
      print "error when indexing file "+ f_name + ": "
      print str(ex)
      log=open("log.txt", "a+")
      log.write("error when indexing file "+ f_name + ":  ")
      log.write(str(ex))
      log.write("\n")
      log.close()