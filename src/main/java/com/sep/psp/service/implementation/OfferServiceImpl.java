package com.sep.psp.service.implementation;

import com.sep.psp.dto.OfferDTO;
import com.sep.psp.model.Offer;
import com.sep.psp.model.User;
import com.sep.psp.repo.IOfferRepository;
import com.sep.psp.repo.IUserRepository;
import com.sep.psp.service.AuthenticationService;
import com.sep.psp.service.definition.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {


    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IOfferRepository offerRepository;

    @Autowired
    private IUserRepository userRepository;


    @Override
    public List<Offer> getAllNotDeleted() {
        return offerRepository.findAllByDeleted(false);
    }

    @Override
    public Offer get(Integer id) {
        return offerRepository.findById(id).get();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Offer add(OfferDTO dto) {

        User merchantUser = userRepository.findById(dto.getUserId()).get();
        User acquirerUser = userRepository.findById(dto.getAcquirerId()).get();

        Offer o = new Offer();
        o.setId(dto.getId());
        o.setName(dto.getName());
        o.setAmount(dto.getAmount());
        o.setDeleted(false);
        o.setUser(merchantUser);
        o.setAcquirerUser(acquirerUser);


        return offerRepository.save(o);
    }



    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean delete(Integer id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        optionalOffer.get().setDeleted(true);
        offerRepository.save(optionalOffer.get());
        return true;
    }

}
