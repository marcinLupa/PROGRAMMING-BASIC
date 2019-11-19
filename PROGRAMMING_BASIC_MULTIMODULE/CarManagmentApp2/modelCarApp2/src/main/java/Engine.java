import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Engine {

   private EngineType type;
   private BigDecimal power;

    public void setPower(BigDecimal power) {
        if(power.compareTo(new BigDecimal(0))<0){
            throw new IllegalArgumentException("VALUE IS UNDER 0 EXCEPTION");
        }
        else{
            this.power = power;
        }
    }

    @Override
    public String toString() {

        return type + ", MOC SILNIKA: " + String.format("%2.2f",power);
    }
}
