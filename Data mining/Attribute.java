package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.*;

public class Attribute {
	private String name;
	private float value;
    private float min;
    private float max;
    private float mediane;
    private float moyenne;
    private ArrayList<Freqatt> mode;
    private float q1;
    private float q3;
    private float iqr;
	private ArrayList<Float> outliers;
	private ArrayList<Freqatt> freqs;
	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMediane() {
		return mediane;
	}

	public void setMediane(float mediane) {
		this.mediane = mediane;
	}

	public float getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(float moyenne) {
		this.moyenne = moyenne;
	}

	public ArrayList<Freqatt> getMode() {
		return mode;
	}

	public void setMode(ArrayList<Freqatt> mode) {
		this.mode = mode;
	}

	public float getQ1() {
		return q1;
	}

	public void setQ1(float q1) {
		this.q1 = q1;
	}

	public float getQ3() {
		return q3;
	}

	public void setQ3(float q3) {
		this.q3 = q3;
	}

	public float getIqr() {
		return iqr;
	}

	public void setIqr(float iqr) {
		this.iqr = iqr;
	}

	public Attribute(String name,float value) {
		super();
		this.name = name;
		this.value=value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
    public ArrayList<Float> getOutliers() {
		return outliers;
	}

	public void setOutliers(ArrayList<Float> outliers) {
		this.outliers = outliers;
	}

	public void moyenne(Instances i) {
    	int indatt=i.getAttributs().indexOf(this.getName());
    	float sum=0;
    	for(int j=0;j<i.getNbrinst();j++) {
    		sum=sum+i.getInstances().get(j).getInstance().get(indatt).getValue();
    	}
    	this.moyenne= sum/i.getNbrinst();
    }
    public void mediane(Instances i) {
    	int indatt=i.getAttributs().indexOf(this.getName());
    	ArrayList<Float> donneesatt=new ArrayList<Float>();
    	for(int j=0;j<i.getNbrinst();j++) {
    		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
    	}
    	Collections.sort(donneesatt);
    	//for (int j=0;j<donneesatt.size();j++) {System.out.print(donneesatt.get(j)+" ");}System.out.println();
    	float m;
    	if(donneesatt.size()%2==1) {m=donneesatt.get((donneesatt.size()+1)/2);}
    	else {m=(donneesatt.get(donneesatt.size()/2)+donneesatt.get(donneesatt.size()/2 +1))/2;}
    	this.mediane=m;
      }
    public void mode(Instances i) {
    	int indatt=i.getAttributs().indexOf(this.getName());
    	ArrayList<Float> donneesatt=new ArrayList<Float>();
    	for(int j=0;j<i.getNbrinst();j++) {
    		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
    	}
    	ArrayList<Float> valeurs=new ArrayList<Float>();
    	ArrayList<Integer> freq=new ArrayList<Integer>();
    	for (int j=0;j<donneesatt.size();j++) {
    		if(valeurs.contains(donneesatt.get(j))){
    			freq.set(valeurs.indexOf(donneesatt.get(j)),freq.get(valeurs.indexOf(donneesatt.get(j)))+1);}
    		else {valeurs.add(donneesatt.get(j));freq.add(1);}
    	}
    	int max=0;ArrayList<Freqatt>modes=new ArrayList<Freqatt>();
    	for(int j=1;j<freq.size();j++) {
    		if(freq.get(j)>freq.get(max)) {max=j;}}
    	for (int j=0;j<freq.size();j++){	
    	    if(j==max) {modes.add(new Freqatt(valeurs.get(max),freq.get(max)));}
    	    else if(freq.get(j)==freq.get(max)){modes.add(new Freqatt(valeurs.get(j),freq.get(j)));}
    	}
    	this.mode=modes;
    }
    public void frequences(Instances i) {
    	int indatt=i.getAttributs().indexOf(this.getName());
    	ArrayList<Float> donneesatt=new ArrayList<Float>();
    	for(int j=0;j<i.getNbrinst();j++) {
    		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
    	}
    	Collections.sort(donneesatt);
    	ArrayList<Float> valeurs=new ArrayList<Float>();
    	ArrayList<Integer> freq=new ArrayList<Integer>();
    	for (int j=0;j<donneesatt.size();j++) {
    		if(valeurs.contains(donneesatt.get(j))){
    			freq.set(valeurs.indexOf(donneesatt.get(j)),freq.get(valeurs.indexOf(donneesatt.get(j)))+1);}
    		else {valeurs.add(donneesatt.get(j));freq.add(1);}
    	}
    	ArrayList<Freqatt>freqs=new ArrayList<Freqatt>();
    	for (int j=0;j<valeurs.size();j++) {
    		freqs.add(new Freqatt(valeurs.get(j),freq.get(j)));
    	}
    	this.freqs=freqs;
    }
     public void quantiles(Instances i) {
    	 int indatt=i.getAttributs().indexOf(this.getName());
     	ArrayList<Float> donneesatt=new ArrayList<Float>();
     	for(int j=0;j<i.getNbrinst();j++) {
     		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
     	}
     	Collections.sort(donneesatt);
    	//for (int j=0;j<donneesatt.size();j++) {System.out.print(donneesatt.get(j)+" ");}System.out.println();
    	int m;
    	if(donneesatt.size()%2==1)
    	{m=(donneesatt.size()+1)/2;
    	  if(m%2==1){this.q1=donneesatt.get((m+1)/2);}
    	  else{this.q1=(donneesatt.get(m/2)+donneesatt.get(m/2+1))/2;}
    	}
    	else {m=(donneesatt.size())/2;
    	      if(m%2==0){this.q1=(donneesatt.get(m/2)+donneesatt.get(m/2+1))/2;
    	      this.q3=(donneesatt.get((m+donneesatt.size())/2)+donneesatt.get((m+donneesatt.size())/2+1))/2;}
    	      else {this.q1=donneesatt.get((m+1)/2);
    	           this.q3=donneesatt.get((donneesatt.size()+m+1)/2);}    	}
    	
    }   
   public void min(Instances i) {
	   int indatt=i.getAttributs().indexOf(this.getName());
	   ArrayList<Float> donneesatt=new ArrayList<Float>();
     	for(int j=0;j<i.getNbrinst();j++) {
   		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
   	   }
     	Collections.sort(donneesatt);
     	this.min=donneesatt.get(0);
   }
   public void max(Instances i) {
	   int indatt=i.getAttributs().indexOf(this.getName());
	   ArrayList<Float> donneesatt=new ArrayList<Float>();
     	for(int j=0;j<i.getNbrinst();j++) {
   		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
   	   }
     	Collections.sort(donneesatt);
     	this.max=donneesatt.get(donneesatt.size()-1);
   }
   public void outliers(Instances i){
	   int indatt=i.getAttributs().indexOf(this.getName());
	   ArrayList<Float> out=new ArrayList<Float>();
     	for(int j=0;j<i.getNbrinst();j++) {
   		if(i.getInstances().get(j).getInstance().get(indatt).getValue()>1.5*this.max
   				||i.getInstances().get(j).getInstance().get(indatt).getValue()<1.5*this.min) 
   		{out.add(i.getInstances().get(j).getInstance().get(indatt).getValue());}
   	   }
     	this.outliers=out;    	
   }
   public void iqr() {this.iqr=this.getQ3()-this.getQ1();}
   
   public void descfunc(Instances i) {
	   mediane(i);mode(i);moyenne(i);quantiles(i);max(i);min(i);iqr();outliers(i);
   }
   public void affichage() {
	   System.out.println("\n*************"+this.getName()+":*************");
	   System.out.println("Mediane:"+this.getMediane());
	   System.out.println("Moyenne:"+this.getMoyenne());
	   System.out.print("Mode:");
	   for(int i=0;i<this.getMode().size();i++) {System.out.print(this.getMode().get(i).getValeur()+" ");}
	   System.out.println("\nQ1:"+this.getQ1());
	   System.out.println("Q3:"+this.getQ3());
	   System.out.println("IQR:"+this.getIqr());
	   System.out.println("Min:"+this.getMin());
	   System.out.println("Max:"+this.getMax());
	   System.out.print("Outliers:");
	   for(int i=0;i<this.getOutliers().size();i++) {System.out.print(this.getOutliers().get(i)+" ");}	   
   }
   

public ArrayList<Freqatt> getFreqs() {
	return freqs;
}

public void setFreqs(ArrayList<Freqatt> freqs) {
	this.freqs = freqs;
}

public float ecarttype(Instances i) {
	//racine(1/N * somme(xi-moyenne)^2)
	int indatt=i.getAttributs().indexOf(this.getName());
	ArrayList<Float> donneesatt=new ArrayList<Float>();
	for(int j=0;j<i.getNbrinst();j++) {
		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
	}
	float sum=0;moyenne(i);float moy=this.getMoyenne();
	for(int j=0;j<donneesatt.size();j++) {
		sum=(float) (sum+Math.pow((donneesatt.get(j)-moy),2));
	}
	//System.out.print();
	return (float) Math.sqrt(sum/i.getNbrinst());
}
public float coefcorr(Attribute a,Instances i) {
	this.moyenne(i);a.moyenne(i);
	//somme((ai-moyenneA)*(bi-moyenneB)) /N*sigmaA*sigmaB
	int indatt=i.getAttributs().indexOf(this.getName());
	int indatt2=i.getAttributs().indexOf(a.getName());
	ArrayList<Float> donneesatt=new ArrayList<Float>();
	for(int j=0;j<i.getNbrinst();j++) {
		donneesatt.add(i.getInstances().get(j).getInstance().get(indatt).getValue());
	}
	ArrayList<Float> donneesatt2=new ArrayList<Float>();
	for(int j=0;j<i.getNbrinst();j++) {
		donneesatt2.add(i.getInstances().get(j).getInstance().get(indatt2).getValue());
	}
	float sum=0;
	for(int j=0;j<i.getNbrinst();j++) {
		sum=sum+((donneesatt.get(j)-this.getMoyenne())*(donneesatt2.get(j)-a.getMoyenne()));}
	return (float) (sum/(i.getNbrinst()*this.ecarttype(i)*a.ecarttype(i)));
}

}
