package com.spring.rest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Getter
@Setter
public class NamedEntity extends BaseEntity{

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Override
    public String toString() {
        return this.getName();
    }
}
