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


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
    public Offer get(Integer id) throws NoSuchAlgorithmException, KeyManagementException {
        Offer offer = offerRepository.findById(id).orElse(null);
        if (offer != null) {
            sendOfferToPSPService(offer); // Send the offer to PSP service
        } else {
            System.out.println("Offer with ID " + id + " not found.");
        }
        return offer;
    }

    private void sendToPSPService(OfferDTO dto) throws NoSuchAlgorithmException, KeyManagementException {
// Trust all certificates (use only for testing, not recommended for production)
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        } };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Trust all hosts (use only for testing, not recommended for production)
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        // Create the URL object
        String pspServiceUrl = "https://localhost:8083/api/bankPayment/offerInfo";
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

    private void sendOfferToPSPService(Offer offer) throws NoSuchAlgorithmException, KeyManagementException {

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
    public Offer add(OfferDTO dto) throws NoSuchAlgorithmException, KeyManagementException {

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
