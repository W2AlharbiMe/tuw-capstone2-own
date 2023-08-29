package com.example.capstone2.Repository;

import com.example.capstone2.Model.DealerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerServiceRepository extends JpaRepository<DealerService, Integer> {
    DealerService findDealerServiceById(Integer id);
}
