package org.innovate.address.model.v1;

import java.util.List;

public class CompareAddressRequest {

    private AddressInstance compareWith;
    private List<Address> addresses;

    public AddressInstance getCompareWith() {
        return compareWith;
    }

    public void setCompareWith(AddressInstance compareWith) {
        this.compareWith = compareWith;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
