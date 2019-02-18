import game.OX;
import game.TicToeBoard;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.nextMove(new TicToeBoard(), OX.O);
    }
}
