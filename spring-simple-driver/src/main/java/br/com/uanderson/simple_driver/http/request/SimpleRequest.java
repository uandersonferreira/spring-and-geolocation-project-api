package br.com.uanderson.simple_driver.http.request;

public record SimpleRequest(
    // longitude (urlStart) and latitude (urlEnd) - ponto A - ponto B
    String urlStart,
    String urlEnd
) { }
