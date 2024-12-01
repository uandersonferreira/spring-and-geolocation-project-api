package br.com.uanderson.simple_driver.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record SimpleResponse(
        String streetInitial,
        String streetFinal,
        @JsonProperty("distance_in_meters") Double distance,
        @JsonProperty("distance_in_km") Double km,
        @JsonProperty("details_travel_values") DetailsTravelValues detailsTravelValues

) {
    public record DetailsTravelValues(
            BigDecimal valuePaidPassenger, // valor pago pelo passageiro
            BigDecimal valueReceivedDriver, // valor recebido pelo motorista
            BigDecimal valueReceivedIntermediate, // valor recebido pelo intermediario ( empresa )
            Double taxOfIntermediate // taxa do intermediario ( empresa )
    ) {
    }
}
