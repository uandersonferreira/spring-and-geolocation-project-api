package br.com.uanderson.simple_driver.service.impl;

import br.com.uanderson.simple_driver.client.SimpleClient;
import br.com.uanderson.simple_driver.client.response.ClientResponse;
import br.com.uanderson.simple_driver.http.request.SimpleRequest;
import br.com.uanderson.simple_driver.http.response.SimpleResponse;
import br.com.uanderson.simple_driver.service.SimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class SimpleServiceImpl implements SimpleService {

    private final SimpleClient simpleClient;

    @Value("${env.minimum-to-pay}")
    private Double minimumToPay;

    @Value("${env.app-taxa-driver}")
    private Double appTaxaDriver;

    @Value("${env.amount-paid-per-km}")
    private Double amountPaidPerKm;

    @Override
    public SimpleResponse request(SimpleRequest request) {
        //Google Maps
        // 1° - LATITUDE (-10.2480344)
        // 2° - LONGITUDE (-48.3140138)
        // ZOOM DO MAPA - 17z
        //https://www.google.com.br/maps/place/Rodoposto+-+BR./@-10.2480344,-48.3140138,17z/data=!3m1!4b1!4m6!3m5!1s0x933b338dad1a031f:0xf8753225e323be1b!8m2!3d-10.2480397!4d-48.3114335!16s%2Fg%2F1q69xymwt?entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%3D%3D

        String[] urlInitial = request.urlStart().split("/@")[1].split(",");
        String[] urlFinal = request.urlEnd().split("/@")[1].split(","); //

        // NA API INVERTE OS VALORES (1° - LONGITUDE e 2° - LATITUDE )
        ClientResponse clientResponse = simpleClient.request(
                urlInitial[1], //LONGITUDE
                urlInitial[0], // LATITUDE
                urlFinal[1], // LONGITUDE
                urlFinal[0] // LATITUDE
        );

        Double distanceInKm = converterDistanceToKm(clientResponse.routes().getFirst().distance());
        return new SimpleResponse(
                clientResponse.waypoints().getFirst().name(),
                clientResponse.waypoints().getLast().name(),
                clientResponse.routes().getFirst().distance(),
                distanceInKm,
                calTax(clientResponse)
        );
    }

    private SimpleResponse.DetailsTravelValues calTax(ClientResponse clientResponse) {
        Double distanceInKm = converterDistanceToKm(clientResponse.routes().getFirst().distance());
        if (distanceInKm <= minimumToPay) {
            double calculateTax = minimumToPay * (appTaxaDriver / 100);
            double valueReceiverDriver = minimumToPay - calculateTax;
            return new SimpleResponse.DetailsTravelValues(
                    new BigDecimal(minimumToPay).setScale(2, RoundingMode.CEILING),
                    new BigDecimal(valueReceiverDriver).setScale(2, RoundingMode.FLOOR),
                    new BigDecimal(calculateTax).setScale(2, RoundingMode.FLOOR),
                    appTaxaDriver
            );
        }
        double valueToPassengerPay = distanceInKm * amountPaidPerKm;
        double calculateTax = valueToPassengerPay * (appTaxaDriver / 100);
        double valueReceiverDriver = valueToPassengerPay - calculateTax;
        return new SimpleResponse.DetailsTravelValues(
                new BigDecimal(valueToPassengerPay).setScale(2, RoundingMode.CEILING),
                new BigDecimal(valueReceiverDriver).setScale(2, RoundingMode.FLOOR),
                new BigDecimal(calculateTax).setScale(2, RoundingMode.FLOOR),
                appTaxaDriver
        );

    }


    private Double converterDistanceToKm(Double distance) {
        String valueConvert = new DecimalFormat("#,00").format(distance / 1000);
        return Double.parseDouble(valueConvert);
    }

}//class
