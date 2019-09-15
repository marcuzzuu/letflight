// var mapboxgl = require("mapbox-gl/dist/mapbox-gl.js");
import { Component, OnInit, AfterViewInit } from "@angular/core";
import { BackendService, WeatherData } from "src/app/shared/backend.service";
import { BehaviorSubject, of } from 'rxjs';
import { SubjectService } from 'src/app/shared/subject.service';
import { tap, map } from "rxjs/operators";

export interface MapLocation {
  lat: number;
  lon: number;
  name: string;
}

@Component({
  selector: "app-map",
  templateUrl: "./map.component.html",
  styleUrls: ["./map.component.scss"]
})
export class MapComponent implements OnInit {
  map: any;

  turf;
  mapboxgl;
  
  locations: MapLocation[] = [];

  departure: MapLocation;

  weatherData: WeatherData[] = [];


  constructor(public backendService: BackendService, private subjectService: SubjectService) {
    this.turf = eval("turf");
    this.mapboxgl = eval("mapboxgl");
  }

  addMarker(location: MapLocation, weatherData: WeatherData, index: number, departure: boolean) {
    this.addMarkerMapbox(
      {
        type: "Feature",
        geometry: {
          type: "Point",
          coordinates: [location.lat, location.lon]
        },
        properties: {
          title: "Mapbox",
          description: location.name
        }
      },
      "marker",
      departure,
      index,
      weatherData
    );
  }

  addMarkerMapbox(
    marker,
    className: string,
    departure: boolean,
    index: number,
    weatherData: WeatherData
  ) {
    // create a HTML element for each feature
    var el: HTMLElement = document.createElement("app-marker");

    if(weatherData){
      el.innerHTML += `<p class="temperature">${weatherData.actualTemp}*C<p>`;
    }

    el.className = className;

    if (departure) {
      el.classList.add("marker-start");
    }

    // make a marker for each feature and add to the map
    new this.mapboxgl.Marker(el)
      .setLngLat(marker.geometry.coordinates)
      .addTo(this.map);

    el.addEventListener("click", e => {
      // Prevent the `map.on('click')` from being triggered
      e.stopPropagation();
      if (className === "marker") {
        this.subjectService.returnSubject.next(this.locations[index]);
        this.drawLine(index)
      } else {
      }
    });
  }

  drawLine(index: number) {
    if(index > 0) {
      const arrival: MapLocation = this.locations[index];
      this.drawLineMapbox(
        [this.departure.lat, this.departure.lon],
        [arrival.lat, arrival.lon]
      );
    }
  }

  drawLineMapbox(origin: number[], destination: number[]) {
    // A simple line from origin to destination.
    var route = {
      type: "FeatureCollection",
      features: [
        {
          type: "Feature",
          geometry: {
            type: "LineString",
            coordinates: [origin, destination]
          }
        }
      ]
    };

    // A single point that animates along the route.
    // Coordinates are initially set to origin.
    var point = {
      type: "FeatureCollection",
      features: [
        {
          type: "Feature",
          properties: {},
          geometry: {
            type: "Point",
            coordinates: origin
          }
        }
      ]
    };

    // Calculate the distance in kilometers between route start/end point.
    var lineDistance = this.turf.lineDistance(route.features[0], "kilometers");

    var arc = [];

    // Number of steps to use in the arc and animation, more steps means
    // a smoother arc and animation, but too many steps will result in a
    // low frame rate
    var steps = 500;

    // Draw an arc between the `origin` & `destination` of the two points
    for (var i = 0; i < lineDistance; i += lineDistance / steps) {
      var segment = this.turf.along(route.features[0], i, "kilometers");
      arc.push(segment.geometry.coordinates);
    }

    // Update the route with calculated arc coordinates
    route.features[0].geometry.coordinates = arc;

    // Used to increment the value of the point measurement against the route.
    var counter = 0;

    var mapLayer = this.map.getLayer('route');
    if(mapLayer) {
      this.map.removeLayer("route");
      this.map.removeLayer("point");
      this.map.removeSource("route");
      this.map.removeSource("point");
    }

    this.map.addSource("route", {
      type: "geojson",
      data: route
    });

    this.map.addSource("point", {
      type: "geojson",
      data: point
    });

    this.map.addLayer({
      id: "route",
      source: "route",
      type: "line",
      paint: {
        "line-width": 2,
        "line-color": "#007cbf"
      }
    });

    this.map.addLayer({
      id: "point",
      source: "point",
      type: "symbol",
      layout: {
        "icon-image": "airport-15",
        "icon-rotate": ["get", "bearing"],
        "icon-rotation-alignment": "map",
        "icon-allow-overlap": true,
        "icon-ignore-placement": true
      }
    });
  }

  ngOnInit() {
    this.mapboxgl.accessToken =
      "pk.eyJ1IjoibW5vd2FraW8iLCJhIjoiY2swam84ZndwMGJyejNsbGkwcnB4eW93bSJ9.-NzyDwHjvusBINe7FLfSvw";
    this.map = new this.mapboxgl.Map({
      container: "map-container",
      style: "mapbox://styles/mapbox/streets-v11"
    });

    this.map.on("load", () => {
      this.backendService.weatherInfo()
      .pipe(map((x)=>{
        this.weatherData = x;
        return this.backendService.available()
        .pipe(map((x)=>{
          this.locations = x.map((e, index)=>{
            return {lat: e.location.y, lon: e.location.x, name: e.name}
          });
          return this.backendService.departureIp("10.250.194.64")
          .pipe(map(x=>{
            this.departure = {
              lat: x.location.y,
              lon: x.location.x,
              name: x.name
            }
            this.subjectService.departureSubject.next(this.departure);
            return of({})
            .pipe(tap(()=>{
              this.locations.forEach((location, index) => {
                this.addMarker(location, this.weatherData[index], index, false);
                });
                this.addMarker(this.departure, null, -1, true);
                this.map.flyTo({
                  center: [this.departure.lat, this.departure.lon]
                });
            })).subscribe();
          })).subscribe();
        })).subscribe();
      })).subscribe();
    });
  }
}
