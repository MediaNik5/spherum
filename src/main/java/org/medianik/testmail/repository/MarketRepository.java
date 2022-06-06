package org.medianik.testmail.repository;

import org.medianik.testmail.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Long>{
}
