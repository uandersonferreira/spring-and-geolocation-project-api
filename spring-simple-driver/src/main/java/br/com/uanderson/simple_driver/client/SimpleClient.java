package br.com.uanderson.simple_driver.client;

import br.com.uanderson.simple_driver.client.response.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "simpleClient", url = "http://localhost:5000/route/v1/driving/")
@Component
public interface SimpleClient {

    @GetMapping(value = "/{longitudeInitial},{latitudeInitial};{longitudeFinal},{latitudeFinal}")
    ClientResponse request(
            @PathVariable(value = "longitudeInitial") String longitudeInitial,
            @PathVariable(value = "latitudeInitial") String latitudeInitial,
            @PathVariable(value = "longitudeFinal") String longitudeFinal,
            @PathVariable(value = "latitudeFinal") String latitudeFinal
    );


}
