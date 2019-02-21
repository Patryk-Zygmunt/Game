import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Field, FieldEnum} from '../_model/interfaces'
import {GameService} from "../_services/game.service";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  @Input()
  side:number;

  @Input()
  _board : FieldEnum[][]=[];
  @Output('setField') boardEvent = new EventEmitter<Field>();
  constructor(private gameService:GameService) { }

  ngOnInit() {
this._board =this.clear();
    console.log(">>>>34343>>>"+ this._board)

  }


  @Input('board')
  set board(val : FieldEnum[][]) {
    if (val.length >0) {
      console.log(">>>>89>>>"+val)
      this._board = val;
    }
    console.log(">>>>89>>>"+val)

  }

  changeField( x:number, y:number){
    console.log("CHANGE_FIELD")
    const move : Field = {
      x: x,
      y: y,
      value: this.side
    };
   // if(GameService.rightMove(move, this._board))
    this.boardEvent.emit(move)
  }


  private clear(){
    return  new Array<FieldEnum[]>(3).fill(new Array<FieldEnum>(3).fill(FieldEnum.EMPTY));
  }




mapSign(field: FieldEnum):string{
    switch (field) {
      case FieldEnum.EMPTY:
        return ""
      case FieldEnum.X:
        return "X"
      case FieldEnum.O:
        return "O"
    }
}

}

