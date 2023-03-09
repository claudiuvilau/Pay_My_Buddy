package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.pay_my_buddy.model.Invoices;

public interface InvoicesRepository extends CrudRepository<Invoices, Integer> {

}
