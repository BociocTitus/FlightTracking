import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {FlightService} from './service/FlightService';
import {HttpClientModule} from '@angular/common/http';
import { SaveflightComponent } from './saveFlight/saveflight.component';
import {FormsModule} from '@angular/forms';
import {AppRoutingModule} from './app.routing';

@NgModule({
  declarations: [
    AppComponent,
    SaveflightComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [FlightService],
  bootstrap: [AppComponent]
})
export class AppModule { }
