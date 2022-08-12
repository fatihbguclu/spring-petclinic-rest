package com.spring.rest.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet extends NamedEntity{

    @Column(name = "birth_date", columnDefinition = "DATE")
    private LocalDate  birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
    private Set<Visit> visits;

    protected Set<Visit> getVisitsInternal(){
        if (this.visits ==  null){
            this.visits = new HashSet<>();
        }
        return this.visits;
    }

    public List<Visit> getVisits(){
        List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
        PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date",false,false));
        return Collections.unmodifiableList(sortedVisits);
    }

}
