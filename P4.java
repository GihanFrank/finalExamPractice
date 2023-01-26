
/*Polymorphism */
/*abstract classes */

abstract class Progression {
    protected int cur;

    // public Progression() {
    //     this.cur = 1;
    // }

    // public Progression(int cur) {
    //     this.cur = cur;
    // }

    abstract protected int nextValue();

    public void printValue(int a) {
        for (int i = 0; i < a; i++) {
            System.out.println(nextValue() + ", ");
        }
    }

}

class SumOf extends Progression {
    private int mulTi;

    public SumOf(int mulTi) {
        this.mulTi = mulTi;
        this.cur = 0;
    }

    public int nextValue() {
        this.cur = this.cur + mulTi;
        return cur;
    }
}

class MultiOf extends Progression {
    private int base;

    public MultiOf(int base) {
        this.base = base;
        this.cur = 1;
    }

    public int nextValue() {
        int a = (int) Math.pow(base, cur);
        cur++;
        return a;
    }
}

class P4 {
    public static void main(String[] args) {
        Progression[] p = new Progression[4];

        p[0] = new SumOf(2);
        p[1] = new MultiOf(4);
        p[2] = new SumOf(6);
        p[3] = new MultiOf(5);

        for (int i = 0; i < p.length; i++) {
            p[i].printValue(10);
        }
    }
}