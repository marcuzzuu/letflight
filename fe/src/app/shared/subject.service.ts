import { Injectable } from "@angular/core";
import { BehaviorSubject } from 'rxjs';
import { MapLocation } from '../components/destinations/map/map.component';

@Injectable({
  providedIn: "root"
})
export class SubjectService {
  constructor() {}

  departureSubject = new BehaviorSubject<MapLocation>(undefined);

  returnSubject = new BehaviorSubject<MapLocation>(undefined);
}
