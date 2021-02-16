# -*- coding: utf-8 -*-
"""
Created on Mon Dec 16 09:53:40 2019

@author: khadidja
"""
from operator import itemgetter
import nltk
text=nltk.corpus.brown.tagged_words(categories="adventure")

#liste triée des mots ayant MD
"""
liste=[]
for (w,tg) in text:
    if tg=="MD":
        liste.append(w.lower())
liste=sorted(set(liste))
print(liste)

#plural noun vs verb
liste={}
for (w,tg) in text:
    if tg=="VBZ" or tg=="NNS":
       if liste.get(w)!=None:       
          liste[w.lower()].append(tg)
       else:
          liste[w.lower()]=[]

liste=sorted(set(liste))
for i in liste:
    if len(liste[i])>1:
        print(liste[i])

"""
"""
liste=[]
for t in nltk.trigrams(text):
    if t[0][1]=="IN" and t[1][1]=='AT' and t[2][1] =="NN":
        l=[(t[0][0],t[1][0],t[2][0])]
        liste.append(l)   
print(liste)
"""
#print(text)
"""listefem=0
listemas=0

for t in text:
    
    if t[1]=="PRON":        
        if t[0].lower()=="she":
            listefem+=1
        elif t[0].lower()=="he":
               listemas+=1

print(listefem)
"""
fd = nltk.FreqDist(nltk.corpus.brown.words(categories='adventure')) 
most_freq_words = sorted(fd.items(), key=itemgetter(1), reverse=True)[:100]
likely_tags = dict((word, most_freq_words[word].max()) for (word,v) in most_freq_words)
baseline_tagger = nltk.UnigramTagger(model=likely_tags, backoff=nltk.DefaultTagger('NN'))
sent = nltk.corpus.brown.sents(categories='adventure')[2007]
baseline_tagger.tag(sent)