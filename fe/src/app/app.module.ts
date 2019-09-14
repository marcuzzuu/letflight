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
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SideNavComponent } from './components/side-nav/side-nav.component';
import { DestinationsComponent } from './components/destinations/destinations.component';
import { MapComponent } from './components/destinations/map/map.component';
import { MarkerComponent } from './components/destinations/map/marker/marker.component';

import {MatIconModule} from '@angular/material/icon';
import { DateComponent } from './components/date/date.component';

import { ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule, MatInputModule,MatNativeDateModule} from '@angular/material';
import { AddonsComponent } from './components/addons/addons.component';


@NgModule({
  declarations: [AppComponent, WelcomeComponent, NavbarComponent, SideNavComponent, DestinationsComponent, MapComponent, MarkerComponent, DateComponent, AddonsComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule,
    MatDatepickerModule, MatInputModule,MatNativeDateModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [BackendService],
  bootstrap: [AppComponent],
  entryComponents: [MarkerComponent]
})
export class AppModule {}
