package code.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Proxy(lazy=false)
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String company;
    private String car;
    private String name;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch=FetchType.EAGER,
//            fetch = FetchType.LAZY,
            mappedBy = "order"
    )
    @Fetch(value = FetchMode.SUBSELECT)
    List<Work> works;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch=FetchType.EAGER,
//            fetch = FetchType.LAZY,
            mappedBy = "order"
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Detail> details;

    @Override
    public String toString() {
        return "Order {\n" +
                "   company=" + company + "\n" +
                "   car=" + car + "\n" +
                "   name=" + name + "\n" +
                "   details=" + (details==null?"нет":details.size()) + "\n" +
                "   works=" + (works ==null?"нет": works.size()) + "\n" +
                "}\n";
    }

    public static Order makeOrder(String str) {
        Order order = new Order();
        order.setCar("VW");
        order.setCompany("Unknown company");
        order.setName(str);
        return order;
    }

    public Double getDetailsSum() {
        Double result = 0.0;
        for(Detail detail : this.details) {
            result += detail.getSum();
        }
        return result;
    }

    public Double getDetailsCount() {
        Double result = 0.0;
        for(Detail detail : this.details) {
            result += detail.getCount();
        }
        return result;
    }

    public Double getWorksCount() {
        Double result = 0.0;
        for(Work work : this.works) {
            result += work.getCount();
        }
        return result;
    }

    public Boolean isOrderEmpty() {
        if(details.size() == 0 && works.size() == 0) {
            return true;
        } else {
            return false;
        }
    }


}
