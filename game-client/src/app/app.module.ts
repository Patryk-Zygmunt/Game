import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BoardComponent } from './board/board.component';
import { GameComponent } from './game/game.component';
import { GomokuComponent } from './gomoku/gomoku.component';
import {GameService} from "./_services/game.service";
import {HtmlSanitizer} from "./pipes/html-sanitizer";

@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    GameComponent,
    GomokuComponent,
    HtmlSanitizer
  ],
  imports: [
    BrowserModule
  ],
  providers: [GameService],
  bootstrap: [AppComponent]
})
export class AppModule { }
