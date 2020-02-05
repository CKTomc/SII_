package application;

import java.util.ArrayList;
import java.util.Random;

public class Kmedoids {


public ArrayList<Cluster> kmedoids(Instances instances,int k) {

ArrayList <Instance> reps=new ArrayList <Instance>();
ArrayList <Cluster> clusters=new ArrayList <Cluster>();

float cost=0;
//generating random medoids and their clusters
for (int medoid=0;medoid<k;medoid++) {
		reps.add(instances.getInstances().get(4*medoid+10));
		clusters.add(new Cluster(medoid,instances.getInstances().get(4*medoid+10)));
 }

//filling clusters
for (int obj=0;obj<instances.getInstances().size();obj++) {
			 int med=0;	boolean ismed=false;
			 while(med<reps.size() && ismed==false)
			  {if (obj==instances.getInstances().indexOf(reps.get(med)))
					{ismed=true;}
			    else{med++;}
			  }
			 if (ismed==true) {continue;}
			//obj is not a medoid
			 float distance=instances.getInstances().get(obj).distance(reps.get(0));
			 int numclust=0;			 	
		     for (med=1;med<reps.size();med++) {
		    		float d=instances.getInstances().get(obj).distance(reps.get(med));
		    		if (d<distance) {distance=d;numclust=med;}  
		               }   
		        
		      clusters.get(numclust).getObjects().add(instances.getInstances().get(obj));
		      cost+=instances.getInstances().get(obj).distance(reps.get(numclust));
 }
Random rand=new Random();
int bestmed=-1,bestobj=-1;
float oldcost=cost;
Instance repobj,prevrep;boolean decreased=true;
while(decreased==true) {
  for (int i=0;i<reps.size();i++) {
	
	for (int obj=0;obj<clusters.get(i).objects.size();obj++) {
	    //int obj=rand.nextInt(clusters.get(i).objects.size());
		float cost2=0;
		//sauv
		repobj=clusters.get(i).objects.get(obj);
		prevrep=reps.get(i);
		//swap
		clusters.get(i).objects.set(obj,prevrep);
		reps.set(i, repobj);		
		clusters.get(i).setRepresentative(repobj);
		
		//************************calc new clusters after swap**************************
		for (int obj1=0;obj1<instances.getInstances().size();obj1++) {
			 int m=0;	boolean ismed=false;
			 while(m<reps.size() && ismed==false)
			  {if (obj1==instances.getInstances().indexOf(reps.get(m)))
					{ismed=true;}
			    else{m++;}
			  }
			 if (ismed==true) {continue;}
			//obj is not a medoid
			 float distance=instances.getInstances().get(obj1).distance(reps.get(0));
			 int numclust=0;			 	
		     for (m=1;m<reps.size();m++) {
		    		float d=instances.getInstances().get(obj1).distance(reps.get(m));
		    		if (d<distance) {distance=d;numclust=m;}  
		               }  			        
		     
		      cost2+=instances.getInstances().get(obj1).distance(reps.get(numclust));
		      }
		//************************determine if swap better**************************
		if(cost2-cost<0) {
			bestmed=i;bestobj=obj;
			cost=cost2;
			}
		
		else {//undo swap
			clusters.get(i).objects.set(obj,repobj);
			reps.set(i, prevrep);			
			clusters.get(i).setRepresentative(prevrep);
		    }
	
	}
  }
  
//***************************************perform best swap***********************************
  if(cost!=oldcost) {
	//sauv
	repobj=clusters.get(bestmed).objects.get(bestobj);
	prevrep=reps.get(bestmed);
	//swap
    clusters.get(bestmed).objects.set(bestobj,prevrep);
    reps.set(bestmed, repobj);		
    clusters.get(bestmed).setRepresentative(repobj);
    //reinit les objets de clusters
 
	for (int l=0;l<reps.size();l++)
	{clusters.get(l).setObjects(new ArrayList<Instance>());}
	
	 for (int obj1=0;obj1<instances.getInstances().size();obj1++) {
		 int m=0;	boolean ismed=false;
		 while(m<reps.size() && ismed==false)
		  {if (obj1==instances.getInstances().indexOf(reps.get(m)))
				{ismed=true;}
		    else{m++;}
		  }
		 if (ismed==true) {continue;}
		//obj is not a medoid
		 float distance=instances.getInstances().get(obj1).distance(reps.get(0));
		 int numclust=0;			 	
	     for (m=1;m<reps.size();m++) {
	    		float d=instances.getInstances().get(obj1).distance(reps.get(m));
	    		if (d<distance) {distance=d;numclust=m;}  
	               }  			        
	      clusters.get(numclust).getObjects().add(instances.getInstances().get(obj1));
	      
	      }
	oldcost=cost;
 } else {decreased=false;}
}

/* for (int i=0;i<clusters.size();i++) {
	System.out.print("******Cluster"+i+"******\n");
	System.out.print(instances.getInstances().indexOf(clusters.get(i).representative)+"\n");
	for(int j=0;j<clusters.get(i).objects.size();j++) {
		System.out.print(instances.getInstances().indexOf(clusters.get(i).objects.get(j))+" ");
	}
	System.out.println();
}*/
return clusters;
}

}
