# -*- coding: utf-8 -*-
"""
Created on Sat Dec  7 11:49:01 2019

@author: khadidja
"""
import nltk
from nltk.corpus import brown
from operator import itemgetter
#exo1
def findtags(plr,sing, tagged_text):
 cfd = nltk.ConditionalFreqDist()
 for (wd, tg) in tagged_text:
    wd=wd[:len(wd)-1]
    if tg==sing:
        cfd[wd]["sing"] += 1
    if tg==plr:
        cfd[wd]["plr"]+=1      
    
 tagdict = {}
 for word in cfd:
    if cfd[word]["sing"]< cfd[word]["plr"]:
        tagdict[word] =cfd[word]["plr"]
 return tagdict

"""wordict=findtags("NNS","NN",brown.tagged_words(categories='adventure')) 
for i in wordict:
    print(i,wordict[i])"""

def pgntagdist(tagged_text):
    wdict=nltk.defaultdict(list)
    max="a"
    for (wd, tg) in tagged_text:
        if not (tg in wdict[wd]):
            wdict[wd].append(tg)
        if len(wdict[wd])>len(max):
            max=wd
    return max,wdict[max]

"""print(pgnetdist(brown.tagged_words(categories='adventure')))"""

def tagfreq(tagged_text):
    tagdict = nltk.defaultdict(int)
    for tg in tagged_text:
      tagdict[tg[1]]+=1
    tagdict=sorted(tagdict.items(), key=itemgetter(1), reverse=True)
    items = [l[0] for l in list(tagdict)[:20]]
    return items

"""print(tagfreq(brown.tagged_words(categories='adventure')))"""

def tagavantnn(tagged_text,tag):
    tagdict = nltk.defaultdict(int)
    for i in range(len(tagged_text)-1):
        if tagged_text[i+1][1]==tag:
            tagdict[tagged_text[i][1]]+=1
    tagdict=sorted(tagdict.items(), key=itemgetter(1), reverse=True)
    return tagdict

print(tagavantnn(brown.tagged_words(),'NN'))

#exo2
def nonambg(tagged_text):
    wdict=nltk.defaultdict(list)
    for (wd, tg) in tagged_text:
        if not tg in wdict[wd]:
            wdict[wd].append(tg)
    cpt=0
    types=[]
    for i in wdict:
        if len(wdict[i])==1:
            cpt+=1
            if not (wdict[i][0] in types):
                types.extend(wdict[i])
    print(cpt/len(wdict)*100,"% des types de mots sont non ambigus:",types)
    print(len(wdict)-cpt,"types de mots sont ambigus.")
    print(100- cpt/len(wdict)*100,"% des types de mots sont ambigus") 

"""nonambg(brown.tagged_words(categories='adventure'))"""