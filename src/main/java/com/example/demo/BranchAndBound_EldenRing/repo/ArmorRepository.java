package com.example.demo.BranchAndBound_EldenRing.repo;

import com.example.demo.BranchAndBound_EldenRing.model.ArmorEntity;
import org.springframework.data.jpa.repository.JpaRepository; // 🌟 Cambiado a JPA tradicional
import org.springframework.stereotype.Repository;

@Repository
public interface ArmorRepository extends JpaRepository<ArmorEntity, String> {
    

}