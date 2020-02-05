package application;

import java.util.ArrayList;
import java.util.Random;

public class Clarans {

public  ArrayList<Cluster> clarans(Instances instances,int numlocal,int maxneighbour, int k){
	 float mincost=1000000000000000000L;
	ArrayList <Instance> bestnode=new ArrayList <Instance>();
	ArrayList <Cluster> clusters=new ArrayList <Cluster>();
	 float cost=0;
	 Random rand=new Random();
		//generating random medoids and their clusters
		for (int medoid=0;medoid<k;medoid++) {
			bestnode.add(instances.getInstances().get(4*medoid+10));
			clusters.add(new Cluster(medoid,instances.getInstances().get(4*medoid+10)));
		}
		ArrayList <Instance> oldnode=bestnode;
		//filling clusters
		for (int obj=0;obj<instances.getInstances().size();obj++) {
			 int med=0;	boolean ismed=false;
			 while(med<bestnode.size() && ismed==false)
			  {if (obj==instances.getInstances().indexOf(bestnode.get(med)))
					{ismed=true;}
			    else{med++;}
			  }
			 if (ismed==true) {continue;}
			//obj is not a medoid
			 float distance=instances.getInstances().get(obj).distance(bestnode.get(0));
			 int numclust=0;			 	
		     for (med=1;med<bestnode.size();med++) {
		    		float d=instances.getInstances().get(obj).distance(bestnode.get(med));
		    		if (d<distance) {distance=d;numclust=med;}  
		               }   
		        
		      clusters.get(numclust).getObjects().add(instances.getInstances().get(obj));
		      cost+=instances.getInstances().get(obj).distance(bestnode.get(numclust));
		      }
		
			
int i=1; Instance repobj,prevrep;
while(i<=numlocal) {	
		//for (int med=0;med<bestnode.size();med++) {
			
	        int j=1;
			while(j<maxneighbour) {
			int med=rand.nextInt(bestnode.size());
			int obj=rand.nextInt(clusters.get(med).objects.size());
				float cost2=0;
				//sauv previous med and new med (neighbour)
				repobj=clusters.get(med).objects.get(obj);
				prevrep=bestnode.get(med);
				//swap
				clusters.get(med).objects.set(obj,prevrep);
				bestnode.set(med, repobj);
				clusters.get(med).setRepresentative(repobj);
				//************************calc new clusters after swap**************************
				for (int obj1=0;obj1<instances.getInstances().size();obj1++) {
					 int m=0;	boolean ismed=false;
					 while(m<bestnode.size() && ismed==false)
					  {if (obj1==instances.getInstances().indexOf(bestnode.get(m)))
							{ismed=true;}
					    else{m++;}
					  }
					 if (ismed==true) {continue;}
					//obj is not a medoid
					 float distance=instances.getInstances().get(obj1).distance(bestnode.get(0));
					 int numclust=0;			 	
				     for (m=1;m<bestnode.size();m++) {
				    		float d=instances.getInstances().get(obj1).distance(bestnode.get(m));
				    		if (d<distance) {distance=d;numclust=m;}  
				               }  			        
				     
				     cost2+=instances.getInstances().get(obj1).distance(bestnode.get(numclust));
				  }
				//******************testing and swapping if swap is better********************************
				if(cost2-cost<0) {
					clusters=new ArrayList<Cluster>();
					for (int l=0;l<bestnode.size();l++)
					{clusters.add(new Cluster(l,bestnode.get(l)));}
					for (int obj1=0;obj1<instances.getInstances().size();obj1++) {
						 int m=0;	boolean ismed=false;
						 while(m<bestnode.size() && ismed==false)
						  {if (obj1==instances.getInstances().indexOf(bestnode.get(m)))
								{ismed=true;}
						    else{m++;}
						  }
						 if (ismed==true) {continue;}
						//obj is not a medoid
						 float distance=instances.getInstances().get(obj1).distance(bestnode.get(0));
						 int numclust=0;			 	
					     for (m=1;m<bestnode.size();m++) {
					    	    
					    	   
					    		float d=instances.getInstances().get(obj1).distance(bestnode.get(m));
					    		if (d<distance) {distance=d;numclust=m;}  
					               }  			
					      
					      clusters.get(numclust).getObjects().add(instances.getInstances().get(obj1));
					      //cost2+=instances.getInstances().get(obj1).distance(bestnode.get(numclust));
					      }
					cost=cost2;
				 }
				
				else {//undo swap 
					clusters.get(med).objects.set(obj,repobj);
					clusters.get(med).setRepresentative(prevrep);
					bestnode.set(med, prevrep);
						j++;			
				    }
				if (j>maxneighbour) {
					if(cost<mincost) {mincost=cost;oldnode=bestnode;}
					else{bestnode=oldnode;}
					break;
					}
			}	
		
	
			i++;}
	return clusters;
}
}
