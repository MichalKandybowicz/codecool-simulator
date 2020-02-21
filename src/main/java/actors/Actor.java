package actors;

import java.util.ArrayList;
import java.util.HashMap;

abstract class Actor {
    String name;
    int satisfaction = 80;
    int punctuality; // base random 0 / 30
    HashMap <Enum, Integer> skillsHashMap;
    ArrayList<Enum> favoriteMusicGenres;
    ArrayList<Enum> dislikedMusicGenres;




}
