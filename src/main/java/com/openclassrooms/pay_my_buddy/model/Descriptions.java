package com.openclassrooms.pay_my_buddy.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "descriptions")
public class Descriptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descriptions")
    private int idDescriptions;

    @Column(name = "description")
    private String description;

    public int getIdDescriptions() {
        return this.idDescriptions;
    }

    public void setIdDescriptions(int idDescriptions) {
        this.idDescriptions = idDescriptions;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
