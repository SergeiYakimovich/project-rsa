package code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Transactional
@Proxy(lazy=false)
@Getter
@Setter
@Entity
@Table(name = "guidesqls")
public class Guidesql {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String text;

    @JsonIgnore
    @OneToOne(
            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
            fetch= FetchType.EAGER
    )
    Car car;
    
}
