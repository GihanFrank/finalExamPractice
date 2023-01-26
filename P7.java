
/*inner classes simple */

class Outer {
    private int outVal = 100;

    class Inner {
        private int inVal = 20;

        public int sum() {
            return outVal + inVal;
        }
    }

}

class P7 {
    public static void main(String[] args) {

        Outer o = new Outer();


        Outer.Inner i = o.new Inner();

        System.out.println(i.sum());
    }

}
