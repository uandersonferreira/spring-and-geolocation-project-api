package br.com.uanderson.simple_driver.http.controller.impl;

import br.com.uanderson.simple_driver.http.controller.SimpleController;
import br.com.uanderson.simple_driver.http.request.SimpleRequest;
import br.com.uanderson.simple_driver.http.response.SimpleResponse;
import br.com.uanderson.simple_driver.service.SimpleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/simple")
public class SimpleControllerImpl implements SimpleController {

    private final SimpleService simpleService;

    public SimpleControllerImpl(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @Override
    public SimpleResponse request(SimpleRequest request) {
        return simpleService.request(request);
    }

}//class
