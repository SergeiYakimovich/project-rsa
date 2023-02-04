package code.guide.element;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * класс для описания з/н
 */
@Getter
@Setter
public class Order {

    private String company;
    private String car;
    private String name; // имя з/н (= имя файла)

    private List<Work> works; // список работ
    private List<Detail> details; // список деталей

    @Override
    public String toString() {
        return "Order { " + "company=" + company + "   car=" + car + "   name=" + name +
                "   details=" + (details==null?"нет":details.size()) +
                "   works=" + (works ==null?"нет": works.size()) +
                " }\n";
    }

    /**
     * @return стоимость всех деталей
     */
    public Double getDetailsSum() {
        Double result = 0.0;
        for(Detail detail : this.details) {
            result += detail.getSum();
        }
        return result;
    }

    /**
     *
     * @return количество деталей
     */
    public Double getDetailsCount() {
        Double result = 0.0;
        for(Detail detail : this.details) {
            result += detail.getCount();
        }
        return result;
    }

    /**
     * @return количество (= сумма) н/ч
     */
    public Double getWorksCount() {
        Double result = 0.0;
        for(Work work : this.works) {
            result += work.getCount();
        }
        return result;
    }

    /**
     * проверка на з/н на отстутствие работ-з/ч
     * @return true, если пустой
     */
    public Boolean isOrderEmpty() {
        if(details.size() == 0 && works.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
