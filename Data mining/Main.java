package application;
	
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
	
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		launch(args);
	  /* String path="C:\\Users\\khadidja\\Desktop\\SII\\S3\\datamining\\data.txt";
		DataSource src =new DataSource();
		Instances instances=src.read(path);
		instances.printdata();	
		instances.infodataset();
		Attribute r=new Attribute("resting_blood_pressure",0);Attribute s=new Attribute("serum_cholestoral",0);Attribute h=new Attribute("maximum_heart_rate_achieved",0);
	    r.descfunc(instances);s.descfunc(instances);h.descfunc(instances);
	    r.affichage();s.affichage();h.affichage();*/
	    //LES DONNEES SONT PRESQUE SYMETRIQUES
	}

}