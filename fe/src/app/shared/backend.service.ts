import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, BehaviorSubject } from "rxjs";
import { map, delay, tap } from "rxjs/operators"
import { of, } from 'rxjs';
import { MapLocation } from '../components/destinations/map/map.component';

export interface WeatherData {
  summary: string,
  icon: string,
  actualTemp: number,
  minTemp: number,
  maxTemp: number
}

// for airport-available
export interface Airport {
  name: string,
  iata: string,
  country: string,
  location: {
    x: number,
    y: number
  }
}

@Injectable({
  providedIn: "root"
})
export class BackendService {

  baseUrls = "http://localhost:8080";

  constructor(private httpClient: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({ 
      'Access-Control-Allow-Origin':'*',
      'Authorization':'authkey',
      'userid':'1'
    })
  };

  weatherInfo() : Observable<WeatherData[]> {
    return this.httpClient.get(this.baseUrls + "/info/weather-at-served-airports", this.httpOptions)
    .pipe(map(x=>{

      const airportNames : string[] = Object.keys(x);
      const weatherData: WeatherData[] = [];
      airportNames.map(key=>{
        weatherData.push(x[key]);
      })

      return weatherData;
    }))
  }

  // weatherInfo() : Observable<WeatherData[]> {
  //   return of([
  //     <WeatherData>{
  //       summary: "Sunny",
  //       icon: "sun",
  //       actualTemp: 22,
  //       minTemp: 27,
  //       maxTemp: 18
  //     },
  //     <WeatherData>{
  //       summary: "Sunny",
  //       icon: "sun",
  //       actualTemp: 22,
  //       minTemp: 27,
  //       maxTemp: 18
  //     },
  //     <WeatherData>{
  //       summary: "Sunny",
  //       icon: "sun",
  //       actualTemp: 22,
  //       minTemp: 27,
  //       maxTemp: 18
  //     }
  //   ])
  //   .pipe(tap(e=>{
  //     console.log(e)
  //   }));
  // }

  available() : Observable<Airport[]> {
    return this.httpClient.get(this.baseUrls + "/airport-available", this.httpOptions)
    .pipe(map(x=>{

      const airportNames : string[] = Object.keys(x);
      const airports: Airport[] = [];
      airportNames.map(key=>{
        airports.push(x[key]);
      })

      return airports
    }));
  }

  // available() : Observable<Airport[]> {
  //   return of([
  //     <Airport>{location: {x: -122.414, y: 37.776}, name: "San Francisco"},
  //     <Airport>{location: {x: -77.032, y: 38.913}, name: "Washington DC"},
  //     <Airport>{location: {x: -74.0059413, y:  40.7127837}, name: "New York"}
  //   ])
  //   .pipe(tap(e=>{
  //     console.log(e)
  //   }));
  // }

  //   //       <MapLocation>{lat: -122.414, lon: 37.776, name: "San Francisco"},
  //   //       <MapLocation>{lat: -77.032, lon: 38.913, name: "Washington DC"},
  //   //       <MapLocation>{lon: 40.7127837, lat: -74.0059413, name: "New York"},
  //   //       <MapLocation>{lon: 41.8781136, lat: -87.6297982, name: "Chicago"},

  departureIp(ip: string) : Observable<Airport> {
    return this.httpClient.get(this.baseUrls + "/nearest-departure",
    {
      params: {
        ip: ip
      },
      headers: new HttpHeaders({
      })
    })
    .pipe(map(e=><Airport>e))
  }

  // departureIp(ip: string) : Observable<Airport> {
  //   return of(<Airport>{location: {x: -118.2436849, y: 34.0522342}, name: "Los Angeles"})
  //   .pipe(tap(e=>{
  //     console.log(e)
  //   }));
  // }
  
  // locations(ip: string) : Observable<{departure: MapLocation, locations: MapLocation[]}> {

  //   this.httpClient.get()

  //   // return of(
  //   //   {
  //   //     departure: <MapLocation>{lon: 34.0522342, lat:-118.2436849, name: "Los Angeles"},
  //   //     locations: [
  //   //       <MapLocation>{lat: -122.414, lon: 37.776, name: "San Francisco"},
  //   //       <MapLocation>{lat: -77.032, lon: 38.913, name: "Washington DC"},
  //   //       <MapLocation>{lon: 40.7127837, lat: -74.0059413, name: "New York"},
  //   //       <MapLocation>{lon: 41.8781136, lat: -87.6297982, name: "Chicago"},
  //   //     ]
  //   //   },
  //   // )
  //   // .pipe(delay(3000));
  //   // .pipe(map(())
  // }
}