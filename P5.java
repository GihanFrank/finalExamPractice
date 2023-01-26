
/*Basinc interfaces */
class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return new String("Name = " + this.name + ". Age = " + this.age);
    }
}

interface Player {
    void play(String x);
}

interface Bank {
    void hasMoney(int x);
}

class Gihan extends Person implements Player {
    private String sex;

    public Gihan(String name, int age, String sex) {
        super(name, age);
        this.sex = sex;
    }

    public void play(String x) {
        System.out.println(super.toString() + " is a " + this.sex + " " + x);
    }
}

class Evan extends Person implements Player, Bank {
    private boolean isaStudent;

    public Evan(String name, int age, boolean isaStudent) {
        super(name, age);
        this.isaStudent = isaStudent;
    }

    public void play(String x) {
    }

    public void hasMoney(int x) {
        System.out.println(super.toString() + " is a" + this.isaStudent + " has summ of $" + x);
    }
}

class P5 {
    public static void main(String[] args) {
        Gihan g = new Gihan("Gihan Frank", 26, "male");
        g.play("Football");
        Evan e = new Evan("Evan perera", 10, true);
        e.play("Cricket");
        e.hasMoney(3500);
    }

}
