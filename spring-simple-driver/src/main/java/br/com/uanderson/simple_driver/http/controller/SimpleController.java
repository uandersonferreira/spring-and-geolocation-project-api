package br.com.uanderson.simple_driver.http.controller;

import br.com.uanderson.simple_driver.http.request.SimpleRequest;
import br.com.uanderson.simple_driver.http.response.SimpleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface SimpleController {

    @PostMapping
    SimpleResponse request(@RequestBody SimpleRequest request);


}//class
