import java.math.BigDecimal;

public interface BankOperations {

    void payment(BigDecimal amount);

    void withdrawal(BigDecimal amount);

    BigDecimal accountBalance();
}
