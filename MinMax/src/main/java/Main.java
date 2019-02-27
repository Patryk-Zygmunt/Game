import board.GomokuVal;
import board.OX;
import board.TicToeBoard;
import com.google.gson.Gson;
import evaluation.EvaluationOX;
import game.Game;
import game.GameOX;
import model.GomokuState;
import model.XOState;


import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {


        port(8000);
        post("/xo/move", (req, res)->{
            XOState state = new Gson().fromJson(req.body(), XOState.class);
            Game game = new GameOX();
         return  game.nextMove(new TicToeBoard(state.getBoard().toArray(new OX[0])), state.getPlayer1(),state.getPlayer2())  ;
        });
        post("/xo/win", (req, res)->{
            XOState state = new Gson().fromJson(req.body(), XOState.class);
            Game game = new GameOX();
            return  game.checkWin(new TicToeBoard(state.getBoard().toArray(new OX[0])), OX.X,OX.O, new EvaluationOX())  ;
        });

        post("/gomoku/move", (req, res)->{
            GomokuState state = new Gson().fromJson(req.body(), GomokuState.class);
            Game game = new GomokuGame();
            return   game.nextMove(new GomokuBoard(state.getBoard()), GomokuVal.BLACK, GomokuVal.WHITE );
        });
        post("/gomoku/win", (req, res)->{
            GomokuState state = new Gson().fromJson(req.body(), GomokuState.class);
            Game game = new GomokuGame();
          return  game.checkWin(new GomokuBoard(state.getBoard()), GomokuVal.BLACK, GomokuVal.WHITE );
        });

    }
}

