package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.Invoices;
import com.openclassrooms.pay_my_buddy.repository.InvoicesRepository;

@Service
public class InvoicesService {

    @Autowired
    InvoicesRepository invoicesRepository;

    public Iterable<Invoices> getInvoices() {
        return invoicesRepository.findAll();
    }

    public Optional<Invoices> getInvoiceById(Integer id) {
        return invoicesRepository.findById(id);
    }

    public Invoices addInvoice(Invoices invoices) {
        return invoicesRepository.save(invoices);
    }

    public void deleteInvoices(Invoices invoices) {
        invoicesRepository.delete(invoices);
    }

    public void deleteInvoicesById(Integer id) {
        invoicesRepository.deleteById(id);
    }
}
