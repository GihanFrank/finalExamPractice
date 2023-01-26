class P9 {
    public static void main(String[] args) {
Inthred c =new Inthred("B");
Inthred d = new Inthred("2");

Thread th1 = new Thread(c);
Thread th2 = new Thread(d);

th1.start();
th2.start();
    }

}

class Inthred implements Runnable {
    private String name;

    public Inthred(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.print(this.name+",");
            try {
                Thread.sleep(1000/2);
            } catch (InterruptedException e) {
            }
        }
    }
}
