package evaluation;

import board.GameValue;
import board.OX;
import board.TicToeBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluationOX implements EvaluationFunction<TicToeBoard> {
    @Override
    public int evaluate(TicToeBoard aBoard, GameValue maximizeVal, GameValue minimizeVal) {
        var val = (OX)maximizeVal;

        int[][] winning = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        List<OX>  board =  new ArrayList<>(Arrays.asList(aBoard.getBoard()));
        for (int[] i: winning
             ) {
            var arr = new ArrayList<OX>();
            for (int j :i
                 ) {
                arr.add(board.get(j));
            }
            if(arr.stream().allMatch(v->v == maximizeVal)) return Integer.MAX_VALUE;
            if(arr.stream().allMatch(v->v == minimizeVal)) return Integer.MIN_VALUE;



        }
     //  return (int) Arrays.asList(board.getBoard()).stream().filter(v->v==val).count();
    return  0;
    }
}
