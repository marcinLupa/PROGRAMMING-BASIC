import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarBody {

    private CarCollor color;
    private KindOfBody type;
    private List<String> components;

    public void setComponents(List<String> components) {

        if(components==null){
            throw new NullPointerException("MODEL IS NULL EXCEPTION");
        }
        this.components = components
                .stream()
                .map(x->x.toUpperCase()
                        .replaceAll("[^W\\s]",""))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return " KOLOR: "
                + color + " "
                + type
                + " LISTA WYPOSAŻENIA: " + components;
    }
}

