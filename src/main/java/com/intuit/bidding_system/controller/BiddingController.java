package com.intuit.bidding_system.controller;

import com.intuit.bidding_system.entity.Bid;
import com.intuit.bidding_system.exceptions.InvalidBidException;
import com.intuit.bidding_system.model.request.BidClaimRequest;
import com.intuit.bidding_system.model.request.BiddingSlotRegistrationRequest;
import com.intuit.bidding_system.model.response.BidSummaryResponse;
import com.intuit.bidding_system.model.response.RegistrationResponse;
import com.intuit.bidding_system.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @PostMapping(path="/claimBid")
    public Bid claimBid(@RequestBody final BidClaimRequest bidClaimRequest) throws InvalidBidException {
        return biddingService.claimBid(bidClaimRequest);
    }

    @GetMapping(path="/getAll/{slotId}")
    public Map<Long, List<BidSummaryResponse>> getAllBidsForCurrentMapping(final @PathVariable Long slotId) {
        return biddingService.getAllBids(slotId);
    }

    @GetMapping(path="/getAllWinners/{slotId}")
    public Map<Long, BidSummaryResponse> getBidWinner(final @PathVariable Long slotId) {
        return biddingService.getWinners(slotId);
    }
}
