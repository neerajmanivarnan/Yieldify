package com.wow.yieldify;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionContractsResponse {
    private List<OptionContract> contractsAboveUpperBound;
    private List<OptionContract> contractsBelowLowerBound;

    public List<OptionContract> getContractsAboveUpperBound() {
        return contractsAboveUpperBound;
    }

    public void setContractsAboveUpperBound(List<OptionContract> contractsAboveUpperBound) {
        this.contractsAboveUpperBound = contractsAboveUpperBound;
    }

    public List<OptionContract> getContractsBelowLowerBound() {
        return contractsBelowLowerBound;
    }

    public void setContractsBelowLowerBound(List<OptionContract> contractsBelowLowerBound) {
        this.contractsBelowLowerBound = contractsBelowLowerBound;
    }
}
