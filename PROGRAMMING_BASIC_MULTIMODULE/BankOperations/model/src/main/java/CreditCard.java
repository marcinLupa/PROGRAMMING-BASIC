
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCard extends AbstractBankOperations {

    private String pin;

    private boolean pinCheck(String pinFromUser) {
        try {
            if (!pinFromUser.matches("[0-9]{4}")) {
                throw new IllegalArgumentException("FORMAT OF PIN ERROR");
            } else {
                return pin.compareTo(pinFromUser) == 0;
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "GIVEN PIN EXCEPTION: " + e.getMessage());
        }

    }

    @Override
    public void withdrawal(BigDecimal amount) {
        System.out.println("PODAJ PIN");
        String pinFromUser = new Scanner(System.in).nextLine();

        try {
            if (pinFromUser.compareTo(pin) != 0) {
                kindOfOperation.add("BLAD_LOGOWANIA");
                throw new IllegalArgumentException("PIN ERROR");
            }
            if (amount.compareTo(getAccountBalance()) > 0) {
                kindOfOperation.add("ANULOWANA");
                throw new IllegalArgumentException("AMOUNT BIGGER THEN ACCOUNT BALLANCE");
            } else {
                System.out.println("POPRAWNY PIN");
                setAccountBalance(getAccountBalance().subtract(amount));

                kindOfOperation.add("WYPLATA_" + String.valueOf(amount));
                System.out.println("STAN KONTA PO OPERACJI: " + getAccountBalance());
            }
        } catch (MyException e) {
            System.err.println(e.getMessage());
            throw new MyException(ExceptionCode.DATA, "CREDIT CARD CLASS EXCEPTION: ");
        }
    }

    //CZYSZCZENIE HISTORII OPERACJI
    @Override
    public void clearHistoryOfOperations() {
        try {
            System.out.println(pin);
            System.out.println("CZY NA PEWNO CHCESZ WYCZYSCIC HISTORIE TRANSAKCJI?");
            String option = new Scanner(System.in).nextLine();
            if (!option.toUpperCase().matches("[A-Z]*")) {
                throw new NumberFormatException("FORMAT OF STRING WRONG");
            }
            if (option.equalsIgnoreCase("TAK")) {

                System.out.println("ABY WYCZYSCIC HISTORIE OPERACJI PODAJ PIN");
                String pinFromUser = new Scanner(System.in).nextLine();
                if (!pinFromUser.matches("[0-9]*")) {
                    throw new NumberFormatException("FORMAT OF PIN EXCEPTION");
                }
                if (!pinCheck(pinFromUser)) {
                    kindOfOperation.add("BLĄD_LOGOWANIA");
                    throw new IllegalArgumentException("PIN ERROR");
                } else {
                    System.out.println("HISTORIA TRANSAKCJI WYCZYSZCONA");
                    kindOfOperation = new ArrayList<>();
                    kindOfOperation.forEach(System.out::println);
                }
            }
            else {
                throw new IllegalArgumentException("POWROT DO MENU GLOWNEGO");
            }
        } catch (MyException e) {
            System.err.println(e.getMessage());
            throw new MyException(ExceptionCode.DATA, "DATA EXCEPTION: " + e.getMessage());
        }
    }


    public static void leastErrors(List<CreditCard> cards) {

        List<Long> occurrencesOfString = new ArrayList<>();
        try {
            if (cards == null) {
                throw new NullPointerException("CARD LIST IS NULL");
            } else {
                cards.forEach(x -> occurrencesOfString
                        .add(x.getKindOfOperation()
                                .stream()
                                .filter(y -> y.equals("BLAD_LOGOWANIA"))
                                .count()));
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "DATA EXCEPTION: " + e.getMessage());
        }
        try {
            int cardBunusPosition = IntStream.range(0, occurrencesOfString.size())
                    .filter(x -> occurrencesOfString
                            .get(x)
                            .equals(occurrencesOfString
                                    .stream()
                                    .min(Comparator.comparing(Long::byteValue))
                                    .get()))
                    .findFirst()
                    .orElse(0);
            CreditCard bonusCard = cards.get(cardBunusPosition);
            if (bonusCard.getAccountBalance().equals(new BigDecimal(0))) {
                throw new NullPointerException("ACCOUNT IS 0");
            }
            bonusCard.
                    setAccountBalance(bonusCard.getAccountBalance()
                            .add(bonusCard.getAccountBalance()
                                    .multiply(new BigDecimal(0.005))));
            System.out.println("NAJMNIEJ BLEDNYCH LOGOWAN MA KONTO O NUMERZE : " + cardBunusPosition + " NA LISCIE," +
                    " A JEGO STAN PO WPROWADZENIU BONUSU TO: " + String.format("%2.2f", bonusCard.getAccountBalance()));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "DATA EXCEPTION: " + e.getMessage());
        }

    }

    public static void comprasionOfAccount(CreditCard cc1, CreditCard cc2) {
        try {
            if (cc1 == null || cc2 == null) {
                throw new NullPointerException("GIVEN CARD IS NULL");
            }
            if (cc1.getAccountBalance().compareTo(cc2.getAccountBalance()) > 0) {
                System.out.println("WIEKSZY STAN KONTA MA KONTO PODANE JAKO PIERWSZE");
                System.out.println("AKTUALNY BUDZET TO: " + cc1.accountBalance + " PLN");
            }
            if (cc1.getAccountBalance().compareTo(cc2.getAccountBalance()) < 0) {
                System.out.println("WIEKSZY STAN KONTA MA KONTO PODANE JAKO DRUGIE");
                System.out.println("AKTUALNY BUDZET TO: " + cc2.accountBalance + " PLN");
            }
            if (cc1.getAccountBalance().compareTo(cc2.getAccountBalance()) == 0) {
                System.out.println("ROWNE STANY KONT");
                System.out.println("ACCOUNT ARE EQUAL");
            } else throw new IllegalArgumentException("DATA EXCEPTION");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA, "GIVEN DATA EXCEPTION: " + e.getMessage());
        }

    }

    //STATYSTYKI OPERACJI DLA POSZCZEGOLNYCH KONT
    public static void viewRosterOfOperations(List<CreditCard> cards) {
        try {
            if (cards == null || cards.size() == 0) {
                throw new NullPointerException("CARD FILE NULL OR EMPTY");
            }
            int counter = 0;
            for (CreditCard cc : cards) {
                System.out.println("KARTA NR: " + counter + " STAN KONTA - " + String.format("%2.2f", cc.getAccountBalance()) +
                        "\n" + " WPŁATA - " +
                        cc.getKindOfOperation()
                                .stream()
                                .filter(y -> y.split("_")[0].equals("WPLATA"))
                                .count() +
                        "\n" + " WYPŁATA - " +
                        cc.getKindOfOperation()
                                .stream()
                                .filter(y -> y.split("_")[0].equals("WYPLATA"))
                                .count() +
                        "\n" + " ANULOWANA - " +
                        cc.getKindOfOperation()
                                .stream()
                                .filter(y -> y.equals("ANULOWANA"))
                                .count() +
                        "\n" + " BLAD_LOGOWANIA - " +
                        cc.getKindOfOperation()
                                .stream()
                                .filter(y -> y.equals("BLAD_LOGOWANIA"))
                                .count() + "\n");
                counter++;
            }

        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "DATA EXCEPTION: " + e.getMessage());
        }
    }
}
