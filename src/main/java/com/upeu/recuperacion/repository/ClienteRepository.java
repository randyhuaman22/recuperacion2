package com.upeu.recuperacion.repository;

import com.upeu.recuperacion.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
