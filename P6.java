
/*Extended Interfaces */

interface Vehicle {
    void moveF(int x);

    void moveB(int x);
}

interface Airplane extends Vehicle {
    void goUp(int x);

    void goDown(int x);
}

class Car implements Vehicle {
    private String model;

    public Car(String model) {
        this.model = model;
    }

    public void moveF(int x) {
        System.out.println(this.model + " move forward " + x + "kms");
    }

    public void moveB(int x) {
        System.out.println(this.model + " move backwards " + x + "kms");
    }
}

class Plane {
    private String model;

    public Plane(String model) {
        this.model = model;
    }

    public void moveF(int x) {
        System.out.println(this.model + " move forward " + x + "kms");
    }

    public void moveB(int x) {

    }

    public void goUp(int x) {
        System.out.println(this.model + " going up by " + x + "kms");
    }

    public void goDown(int x) {
        System.out.println(this.model + " going Down by " + x + "kms");
    }
}

class P6 {
    public static void main(String[] args) {
        Car c = new Car("Porsche");
        Plane p = new Plane("A380");

        c.moveF(30);
        c.moveB(50);
        p.moveF(400);
        p.moveB(25);
        p.goUp(30);
        p.goDown(10);
    }

}
