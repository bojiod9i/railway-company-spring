package ru.tsystems.railway.domain;


import javax.persistence.*;

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
