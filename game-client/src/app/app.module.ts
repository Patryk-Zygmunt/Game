import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BoardComponent } from './board/board.component';
import { GameComponent } from './game/game.component';
import { GomokuComponent } from './gomoku/gomoku.component';
import {GameService} from "./_services/game.service";
import {HtmlSanitizer} from "./pipes/html-sanitizer";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { ModalResultComponent } from './modal-result/modal-result.component';

@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    GameComponent,
    GomokuComponent,
    HtmlSanitizer,
    ModalResultComponent,


  ],
  entryComponents:[ModalResultComponent],
  imports: [
    BrowserModule,
    NgbModule
  ],
  providers: [GameService],
  bootstrap: [AppComponent]
})
export class AppModule { }
