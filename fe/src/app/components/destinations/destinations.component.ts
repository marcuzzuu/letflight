import { Component, OnInit } from '@angular/core';
import { BackendService } from 'src/app/shared/backend.service';
import { BehaviorSubject } from 'rxjs';
import { SubjectService } from 'src/app/shared/subject.service';
import { MapLocation } from './map/map.component';
import { Router } from '@angular/router';
import { FormService } from 'src/app/shared/form.service';

@Component({
  selector: 'app-destinations',
  templateUrl: './destinations.component.html',
  styleUrls: ['./destinations.component.scss']
})
export class DestinationsComponent implements OnInit {

  departure: string = "hackyeah";

  arrival = "";

  arrivalSelected: boolean = true;

  constructor(public subjectService: SubjectService, private router: Router, public formService: FormService) {
  }

  ngOnInit() {
    this.subjectService.returnSubject.subscribe(x=>{
      if(x != undefined){
        this.arrivalSelected = false;
        this.arrival = x.name;
      }
    });

    this.subjectService.departureSubject.subscribe(x=>{
      if(x != undefined){
        this.departure = x.name;
      }
    })
  }

  onNext(){
    this.formService.departure = this.subjectService.departureSubject.value;
    this.formService.return = this.subjectService.returnSubject.value;
    this.router.navigate(["date"]);
  }

}
