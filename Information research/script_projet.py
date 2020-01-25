# -*- coding: utf-8 -*-
"""
Created on Tue Nov 19 10:20:40 2019

@author: khadidja
"""
import time
import pickle
import math
from nltk.tokenize import TweetTokenizer
from nltk import defaultdict
from nltk.corpus import stopwords 
import operator
from operator import itemgetter

ind_file_path="docs_index.pickle"
rev_file_path="reversed_file.pickle"
w_ind_file_path="w_docs_index.pickle"
w_rev_file_path="w_reversed_file.pickle"

queries_index_file="queries.pickle"
true_pertinent_docs_file="true_pertinent_docs.pickle"

internalproduct_results_file="internalproduct_results.pickle"
intpro_fmesures_file="intpro_fmesures_file.pickle"

dicecoeff_results_file="dicecoeff_results.pickle"
dicco_fmesures_file="dicco_fmesures.pickle"

cosinus_results_file="cosinus_results.pickle"
cos_fmesures_file="cos_fmesures.pickle"

jaccard_results_file="jaccard_results.pickle"
jac_fmesures_file="jac_fmesures.pickle"

evaluation_file="queries_sizes_thresh_fmesures.pickle"
best_queries_metrics_file="best_queries_metrics.pickle"
bestofbest_metrics_file="bestofbest_metrics.pickle"

queries_size_recpre_file="queries_size_recpre.pickle"
size_means_file="size_means.pickle"

pertinence_file="qrels.text"
name="cacm.all"
queries_file="query.text"
punc={"!":0,"#":0,"$":0,"%":0,"&":0,"(":0,")":0,"*":0,"+":0,",":0,".":0,"-":0,"/":0,":":0,";":0,"<":0,"=":0,">":0,"?":0,"@":0,"[":0,"]":0,"^":0,"_":0,"`":0,"{":0,"|":0,"}":0,"~":0,"«":0,"»":0,"°":0,"“":0,"”":0,"’":0,"%":0,"\"":0}

stpwrd=stopwords.words('english')    
stpwrdict=defaultdict(int)
for w in stpwrd:       
     stpwrdict[w]=0
t=TweetTokenizer()

     
#saves an index into a specified file path
def save_index(index,indexpath):
    with open(indexpath,'wb') as src:
          pickle.dump(index,src,protocol=pickle.HIGHEST_PROTOCOL)
          
#rebuilds the index stored in a specified file
def rebuild_index(indorrev_file):
    with open(indorrev_file,'rb') as src:
        index=pickle.load(src)
    return index
  
#creates the docs index
def ind_doc(name):
  index={}
  text=False 
  starttime=time.time() 
  with open(name,encoding="utf-8") as src:     
    for line in src:
        if line.startswith('.I'):
            num=int(line[3:len(line)])          
            index[num]={}
            text=False
        if line.startswith('.W') or line.startswith('.T'):
            text=True
            continue
        if line.startswith('.N') or line.startswith('.X') or line.startswith('.B') or line.startswith('.A'):
            text=False
        if text==True:
            ttl=t.tokenize(line)
            for word in ttl:
                word=word.lower()
                if punc.get(word)==None and stpwrdict.get(word)==None:
                    if index[num].get(word)==None:
                       index[num][word]=1
                    else: 
                       index[num][word]+=1            
       
    endtime=time.time()
    print("The docs index creation took: ",endtime-starttime," seconds")
    save_index(index,ind_file_path)

#creates the reversed index from the docs index file and saves it into a binary file
def reversed_file():
    index=rebuild_index(ind_file_path)
    inv={}
    starttime=time.time()
    for i in index:
        for j in index[i]:
            if inv.get(j)==None:
                inv[j]={}                
            inv[j][i]=index[i][j]
    endtime=time.time()
    print("The reversed file creation took: ",endtime-starttime," seconds")
    save_index(inv,rev_file_path) 
    
#returns a specified doc words with their frequencies,using the docs index stored previously
def access1(numdoc): 
    starttime=time.time()
    index=rebuild_index(ind_file_path)
    endtime=time.time()
    print("Access 1 took: ",endtime-starttime," seconds")
    if index[numdoc]!=None:
        return index[numdoc]
    else :
        return None
#returns a specified word (doc,frequency),using the reversed index stored previously      
def access2(word):
    starttime=time.time()
    index=rebuild_index(rev_file_path)
    endtime=time.time()
    print("Access 2 took: ",endtime-starttime," seconds")
    if index[word]!=None:
        return index[word] 
    else :
        return None
#returns and saves a new docs index and a new reversed index that hold weights instead of frequencies into binary files
#using the docs index and the reversed index stored previously      
def weights_indexes():
    starttime=time.time()
    indexwords=rebuild_index(rev_file_path)
    indexdocs=rebuild_index(ind_file_path)
    windexdocs=indexdocs
    n=len(indexdocs)    
    for i in indexwords:
        ni=len(indexwords[i])
        for j in indexwords[i]:
            indexwords[i][j]=(indexwords[i][j]/max(indexdocs[j].values()))*math.log10((n/ni)+1)
            windexdocs[j][i]=(indexdocs[j][i]/max(indexdocs[j].values()))*math.log10((n/ni)+1)
    endtime=time.time()
    print("The weighted index and reversed file creation took: ",endtime-starttime," seconds")
    save_index(windexdocs,w_ind_file_path)
    save_index(indexwords,w_rev_file_path)

def boolean_model(query,ind_file_path):
    index=rebuild_index(ind_file_path)
    starttime=time.time()
    docs={}
    query=t.tokenize(query)#list that holds the query words
    
    #verify if query is written correctly
    for ind in index:
        #evaluation
        res=''        
        for i in query:
            if i!="and" and i!="or" and i!="not" and i!='(' and i!=')' :
                if index[ind].get(i)==None:
                    res+=' 0'
                else:
                  res+=' 1'  
            else:
                res+=' '+i
        try:
            if(eval(res)==True or eval(res)==1):
               docs[ind]=0  
        except SyntaxError:
            return 0
    endtime=time.time()
    print("The boolean query execution took: ",endtime-starttime," seconds")
    return docs

#creates and saves an index that holds all the queries such as number:query
def queries_index(queries_file):
   index={}
   starttime=time.time()
   text=False
   with open(queries_file,encoding="utf-8") as src:      
    for line in src:
        if(line.startswith('.I')):
            num=int(line[3:len(line)])
            index[num]=''
            text=False
        if(line.startswith('.W')):
            text=True
            continue
        if line.startswith('.T') or line.startswith('.N') or line.startswith('.X') or line.startswith('.B') or line.startswith('.A'):
            text=False
        if(text==True):
            words=t.tokenize(line)
            for i in range(len(words)):
                if punc.get(words[i])==None and stpwrdict.get(words[i].lower())==None:
                    index[num]=index[num]+words[i].lower()+' '                  
    endtime=time.time()
    print(index)
    print("The queries index creation took: ",endtime-starttime," seconds")
    save_index(index,queries_index_file)      
    

#creates and saves an index that holds the queries real pertinent docs using the qrels file
def true_pertinent_docs(pertinence_file):
    queriesresults={}
    starttime=time.time()
    with open(pertinence_file,"r") as src:
        for line in src:
            l=t.tokenize(line[0:7])
            n=int(l[0])
            doc=int(l[1])
            if queriesresults.get(n)==None:
                queriesresults[n]={}
            queriesresults[n][doc]=0
    endtime=time.time()
    print(queriesresults)
    print("The queries results index creation took: ",endtime-starttime," seconds")
    save_index(queriesresults,true_pertinent_docs_file) 


#creates and saves an index that holds the internal product application results such as
#{query1:{doc1:rsv,doc2:rsv,...},query2:{..},...}
def vector_model1(queries_index_file,w_ind_file_path):
    """produit interne"""
    internalproduct_results={}
    internalproduct_fmesures={}
    
    starttime=time.time()
    realresults=rebuild_index(true_pertinent_docs_file)
    queries=rebuild_index(queries_index_file) #queries index
    index=rebuild_index(w_rev_file_path) #weighted docs index    
    
    for i in range(1,len(queries)+1):
        lqdq=0
        querydict={} #to store the docs-similarity resulting from the query
        q=t.tokenize(queries[i])  #list holding query words  
        for term in q:
           if index.get(term)!=None:
             for doc in index[term]:
                #calculating the rsv
                rsv=index[term][doc]
                if(querydict.get(doc)==None):
                    querydict[doc]=rsv #storing current document:rsv in the query dictionary
                else:
                    querydict[doc]=querydict[doc]+rsv
                if realresults.get(i)!=None and realresults[i].get(doc)!=None:
                            lqdq+=1    
        if(lqdq!=0):
            recall=lqdq/len(realresults[i])
            precision=lqdq/len(querydict)        
            fmesure=2*recall*precision/(recall+precision)
            internalproduct_fmesures[i]=(recall,precision,fmesure)                    
        else:
            internalproduct_fmesures[i]=(0,0,0)
            
        if(len(querydict)!=0):
           querydict=sorted(querydict.items(), key=itemgetter(1), reverse=True) #sorting the docs/rsv of the current query
           internalproduct_results[i]=dict(querydict) #storing the query and its docs in this model dictionary
    endtime=time.time()
    
    print("The internal product results and fmesures indexes  creation took: ",endtime-starttime," seconds")
    save_index(internalproduct_results,internalproduct_results_file)
    save_index(internalproduct_fmesures,intpro_fmesures_file)
   
    
#creates and saves an index that holds the dice coeff application results such as
#{query1:{doc1:rsv,doc2:rsv,...},query2:{..},...}    
def vector_model2(queries_index_file,w_ind_file_path):
    """coef de dice"""
    dicecoeff_results={}
    dicecoeff_fmesures={}
       
    starttime=time.time()
    realresults=rebuild_index(true_pertinent_docs_file)
    queries=rebuild_index(queries_index_file) #queries index
    index=rebuild_index(w_rev_file_path) #weighted docs index
    
    for i in range(1,len(queries)+1):
        s={}
        ssq={}
        lqdq=0
        q=t.tokenize(queries[i])  #list holding query words  
        querydict={}#to store the docs-similarity resulting from the query
        l=len(q) #nbr termes
        for term in q:                     
            if index.get(term)!=None:
              for doc in index[term]:
                  if realresults.get(i)!=None and realresults[i].get(doc)!=None:
                            lqdq+=1
                 #calculating the rsv
                  if s.get(doc)==None:
                     s[doc]=index[term][doc]
                     ssq[doc]=index[term][doc]**2
                  else:
                     s[doc]+=index[term][doc]
                     ssq[doc]+=index[term][doc]**2
        for doc in s:
            s[doc]=2*s[doc]/(ssq[doc]+l)
            querydict[doc]=s[doc]  #storing current document:rsv in the query dictionary
        if lqdq!=0:
            recall=lqdq/len(realresults[i])
            precision=lqdq/len(querydict)
            fmesure=2*recall*precision/(recall+precision)
            dicecoeff_fmesures[i]=(recall,precision,fmesure)
        else:
            dicecoeff_fmesures[i]=(0,0,0)
            
        if(len(querydict)!=0):    
            querydict=sorted(querydict.items(), key=itemgetter(1), reverse=True) #sorting the docs/rsv of the current query
            dicecoeff_results[i]=dict(querydict) #storing the query and its docs in this model dictionary    
    endtime=time.time()
    print("The dice coef results and fmesures indexes  creation took: ",endtime-starttime," seconds")
    save_index(dicecoeff_results,dicecoeff_results_file)
    save_index(dicecoeff_fmesures,dicco_fmesures_file)

      
#creates and saves an index that holds the cosinus application results such as
#{query1:{doc1:rsv,doc2:rsv,...},query2:{..},...}    
def vector_model3(queries_index_file,w_ind_file_path):
    """cosinus"""
    cosinus_results={}
    cosinus_fmesures={}
       
    starttime=time.time()
    realresults=rebuild_index(true_pertinent_docs_file)
    queries=rebuild_index(queries_index_file) #queries index
    index=rebuild_index(w_rev_file_path) #weighted docs index
    
    for i in range(1,len(queries)+1):
        s={}
        ssq={}
        lqdq=0
        q=t.tokenize(queries[i])  #list holding query words  
        querydict={}#to store the docs-similarity resulting from the query
        l=len(q) #nbr termes
        for term in q:                     
            if index.get(term)!=None:
              for doc in index[term]:
                  if realresults.get(i)!=None and realresults[i].get(doc)!=None:
                            lqdq+=1
                 #calculating the rsv
                  if s.get(doc)==None:
                     s[doc]=index[term][doc]
                     ssq[doc]=index[term][doc]**2
                  else:
                     s[doc]+=index[term][doc]
                     ssq[doc]+=index[term][doc]**2
        for doc in s:
            s[doc]=s[doc]/math.sqrt((ssq[doc]*l))
            querydict[doc]=s[doc]  #storing current document:rsv in the query dictionary
        if lqdq!=0:
            recall=lqdq/len(realresults[i])
            precision=lqdq/len(querydict)
            fmesure=2*recall*precision/(recall+precision)
            cosinus_fmesures[i]=(recall,precision,fmesure)
                  
        else:
            cosinus_fmesures[i]=(0,0,0)
        
        if len(querydict)!=0:
            querydict=sorted(querydict.items(), key=itemgetter(1), reverse=True) #sorting the docs/rsv of the current query
            cosinus_results[i]=dict(querydict) #storing the query and its docs in this model dictionary    
    endtime=time.time()
    print("The cosinus results and fmesures indexes  creation took: ",endtime-starttime," seconds")
    save_index(cosinus_results,cosinus_results_file)
    save_index(cosinus_fmesures,cos_fmesures_file)        
            

#creates and saves an index that holds the jaccard application results such as
#{query1:{doc1:rsv,doc2:rsv,...},query2:{..},...}
def vector_model4(queries_index_file,w_ind_file_path):
    """jaccard"""
    jaccard_results={}
    jaccard_fmesures={}
       
    starttime=time.time()
    realresults=rebuild_index(true_pertinent_docs_file)
    queries=rebuild_index(queries_index_file) #queries index
    index=rebuild_index(w_rev_file_path) #weighted docs index
    
    for i in range(1,len(queries)+1):
        s={}
        ssq={}
        lqdq=0
        q=t.tokenize(queries[i])  #list holding query words  
        querydict={}#to store the docs-similarity resulting from the query
        l=len(q) #nbr termes
        for term in q:                     
            if index.get(term)!=None:
              for doc in index[term]:
                  if realresults.get(i)!=None and realresults[i].get(doc)!=None:
                            lqdq+=1
                 #calculating the rsv
                  if s.get(doc)==None:
                     s[doc]=index[term][doc]
                     ssq[doc]=index[term][doc]**2
                  else:
                     s[doc]+=index[term][doc]
                     ssq[doc]+=index[term][doc]**2
        for doc in s:
            s[doc]=s[doc]/(ssq[doc]+l-s[doc])
            querydict[doc]=s[doc]  #storing current document:rsv in the query dictionary
        if lqdq!=0:
            recall=lqdq/len(realresults[i])
            precision=lqdq/len(querydict)
            fmesure=2*recall*precision/(recall+precision)
            jaccard_fmesures[i]=(recall,precision,fmesure)
        
        else:
            jaccard_fmesures[i]=(0,0,0)
        
        if len(querydict)!=0:
             querydict=sorted(querydict.items(), key=itemgetter(1), reverse=True) #sorting the docs/rsv of the current query
             jaccard_results[i]=dict(querydict) #storing the query and its docs in this model dictionary    
   
    endtime=time.time()
    print("The jaccard results and fmesures indexes  creation took: ",endtime-starttime," seconds")
    save_index(jaccard_results,jaccard_results_file)
    save_index(jaccard_fmesures,jac_fmesures_file)   
  

#creates and saves an index that holds: {(size,threshold):{q1:(recall,prec,fmesure),..},(size,threshold):{q1:fmesure,...},...}
def evalfmesures(cosinus_results_file):
    queries_evaluation={}
    thresholds=[s/10 for s in range(0,10)]
    
    starttime=time.time()
    ourresults=rebuild_index(cosinus_results_file)
    realresults=rebuild_index(true_pertinent_docs_file)   
    
    for q in range(1,len(ourresults)+1):
        queries_evaluation[q]={}
        
        #docs=list(ourresults[q]) 
        docs={}       
        for doc in ourresults[q]:
            docs[doc]=0            
            for t in thresholds:
               lqdq=0
               docret=0
               for d in docs:               
                   if ourresults[q][d]>=t:
                       if realresults.get(q)!=None and realresults[q].get(d)!=None :
                          lqdq+=1
                   docret+=1
                   
               if lqdq==0:
                  recall=0
                  precision=0
                  fmesure=0
               elif docret==0 :
                   recall=lqdq/len(realresults[q])
                   precision=0
                   fmesure=2*recall*precision/(recall+precision)
               else:
                   precision=lqdq/docret
                   recall=lqdq/len(realresults[q])
                   fmesure=2*recall*precision/(recall+precision)               
            
               queries_evaluation[q][(len(docs),t)]=(recall,precision,fmesure)               
           
 
    endtime=time.time()
 
    print("The queries-size-thres-recall-precision-fmesures index creation took: ",endtime-starttime," seconds")         
    save_index(queries_evaluation,evaluation_file)

#creates and saves an index that holds the best metric(s) for each query
##creates and saves a list that holds the best metric(s) of the best metrics
def bestofbestmetrics(evaluation_file):
    bestqueriesmetrics={}
    bob=defaultdict(int)
    bestofbest=[]
    
    starttime=time.time()
    queries_fmesures=rebuild_index(evaluation_file)    

    for q in range(1,len(queries_fmesures)+1):
        bestmetrics={} #for current query
        #best=max(queries_fmesures[q].items(), key=operator.itemgetter(1))[0]   
        best=max(queries_fmesures[q].keys(), key=lambda x: queries_fmesures[q][x][2])
        for m in queries_fmesures[q]:
            if queries_fmesures[q][m][2]==queries_fmesures[q][best][2]:
                bestmetrics[m]=queries_fmesures[q][m]
                bob[m]+=1
        bestqueriesmetrics[q]=bestmetrics    
     
    best=max(bob.items(), key=operator.itemgetter(1))[0]
    for b in bob:
         if bob[b]==bob[best]:
             bestofbest.append(b)
    endtime=time.time()
    
    print("The best-queries-metrics and bestofbest indexes creation took: ",endtime-starttime," seconds")         
    save_index(bestqueriesmetrics,best_queries_metrics_file)
    save_index(bestofbest,bestofbest_metrics_file)       
             
def recallprecision_curve():
    queries_size_recpre={}
    sizesrecpre={}
    size_means={}
    starttime=time.time()
    evaluation=rebuild_index(evaluation_file)   
    
    for q in range(1,len(evaluation)+1):
        queries_size_recpre[q]={}
        for m in evaluation[q]:
            if m[1]==0.0:
                queries_size_recpre[q][m[0]]=evaluation[q][m]
                if sizesrecpre.get(m[0])==None: sizesrecpre[m[0]]=[]
                sizesrecpre[m[0]].append(evaluation[q][m])
            
        
    for size in sizesrecpre:
        r=0
        p=0
        for recpre in range(len(sizesrecpre[size])):
            r=r+sizesrecpre[size][recpre][0]
            p=p+sizesrecpre[size][recpre][1]
        size_means[size]=(r/len(sizesrecpre[size]),p/len(sizesrecpre[size]))
    endtime=time.time()    
    
    print("The queries-size-recall-precision values and means indexes creation took: ",endtime-starttime," seconds")         
    save_index(queries_size_recpre,queries_size_recpre_file)
    save_index(size_means,size_means_file) 
            
"""gui"""
import sys
from PyQt5 import QtWidgets, uic, QtCore
import pyqtgraph as pg
from pyqtgraph import PlotWidget, plot
import os

class Ui(QtWidgets.QMainWindow):
    def __init__(self):
        super(Ui, self).__init__()
        uic.loadUi('gui.ui', self)
        self.exploreindexes.clicked.connect(self.expind)
        self.vectormodel.clicked.connect(self.expvector)
        self.booleanmodel.clicked.connect(self.expboolean)
        self.evaluation.clicked.connect(self.evalf)
        self.curve.clicked.connect(self.cur)
        self.about.clicked.connect(self.ab)
        self.show()  
    def expind(self):        
        self.w=Uiindexes()
    def expvector(self):
        self.w=Uivector()
    def expboolean(self):
        self.w=Uiboolean()
    def evalf(self):
        self.w=Uieval()
    def cur(self):
        self.w=Uicurve()
    def ab(self):
        self.w=Uiabout()

class Uiabout(QtWidgets.QMainWindow):
    def __init__(self):
        super(Uiabout, self).__init__()
        uic.loadUi('about.ui', self)
        self.show()
        
class Uigraph(QtWidgets.QMainWindow):  
    def __init__(self,parent):
        super(Uigraph, self).__init__(parent)         
        self.graphWidget = pg.PlotWidget()
        self.setCentralWidget(self.graphWidget)
        self.graphWidget.setBackground('w')
        pen = pg.mkPen(color=(0, 0, 255), width=5, style=QtCore.Qt.DashLine)
        self.graphWidget.plot(parent.recalls, parent.precisions,pen=pen)
        self.graphWidget.setLabel('left', 'Precision', color='red', size=15)
        self.graphWidget.setLabel('bottom', 'Recall', color='red', size=15)
        self.graphWidget.showGrid(x=True, y=True)
        #self.graphWidget.setTitle("Recall-precision curve", size=15)
        self.show()
        
class Uimgraph(QtWidgets.QMainWindow):  
    def __init__(self,parent):
        super(Uimgraph, self).__init__(parent)         
        self.graphWidget = pg.PlotWidget()
        self.setCentralWidget(self.graphWidget)
        self.graphWidget.setBackground('w')
        pen = pg.mkPen(color=(0, 0, 255), width=5, style=QtCore.Qt.DashLine)
        self.graphWidget.plot(parent.meanrecalls, parent.meanprecisions,pen=pen)      
        self.graphWidget.setLabel('left', 'Mean precision', color='red', size=15)
        self.graphWidget.setLabel('bottom', 'Mean recall', color='red', size=15)
        self.graphWidget.showGrid(x=True, y=True)
        #self.graphWidget.setTitle("Mean recall-mean precision curve", size=15)
        self.show()
        
class Uicurve(QtWidgets.QMainWindow):
    #signal=QtCore.pyqtSignal(list,list)
    recalls=[]
    precisions=[]
    meanrecalls=[]
    meanprecisions=[]
    def __init__(self):
        super(Uicurve, self).__init__()
        uic.loadUi('curve.ui', self)
        self.recpre.clicked.connect(self.recpref)
        self.means.clicked.connect(self.meansf)
        self.show()          
    
    def recpref(self):
        self.recpreres.setPlainText("")
        index=rebuild_index(queries_size_recpre_file)
        index=index[int(self.query.value())]
        self.recalls=[]
        self.precisions=[]
        for i in index:
            self.recpreres.appendPlainText(str(i)+"\t"+str(index[i][0])+"\t"+str(index[i][1]))
            self.recalls.append(index[i][0])
            self.precisions.append(index[i][1])
        self.w=Uigraph(self)
            
    def meansf(self):
        self.meansres.setPlainText("")
        index=rebuild_index(size_means_file)
        self.meanrecalls=[]
        self.meanprecisions=[]
        for i in index:
            self.meansres.appendPlainText(str(i)+'\t'+str(index[i][0])+"\t"+str(index[i][1]))    
            self.meanrecalls.append(index[i][0])
            self.meanprecisions.append(index[i][1])
        self.w=Uimgraph(self)
            
class Uieval(QtWidgets.QMainWindow):
    def __init__(self):
        super(Uieval, self).__init__()
        uic.loadUi('cosevaluation.ui', self)
        self.fmes.clicked.connect(self.fmesresf)
        self.bestmet.clicked.connect(self.bestmetresf)
        self.bestofbest.clicked.connect(self.bestbestf)
        self.show()
    def fmesresf(self):
        self.fmesres.setPlainText("")
        index=rebuild_index(evaluation_file)
        index=index[int(self.query.value())]
        for i in index:
            self.fmesres.appendPlainText(str(i)+'\t'+str(index[i][0])+'\t'+str(index[i][1])+'\t'+str(index[i][2]) )
    def bestmetresf(self):
        self.bestmetres.setPlainText("")
        index=rebuild_index(best_queries_metrics_file)
        index=index[int(self.query.value())]
        for i in index:
            self.bestmetres.appendPlainText(str(i)+'\t'+str(index[i][0])+'\t'+str(index[i][1])+'\t'+str(index[i][2]) )
    def bestbestf(self):
        self.bestbestres.setPlainText("")
        listb=rebuild_index(bestofbest_metrics_file)
        for i in listb:
            self.bestbestres.appendPlainText(str(i))
        
class Uiboolean(QtWidgets.QMainWindow):
    def __init__(self):
        super(Uiboolean, self).__init__()
        uic.loadUi('booleen.ui', self)
        self.process.clicked.connect(self.proc)
        self.show()
        
    def proc(self):
        error_dialog = QtWidgets.QErrorMessage()
        self.res.setPlainText("")
        if len(self.query.toPlainText())!=0:          
           res=boolean_model(self.query.toPlainText(),ind_file_path)
           if res==False:
              
              error_dialog.showMessage('Invalid query! Please try again.')
              error_dialog.exec()
           else:
               for doc in res:
                  self.res.appendPlainText(str(doc))

class Uiindexes(QtWidgets.QMainWindow):
    def __init__(self):
        super(Uiindexes, self).__init__()
        uic.loadUi('indexes.ui', self)
        #docind=self.findChild(QtWidgets.QPushButton,'docind')
        
        self.docind.clicked.connect(self.doc_ind)
        self.revind.clicked.connect(self.rev_ind)
        self.wind.clicked.connect(self.w_ind)
        self.wrev.clicked.connect(self.w_rev)
        self.lookup.clicked.connect(self.access)
        self.show() 
    
    def doc_ind(self):
            index=rebuild_index(ind_file_path)
            self.docindres.setPlainText("")
            for i in index:
                self.docindres.appendPlainText("*****"+str(i)+"*****")
                for w in index[i]:
                    self.docindres.appendPlainText(w+'\t'+str(index[i][w]))
            
    def rev_ind(self):            
            index=rebuild_index(rev_file_path)
            self.revindres.setPlainText("")
            for i in index:
                self.revindres.appendPlainText("*****"+i+"*****")
                for d in index[i]:
                    self.revindres.appendPlainText(str(d)+'\t'+str(index[i][d]))
            
    def w_ind(self):
            docind=rebuild_index(w_ind_file_path)            
            self.wdocres.setPlainText("")            
            for i in docind:
                self.wdocres.appendPlainText("*****"+str(i)+"*****")
                for w in docind[i]:
                    self.wdocres.appendPlainText(w+'\t'+str(docind[i][w]))
            
   
    def w_rev(self):
        revind=rebuild_index(w_rev_file_path)
        self.wrevres.setPlainText("")
        for i in revind:
            self.wrevres.appendPlainText("*****"+i+"*****")
            for d in revind[i]:
                self.wrevres.appendPlainText(str(d)+'\t'+str(revind[i][d]))    
    
    def access(self):
        if len(self.word.text())!=0:
            self.acces2.setPlainText("")
            self.acces2w.setPlainText("")
            windex=rebuild_index(w_rev_file_path)
            windex=windex[self.word.text()]
            index=access2(self.word.text())
            if index!=None:
                for i in index:
                   self.acces2.appendPlainText(str(i)+'\t'+str(index[i]))
            #else: self.acces2.setPlainText("No results found!")
            if(windex!=None):
                for i in windex:
                   self.acces2w.appendPlainText(str(i)+'\t'+str(windex[i]))
           # else: self.acces2w.setPlainText("No results found!")       
        
        if len(self.docnum.text())!=0:
            self.acces1.setPlainText("")
            self.acces1w.setPlainText("")
            index=access1(int(self.docnum.text()))
            windex=rebuild_index(w_ind_file_path)
            windex=windex[int(self.docnum.text())]
            if index!=None:
                for i in index:
                   self.acces1.appendPlainText(i+'\t'+str(index[i]))
            #Pelse: self.acces1.setPlainText("No results found!") 
            if windex!=None:
                for i in windex:
                   self.acces1w.appendPlainText(str(i)+'\t'+str(windex[i])) 
            #else: self.acces1w.setPlainText("No results found!")
            
class Uivector(QtWidgets.QMainWindow):
    def __init__(self):
        super(Uivector, self).__init__()
        uic.loadUi('vectoriel.ui', self)
        self.jac.clicked.connect(self.jacf)
        self.cos.clicked.connect(self.cosf)
        self.dicoef.clicked.connect(self.dicoeff)
        self.intpro.clicked.connect(self.intprof)
        self.show() 
        
    def intprof(self):
        res=rebuild_index(internalproduct_results_file)
        fmesures=rebuild_index(intpro_fmesures_file)
        res=res[self.query.value()]
        fmesures=fmesures[self.query.value()]
        self.intprores.setPlainText("")
        self.rec1.setText("")
        self.pre1.setText("")
        self.fmes1.setText("")
        for i in res:
            self.intprores.appendPlainText(str(i)+'\t'+str(res[i]))
        self.rec1.setText(str(fmesures[0]))
        self.pre1.setText(str(fmesures[1]))
        self.fmes1.setText(str(fmesures[2]))
        
    def dicoeff(self):
        res=rebuild_index(dicecoeff_results_file)
        fmesures=rebuild_index(dicco_fmesures_file)
        res=res[self.query.value()]
        fmesures=fmesures[self.query.value()]
        self.dicoefres.setPlainText("")
        self.rec2.setText("")
        self.pre2.setText("")
        self.fmes2.setText("")
        for i in res:
            self.dicoefres.appendPlainText(str(i)+'\t'+str(res[i]))
        self.rec2.setText(str(fmesures[0]))
        self.pre2.setText(str(fmesures[1]))
        self.fmes2.setText(str(fmesures[2]))
        
    def cosf(self):
        res=rebuild_index(cosinus_results_file)
        fmesures=rebuild_index(cos_fmesures_file)
        res=res[self.query.value()]
        fmesures=fmesures[self.query.value()]
        self.cosres.setPlainText("")
        self.rec3.setText("")
        self.pre3.setText("")
        self.fmes3.setText("")
        for i in res:
            self.cosres.appendPlainText(str(i)+'\t'+str(res[i]))
        self.rec3.setText(str(fmesures[0]))
        self.pre3.setText(str(fmesures[1]))
        self.fmes3.setText(str(fmesures[2]))
        
    def jacf(self):
        res=rebuild_index(jaccard_results_file)
        fmesures=rebuild_index(jac_fmesures_file)
        res=res[self.query.value()]
        fmesures=fmesures[self.query.value()]
        self.jacres.setPlainText("")
        self.rec4.setText("")
        self.pre4.setText("")
        self.fmes4.setText("")
        for i in res:
            self.jacres.appendPlainText(str(i)+'\t'+str(res[i]))
        self.rec4.setText(str(fmesures[0]))
        self.pre4.setText(str(fmesures[1]))
        self.fmes4.setText(str(fmesures[2]))
        
app = QtWidgets.QApplication(sys.argv)
window = Ui()
#ءwindow.show()
app.exec()

"""ind_doc(name)
reversed_file()
weights_indexes()
queries_index(queries_file)
true_pertinent_docs(pertinence_file)
vector_model4(queries_index_file,w_ind_file_path)
vector_model2(queries_index_file,w_ind_file_path)
vector_model3(queries_index_file,w_ind_file_path)
vector_model1(queries_index_file,w_ind_file_path)"""
#evalfmesures(cosinus_results_file)
#bestofbestmetrics(evaluation_file)
#recallprecision_curve()