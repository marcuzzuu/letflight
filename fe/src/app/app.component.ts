import { Component } from "@angular/core";
import { BackendService, Item } from './shared/backend.service';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent {
  title = "lot-challenge-front";
}
