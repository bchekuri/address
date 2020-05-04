package org.innovate.address.web;

import org.innovate.address.model.v1.CompareAddressRequest;
import org.innovate.address.model.v1.CompareAddressResponse;
import org.innovate.address.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author BChekuri
 */
@EnableSwagger2
@RestController
@RequestMapping("/v1")
public class AddressController {

    public static final Logger LOG = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @PostMapping(value = "compare", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompareAddressResponse> convert(@RequestBody CompareAddressRequest compareAddressRequest) {
        LOG.info("Address controller!");
        return new ResponseEntity<CompareAddressResponse>(addressService.compare(compareAddressRequest), HttpStatus.OK);
    }

}
