package com.micasa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

/**
 * Model class for database operations.
 */
@Data
@ToString
@NoArgsConstructor
@Entity(name = "flats")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"flat_number","flat_block"})
})
public class Flat
{
    @Id
    @Column(name = "flat_id")
    @UuidGenerator
    private String flatId;
    @Column(name = "flat_number")
    private String flatNumber;
    @Column(name = "flat_block")
    private String flatBlock;
    private boolean occupied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    private Society society;

    public Flat(String flatNumber, String flatBlock, boolean occupied, Society society)
    {
        this.flatNumber = flatNumber;
        this.flatBlock = flatBlock;
        this.occupied = occupied;
        this.society = society;
    }
}
