package application;

import java.util.ArrayList;

public class Cluster {
	int num;Instance representative;	ArrayList<Instance> objects;
	
	
Cluster(){}
Cluster(int num,Instance representative){
	this.num=num;this.representative=representative;
	this.objects=new ArrayList<Instance>();
}

public int getNum() {
	return num;
}


public void setNum(int num) {
	this.num = num;
}


public Instance getRepresentative() {
	return representative;
}


public void setRepresentative(Instance representative) {
	this.representative = representative;
}


public ArrayList<Instance> getObjects() {
	return objects;
}


public void setObjects(ArrayList<Instance> objects) {
	this.objects = objects;
}
public float coutclust() {
	float cost=0;
	for (int i =0;i<this.objects.size();i++) {
		cost+=this.objects.get(i).distance(this.representative);
	}
	return cost;
}

}
