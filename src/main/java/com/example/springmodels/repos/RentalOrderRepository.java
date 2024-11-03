package com.example.springmodels.repos;

import com.example.springmodels.models.RentalOrder;
import org.springframework.data.repository.CrudRepository;

public interface RentalOrderRepository extends CrudRepository<RentalOrder, Long> {}
