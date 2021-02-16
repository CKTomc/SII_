# -*- coding: utf-8 -*-
"""
Created on Mon Oct 21 12:20:39 2019

@author: khadidja
"""
"""
#QUESTION 2
length=[]
chomsky=["colorless","green","ideas","sleep","furiously"]
for word in chomsky:
    length.append(len(word))

print(length)

silly="newly formed bland ideas are inexpressible in an infuriating way"
listesilly=silly.split()
print(listesilly)
s=""
for word in listesilly:
    s+=word[1]

print(s)

j=" ".join(listesilly)
print(j)
std=(listesilly.sort())
print(std)

#QUESTION 3
silly="newly formed bland ideas are inexpressible in an infuriating way"
list=silly.split()
pos=list.index("in")
str=" ".join(list[:pos])
print(str) 

#QUESTION 4
sentence="she sells sea shells by the sea shore"
words=sentence.split()
list1=[]
for word in words:
    if word.startswith("sh"):
        print(word)
for word in words:
    if len(word)>4:
        print(word) 
for word in words:
    if word.startswith("se"):
        list1.extend(["like",word])
    else: list1.append(word)
sent=" ".join(list1)       
print(sent) 

#QUESTION 5
vow="aeiou"
text="she sells sea shells by the sea shore"
for v in vow:
    text=text.replace(v,"")
print(text)

#QUESTION 6
d1={"k":"khadidja","c":"chikh"}
d2={"e":"engineering","i":"industry"}
d1.update(d2)
print(d1)
#d1 has been extended with d2 elements
"""
#QUESTION 7
from nltk import defaultdict
count=defaultdict(int)
silly="newly formed bland ideas are inexpressible in an infuriating way"
divided=silly.split()
for word in divided:
     word=word.lower()
     count[word]=+1
for k,v in sorted(count.items()):
     print(k,":",v)
      
        