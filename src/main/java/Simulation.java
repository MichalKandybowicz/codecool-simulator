import actors.*;
import enums.CodeCoolModules;
import enums.KnowledgeLevel;
import enums.Music;

import java.sql.SQLOutput;
import java.util.*;

import static java.lang.Math.abs;

public class Simulation {


    private static int NUMBER_OF_STARTED_MENTORS = 6;
    private static int NUMBER_OF_STARTED_STUDENTS = 30;
    private static int MAX_NEW_STUDENTS_PER_ROUNDS = 100;
    private static int CHANCE_FOR_CINEMA = 100; // if is 100 is random 0-99
    private static int CHANCE_FOR_CINEMA_1 = 85;  // Cinema >= Cinema_1
    private static int MODULE_TIME_LIMIT = 8;

    private int studentsWithNewJob = 0;
    private int studentBackToOldJob = 0;

    private int mentorsChangeJob = 0;
    private int firedMentors = 0;

    private int tour = 0;
    private int ONE_YEAR = 24;
    private int durationOfTheSimulation = 10 * ONE_YEAR;


    private ArrayList < Student > studentsArrayList = new ArrayList< Student >();
    private ArrayList < Mentor > mentorsArrayList = new ArrayList< Mentor>();
    private HashMap< Skills, Long > jobsHashMap = new HashMap< Skills, Long >();

    public static void main(String[] args) {

        Simulation simulation = new Simulation();

        simulation.createMentor(NUMBER_OF_STARTED_MENTORS);
        simulation.createStudents(NUMBER_OF_STARTED_STUDENTS);
        simulation.engageJobs();

        simulation.simulation();
    }


    private void engageJobs(){

        jobsHashMap.put(Skills.JAVASCRIPT, Skills.JAVASCRIPT.jobs);
        jobsHashMap.put(Skills.JAVA, Skills.JAVA.jobs);
        jobsHashMap.put(Skills.C_SHARP,  Skills.C_SHARP.jobs);
        jobsHashMap.put(Skills.PYTHON,  Skills.PYTHON.jobs);
        jobsHashMap.put(Skills.SQL,  Skills.SQL.jobs);
        jobsHashMap.put(Skills.RUBY,  Skills.RUBY.jobs);
        jobsHashMap.put(Skills.SOFT_SKILLS,  Skills.SOFT_SKILLS.jobs);

    }


    private void createMentor(int howMany){

        for (int j = 0; j < howMany; j++){
            HashMap  <Enum, Enum>  mentorSkills = new HashMap <Enum,Enum>();
            for (int i = 0; i < 3 ; i++) {
                mentorSkills.put(CodeCoolModules.getRandom(), KnowledgeLevel.getRandom());
            }

            ArrayList < Music > mentorFavoriteMusic = new ArrayList < Music >();
            for (int i = 0; i < 3 ; i++) {
                mentorFavoriteMusic.add((Music.getRandomMusic()));
            }

            ArrayList < Music > mentorDislikedMusic = new ArrayList < Music >();
            for (int i = 0; i < 3 ; i++) {
                mentorDislikedMusic.add((Music.getRandomMusic()));
            }

            this.mentorsArrayList.add(new Mentor("Name? not now..",
                    80,
                    mentorSkills,
                    mentorFavoriteMusic,
                    mentorDislikedMusic));
        }
    }

    private void createStudents(int how_many){
        for ( int i = 0; i < how_many; i++ ){

            ArrayList < Music > favoriteMusic = new ArrayList< Music >();
            for (int j = 0; j < 3 ; j++){
                favoriteMusic.add(Music.getRandomMusic());
            }
            ArrayList < Music > dislikedMusic = new ArrayList< Music >();
            for (int k = 0; k < 4 ; k++){
                dislikedMusic.add((Music.getRandomMusic()));
            }

            this.studentsArrayList.add(new Student("PotentialStudents",
                    55,
                    new HashMap <Enum, Enum>(),
                    favoriteMusic,
                    dislikedMusic,
                    0,
                    0,
                    CodeCoolModules.PROGRAMING_BASIC
            ));
        }
    }


    private void changeJobsNumber(int randomNumber){ // +
        // change jobs number random for all single technology -3%/+3%
        for (Skills skill : Skills.values()){
            long jobs = jobsHashMap.get(skill);
            Random generator = new Random();
            generator.nextInt(6); // 0  to 5
            double percentage = abs(randomNumber) / 100.00;

            if ( randomNumber == 0){
                jobsHashMap.put(skill, jobs);
            }
            else if (randomNumber > 0){
                jobsHashMap.put(skill,(long) (jobs + (percentage * jobs)));
            }
            else {
                jobsHashMap.put(skill, (long) (jobs - (percentage * jobs)));
            }

        }
    }

    private boolean mentorWithTechnology(CodeCoolModules module) {
        boolean doesKnowTechnology = false;
        for (Mentor mentor : this.mentorsArrayList) {

            if(mentor.isCodeCoolModule(module)) {
                doesKnowTechnology =  mentor.isCodeCoolModule(module);}
        }
        return doesKnowTechnology;
    }

    private boolean iscCodeCoolCinema() {
        Random generator = new Random();
        int chance = generator.nextInt(CHANCE_FOR_CINEMA);
        return chance >= CHANCE_FOR_CINEMA_1;
    }

    private void codeCoolCinema(){
        if (iscCodeCoolCinema()){
            for (Mentor mentor : this.mentorsArrayList){
                mentor.setSatisfactionAfterCinema();
            }
            for (Student student : this.studentsArrayList){
                student.setSatisfactionAfterCinema();
            }
            System.out.println("CodeCoolCinema!!");
        }
    }

    private void workshopForMentors(){
        for (int i = 0; i < mentorsArrayList.size(); i++){
            
        }
    }

    private void musicForThisWeek() {
        Music dayMusic = Music.getRandomMusic();
        System.out.println("Music for this week is: " + dayMusic);
        for (Mentor mentor : this.mentorsArrayList){
            mentor.listeningMusic(dayMusic);
        }
        for (Student student : this.studentsArrayList){
            student.listeningMusic(dayMusic);
        }
    }

    private void studentsSimulation() {

        int howManyTodayGetNewJob = 0;
        int howManyTodayBackToOldJob = 0;

        for (int j = this.studentsArrayList.size() - 1; j > 0; j--) {
            Student student = this.studentsArrayList.get(j);
            if (student.getSatisfaction() < 30) {
                this.studentsArrayList.remove(j);
                howManyTodayBackToOldJob++;
            }
            else {
                CodeCoolModules actualModule = student.getActualModule();
                boolean isTechnology = mentorWithTechnology(actualModule);
                student.meetMentor(isTechnology);
                if (student.getHowLongInThisModule() > MODULE_TIME_LIMIT) {
                    this.studentsArrayList.remove(j);
                    howManyTodayBackToOldJob++;
                } else if (student.getHowLongInThisModule() > 5 &&
                        student.getActualModule() != CodeCoolModules.ADVANCED) {
                    student.tryPa();
                } else if (student.getHowLongInThisModule() > 5 &&
                        student.getActualModule() == CodeCoolModules.ADVANCED) {
                    this.studentsArrayList.remove(j);
                    howManyTodayGetNewJob++;
                }
                student.setPlasOneInHowLongInThisModule();
                student.setPlasOneInProgressInActualModule();
            }
        }
        this.studentBackToOldJob += howManyTodayBackToOldJob;
        this.studentsWithNewJob += howManyTodayGetNewJob;
        System.out.println("\n thx for " + tour + " week our course");
        System.out.println("inThisWeek " + howManyTodayBackToOldJob + " students back to old job");
        System.out.println("inThisWeek " + howManyTodayGetNewJob + " student finish course");
    }


    private void mentorsSimulation(){
        for (int j = mentorsArrayList.size() - 1; j > 0; j--) {
            if (mentorsArrayList.get(j).getName().equals("fired")){
                mentorsArrayList.remove(j);
            }
        }
        for (int j = 0 ; j < mentorsArrayList.size(); j++){
            if (mentorsArrayList.get(j).isDrunk()){
                System.out.println("mentor was drunk");
                mentorsArrayList.get(j).setName("fired");
                firedMentors ++;
            }
            if (mentorsArrayList.get(j).getSatisfaction() < 25){
                System.out.println("mentor has low satisfaction");
                mentorsArrayList.get(j).setName("fired");
                mentorsChangeJob ++;
           }
            mentorsArrayList.get(j).mentorsMeeting();

        }
        mentorsBalance();
    }

    private void mentorsBalance(){
        double mentorsStudents = (double) this.mentorsArrayList.size() / // ilu jest mentorów na studenta
                (double) this.studentsArrayList.size() ;
        if (mentorsStudents < 0.035){
            createMentor(1);
            System.out.println("need more mentors");

        }else if(mentorsStudents > 0.05 ){
            //todo need scoring system for fire worst mentor not like now XD
            System.out.println("fire mentor");
            Random random = new Random();
            this.mentorsArrayList.remove(random.nextInt(this.mentorsArrayList.size()));
        }
    }

    private void simulation() {
        for (int i = 0; i < this.durationOfTheSimulation; i++) {
            Random generator = new Random();
            createStudents(generator.nextInt(MAX_NEW_STUDENTS_PER_ROUNDS));
            changeJobsNumber(generator.nextInt(16) - 8); // max 8% jobs change

            musicForThisWeek();
            codeCoolCinema();

            studentsSimulation();
            mentorsSimulation();
            this.tour++;
            System.out.println("\nStudents No. " + studentsArrayList.size() + "\n");
        }

        // end simulation loop
        System.out.println("\nlosers: " + this.studentBackToOldJob);
        System.out.println("winners: " + this.studentsWithNewJob);
        System.out.println("How many mentors change job: " + mentorsChangeJob);
        System.out.println("How many mentors has fired: " + firedMentors);
        System.out.println("How many mentors work now: " + mentorsArrayList.size());
    }
}


