package org.neu.psa.algorithms.gentic;

import org.neu.psa.model.Location;
import java.util.ArrayList;

public class Path{

    public double distance;
    public Location[] Locations;
    public Location[] allLocations;
    private int size;
    private double[][]matrix;
    public double fitness;


    public Path(double[][]matrix, Location[]allLocations){
        this.matrix = matrix;
        this.allLocations = allLocations;
        Locations = new Location[matrix.length];
        distance = 0.0;
        size = 0;
        fitness = 0.0;
    }

    public Path(double[][]matrix, Location[]allLocations, Location[]inOrder){
        this.matrix = matrix;
        this.allLocations = allLocations;
        Locations = inOrder;
        distance = 0.0;
        size = 0;
        fitness = 0;
        distance = calcDistance();
        size = Locations.length;
    }

    public Path(){}


    public String makeString(){
        String str = "";
        for(int i=0; i<size; i++){
            str += Locations[i].id+".";
        }
        return str;
    }


//    public void mutate(double mutationRate){
//        if(Math.random()<mutationRate){
//            int a = (int)(Math.random()*(size-3));
//            int b = (int)(Math.random()*(size-3));
//            Location LocationA = new Location(Locations[a+1].id, Locations[a+1].name, Locations[a+1].latitude, Locations[a+1].longitude);
//            Location LocationB = new Location(Locations[b+1].id, Locations[b+1].name, Locations[b+1].latitude, Locations[b+1].longitude);
//            Locations[a+1] = LocationB;
//            Locations[b+1] = LocationA;
//            distance = calcDistance();
//        }
//    }


    public void mutate4(double mutationRate){
        if(Math.random() < mutationRate){
            int a = (int)(Math.random()*(size-5)+1);
            int b = (int)(Math.random()*(size-5)+1);
            Location [] subLocations = new Location [3];
            for(int i=0; i<subLocations.length; i++){
                subLocations[i] = Locations[a+i];
            }
            if(a < b){
                for(int i=a; i<b; i++){
                    Locations[i] = Locations[i+3];
                }
            }else{
                for(int i=a; i>b; i--){
                    Locations[i+2] = Locations[i-1];
                }
            }
            for(int i=b, j=subLocations.length-1; i<b+subLocations.length; i++, j--){
                Locations[i] = subLocations[j];
            }
            distance = calcDistance();
        }
    }

    public void randomPath(){
        boolean [] visited = new boolean[allLocations.length];
        int added = 0;
        while(added < allLocations.length){
            int rand = (int)(Math.random()*allLocations.length);
            if(!visited[rand]){
                Locations[added] = allLocations[rand];
                visited[rand] = true;
                added++;
            }
        }
        Locations[added] = Locations[0];
        distance = calcDistance();
        size = Locations.length;
    }


    public Location [] crossOver(Path partner){

        try{
            ArrayList [] neighbours = getNeighbours();
            ArrayList [] p_neighbours = partner.getNeighbours();
            ArrayList [] union = new ArrayList[Locations.length];
            for(int i=1; i<neighbours.length; i++){
                ArrayList temp = new ArrayList (4);
                for(int j=0; j<2; j++){
                    Integer candidate = (Integer) neighbours[i].get(j);
                    temp.add(candidate);
                }
                for(int j=0; j<2; j++){
                    Integer candidate = (Integer) p_neighbours[i].get(j);
                    if(!temp.contains(candidate)){
                        temp.add(candidate);
                    }
                }
                union[i] = temp;
            }
            ArrayList <Integer> list = new ArrayList <Integer> ();
            Integer n = (Math.random()<0.5) ? Integer.valueOf(Locations[0].id) : Integer.valueOf(partner.Locations[0].id);
            while(list.size() < Locations.length-1){
                list.add(n);
                for(int i=1; i<union.length; i++){
                    boolean removed = union[i].remove(n);
                }
                int n_int = n.intValue();
                ArrayList find_n = union[n_int];
                if(find_n.size() > 0){
                    Integer leastI = (Integer)find_n.get(0);
                    int least_size = union[leastI.intValue()].size();
                    for(int i=1; i<find_n.size(); i++){
                        Integer testI = (Integer)find_n.get(i);
                        if(union[testI.intValue()].size() < least_size){
                            least_size = union[testI.intValue()].size();
                            leastI = (Integer)find_n.get(i);
                        }else if(union[testI.intValue()].size() == least_size){
                            if(Math.random() < 0.5){
                                leastI = (Integer)find_n.get(i);
                            }
                        }
                    }
                    n = leastI;
                }else{
                    double least = 100000;
                    Integer choose = Integer.valueOf(1);
                    for(int i=1; i<Locations.length; i++){
                        Integer temp = Integer.valueOf(i);
                        if(!list.contains(temp) && matrix[Locations[i].id][Locations[n_int].id] < least){
                            least = matrix[Locations[i].id][Locations[n_int].id];
                            choose = temp;
                        }
                    }
                    n = choose;
                }
            }
            Integer first = (Integer)list.get(0);
            list.add(first);
            Location [] newLocations = new Location[list.size()];
            for (int i=0; i<list.size(); i++) {
                Integer temp = (Integer)list.get(i);
                newLocations[i] = allLocations[temp.intValue()-1];
            }
            return newLocations;
        }catch(Exception e){
            System.out.println(e+" caught while Locations = \n"+makeString());
            return Locations;
        }
    }


    private ArrayList [] getNeighbours(){

        ArrayList [] lists = new ArrayList [Locations.length];
        ArrayList temp = new ArrayList(2);
        temp.add(Integer.valueOf(Locations[Locations.length-2].id));
        temp.add(Integer.valueOf(Locations[1].id));
        lists[Locations[0].id] = temp;
        for(int i=1; i<Locations.length-1; i++){
            ArrayList temp2 = new ArrayList(2);
            temp2.add(Integer.valueOf(Locations[i-1].id));
            temp2.add(Integer.valueOf(Locations[i+1].id));
            lists[Locations[i].id] = temp2;
        }
        return lists;
    }

    public double calcDistance(){
        double result = 0.0;
        for(int i=0; i<Locations.length-1; i++){
            result += matrix[Locations[i].id][Locations[i+1].id];
        }
        return result;
    }
}