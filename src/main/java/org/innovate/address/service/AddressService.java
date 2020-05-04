package org.innovate.address.service;

import org.innovate.address.model.v1.CompareAddressRequest;
import org.innovate.address.model.v1.CompareAddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public final static Logger LOG = LoggerFactory.getLogger(AddressService.class);

    public CompareAddressResponse compare(CompareAddressRequest compareAddressRequest) {
        return new CompareAddressResponse();
    }

}
