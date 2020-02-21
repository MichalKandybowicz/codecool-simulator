package enums;

import java.util.Random;

public enum CurrentModule {
    PROGRAMING_BASIC(1),
    WEB_PYTHON_SQL(2),
    OOP(3),
    ADVANCED(4);

    int moduleNumber;
    CurrentModule(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }
    public static Enum getRandomMusic(){ //todo now music genre can be favorite and disliked and can repeat genres xd
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
