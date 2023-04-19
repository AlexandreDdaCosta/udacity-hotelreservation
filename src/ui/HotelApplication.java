package ui;

/*
Main access point for hotel reservation UI
 */
public class HotelApplication {

    public HotelApplication() {}

    public static void main (String[] args) {
        splashScreen();
        new MainMenu();
        System.out.println("\nThank you for using the Hanna-Barbera reservation system!");
    }

    public static void splashScreen() {
        System.out.print("""

                WELCOME to the Hanna-Barbera Hotel!
                Where smarter than average bears come to hibernate.
                """);
    }
}
