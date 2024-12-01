package br.com.uanderson.simple_driver.http.request;

public record ClientRequest(
    String longitudeInitial,
    String latitudeInitial,
    String longitudeFinal,
    String latitudeFinal
) { }
