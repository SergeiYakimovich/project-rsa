package code.guide.parse.csvtype;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * класс для описания деталей - 2 позиции
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"name", "count"})
public class CsvDetail {
    public String name;
    public String count;
}
