import { Injectable } from '@angular/core';
import { MapLocation } from '../components/destinations/map/map.component';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  departure: MapLocation;
  return: MapLocation;
  selections: boolean[];
  departureDate: string;
  returnDate: string;

  constructor() { }

}
