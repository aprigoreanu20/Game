public class ImpossibleMove extends Exception {
    public ImpossibleMove() {
        super("The player cannot move in the chosen direction. Please select another direction.");
    }
}
