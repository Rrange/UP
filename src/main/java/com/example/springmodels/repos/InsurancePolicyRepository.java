package com.example.springmodels.repos;

import com.example.springmodels.models.InsurancePolicy;
import org.springframework.data.repository.CrudRepository;

public interface InsurancePolicyRepository extends CrudRepository<InsurancePolicy, Long> {}
