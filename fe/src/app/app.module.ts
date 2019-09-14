import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { WelcomeComponent } from "./components/welcome/welcome.component";

import { HttpClientModule } from "@angular/common/http";
import { BackendService } from "./shared/backend.service";

import { MatToolbarModule } from  '@angular/material/toolbar';
import { MatCardModule } from  '@angular/material/card';
import { MatButtonModule } from  '@angular/material/button';

@NgModule({
  declarations: [AppComponent, WelcomeComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatCardModule,
    MatButtonModule
  ],
  providers: [BackendService],
  bootstrap: [AppComponent]
})
export class AppModule {}
