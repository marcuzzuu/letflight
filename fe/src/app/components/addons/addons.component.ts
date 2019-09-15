import { Component, OnInit } from '@angular/core';
import { FormService } from 'src/app/shared/form.service';

export interface AdditionalService {
  name: string,
  style: string
}

@Component({
  selector: 'app-addons',
  templateUrl: './addons.component.html',
  styleUrls: ['./addons.component.scss']
})
export class AddonsComponent implements OnInit {

  selections = [false, false, false];

  services : AdditionalService[] = [
    {name: "Additional luggage", style: 'luggage'},
    {name: "Car rental", style: 'car'},
    {name: "Seat selection", style: 'seat'}
  ]

  constructor(public formService: FormService) { }

  ngOnInit() {
  }

  onSwitch(index: number) {
    this.selections[index] = !this.selections[index];
  }

  onSubmit(){
    this.formService.selections = this.selections;
    console.log(this.formService);
  }

}
