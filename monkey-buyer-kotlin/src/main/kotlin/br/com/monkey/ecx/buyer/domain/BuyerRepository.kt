package br.com.monkey.ecx.buyer.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BuyerRepository: JpaRepository<Buyer, Long>

