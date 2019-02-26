package game;

import board.Board;
import evaluation.EvaluationOX;
import board.GameValue;

public class GameOX extends Game {


    public int nextMove(Board board, GameValue maximizedVal, GameValue minimizedVal) {
        return super.nextMove(board, maximizedVal, minimizedVal, 7, new EvaluationOX());
    }

    @Override
    public int checkWin(Board board, GameValue maximizedVal, GameValue minimizedVal) {
        return super.checkWin(board, maximizedVal, minimizedVal,  new EvaluationOX());

    }


}
