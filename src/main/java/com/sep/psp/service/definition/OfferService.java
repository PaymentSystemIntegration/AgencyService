package com.sep.psp.service.definition;

import com.sep.psp.dto.OfferDTO;
import com.sep.psp.model.Offer;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface OfferService {
    Offer get(Integer id) throws NoSuchAlgorithmException, KeyManagementException;
    List<Offer> getAllNotDeleted();
    boolean delete(Integer id);
    Offer add(OfferDTO dto) throws NoSuchAlgorithmException, KeyManagementException;
}
