package org.innovate.address.model.v1;

import java.util.List;

public class CompareAddressResponse {

    private List<CompareResults> compareResults;

    public List<CompareResults> getCompareResults() {
        return compareResults;
    }

    public void setCompareResults(List<CompareResults> compareResults) {
        this.compareResults = compareResults;
    }
}
