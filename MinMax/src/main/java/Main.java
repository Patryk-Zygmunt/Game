import board.Board;
import board.OX;
import board.TicToeBoard;
import com.google.gson.Gson;
import evaluation.EvaluationOX;
import game.Game;
import game.GameOX;

import java.util.List;
import java.util.Random;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        HelloWorld.main();

        port(8000);
        post("/xo/move", (req, res)->{
            State state = new Gson().fromJson(req.body(),State.class);
            Game game = new GameOX();
         return  game.nextMove(new TicToeBoard(state.getBoard().toArray(new OX[0])), state.getPlayer1(),state.getPlayer2())  ;
        });
        post("/xo/win", (req, res)->{
            State state = new Gson().fromJson(req.body(),State.class);
            Game game = new GameOX();
            return  game.checkWin(new TicToeBoard(state.getBoard().toArray(new OX[0])), OX.X,OX.O, new EvaluationOX())  ;
        });

        post("/gomoku/move", (req, res)->{
            State state = new Gson().fromJson(req.body(),State.class);
            Game game = new GameOX();
            return  new Random().nextInt(224) ;
        });
        post("/gomoku/win", (req, res)->{
            State state = new Gson().fromJson(req.body(),State.class);

            return "0"  ;
        });

    }
}

class State{
    private List<OX> board;
    private OX player1;
    private OX player2;

    public List<OX> getBoard() {
        return board;
    }

    public void setBoard(List<OX> board) {
        this.board = board;
    }

    public OX getPlayer1() {
        return player1;
    }

    public void setPlayer1(OX player1) {
        this.player1 = player1;
    }

    public OX getPlayer2() {
        return player2;
    }

    public void setPlayer2(OX player2) {
        this.player2 = player2;
    }
}