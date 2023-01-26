
/*Rectangle and square */
/*super class and child class */
/*IsA relationship */

class Pectangle {
    protected int width;
    protected int height;
    protected Point origin;

    public Pectangle(int width, int height, Point origin) {
        this.width = width;
        this.height = height;
        this.origin = origin;
    }

    public String toString() {
        return new String(
                "Width and Height = " + this.width + ", " + this.height + "\nOrigin = " + this.origin.toString()
                        + "\n");
    }
}

class Square extends Pectangle {
    private int side;

    public Square(int side, Point origin) {
        super(side, side, origin);
        this.side = side;
    }

    public String areaC() {
        int a = this.side * this.side;
        String b = Integer.toString(a);
        return b;
    }

    public String diaG() {
        double a = Math.sqrt(2 * this.side * this.side);
        String b = Double.toString(a);
        return b;
    }
}

class P3 {
    public static void main(String[] args) {
        Point p1 = new Point(10, 65);
        Square s1 = new Square(12, p1);
        Pectangle r1 = new Pectangle(10, 15, p1);

        System.out.println(s1.toString());
        System.out.println(r1.toString());
        System.out.println(s1.areaC());
        System.out.println(s1.diaG());
    }
}
