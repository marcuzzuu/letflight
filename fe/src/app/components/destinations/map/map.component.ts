import { Component, OnInit, AfterViewInit } from "@angular/core";

var mapboxgl = require("mapbox-gl/dist/mapbox-gl.js");

@Component({
  selector: "app-map",
  templateUrl: "./map.component.html",
  styleUrls: ["./map.component.scss"]
})
export class MapComponent implements OnInit {
  map: any;

  turf;

  constructor() {
    this.turf = eval("turf");
  }

  addMarker(marker, className: string = "marker") {
    
    // create a HTML element for each feature
    var el = document.createElement("app-marker");

    el.className = className;

    // make a marker for each feature and add to the map
    new mapboxgl.Marker(el)
      .setLngLat(marker.geometry.coordinates)
      .addTo(this.map);

    new mapboxgl.Marker(el)
      .setLngLat(marker.geometry.coordinates)
      .setPopup(
        new mapboxgl.Popup({ offset: 25 }) // add popups
          .setHTML(
            "<h3>" +
              marker.properties.title +
              "</h3><p>" +
              marker.properties.description +
              "</p>"
          )
      )
      .addTo(this.map);

    el.addEventListener("click", e => {
      // Prevent the `map.on('click')` from being triggered
      e.stopPropagation();
      if (className === "marker") {
        console.log(marker);
      }
    });
  }

  drawLine() {
    // San Francisco
    var origin = [-122.414, 37.776];

    // Washington DC
    var destination = [-77.032, 38.913];

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
    console.log(lineDistance);

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
    console.log("eval turf");
    console.log(eval("turf"));

    mapboxgl.accessToken =
      "pk.eyJ1IjoibW5vd2FraW8iLCJhIjoiY2swam84ZndwMGJyejNsbGkwcnB4eW93bSJ9.-NzyDwHjvusBINe7FLfSvw";
    this.map = new mapboxgl.Map({
      container: "map-container",
      style: "mapbox://styles/mapbox/streets-v11"
    });

    var geojson = {
      type: "FeatureCollection",
      features: [
        {
          type: "Feature",
          geometry: {
            type: "Point",
            coordinates: [-77.032, 38.913]
          },
          properties: {
            title: "Mapbox",
            description: "Washington, D.C."
          }
        },
        {
          type: "Feature",
          geometry: {
            type: "Point",
            coordinates: [-122.414, 37.776]
          },
          properties: {
            title: "Mapbox",
            description: "San Francisco, California"
          }
        }
      ]
    };

    this.map.on("load", () => {
      // this.map.addLayer(
      //   {
      //     id: "cities",
      //     type: "circle",
      //     source: {
      //       type: "vector",
      //       url: "mapbox://examples.8fgz4egr"
      //     },
      //     "source-layer": "sf2010",
      //     paint: {
      //       // make circles larger as the user zooms from z12 to z22
      //       "circle-radius": {
      //         base: 1.75,
      //         stops: [[12, 2], [22, 180]]
      //       },
      //       // color circles by ethnicity, using a match expression
      //       // https://docs.mapbox.com/mapbox-gl-js/style-spec/#expressions-match
      //       "circle-color": [
      //         "match",
      //         ["get", "ethnicity"],
      //         "White",
      //         "#fbb03b",
      //         "Black",
      //         "#223b53",
      //         "Hispanic",
      //         "#e55e5e",
      //         "Asian",
      //         "#3bb2d0",
      //         /* other */ "#ccc"
      //       ]
      //   }
      // });

      let markers = [];
      const ownIndex = 1;
      geojson.features.forEach((marker, index) => {
        markers.push(
          this.addMarker(marker, index === ownIndex ? "marker-start" : "marker")
        );
      });

      // draw line
      this.drawLine();
    });
  }
}
