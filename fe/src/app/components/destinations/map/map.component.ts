import { Component, OnInit, AfterViewInit } from "@angular/core";
var mapboxgl = require("mapbox-gl/dist/mapbox-gl.js");

@Component({
  selector: "app-map",
  templateUrl: "./map.component.html",
  styleUrls: ["./map.component.scss"]
})
export class MapComponent implements OnInit {

  map: any;

  coords;

  constructor() {}

  addMarker(marker, className: string = 'marker') {
    // create a HTML element for each feature
    var el = document.createElement('app-marker');

    el.className = className;
  
    // make a marker for each feature and add to the map
    new mapboxgl.Marker(el)
      .setLngLat(marker.geometry.coordinates)
      .addTo(this.map);

    new mapboxgl.Marker(el)
      .setLngLat(marker.geometry.coordinates)
      .setPopup(new mapboxgl.Popup({ offset: 25 }) // add popups
        .setHTML('<h3>' + marker.properties.title + '</h3><p>' + marker.properties.description + '</p>'))
      .addTo(this.map);

      el.addEventListener('click', (e)=>{
        // Prevent the `map.on('click')` from being triggered
        e.stopPropagation();
        if(className === "marker"){
          console.log(marker);
        }
      });
  }

  

  ngOnInit() {
    mapboxgl.accessToken =
      "pk.eyJ1IjoibW5vd2FraW8iLCJhIjoiY2swam84ZndwMGJyejNsbGkwcnB4eW93bSJ9.-NzyDwHjvusBINe7FLfSvw";
    this.map = new mapboxgl.Map({
      container: "map-container",
      style: "mapbox://styles/mapbox/streets-v11"
    });

    var geojson = {
      type: 'FeatureCollection',
      features: [{
        type: 'Feature',
        geometry: {
          type: 'Point',
          coordinates: [-77.032, 38.913]
        },
        properties: {
          title: 'Mapbox',
          description: 'Washington, D.C.'
        }
      },
      {
        type: 'Feature',
        geometry: {
          type: 'Point',
          coordinates: [-122.414, 37.776]
        },
        properties: {
          title: 'Mapbox',
          description: 'San Francisco, California'
        }
      }]
    };




    this.map.on('load', ()=>{
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
        markers.push(this.addMarker(marker, (index === ownIndex) ? "marker-start" : "marker"));
      });



    });
  }
}
