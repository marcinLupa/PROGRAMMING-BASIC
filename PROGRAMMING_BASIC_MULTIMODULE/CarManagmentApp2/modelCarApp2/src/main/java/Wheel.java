import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Wheel {

    private String model;
    private int size;
    private TyreType type;


    public void setModel(String model) {
        if(model==null){
            throw new NullPointerException("MODEL IS NULL EXCEPTION");
        }
        this.model = model.toUpperCase().replaceAll("[^W\\s]","");
    }

    public void setSize(int size) {
        if(size<0){
            throw new IllegalArgumentException("SIZE UNDER 0 EXCEPTION");
        }
        this.size = size;
    }


    @Override
    public String toString() {
        return " PRODUCENT: " + model +
                " ROZMIAR: " + size +
                " " + type;
    }
}
