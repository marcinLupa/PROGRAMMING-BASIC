
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractBankOperations implements BankOperations {

     BigDecimal accountBalance;
     List<String> kindOfOperation;

    @Builder(builderMethodName="AbstractBankOperationsBuilder")

       public void checkAccountBallanceInHistory(int operations) {
        try {
            if (accountBalance == null || kindOfOperation == null) {
                throw new NullPointerException("ACCOUNT NEVER USED ERROR");
            } else {

                List<String> operationsList = new ArrayList<>(kindOfOperation.subList(0, kindOfOperation.size() - operations))
                        .stream()
                        .filter(x -> x.split("_")[0].equals("WPLATA") || x.split("_")[0].equals("WYPŁATA"))
                        .collect(Collectors.toList());

                BigDecimal totalAmount = new BigDecimal(0);

                for (String s : operationsList) {
                    if (s.split("_")[0].matches("WPLATA")) {
                        totalAmount = totalAmount.add(new BigDecimal(s.split("_")[1]));
                    }
                    if (s.split("_")[0].matches("WYPLATA")) {
                        totalAmount = totalAmount.subtract(new BigDecimal(s.split("_")[1]));
                    }
                }
                System.out.println("STAN KONTA SPRZED " + operations + " OPERACJI,WYNOSIL: " + totalAmount+ "PLN"+"\n"
                +"AKTUALNY STAN KONTA WYNOSI: "+accountBalance+" PLN");
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "EXCEPTION: " + e.getMessage());
        }
    }
    public int howManyAnullation() {
        try {
            if (kindOfOperation == null) {
                throw new NullPointerException("NULL ACCOUNT HISTORY");
            } else {
                return (int) kindOfOperation.stream().filter(x -> x.matches("ANULOWANA")).count();
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "ACCOUNT HISTORY EXCEPTION: " + e.getMessage());
        }

    }

    public void clearHistoryOfOperations() {
        kindOfOperation.clear();
    }

    @Override

    public void payment(BigDecimal amount) {
        try {
            if (amount.compareTo(new BigDecimal(0)) <= 0) {
                kindOfOperation.add("ANULOWANA");
                throw new IllegalArgumentException("AMOUNT PAYMENT ERROR");
            }
            if (amount.compareTo(new BigDecimal(0)) > 0) {
                accountBalance= accountBalance.add(amount);
                kindOfOperation.add("WPLATA_" + String.valueOf(amount));
                System.out.println("STAN KONTA PO OPERACJI: " + getAccountBalance());
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "EXCEPTION: " + e.getMessage());
        }
    }

    @Override
    public void withdrawal(BigDecimal amount) {
        //NIEZDEFINIOWANA W KLASIE ABSTRACT
    }

    @Override
    public BigDecimal accountBalance() {
        return accountBalance;
    }
}
