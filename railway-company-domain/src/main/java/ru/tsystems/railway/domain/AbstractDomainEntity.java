package ru.tsystems.railway.domain;


import javax.persistence.*;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects needing this property.
 *
 * @author lazukovvs@gmail.com
 */
@MappedSuperclass
public abstract class AbstractDomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Indicates that this instance has a persisted copy.
     *
     * @return true if the object has a persisted copy.
     */
    public boolean isPersistent() {
        return getId() != null && getId() > 0;
    }

    @Override
    public int hashCode() {
        if (!isPersistent()) {
            return super.hashCode();
        }
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!isPersistent()) {
            return super.equals(obj);
        }
        return !(obj == null || !(obj instanceof AbstractDomainEntity))
                && ((AbstractDomainEntity) obj).getId() != null
                && getId().equals(((AbstractDomainEntity) obj).getId());
    }

}
