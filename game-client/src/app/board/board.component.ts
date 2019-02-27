import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Field, FieldEnum} from '../_model/interfaces'
import {GameService} from "../_services/game.service";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ModalResultComponent} from "../modal-result/modal-result.component";



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

  @Input()
  blockMove:boolean;

  @Input()
  _board : FieldEnum[][]=[];
  @Output('setField') boardEvent = new EventEmitter<Field>();
  constructor(private gameService:GameService,private modalService: NgbModal) { }

  ngOnInit() {
this._board =this.clear();
  }

  @Input()
  set side(val : number) {
    this._side =val;
      if(val==1) this.oppositeSide = 2;
      if(val==2) this.oppositeSide = 1;
  }

  open(val) {
    const modalRef = this.modalService.open(ModalResultComponent);
    modalRef.componentInstance.result = {result:val,side:this._side,opponent:this.oppositeSide};
  }


  @Input()
  set result(val : number) {
   // if(val!=0)this.open(val)
  }

  @Input('board')
  set board(val : FieldEnum[][]) {
    if (val.length ==3) {
      this._board = val;
    }
    else {
      this._board = this.clear()
    }

  }

  changeField( x:number, y:number){
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

