package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SampleController {
	@FXML MenuItem edit=new MenuItem();
	@FXML MenuItem info=new MenuItem();
	@FXML MenuItem histo=new MenuItem();
	@FXML MenuItem boxplot=new MenuItem();
	 TableView<Instance> data;
	 TableColumn<Instance, String> age =new TableColumn<Instance, String>("age");
	 TableColumn <Instance, String>sex =new TableColumn<Instance, String>("sex");
	 TableColumn <Instance, String>chest=new TableColumn<Instance, String>("chest");
	 TableColumn <Instance, String>resting_blood_pressure=new TableColumn<Instance, String>("resting_blood_pressure");
	 TableColumn <Instance, String>serum_cholestoral=new TableColumn<Instance, String>("serum_cholestoral");
	 TableColumn <Instance, String>fasting_blood_sugar=new TableColumn<Instance, String>("fasting_blood_sugar");
	 TableColumn <Instance, String>resting_electrocardiographic_results=new TableColumn<Instance, String>("resting_electrocardiographic_results");
	 TableColumn <Instance, String>maximum_heart_rate_achieved=new TableColumn<Instance, String>("maximum_heart_rate_achieved");
	 TableColumn <Instance, String>exercise_induced_angina=new TableColumn<Instance, String>("exercise_induced_angina");
	 TableColumn <Instance, String>oldpeak=new TableColumn<Instance, String>("oldpeak");
	 TableColumn <Instance, String>slope=new TableColumn<Instance, String>("slope");
	 TableColumn <Instance, String>number_of_major_vessels=new TableColumn<Instance, String>("number_of_major_vessels");
	 TableColumn<Instance, String> thal=new TableColumn<Instance, String>("thal");	
	  TableColumn<Instance, String> Class=new TableColumn<Instance, String>("Class");
	 String path="C:\\Users\\khadidja\\Desktop\\SII\\S3\\datamining\\data.txt";
	 DataSource src =new DataSource();
		Instances instances=src.read(path);
		@FXML TextArea kmedres=new TextArea();
		@FXML TextArea apriorires=new TextArea();
		@FXML TextArea claransres=new TextArea();
		@FXML TextArea eclatres=new TextArea();
		
		@FXML TextField kmedtime=new TextField();
		@FXML TextField claranstime=new TextField();
		@FXML TextField eclattime=new TextField();
		@FXML TextField aprioritime=new TextField();
		
public ObservableList<Instance> getInsts(){
	ObservableList<Instance>insts=FXCollections.observableArrayList();
	for(int i=0;i<instances.getNbrinst();i++) {
	insts.add(new Instance(
			Float.toString(instances.getInstances().get(i).getInstance().get(0).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(1).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(2).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(3).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(4).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(5).getValue()),
    	Float.toString(instances.getInstances().get(i).getInstance().get(6).getValue()),
    	Float.toString(instances.getInstances().get(i).getInstance().get(7).getValue()),
    	Float.toString(instances.getInstances().get(i).getInstance().get(8).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(9).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(10).getValue()),
    		Float.toString(instances.getInstances().get(i).getInstance().get(11).getValue()),
    			Float.toString(instances.getInstances().get(i).getInstance().get(12).getValue()),
    			Float.toString(instances.getInstances().get(i).getInstance().get(13).getValue())
    		));}
	return insts;
}
		@FXML
public void edit() {		
	data=new TableView<Instance>();
	age.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a1.getValue())));
	sex.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a2.getValue())));
	chest.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a3.getValue())));
	resting_blood_pressure.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a4.getValue())));
	serum_cholestoral.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a5.getValue())));
	fasting_blood_sugar.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a6.getValue())));
	resting_electrocardiographic_results.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a7.getValue())));
	maximum_heart_rate_achieved.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a8.getValue())));
	exercise_induced_angina.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a9.getValue())));
	oldpeak.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a10.getValue())));
	slope.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a11.getValue())));
	number_of_major_vessels.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a12.getValue())));
	thal.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a13.getValue())));
	Class.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().a14.getValue())));
   
	data.getColumns().add(age);data.getColumns().add(sex);data.getColumns().add(chest);data.getColumns().add(resting_blood_pressure);
	data.getColumns().add(serum_cholestoral);data.getColumns().add(fasting_blood_sugar);data.getColumns().add(resting_electrocardiographic_results);
	data.getColumns().add(maximum_heart_rate_achieved);data.getColumns().add(exercise_induced_angina);data.getColumns().add(oldpeak);
	data.getColumns().add(slope);data.getColumns().add(number_of_major_vessels);data.getColumns().add(thal);data.getColumns().add(Class);

	data.setItems(this.getInsts());		

	VBox vbox=new VBox(data);
     Scene s=new Scene(vbox);
     Stage primarystage=new Stage();
     primarystage.setScene(s);
	 primarystage.show();
	 instances.printdata();
}
@FXML
public void info() {
 
	TextArea textarea = new TextArea();
	 textarea.appendText("Nombre d'instances: "+instances.getNbrinst());
	 textarea.appendText("\nNombre d'attributs: "+instances.getNbratt());
	 textarea.appendText("\nValeurs manquantes: Aucune.\n");
	 textarea.appendText("\nTypes: Numériques\n");
	 VBox vbox=new VBox(textarea);
     Scene s=new Scene(vbox);
     Stage primarystage=new Stage();
     primarystage.setScene(s);
	 primarystage.show();	
}

@FXML
public void apriori() {
	apriorires.clear();
	aprioritime.clear();
	
	int support=84;
	ArrayList <Instance>itemsets;
	long startTime = System.currentTimeMillis();
	//intialisation
	itemsets=instances.getInstances();
	for (int i=0;i<itemsets.size();i++) {
		int j=0;
		float v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=29&&v<=49) {v=0;}
		else if(v>49&&v<=69) {v=1;}
		else if(v>69&&v<=89) {v=2;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		/*j=2;
		v=itemsets.get(i).getInstance().get(j).getValue();
		v=v-1;
		itemsets.get(i).getInstance().get(j).setValue(v);*/
		
		j=3;
		v=itemsets.get(i).getInstance().get(j).getValue();
			if(v>=94&&v<=124) {v=0;}
			else if(v>124&&v<=154) {v=1;}
			else if(v>154&&v<=184) {v=2;}
			else if(v>184&&v<=214) {v=3;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=4;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=126&&v<=226) {v=0;}
		else if(v>226&&v<=326) {v=1;}
		else if(v>326&&v<=426) {v=2;}
		else if(v>426&&v<=526) {v=3;}
		else if(v>526&&v<=626) {v=4;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=7;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=71&&v<=111) {v=0;}
		else if(v>111&&v<=151) {v=1;}
		else if(v>151&&v<=191) {v=2;}
		else if(v>191&&v<=231) {v=3;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=9;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=0&&v<=3) {v=0;}
		else if(v>3&&v<=6) {v=1;}
		else if(v>6&&v<=9) {v=2;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=12;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=3&&v<=5) {v=0;}
		else if(v>5&&v<=7) {v=1;}
		itemsets.get(i).getInstance().get(j).setValue(v);
	}
	
	
	//affichage des instances après discretisation
	for(int i=0;i<itemsets.size();i++) {
		for(int j=0;j<itemsets.get(i).getInstance().size();j++) {
			System.out.print(itemsets.get(i).getInstance().get(j).getValue());}	
		System.out.println();
	}
	
	//cpt chaque item : item : compte
	ArrayList<Item> newitemsets=new ArrayList<Item>();
	int cpt=0;int valitem=0;
	int j=itemsets.get(0).getInstance().size()-1;//les instances après discretisation		
	//comptage
	for(int i=0;i<j;i++) {	
		ArrayList<Integer> vals=new ArrayList<Integer>();
		for(int m=0;m<6;m++) {vals.add(m, 0);}//init la liste des vals de latt par des zeros
		 //parcourir toutes les instances pour cet attribut pour incrementer la freq de chaque val
		for (int k=0;k<itemsets.size();k++) {
			valitem=(int) itemsets.get(k).getInstance().get(i).getValue();//recup la val de l att
			cpt=vals.get(valitem);//recup la freq
			vals.set(valitem, cpt+1);			//incrementer freq	
		}
        //ajouter les items de cet attribut en prenant en compte le support
		for(int m=0;m<vals.size();m++) {
		 if(vals.get(m)!=0 && vals.get(m)>=support) {
		  Attribute a =new Attribute(instances.getAttributs().get(i),m);			
		  Item it=new Item(a,vals.get(m));
		  newitemsets.add(it);}
      } }
	
	
	ArrayList<ArrayList<Item>> combinaisons=new ArrayList<ArrayList<Item>>();
	
	
	int oldsize=combinaisons.size();
	//parcourir les itemsets
	do {
	   if(combinaisons.size()==0) {
	     for(int i=0;i<newitemsets.size()-1;i++) {
			for( j=i+1;j<newitemsets.size();j++) {					
			    	     ArrayList<Item> set=new ArrayList<Item>();
			    	     set.add(newitemsets.get(i));set.add(newitemsets.get(j));
			    	     combinaisons.add(set);				    	
			}
		}}
		else {
				//pour chaque item des items combinés précédemment
				for(int i=0;i<combinaisons.size();i++)					
				 {//parcourir les itemsets
					ArrayList <Item>nouv=new ArrayList<Item>();
				   for(int h=0;h<newitemsets.size();h++){	
					//int freq=0;
					//parcourir un itemset
					for( j=0;j<combinaisons.get(i).size();j++) {
														 
						if(newitemsets.get(h).a.getName().equals(combinaisons.get(i).get(j).a.getName())==true ) {break;} 
						 
				    	   
						 //si la nouvelle combi est frequente on l'ajoute
					      }if(j==combinaisons.get(i).size()) {
					    	  nouv=combinaisons.get(i);
					    	  nouv.add(newitemsets.get(h));}
						}
					
				}}
									
			
				//elimination des ens vides et < support
			ArrayList<Integer> setasupp;
			setasupp=new ArrayList<Integer>();
			for(int i=0;i<combinaisons.get(i).size();i++) {
				int cpt1=0;
				for(int k=0;k<itemsets.size();k++) {
				for( j=0;j<combinaisons.get(i).size();j++) {
					int ind=instances.getAttributs().indexOf(combinaisons.get(i).get(j).a.getName());
							
					if(itemsets.get(k).getInstance().get(ind).getValue()!=combinaisons.get(i).get(j).a.getValue())
					{break;}				
				}
				if(j==combinaisons.get(i).size()) {cpt1=cpt1+1;}
				}
				 if(cpt1<support){setasupp.add(i);} 
				// else{frequences.set(i, cpt1);}
			}
			oldsize=combinaisons.size();
			for(int i=0;i<setasupp.size();i++) {combinaisons.remove(setasupp.get(i));}
		    
	}while(combinaisons.size()!=oldsize);	
	
	
	//affichage des itemsets finaux
	for (int i=0;i<combinaisons.size();i++) {
		for (j=0;j<combinaisons.get(i).size();j++)
		{System.out.print(combinaisons.get(i).get(j).a.getName()+ " ");}
		System.out.println();System.out.println();
	}

	
//association rules	
	int ind;
	apriorires.appendText("************Règles d'associations: Calcul des confiances*********\n");
	for(int i=0;i<combinaisons.size();i++) {
	 
	int cpt1=0;int cpt2=0;
	for(int k=0;k<itemsets.size();k++) {
		//calculer frq de A ^ B 
	 for(j=0;j<combinaisons.get(i).size()-1;j++) {				
			 ind=instances.getAttributs().indexOf(combinaisons.get(i).get(j).a.getName());						
			if(itemsets.get(k).getInstance().get(ind).getValue()!=combinaisons.get(i).get(j).a.getValue())
			{break;}					    
	    }if(j==combinaisons.get(i).size()-1) {cpt1=cpt1+1;}
	
	// calculer freq A^b^c
	for(j=0;j<combinaisons.get(i).size();j++) {				
		 ind=instances.getAttributs().indexOf(combinaisons.get(i).get(j).a.getName());						
		if(itemsets.get(k).getInstance().get(ind).getValue()!=combinaisons.get(i).get(j).a.getValue())
		{break;}		
    
        }if(j==combinaisons.get(i).size()) {cpt2=cpt2+1;}		
	//aff confiance  pour a,b => c		
        
         }  if(combinaisons.get(i).size()>1 && (float)cpt2/cpt1>=0.9) {
        	 
        	 ArrayList<Attribute> set=new ArrayList<Attribute>();
	        for( int h=0;h<combinaisons.get(i).size();h++) {		        	
	        	apriorires.appendText(combinaisons.get(i).get(h).a.getName()+"= "+combinaisons.get(i).get(h).a.getValue()+" ");
				set.add(combinaisons.get(i).get(h).a);
				
			    }
	            apriorires.appendText(" Confidence= "+(float)cpt2/cpt1);
	          
	            apriorires.appendText("\n");
			    
		     }
}
	long endTime = System.currentTimeMillis();
	long timeElapsed = endTime - startTime;	
	aprioritime.appendText(timeElapsed+" ms");
}
@FXML
public void eclat() {
	eclatres.clear();
	eclattime.clear();
	int support=84;
	ArrayList <Instance>itemsets;
	long startTime = System.currentTimeMillis();	 
	//intialisation
	itemsets=instances.getInstances();
	for (int i=0;i<itemsets.size();i++) {
		int j=0;
		float v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=29&&v<=49) {v=0;}
		else if(v>49&&v<=69) {v=1;}
		else if(v>69&&v<=89) {v=2;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		/*j=2;
		v=itemsets.get(i).getInstance().get(j).getValue();
		v=v-1;
		itemsets.get(i).getInstance().get(j).setValue(v);*/
		
		j=3;
		v=itemsets.get(i).getInstance().get(j).getValue();
			if(v>=94&&v<=124) {v=0;}
			else if(v>124&&v<=154) {v=1;}
			else if(v>154&&v<=184) {v=2;}
			else if(v>184&&v<=214) {v=3;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=4;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=126&&v<=226) {v=0;}
		else if(v>226&&v<=326) {v=1;}
		else if(v>326&&v<=426) {v=2;}
		else if(v>426&&v<=526) {v=3;}
		else if(v>526&&v<=626) {v=4;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=7;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=71&&v<=111) {v=0;}
		else if(v>111&&v<=151) {v=1;}
		else if(v>151&&v<=191) {v=2;}
		else if(v>191&&v<=231) {v=3;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=9;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=0&&v<=3) {v=0;}
		else if(v>3&&v<=6) {v=1;}
		else if(v>6&&v<=9) {v=2;}
		itemsets.get(i).getInstance().get(j).setValue(v);
		
		j=12;
		v=itemsets.get(i).getInstance().get(j).getValue();
		if(v>=3&&v<=5) {v=0;}
		else if(v>5&&v<=7) {v=1;}
		itemsets.get(i).getInstance().get(j).setValue(v);
	}
	
	
	//affichage des instances après discretisation
	for(int i=0;i<itemsets.size();i++) {
		for(int j=0;j<itemsets.get(i).getInstance().size();j++) {
			System.out.print(itemsets.get(i).getInstance().get(j).getValue());}	
		System.out.println();
	}
	
	//cpt chaque item : item : compte
	ArrayList<Item> newitemsets=new ArrayList<Item>();
	int cpt;int valitem;
	int j=itemsets.get(0).getInstance().size()-1;//les instances après discretisation		
	//comptage
	for(int i=0;i<j;i++) {	
		ArrayList<Integer> vals=new ArrayList<Integer>();
		for(int m=0;m<6;m++) {vals.add(m, 0);}//init la liste des vals de latt par des zeros
		 //parcourir toutes les instances pour cet attribut pour incrementer la freq de chaque val
		for (int k=0;k<itemsets.size();k++) {
			valitem=(int) itemsets.get(k).getInstance().get(i).getValue();//recup la val de l att
			
			cpt=vals.get(valitem);//recup la freq
			vals.set(valitem, cpt+1);			//incrementer freq	
		}
        //ajouter les items de cet attribut en prenant en compte le support
		for(int m=0;m<vals.size();m++) {
		 if(vals.get(m)!=0 && vals.get(m)>=support) {
		  Attribute a =new Attribute(instances.getAttributs().get(i),m);			
		  Item it=new Item(a,vals.get(m));
		  newitemsets.add(it);}
      } }
	
	ArrayList<ArrayList<Item>> combinaisons=new ArrayList<ArrayList<Item>>();
	
	
	int oldsize=combinaisons.size();
	//parcourir les itemsets
	do {
	   if(combinaisons.size()==0) {
	     for(int i=0;i<newitemsets.size()-1;i++) {
			for( j=i+1;j<newitemsets.size();j++) {					
			    	     ArrayList<Item> set=new ArrayList<Item>();
			    	     set.add(newitemsets.get(i));set.add(newitemsets.get(j));
			    	     combinaisons.add(set);				    	
			}
		}}
		else {
				//pour chaque item des items combinés précédemment
				for(int i=0;i<combinaisons.size();i++)					
				 {//parcourir les itemsets
					ArrayList <Item>nouv=new ArrayList<Item>();
				   for(int h=0;h<newitemsets.size();h++){	
					//int freq=0;
					//parcourir un itemset
					for( j=0;j<combinaisons.get(i).size();j++) {
														 
						if(newitemsets.get(h).a.getName().equals(combinaisons.get(i).get(j).a.getName())==true ) {break;} 
						 
				    	   
						 //si la nouvelle combi est frequente on l'ajoute
					      }if(j==combinaisons.get(i).size()) {
					    	  nouv=combinaisons.get(i);
					    	  nouv.add(newitemsets.get(h));}
						}
					
				}}
									
			
				//elimination des ens vides et < support
			ArrayList<Integer> setasupp;
			setasupp=new ArrayList<Integer>();
			for(int i=0;i<combinaisons.get(i).size();i++) {
				int cpt1=0;
				for(int k=0;k<itemsets.size();k++) {
				for( j=0;j<combinaisons.get(i).size();j++) {
					int ind=instances.getAttributs().indexOf(combinaisons.get(i).get(j).a.getName());
							
					if(itemsets.get(k).getInstance().get(ind).getValue()!=combinaisons.get(i).get(j).a.getValue())
					{break;}				
				}
				if(j==combinaisons.get(i).size()) {cpt1=cpt1+1;}
				}
				 if(cpt1<support){setasupp.add(i);} 
				// else{frequences.set(i, cpt1);}
			}
			oldsize=combinaisons.size();
			for(int i=0;i<setasupp.size();i++) {combinaisons.remove(setasupp.get(i));}
		    
	}while(combinaisons.size()!=oldsize);	
	
	//affichage des itemsets finaux
	for (int i=0;i<combinaisons.size();i++) {
		for (j=0;j<combinaisons.get(i).size();j++)
		{System.out.print(combinaisons.get(i).get(j).a.getName()+ " ");}
		System.out.println();System.out.println();
	}

	
//association rules	
	int ind;
	eclatres.appendText("************Règles d'associations: Calcul des confiances*********\n");
	for(int i=0;i<combinaisons.size();i++) {
	 
	int cpt1=0;int cpt2=0;
	for(int k=0;k<itemsets.size();k++) {
		//calculer frq de A ^ B 
	 for(j=0;j<combinaisons.get(i).size()-1;j++) {				
			 ind=instances.getAttributs().indexOf(combinaisons.get(i).get(j).a.getName());						
			if(itemsets.get(k).getInstance().get(ind).getValue()!=combinaisons.get(i).get(j).a.getValue())
			{break;}					    
	    }if(j==combinaisons.get(i).size()-1) {cpt1=cpt1+1;}
	
	// calculer freq A^b^c
	for(j=0;j<combinaisons.get(i).size();j++) {				
		 ind=instances.getAttributs().indexOf(combinaisons.get(i).get(j).a.getName());						
		if(itemsets.get(k).getInstance().get(ind).getValue()!=combinaisons.get(i).get(j).a.getValue())
		{break;}		
    
        }if(j==combinaisons.get(i).size()) {cpt2=cpt2+1;}		
	//aff confiance  pour a,b => c		
        
         }  if(combinaisons.get(i).size()>1 && (float)cpt2/cpt1 >=0.9) {
        	 
        	 ArrayList<Attribute> set=new ArrayList<Attribute>();
	        for( int h=0;h<combinaisons.get(i).size();h++) {		        	
	        	eclatres.appendText(combinaisons.get(i).get(h).a.getName()+"= "+combinaisons.get(i).get(h).a.getValue()+" ");
				set.add(combinaisons.get(i).get(h).a);
				
			    }
	            eclatres.appendText(" Confidence= "+(float)cpt2/cpt1);
	          
	           eclatres.appendText("\n");
			    
		     }
}long endTime = System.currentTimeMillis();
long timeElapsed = endTime - startTime;
eclattime.appendText(timeElapsed+" ms");
}

@FXML
public void kmedoids() {
	kmedres.clear();
	kmedtime.clear();
	
	  long startTime = System.currentTimeMillis();
	  Kmedoids k=new Kmedoids();
	  int cl=10;
	  ArrayList<Cluster>clusters=k.kmedoids(instances,cl);
	 
	 long endTime = System.currentTimeMillis();
	 long timeElapsed = endTime - startTime;
	for (int i=0;i<clusters.size();i++) {
			kmedres.appendText("******Cluster"+i+"******\n");
			kmedres.appendText("Cout: "+clusters.get(i).coutclust());
			kmedres.appendText("Nombre d'objets: "+clusters.get(i).getObjects().size()+"\n");
			kmedres.appendText("Medoid: "+instances.getInstances().indexOf(clusters.get(i).representative)+"\n");
			for(int j=0;j<clusters.get(i).objects.size();j++) {
				kmedres.appendText(instances.getInstances().indexOf(clusters.get(i).objects.get(j))+" ");
			}
			kmedres.appendText("\n");
		}
	
	kmedtime.appendText(timeElapsed+" ms");
	 
}
@FXML
public void clarans() {
	long startTime = System.currentTimeMillis();
	 claransres.clear();
	 claranstime.clear();
	 Clarans k=new Clarans();
	 int nbiter=10; int maxneighb=10;int cl=10;
	 ArrayList<Cluster>clusters=k.clarans(instances,nbiter,maxneighb,cl);
     long endTime = System.currentTimeMillis();
	 long timeElapsed = endTime - startTime;
	 for (int i=0;i<clusters.size();i++) {
		 claransres.appendText("******Cluster"+i+"******\n");
		 claransres.appendText("Cout: "+clusters.get(i).coutclust());
		 claransres.appendText("Nombre d'objets: "+clusters.get(i).getObjects().size()+"\n");
		 claransres.appendText("Medoid: "+instances.getInstances().indexOf(clusters.get(i).representative)+"\n");
			for(int j=0;j<clusters.get(i).objects.size();j++) {
				claransres.appendText(instances.getInstances().indexOf(clusters.get(i).objects.get(j))+" ");
			}
			claransres.appendText("\n");
		}
	 claranstime.appendText(timeElapsed+"ms");
	 
}
@FXML
public void histo() { 
    for(int i=0;i<instances.getAttributs().size();i++) {
    	Axis<String> xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
     BarChart<String, Number> bc =  new BarChart<String,Number>(xAxis,yAxis);

    Attribute a=new Attribute(instances.getAttributs().get(i),0);
    a.frequences(instances);
    ArrayList<Freqatt> freq=a.getFreqs();
    xAxis.setLabel(instances.getAttributs().get(i));       
    yAxis.setLabel("Frequencies");
    XYChart.Series series1 = new XYChart.Series(); 
    for(int j=0;j<freq.size();j++)
    {series1.getData().add(new XYChart.Data(""+freq.get(j).getValeur(),freq.get(j).getFreq()));}       
    bc.getData().addAll(series1);
    VBox vbox=new VBox(bc);
    Scene scene  = new Scene(vbox);    
    Stage stage=new Stage();
    stage.setScene(scene);
    stage.show();
  }
}

@FXML
public void scatter() {
	//Defining the x axis  
	Attribute a=new Attribute("resting_blood_pressure",0);Attribute b=new Attribute("serum_cholestoral",0);
	Attribute c=new Attribute("maximum_heart_rate_achieved",0);Attribute d=new Attribute("age",0);
	scatterfonc(b,a);
	scatterfonc(a,c);
	scatterfonc(c,b);
	scatterfonc(d,a);
	scatterfonc(d,c);
	scatterfonc(b,d);
}
public void scatterfonc(Attribute a, Attribute b) {
	int inda=instances.getAttributs().indexOf(a.getName());
	int indb=instances.getAttributs().indexOf(b.getName());
	NumberAxis xAxis = new NumberAxis(); xAxis.setLabel(a.getName());
	NumberAxis yAxis = new NumberAxis();yAxis.setLabel(b.getName());
	ScatterChart<Number, Number> scatterChart = new ScatterChart(xAxis, yAxis); 
	//Prepare XYChart.Series objects by setting data 
	XYChart.Series series = new XYChart.Series(); 
	for(int i=0;i<instances.getNbrinst();i++) {
	series.getData().add(new XYChart.Data(instances.getInstances().get(i).getInstance().get(inda).getValue(),
			instances.getInstances().get(i).getInstance().get(indb).getValue()));}
	//Setting the data to scatter chart        
	scatterChart.getData().addAll(series);
	TextArea textarea = new TextArea();
	 textarea.appendText("Coef de correlation entre "+a.getName()+" et "+b.getName()+":\n");
	 textarea.appendText(Double.toString(a.coefcorr(b, instances)));
	 VBox vbox=new VBox(scatterChart,textarea);
	    Scene scene  = new Scene(vbox);	    
	    Stage stage=new Stage();
	    stage.setScene(scene);
	    stage.show();  
	    
}
@FXML
public void boxplot() {

	TextArea textarea = new TextArea();
	 for (int i=0;i<instances.getAttributs().size()-1;i++) {
		 Attribute a=new Attribute(instances.getAttributs().get(i),0);
		 a.descfunc(instances);
		 textarea.appendText("\n*************"+a.getName()+":*************\n");
		   textarea.appendText("Mediane: "+a.getMediane()+"\n");
		   textarea.appendText("Moyenne: "+a.getMoyenne()+"\n");
		   textarea.appendText("Mode: ");
		   for(int j=0;j<a.getMode().size();j++) {textarea.appendText(a.getMode().get(j).getValeur()+" ");}
		   textarea.appendText("\nQ1: "+a.getQ1());
		   textarea.appendText("\nQ3: "+a.getQ3());
		   textarea.appendText("\nIQR: "+a.getIqr());
		   textarea.appendText("\nMin: "+a.getMin());
		   textarea.appendText("\nMax: "+a.getMax());
		   textarea.appendText("\nOutliers:");
		   for(int j=0;j<a.getOutliers().size();j++) {textarea.appendText(a.getOutliers().get(j)+" ");}
     }
	        VBox vbox=new VBox(textarea);
           Scene s=new Scene(vbox);
           Stage primarystage=new Stage();
           primarystage.setScene(s);
	       primarystage.show();	
	
	
	
}
}
