package com.sep.psp.repo;

import com.sep.psp.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOfferRepository extends JpaRepository<Offer,Integer> {
    List<Offer> findAllByDeleted(boolean deleted);

}
