import {Component, Input, OnInit} from '@angular/core';
import {Field, FieldEnum} from "../_model/interfaces";
import {GameService} from "../_services/game.service";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {


  showConversation: boolean = false;
  ws: any;
  name: string;
  disabled: boolean;
  board: FieldEnum[][]=[];
  gameID:string;
  side:number=0;
  readonly  startingSide=1;
  withAI:boolean;
  gameResult:number=null;
  _gameUri:string;
  blockMove:boolean;


  constructor(private gameService:GameService){

  }
  ngOnInit(): void {
  }

  @Input()
  set gameUri(val : string) {
      this._gameUri=val;

  }


  connect() {
    //connect to stomp where stomp endpoint is exposed
    //let socket = new SockJS('http://localhost:8086/socket');
    let socket = new WebSocket("ws://localhost:8086/socket");
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect({}, function(frame) {
      that.ws.subscribe("/errors", function(message) {
        console.log("Error " + message.body);
      });
      that.ws.subscribe(`/topic${that._gameUri}/board/`+that.gameID, function(state) {
        that.board = <FieldEnum[][]>(JSON.parse(state.body)).body.board;
        that.blockMove = <number>(JSON.parse(state.body)).body.move != that.side;
        that.gameResult = <number>(JSON.parse(state.body)).body.win;
      });
      that.ws.subscribe(`/topic${that._gameUri}/ai/` + that.gameID, function(state) {
        that.board = <FieldEnum[][]>(JSON.parse(state.body)).body.board;
        that.blockMove = <number>(JSON.parse(state.body)).body.move != that.side;
        that.gameResult = <number>(JSON.parse(state.body)).body.win;
      });
      that.disabled = true;
    }, function(error) {
      console.log("STOMP error " + error);
    });
  }


  newGame(withAI:boolean) {
    this.board = this.gameService.clear();
    this.gameResult = null;
    this.side=0;
    let socket = new WebSocket("ws://localhost:8086/socket");
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect({}, function(frame) {
      that.ws.subscribe("/errors", function(message) {
        console.log("Error " + message.body);
      });
      that.ws.subscribe(`/topic${that._gameUri}/play`, function(state) {
        console.log(state);
        if(that.side==0)   that.side = <number>(JSON.parse(state.body)).side
        that.gameID = <string>(JSON.parse(state.body)).gameId
        if(withAI || (that.gameID.length>10)){
          that.blockMove = that.startingSide != that.side;
          that.disconnect();
          that.connect()
        }
      });
      that.ws.send(`/app${that._gameUri}/play`,{},JSON.stringify({ai:withAI,name:"player"}));
      that.disabled = true;
    }, function(error) {
      console.log("STOMP error " + error);
    });
  }

  disconnect() {
    if (this.ws != null) {
      this.ws.ws.close();
    }
    this.setConnected(false);
    console.log("Disconnected");
  }

  play() {
    this.disconnect();
    this.withAI = false;
    this.newGame(false);
  }

  playWithAI() {
    this.disconnect();
    this.withAI = true;
    this.newGame(true);
  }

  setField(data: Field) {
    this.blockMove =true;
    if(this.withAI) this.ws.send(`/app${this._gameUri}/move/ai/`+this.gameID, {}, JSON.stringify(data));
    else this.ws.send(`/app${this._gameUri}/move/`+this.gameID, {}, JSON.stringify(data));
  }

  clear(){
    this.disconnect();
    this.ws.send("/app/clear" );

  }

  setConnected(connected) {
    this.disabled = connected;
    this.showConversation = connected;
  }



}
