package br.com.monkey.ecx.buyer.domain;

import org.springframework.data.jpa.domain.Specification;

public class BuyerSpecification {

    public static Specification<Buyer> governmentId(String governmentId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Buyer_.fundGovernmentId), governmentId);
    }
}
