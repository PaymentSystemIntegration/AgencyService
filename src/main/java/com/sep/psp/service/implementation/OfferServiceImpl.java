package com.sep.psp.service.implementation;

import com.sep.psp.dto.OfferDTO;
import com.sep.psp.model.Offer;
import com.sep.psp.model.User;
import com.sep.psp.repo.IOfferRepository;
import com.sep.psp.repo.IUserRepository;
import com.sep.psp.service.AuthenticationService;
import com.sep.psp.service.definition.OfferService;
import com.sep.psp.service.definition.UserService;
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
    private UserService userService;

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
        Offer offer = offerRepository.findById(id).orElse(null);
        if (offer != null) {
            sendOfferToPSPService(offer); // Send the offer to PSP service
        } else {
            System.out.println("Offer with ID " + id + " not found.");
        }
        return offer;
    }

    private void sendToPSPService(OfferDTO dto) {
        String pspServiceUrl = "http://localhost:8083/api/bankPayment/offerInfo";
        System.out.println("DTO UNUTAR PSP SERVICE  "+dto.toString());
        User user= userRepository.findById(1).get();
        User user2=userRepository.findById(2).get();

        dto.setUserId(user.getUserId());
        dto.setAcquirerId(user2.getUserId());
        dto.setBalanceMerchant(user.getBalance());
        dto.setBalanceAcquirer(user2.getBalance());
        dto.setPanAcquirer(user2.getPan());
        dto.setPanMerchant(user.getPan());
        dto.setUserByMerchantId(user);
        dto.setUserByAcquirerId(user2);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println(headers.toString());
        HttpEntity<OfferDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                pspServiceUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        // Handle the response or throw an exception based on your requirements
    }

    private void sendOfferToPSPService(Offer offer) {

        if (offer != null) {
            OfferDTO dto = new OfferDTO();
            // Extract necessary information from the Offer entity
            dto.setId(offer.getId());
            dto.setName(offer.getName());
            dto.setAmount(offer.getAmount());
            dto.setDeleted(offer.getDeleted());



            sendToPSPService(dto);
        } else {
            // Handle case where offer with given ID is not found
            System.out.println("Offer not found.");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Offer add(OfferDTO dto) {

        User merchantUser = userRepository.findById(1).get();
        User acquirerUser = userRepository.findById(2).get();

        Offer o = new Offer();
        o.setId(dto.getId());
        o.setName(dto.getName());
        o.setAmount(dto.getAmount());
        o.setDeleted(false);
        o.setUser(merchantUser);
        o.setAcquirerUser(acquirerUser);
        sendToPSPService(dto);


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
