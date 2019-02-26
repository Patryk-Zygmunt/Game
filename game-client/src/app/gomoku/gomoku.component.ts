import {Component, EventEmitter, Input, OnInit, Output, Pipe} from '@angular/core';
import {Field, FieldEnum} from "../_model/interfaces";
import {GameService} from "../_services/game.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-gomoku',
  templateUrl: './gomoku.component.html',
  styleUrls: ['./gomoku.component.css']
})
export class GomokuComponent implements OnInit {

  @Input()
  _side:number;
  oppositeSide:number;
  badMove:boolean;
  gameResult:String="";
  @Input()
  blockMove:boolean;
  readonly  board_X =15;
  readonly  board_Y =15;
  @Input()
  _board : FieldEnum[][]=[];
  @Output('setField') boardEvent = new EventEmitter<Field>();
  constructor(private gameService:GameService) { }

  ngOnInit() {
    this._board =this.clear();
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
      this._board = val;
    }
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
    return  new Array<FieldEnum[]>(this.board_Y).fill(new Array<FieldEnum>(this.board_X).fill(FieldEnum.EMPTY));
  }




  mapSign(field: FieldEnum):string{
    switch (field) {
      case FieldEnum.EMPTY:
        return " "
      case FieldEnum.X:
        return "<span style='color:black'> &#x2688</span>  "
      case FieldEnum.O:
        return "<span style='color:white'> &#x2688</span>  "
    }
  }


  signalBadMove():void {
    this.badMove = true;
    setTimeout(() => this.badMove = false, 300);
  }




}
