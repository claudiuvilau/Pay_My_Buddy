package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.CollectionMoney;
import com.openclassrooms.pay_my_buddy.repository.CollectionMoneyRepository;

@Service
public class CollectionMoneyService {

    @Autowired
    CollectionMoneyRepository collectionMoneyRepository;

    public Iterable<CollectionMoney> getCollectionMoney() {
        return collectionMoneyRepository.findAll();
    }

    public Optional<CollectionMoney> getCollectionMoneyById(Integer id) {
        return collectionMoneyRepository.findById(id);
    }

    public CollectionMoney addCollectionMoney(CollectionMoney collectionMoney) {
        return collectionMoneyRepository.save(collectionMoney);
    }

    public void deleteCollectionMoney(CollectionMoney collectionMoney) {
        collectionMoneyRepository.delete(collectionMoney);
    }

    public void deleteCollectionMoneyById(Integer id) {
        collectionMoneyRepository.deleteById(id);
    }

}
