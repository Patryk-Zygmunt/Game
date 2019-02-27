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

  game:string='/xo';

  constructor() { }

  ngOnInit() {
  }

}

