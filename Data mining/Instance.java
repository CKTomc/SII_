package application;

import java.util.ArrayList;


import javafx.beans.property.SimpleStringProperty;
import java.lang.Math;
public class Instance {
	private ArrayList<Attribute> instance;
    private int classe;
    SimpleStringProperty a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;
	
	public int getClasse() {
		return (int)instance.get(instance.size()-1).getValue();
	}
	public void setClasse(int classe) {
		this.classe = classe;
	}
	
	public ArrayList<Attribute> getInstance() {
		return instance;
	}
	public void setInstance(ArrayList<Attribute> instance) {
		this.instance = instance;
	}
	public Instance() {}
	public Instance(String a1,String a2,String a3,String a4,String a5,String a6,String a7,String a8,String a9,String a10,String a11,String a12,String a13,String a14) 
	{this.a1=new SimpleStringProperty(a1);
	this.a2=new SimpleStringProperty(a2);
	this.a3=new SimpleStringProperty(a3);this.a4=new SimpleStringProperty(a4);this.a5=new SimpleStringProperty(a5);
	this.a6=new SimpleStringProperty(a6);this.a7=new SimpleStringProperty(a7);this.a8=new SimpleStringProperty(a8);
	this.a9=new SimpleStringProperty(a9);this.a10=new SimpleStringProperty(a10);this.a11=new SimpleStringProperty(a11);
	this.a12=new SimpleStringProperty(a12);this.a13=new SimpleStringProperty(a13);this.a14=new SimpleStringProperty(a14);		
	}
   
	public float distance(Instance instance) {
	    float distance=0;
	    for(int i=0;i<instance.getInstance().size();i++) {
	    	distance=distance+Math.abs(this.getInstance().get(i).getValue()-instance.getInstance().get(i).getValue());
	    }
	return distance;
	}
	
}
