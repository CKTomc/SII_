# -*- coding: utf-8 -*-
"""
Created on Sun Nov  3 20:03:32 2019

@author: khadidja
"""
import nltk
import re
s="A text can be any example of written:a book, the body of an email or the\
words on the back of a cereal box"
s2="qsdfqsdf 2*9.3+7 zar zer 1*9*9 (9.9+9.9), 4.58+(5.6/9)"
d=r"\s(the|a|an)\s"
print(re.findall(d,s))

r="\d+[.]\d+"
e=r"("+r+"[/+\-*]"+r+")"


entier='(+|-)?\d+'
reel='(+|-)?\d+[.]\d+'
nombre='('+entier+'|'+reel+')'
operation='[+\-*/]'
exp_sp=nombre+'('+operation+nombre+')*'
exp_p='\('+exp_sp+'\)'
expd='('+exp_sp+'|'+exp_p+')'
exp=expd+'('+operation+expd+')*'
print(re.findall(exp,s2))
"""

s = "<austen-emma.txt:hart@vmd.cso.uiuc.edu> (internet) <hart@uiucvmd> (bitnet)\
austen-emma.txt:Internet <72600.2026@compuserve.com>; TEL: (212-254-5093)\
austen-persuasion.txt:Editing by Martin Ward <Martin.Ward@uk.ac.durham>\
blake-songs.txt:Prepared by David Price, email <ccx074@coventry.ac.uk>"
"
print(re.findall(r"<(.+)@(.+)>",s))"""

re.findall(r'<[A-Za-z0-9_.-]+@(\w+.\\w+)>',s)
expmail="<