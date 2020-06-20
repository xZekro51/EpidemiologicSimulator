import java.util.ArrayList;
import java.util.List;

//Defining the Population with size, encounter rate and other values
public class Population {

    public ArrayList<Person> Components;

    public int Size = 100; //The number of people in the population

    public float EncounterRate = 13; //The medium number of encounters each person has each day (assuming all are moving)

    public Population(){
        Components = new ArrayList<Person>(Size);
    }

    public Population(int size, float encounterRate){
        Size = size;
        EncounterRate = encounterRate;
        Components = new ArrayList<Person>(Size);
    }

}
