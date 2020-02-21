package actors;

public enum  Skills {
    JAVASCRIPT(120000) ,
    JAVA(19321),
    C_SHARP(18200),
    PYTHON(10000),
    SQL(1263),
    RUBY(12341),
    SOFT_SKILLS(14242);


    public long jobs;
    Skills(long jobs) {
        this.jobs = jobs;
    }

    public long getJobs() {
        return jobs;
    }
}
