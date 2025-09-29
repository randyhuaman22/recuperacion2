package com.upeu.recuperacion.repository;

import com.upeu.recuperacion.entity.Alquiler;
import com.upeu.recuperacion.entity.Cd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdRepository extends JpaRepository<Cd,Long> {
}
