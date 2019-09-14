import { Component, OnInit } from '@angular/core';
import { Item, BackendService } from 'src/app/shared/backend.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {

  items: Array<Item>;
  constructor(private apiService: BackendService) {}
  ngOnInit() {
    this.fetchData();
  }
  fetchData() {
    this.apiService.fetch().subscribe(
      (data: Array<Item>) => {
        console.log(data);
        this.items = data;
      },
      err => {
        console.log(err);
      }
    );
  }

}
