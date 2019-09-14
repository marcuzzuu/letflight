import { Component, OnInit } from '@angular/core';
import { BackendService } from 'src/app/shared/backend.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {

  options = ["destinations", "luggage information", "special offers", "option 4"];

  onOptionSelect(option: string) {
    switch(option.toLowerCase()) {
      case "destinations": {
        this.router.navigate(["destinations"]);
      }
    }
  }

  constructor(private apiService: BackendService, public router: Router) {}

  ngOnInit() {}

}
