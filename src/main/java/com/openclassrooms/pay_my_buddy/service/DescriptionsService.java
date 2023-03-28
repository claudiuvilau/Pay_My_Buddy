package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.pay_my_buddy.model.Descriptions;
import com.openclassrooms.pay_my_buddy.repository.DescriptionsRepository;

@Service
@Transactional
public class DescriptionsService {

    @Autowired
    DescriptionsRepository descriptionsRepository;

    public Iterable<Descriptions> getDescriptions() {
        return descriptionsRepository.findAll();
    }

    public Optional<Descriptions> getDescriptionById(Integer id) {
        return descriptionsRepository.findById(id);
    }

    public Descriptions addDescription(Descriptions descriptions) {
        return descriptionsRepository.save(descriptions);
    }

    public void deleteUDescription(Descriptions descriptions) {
        descriptionsRepository.delete(descriptions);
    }

    public void deleteDescriptionById(Integer id) {
        descriptionsRepository.deleteById(id);
    }
}
