import {Injectable} from '@angular/core';
import {Field, FieldEnum} from "../_model/interfaces";


@Injectable({
  providedIn: 'root'
})
export class GameService {

  static rightMove(move: Field, board: FieldEnum[][]){
   return board[move.y][move.y] == FieldEnum.EMPTY;

  }




}
