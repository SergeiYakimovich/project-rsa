package code.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * справочник - сущность для БД
 */
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

    @Column(name = "name")
    private String name;

    @Lob
    private String text;

}
