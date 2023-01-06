package code.guide.element;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private String company;
    private String car;
    private String name;

    private List<Work> works;
    private List<Detail> details;

    @Override
    public String toString() {
        return "Order { " + "company=" + company + "   car=" + car + "   name=" + name +
                "   details=" + (details==null?"нет":details.size()) +
                "   works=" + (works ==null?"нет": works.size()) +
                " }\n";
    }

    // стоимость всех деталей
    public Double getDetailsSum() {
        Double result = 0.0;
        for(Detail detail : this.details) {
            result += detail.getSum();
        }
        return result;
    }

    // количество деталей
    public Double getDetailsCount() {
        Double result = 0.0;
        for(Detail detail : this.details) {
            result += detail.getCount();
        }
        return result;
    }

    // количество (сумма) н/ч
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
