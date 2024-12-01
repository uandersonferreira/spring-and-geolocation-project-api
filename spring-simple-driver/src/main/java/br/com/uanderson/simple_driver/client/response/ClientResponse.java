package br.com.uanderson.simple_driver.client.response;

import java.util.List;

public record ClientResponse(
        String code,
        List<Routes> routes,
        List<WayPoints> waypoints
) {

    public record Routes(Double distance) { }

    public record WayPoints(String name) { }


}//ClientResponse
/*
{
  "code": "Ok",
  "routes": [
    {
      "geometry": "nmjElsuxG??",
      "legs": [
        {
          "steps": [],
          "summary": "",
          "weight": 0,
          "duration": 0,
          "distance": 0
        }
      ],
      "weight_name": "routability",
      "weight": 0,
      "duration": 0,
      "distance": 0
    }
  ],
  "waypoints": [
    {
      "hint": "Lx4DgFg8A4BoAQAAAAAAAIIPAAAAAAAAQw-WQgAAAAAh1E5EAAAAALQAAAAAAAAAwgcAAAAAAABYAwCAObs__e0a8P88TMwArVghAwgAfwC8w-Xp",
      "distance": 8420731.667199885,
      "name": "",
      "location": [
        -46.154951,
        -1.041683
      ]
    },
    {
      "hint": "Lx4DgFg8A4BoAQAAAAAAAIIPAAAAAAAAQw-WQgAAAAAh1E5EAAAAALQAAAAAAAAAwgcAAAAAAABYAwCAObs__e0a8P__QMwA-wkhAwgAfwC8w-Xp",
      "distance": 8418954.307403382,
      "name": "",
      "location": [
        -46.154951,
        -1.041683
      ]
    }
  ]
}
 */