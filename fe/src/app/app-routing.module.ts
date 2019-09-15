import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { DestinationsComponent } from './components/destinations/destinations.component';
import { DateComponent } from './components/date/date.component';
import { AddonsComponent } from './components/addons/addons.component';

const routes: Routes = [
  { path: '', redirectTo: "/welcome", pathMatch: 'full'},
  { path: 'welcome', component: WelcomeComponent },
  { path: 'destinations', component: DestinationsComponent},
  { path: 'date', component: DateComponent },
  { path: 'services', component: AddonsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
