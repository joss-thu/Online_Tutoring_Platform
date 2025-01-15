package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allowed_domain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllowedDomainDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domain_id")
    private Long domainId;

    @Column(name = "domain_name", nullable = false, unique = true)
    private String domainName;

    @ManyToOne
    @JoinColumn(name = "affiliation_id", nullable = false)
    private AffiliationDBO affiliation;
}
