import {Injectable} from '@angular/core';
import {Field, FieldEnum} from "../_model/interfaces";


@Injectable({
  providedIn: 'root'
})
export class GameService {

  static rightMove(move: Field, board: FieldEnum[][]){
   return board[move.y][move.x] == FieldEnum.EMPTY;
  }

  static rightMoveGomoku(move: Field, board: FieldEnum[][]){
    return board[move.y][move.x] == FieldEnum.EMPTY;
  }



   clear(){
    return  new Array<FieldEnum[]>(15).fill(new Array<FieldEnum>(15).fill(FieldEnum.EMPTY));
  }



}
