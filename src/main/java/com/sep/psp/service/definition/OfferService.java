package com.sep.psp.service.definition;

import com.sep.psp.dto.OfferDTO;
import com.sep.psp.model.Offer;

import java.util.List;

public interface OfferService {
    Offer get(Integer id);
    List<Offer> getAllNotDeleted();
    boolean delete(Integer id);
    Offer add(OfferDTO dto);
}
