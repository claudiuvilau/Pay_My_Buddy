package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.model.Descriptions;
import com.openclassrooms.pay_my_buddy.repository.DescriptionsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public Descriptions getDescription(String description) {
    Iterable<Descriptions> descriptionsList = getDescriptions();
    for (Descriptions descriptions : descriptionsList) {
      if (descriptions.getDescription().equals(description)) {
        return descriptions;
      }
    }
    return null;
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
