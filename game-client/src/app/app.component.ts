import { Component } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Field, FieldEnum} from "./_model/interfaces";
import {GameService} from "./_services/game.service";
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  showConversation: boolean = false;
  ws: any;
  name: string;
  disabled: boolean;
  board: FieldEnum[][]=[];
  gameID:string;
  side:number=0;
  withAI:boolean;

  constructor(private gameService:GameService){

  }

  connect() {
    //connect to stomp where stomp endpoint is exposed
    //let socket = new SockJS('http://localhost:8086/socket');
   let socket = new WebSocket("ws://localhost:8086/socket");
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect({}, function(frame) {
      that.ws.subscribe("/errors", function(message) {
        alert("Error " + message.body);
      });
      that.ws.subscribe("/topic/board/"+that.gameID, function(state) {
        that.board = <FieldEnum[][]>(JSON.parse(state.body)).body.board;
      });
      that.ws.subscribe("/topic/ai/" + that.gameID, function(state) {
        that.board = <FieldEnum[][]>(JSON.parse(state.body)).body.board;
      });
      that.disabled = true;
    }, function(error) {
      alert("STOMP error " + error);
    });
  }


  newGame(withAI:boolean) {
    this.board = this.gameService.clear();
    let socket = new WebSocket("ws://localhost:8086/socket");
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect({}, function(frame) {
      that.ws.subscribe("/errors", function(message) {
        console.log("Error " + message.body);
      });
      that.ws.subscribe("/topic/play", function(state) {
        console.log(state);
        if(that.side==0)   that.side = <number>(JSON.parse(state.body)).side
        that.gameID = <string>(JSON.parse(state.body)).gameId
        if(withAI || (that.gameID.length>10)){
          that.disconnect()
          that.connect()
        }
      });
      that.ws.send("/app/play",{},JSON.stringify({ai:withAI,name:"player"}));
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
    this.disconnect()
    this.withAI = false;
    this.newGame(false);
  }

  playWithAI() {
    this.disconnect();
    this.withAI = true;
    this.newGame(true);
  }

  setField(data: Field) {
    if(this.withAI) this.ws.send("/app/move/ai/"+this.gameID, {}, JSON.stringify(data));
    else this.ws.send("/app/move/"+this.gameID, {}, JSON.stringify(data));
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

