package SimulationInfo;

public class HealthCare {
    //The probability, always greater than 0, that a sane individual gets infected by meeting a contagiouss person. Green->Yellow
    public static float Infectivity = 50;

    //The chance an infected people starts showing symptoms. Yellow->Red
    public static float SymptomaticChance = 30;

    //The chance an infected syntomatic dies. Red->Black
    public static float Lethality = 30;

    //The number of days that are needed between getting infected and recovering. Yellow||Red -> Blue
    //This is the reference for duration: R < P*D.
    public static float Duration = 45;
}
