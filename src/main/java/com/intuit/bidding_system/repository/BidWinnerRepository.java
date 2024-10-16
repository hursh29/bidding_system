package com.intuit.bidding_system.repository;

import com.intuit.bidding_system.entity.BidWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidWinnerRepository extends JpaRepository<BidWinner, Long> {

}
