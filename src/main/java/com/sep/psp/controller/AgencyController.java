package com.sep.psp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sep.psp.dto.AuthenticationResponse;
import com.sep.psp.dto.OfferDTO;
import com.sep.psp.dto.PaypalOrderPaymentDTO;
import com.sep.psp.model.Offer;
import com.sep.psp.service.AuthenticationRequestFA;
import com.sep.psp.service.AuthenticationService;
import com.sep.psp.service.definition.OfferService;
import com.sep.psp.service.definition.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/agency")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AgencyController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;
    @Autowired
    private OfferService offerService;
    @GetMapping("/hello")
    public ResponseEntity<String> authenticate() {

        return ResponseEntity.ok().body("{\"token\": \"" + "Hello from agency microservice!" + "\"}");
    }
    @PostMapping("/createPaypalOrderPayment")
    public ResponseEntity<String> createPaypalOrderPayment(@RequestBody PaypalOrderPaymentDTO paymentDTO) {
        System.out.println(paymentDTO.toString());
        try {
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
            URL url = new URL("https://localhost:8080/gateway/paypal/order");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Enable input and output streams
            connection.setDoOutput(true);

            // Set the content type of the request
            connection.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            // Write the request body
            String requestBody = objectMapper.writeValueAsString(paymentDTO);  // Replace this with your actual JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Process the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response body
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    System.out.println("Response Body: " + response.toString());

                    return ResponseEntity.ok().body(response.toString());
                }
            } else {
                System.out.println("POST request not worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().body("{\"token\": \"" + "Hello from crypto microservice didn't reach say gateway!" + "\"}");
    }

    @PostMapping("/captureOrder")
    public ResponseEntity<String> captureOrder(@RequestBody PaypalOrderPaymentDTO paymentDTO) {
        System.out.println(paymentDTO.toString());
        try {
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
            URL url = new URL("https://localhost:8080/gateway/paypal/captureOrder");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Enable input and output streams
            connection.setDoOutput(true);

            // Set the content type of the request
            connection.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            // Write the request body
            String requestBody = objectMapper.writeValueAsString(paymentDTO);  // Replace this with your actual JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Process the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response body
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    System.out.println("Response Body: " + response.toString());

                    return ResponseEntity.ok().body("\"" + response.toString() + "\"");
                }
            } else {
                System.out.println("POST request not worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().body("{\"token\": \"" + "Hello from crypto microservice didn't reach say gateway!" + "\"}");
    }
    @PostMapping(path="/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequestFA request) {
        System.out.println(request.getEmail() + " " + request.getPassword());
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        String token = "";
        if(authenticationResponse.getToken() != null)
            token = authenticationResponse.getToken();

        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws NoSuchAlgorithmException, KeyManagementException {
        Offer o = offerService.get(id);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping(path = "/offers")
    public ResponseEntity<?> getAll() {
        List<Offer> offers = offerService.getAllNotDeleted();

        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping(path = "/addOffer")
    public ResponseEntity<?> add(@RequestBody OfferDTO dto) throws NoSuchAlgorithmException, KeyManagementException {

        Offer o = offerService.add(dto);

        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean delete = offerService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
