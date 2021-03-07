package com.example.demo.models.vet;

import com.example.demo.models.NamedEntity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "specialties")
public class Speciality extends NamedEntity implements Serializable {

}