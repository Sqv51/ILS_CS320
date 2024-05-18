package src;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        Model model = new Model();
        Control control = new Control(model, view);

    }
}