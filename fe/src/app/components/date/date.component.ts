import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormControl,
  Validators,
  ValidationErrors
} from "@angular/forms";
import { Router } from '@angular/router';
import { FormService } from 'src/app/shared/form.service';

@Component({
  selector: "app-date",
  templateUrl: "./date.component.html",
  styleUrls: ["./date.component.scss"]
})
export class DateComponent implements OnInit {
  dateForm: FormGroup;

  constructor(public router: Router, public formService: FormService) {
    this.dateForm = new FormGroup({
      departure: new FormControl(null, [Validators.required]),
      return: new FormControl(null)
    });
  }

  ngOnInit() {}

  onSubmit(){
    this.formService.departureDate = this.dateForm.value.departure;
    this.formService.returnDate = this.dateForm.value.return;
    this.router.navigate(["services"]);
  }
}
