# -*- coding: utf-8 -*-
"""
Created on Wed Nov 20 11:30:07 2019

@author: khadidja
"""
import nltk
from nltk.corpus import brown
from operator import itemgetter
#Ecrire un programme qui trouve tous les mots qui ont au moins trois occurrences dans le Brown Corpus.
#Q1
"""sec_a = b.words()
fd=nltk.FreqDist(sec_a)
for token in fd:
    if fd[token]>=3:
        print(token,fd[token])"""
#pourcentages de tokens/type en incluant l'ensemble des genres du Brown corpus.
"""for token in fd:
    print(token,fd.freq(token))"""
#Q2
"""
cfd = nltk.ConditionalFreqDist((genre, word) for genre in brown.categories() for word in brown.words(categories=genre))
for cat in brown.categories():
    s=0
    for w in cfd[cat]:
        s+=cfd[cat][w]
    
    print(cat,"          ",s/len(cfd[cat]))
cfd.tabulate(conditions=brown.categories(),samples= brown.words()[:9])
print(cfd.plot(conditions=brown.categories(),samples= brown.words()[:9]))
"""
#learned
#Q3

#from nltk.corpus import stopwords
text = nltk.corpus.genesis.words('english-kjv.txt') 
#text = [w for w in text if not w in stopwords.words('english')]
bigrams = nltk.bigrams(text)
fd=nltk.FreqDist(bigrams)
sfd = sorted(fd.items(), key=itemgetter(1), reverse=True)
print([item for item in sfd])

#Q4
"""words=["military","weights","swimming","detective","cold","ambition","luxury","robotics"]
cfd = nltk.ConditionalFreqDist((genre, word) for genre in brown.categories() for word in brown.words(categories=genre))
cfd.tabulate(conditions=brown.categories(),samples=words)
"""
#Q5
"""
def tf(word,txt):
    print(word,nltk.FreqDist(txt)[word])
tf("poison",brown.words(categories="mystery"))
tf("cold",nltk.corpus.genesis.words('english-kjv.txt'))"""

#ngram est une séquence de n elements (mots,phonemes etc)