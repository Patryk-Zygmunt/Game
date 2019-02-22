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
  _side:number;
  oppositeSide:number;
  badMove:boolean;
  gameResult:String="";
  @Input()
  blockMove:boolean;

  @Input()
  _board : FieldEnum[][]=[];
  @Output('setField') boardEvent = new EventEmitter<Field>();
  constructor(private gameService:GameService) { }

  ngOnInit() {
this._board =this.clear();
    console.log(">>>>34343>>>"+ this._board)
  }

  @Input()
  set side(val : number) {
    this._side =val;
      if(val==1) this.oppositeSide = 2;
      if(val==2) this.oppositeSide = 1;
  }


  @Input()
  set result(val : number) {
    this.gameResult="";
    if(val===this._side) this.gameResult="WIN"
        if(val===this.oppositeSide) this.gameResult="Loose";
        if(val==-1) this.gameResult="REMIS"
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
      value: this._side
    };
   if(!GameService.rightMove(move, this._board) || this.blockMove){
    this.signalBadMove()
   }else {
     this.blockMove = true;

     this.boardEvent.emit(move)
   }
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


signalBadMove():void {
  this.badMove = true;
  setTimeout(() => this.badMove = false, 300);
}

}

