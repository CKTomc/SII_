# -*- coding: utf-8 -*-
"""
Created on Sun Nov 17 20:34:06 2019

@author: khadidja
"""
f="C:\\Users\\khadidja\\Desktop\\SII\\S3\\ri\\docs\\d157.txt"
import nltk
pattern=r"""(?x)[.]+
         |
         [.;:,?!)(]   
         |\[
         |\]
         """
  # print(nltk.tokenize.regexp_tokenize(data, pattern))
#tokenizer= nltk.tokenize.RegexpTokenizer(pattern)
def load(f):
   with open(f,encoding="UTF-8") as src:
        data=src.read() 
        return data
load(f)

regex=r"""(?x)
      (?:\d+(?:.\d)?\s*(?:$/£/dzd)) #money
      |(?:(?:[A-Z][a-z])?[\s][A-Z][a-z]+)
      |(?:\d\d/\d\d|\d\d\d\d)#date
      |(?:\d\d\s[A-Za-z]{3}\s\d\d\d\d)#date #il faut mettre des intervalles pour verifier les dates
      # on peut prendre en consideration les mr mme mlle etc
"""

tokenizer=nltk.tokenize.RegexpTokenizer(regex)
print(tokenizer.tokenize(load(f)))

sent = ['The', 'dog', 'gave', 'John', 'the', 'newspaper']
result = [(word, len(word))for word in sent]
print(result)


from nltk.tokenize import TweetTokenizer
tokenizer= TweetTokenizer()
tokens=tokenizer.tokenize(load(f))
stemmer=nltk.PorterStemmer()
stemmer2=nltk.LancasterStemmer()

stems = []
for t in tokens:
    st = stemmer.stem(t)
    st2=stemmer2.stem(t)
    if(st!=st2):
        print("Porter stemmer:",st,"Lancaster stemmer:",st2,"token:",t)

words = ['attribution', 'confabulation', 'elocution', 
         'sequoia', 'tenacious', 'unidirectional']
vsequences = sorted(set([''.join(vowels) for vowels in
            [[char for char in word if char in 'aeiou'] 
            for word in words]]))
print(vsequences)