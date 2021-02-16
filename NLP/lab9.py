# -*- coding: utf-8 -*-
"""
Created on Wed Jan  8 12:48:44 2020

@author: khadidja
"""

import nltk
from nltk.grammar import CFG
sent="Karim arrived or Dana left and everyone cheered"
sent1="(Karim arrived or Dana left) and everyone cheered"

grammar1 = CFG.fromstring ("""
S -> NP VP S | NP VP 
NP ‐> 'Karim' | 'Dana' | 'everyone'
VP ‐> V | V P
V ‐> 'arrived' | 'left' | 'cheered'
P ‐> 'and' | 'or' 
""")

sent=sent.split()
rd_parser=nltk.RecursiveDescnetParser(grammar1)
for tree in rd_parser. parse(sent):
    print(tree)
"""sent1=sent1.split()
rd_parser=nltk.RecursiveDescnetParser(grammar1)
for tree in rd_parser. parse(sent):
    print(tree)"""

#continue and send
    #enrichir la grammaire vers le haut: s ou s , s and s etc