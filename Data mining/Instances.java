package application;
import java.io.*;

import java.util.ArrayList;

public class Instances  {
	private ArrayList<String> attributs;private int nbrinst;private int nbratt;
	private ArrayList<Instance>instances;
	
    public Instances() {}
    
	public ArrayList<Instance> getInstances() {
		return instances;
	}

	public void setInstances(ArrayList<Instance> instances) {
		this.instances = instances;
	}

	public ArrayList<String> getAttributs() {
    	return attributs;
    }
	public void setAttributs(ArrayList<String> attributs) {
		this.attributs = attributs;
	}
    public int getNbrinst() {
		return instances.size();
	}
	public void setNbrinst(int nbrinst) {
		this.nbrinst = nbrinst;
	}
	public int getNbratt() {
		return attributs.size();
	}
	public void setNbratt(int nbratt) {
		this.nbratt = nbratt;
	}
	
	
	
	
		
	
	
	
	
	public void printdata() {
    	//System.out.format("%32s%10d%16s", string1, int1, string2);
    	for (int i=0;i<this.getAttributs().size();i++)
    	{System.out.print(String.format("%20s",this.getAttributs().get(i)+" "));}
    	System.out.print("\n");
    	for(int i=0;i<this.getNbrinst();i++){
       		for(int j=0;j<this.getNbratt();j++)
    	     {System.out.print(String.format("%20f",this.getInstances().get(i).getInstance().get(j).getValue()));}
       		System.out.print("\n"); }}
  public void infodataset() {
	  System.out.println("Nombre d'instances: "+this.getNbrinst());
	  System.out.println("Nombre d'attributs: "+this.getNbratt());
	  System.out.print("Valeur manquantes: Aucune.\n");
  }
  
}