package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class DataSource extends java.lang.Object implements java.io.Serializable{
	Instances instances=new Instances();
	
	public Instances getInstances() {
		return instances;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

	public Instances read(String path)
	{Path p=Paths.get(path);
	try (BufferedReader br = Files.newBufferedReader(p)){		 
		String line= br.readLine();
		String[]attributs=line.split(",");ArrayList<String>att=new ArrayList<String>(Arrays.asList(attributs));
		instances.setAttributs(att);
		instances.setInstances(new ArrayList<Instance>());
	    line= br.readLine();
	    
	    while (line != null) {
	        String[] instance=line.split(",");
	        
	        //relabel the attribute "class"
	        if(instance[instance.length-1].equals("present")){instance[instance.length-1]="2";}
	        else if(instance[instance.length-1].equals("absent")){instance[instance.length-1]="1";}
	        //construct new instance
	        Instance inst=new Instance();inst.setInstance(new ArrayList<Attribute>());
	        for(int j=0;j<instance.length;j++) {
	        	Attribute a=new Attribute(attributs[j],Float.parseFloat(instance[j]));        	
	        	inst.getInstance().add(a);}	        
	        //Stream.of(instance).mapToFloat(Float::parseFloat).toArray();}
	        instances.getInstances().add(inst);
	        line = br.readLine();
	    }	    
		}
	    catch (IOException ioe) {
           ioe.printStackTrace();
       }
	
	return instances;}
	
}