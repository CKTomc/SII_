# -*- coding: utf-8 -*-
"""
Created on Sat Nov  9 10:27:36 2019

@author: khadidja
"""
"""file="C:\\Users\\khadidja\\Desktop\\SII\\S3\\ri\\d238.txt"
with open (file,encoding="latin-1")as src:
    lines=src.read().split("\n")
    for line in lines[::-1]:
        print (line)"""

"""from nltk.corpus import gutenberg
words=gutenberg.words("austen-emma.txt")
for word in words:
    if word.startswith("wh") or ( word.startswith("Wh") :
        print(word)"""
""" list_pos=nltk.pos_tag(file)
for word_pos in list_pos: 
    if word_pos[1] in """
""" 
import re
from urllib.request import urlopen
from bs4 import BeautifulSoup
page = urlopen("http://www.usthb.dz/").read().decode("utf-8")
page = re.sub(r'<(.|\n)*?>','', page)
#amelioration
page=re.sub(r'\s\s+','\s',page)
page=re.sub(r'<[!](.|\n)*?>', '',page)
page=re.sub(r'<div(.|\n)*?>','',page)
print(page)
#result = BeautifulSoup(new, "lxml")
#print(result.get_text())"""
import re
from urllib.request import urlopen
page = urlopen("http://www.usthb.dz/").read().decode("utf-8")
page=re.findall("(?<script.*?>|<script>\s*).*\s*</script>",page)