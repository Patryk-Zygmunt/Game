import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-modal-result',
  template: `    
    <div class="modal-body">
      <p>{{_result}}</p>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-dark" (click)="activeModal.close('Close click')">Close</button>
    </div>
  `,
  styleUrls: ['./modal-result.component.css']
})
export class ModalResultComponent {

_result:string;
  @Input()
  set result(val:any) {
    this._result="";
    if(val.result===val.side)  this._result="YOU WIN"
    if(val.result===val.opponent)  this._result="YOU LOST";
    if(val.result==-1)  this._result="DRAW"
  }


  constructor(public activeModal: NgbActiveModal) {}

}
