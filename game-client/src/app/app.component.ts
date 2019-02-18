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

  constructor(private gameService:GameService){
    this.connect()
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
      that.ws.subscribe("/topic/board", function(state) {
        that.board = <FieldEnum[][]>(JSON.parse(state.body)).body.board;
      });
      that.disabled = true;
    }, function(error) {
      alert("STOMP error " + error);
    });
  }

  disconnect() {
    if (this.ws != null) {
      this.ws.ws.close();
    }
    this.setConnected(false);
    console.log("Disconnected");
  }

  setField(data: Field) {
    this.ws.send("/app/play", {}, JSON.stringify(data));
  }
clear(){
  this.ws.send("/app/clear" );

}

  setConnected(connected) {
    this.disabled = connected;
    this.showConversation = connected;
  }




}

