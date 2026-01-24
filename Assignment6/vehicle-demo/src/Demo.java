public class Demo {
    public static void main(String[] args) {
        Vehicle[] fleet = { new SUV(), new SportsCar(), new Hybrid() };

        for (Vehicle v : fleet) {
            v.forward();
            v.reverse();
            System.out.println("==============================");
        }
    }
}
