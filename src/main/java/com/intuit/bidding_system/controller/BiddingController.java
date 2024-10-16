package com.intuit.bidding_system.controller;

import com.intuit.bidding_system.entity.Bid;
import com.intuit.bidding_system.entity.BiddingSlot;
import com.intuit.bidding_system.model.request.BiddingSlotRegistrationRequest;
import com.intuit.bidding_system.model.response.RegistrationResponse;
import com.intuit.bidding_system.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/bidding")
public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    // admin privileges only
    @PostMapping(path = "/registerBiddingSlot")
    public ResponseEntity<RegistrationResponse> registerBiddingSlot(final @RequestBody BiddingSlotRegistrationRequest requestPayload) {
        // validate first then save
        final var persistedSlot = biddingService.scheduleSlotWindow(requestPayload);
        final var registrationResponse = RegistrationResponse.builder()
            .buildWithAcknowledged(persistedSlot)
            .buildWithMessage("Request recieved")
            .build();
        return ResponseEntity.ok()
            .body(registrationResponse);
    }

    @GetMapping(path ="/bids/productSlot/{productSlotId}")
    public List<Bid> getBidsByProductSlotId(final @PathVariable Long productSlotId) {
        return biddingService.getBidsByProductSlotId(productSlotId);
    }

    @GetMapping(path="/bids/productSlot/{productSlotId}/highest")
    public Bid getCurrentHighestBidByProductSlotId(final @PathVariable Long productSlotId) {
        return biddingService.getCurrentHighestBidByProductSlotId(productSlotId);
    }
}
