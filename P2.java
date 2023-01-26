
/*Rational numbers --- interactions between objects of the same type */

class Rn {
    private int nom;
    private int denom;

    public Rn() {
        this.nom = 0;
        this.denom = 1;
    }

    public Rn(int nom, int denom) {
        this.nom = nom;
        this.denom = denom;
    }

    public Rn add(Rn r) {
        int a = (this.nom * r.denom) + (this.denom * r.nom);
        int b = (this.denom * r.denom);
        return new Rn(a, b);
    }

    public Rn subR(Rn r) {
        int a = (this.nom * r.denom) - (this.denom * r.nom);
        int b = (this.denom * r.denom);
        return new Rn(a, b);
    }

    public Rn multi(Rn r) {
        int a = (this.nom * r.nom);
        int b = (this.denom * r.denom);
        return new Rn(a, b);
    }

    public Rn devR(Rn r) {
        int a = (this.nom * r.denom);
        int b = (this.denom * r.nom);
        return new Rn(a, b);
    }

    public String toString() {
        return new String(this.nom + " / " + this.denom);
    }
}

class P2 {
    public static void main(String[] args) {
        Rn r1 = new Rn();
        System.out.println("r1 = " + r1);
        Rn r2 = new Rn(15, 68);
        Rn r3 = new Rn(16, 73);
        System.out.println("r2 = " + r2 + "\n r3 = " + r3);
        Rn r4 = r2.add(r3);
        System.out.println("r4 = " + r4);
        Rn r5 = r2.subR(r3);
        Rn r6 = r2.multi(r3);
        Rn r7 = r2.devR(r3);
        System.out.println("r5" + r5);
        System.out.println("r6" + r6);
        System.out.println("r7" + r7);

    }

}
