package br.com.uanderson.simple_driver.service;

import br.com.uanderson.simple_driver.http.request.SimpleRequest;
import br.com.uanderson.simple_driver.http.response.SimpleResponse;

public interface SimpleService {
    SimpleResponse request(SimpleRequest request);
}
