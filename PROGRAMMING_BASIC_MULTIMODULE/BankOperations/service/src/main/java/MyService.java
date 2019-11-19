import com.google.gson.JsonParseException;


import java.math.BigDecimal;
import java.util.Scanner;

public class MyService {

    public void cardOperationManager(String filename) {

        final ListOfCreditCards cards = new JsonListOfCreditCardsConverter(filename)
                .fromJson()
                .orElseThrow(() -> new JsonParseException("JSON PARSE EXCEPTION"));

        boolean continuation = true;
        try {
            while (continuation) {
                try {
                    System.out.println("\n"+"DLA KTOREGO KONTA CHCESZ WYKONAC OPERACJE? " + "\n"+
                            "JEZELI NIE CHCESZ WYBIERAC KONTA NACISNIJ ENTER"+ "\n"+ "\n"+
                            "OBECNA LISTA KONT MA DLUGOSC " + cards.getCards().size() + "\n"+
                            "PODAJ ID KONTA KTÓRE WYBIERASZ:");

                    String cardFromUser = new Scanner(System.in).nextLine();
                    if (cardFromUser.equals("")) {
                        continuation=false;
                        break;

                    }
                    if (!cardFromUser.matches("[0-9]*")) {
                        throw new NumberFormatException("FORMAT OR RANGE OF NUMBER EXCEPTION");
                    }
                    int intCardFromUser = Integer.valueOf(cardFromUser)-1;
                    if (intCardFromUser < 0 || intCardFromUser > cards.getCards().size()) {
                        throw new NumberFormatException("CARD NUMBER OUT OF BASE");
                    }
                    CreditCard curentCard = cards.getCards().get(intCardFromUser);

                    System.out.println("-----------------------------------");
                    System.out.println("JAKA OPERACJE CHCESZ WYKONAC? WYBIERZ ODPOWIEDNI NUMER");
                    System.out.println("POKAZ HISTORIE OPERACJI - 0"
                            + "\n" + "WPLATA - 1 "
                            + "\n" + "WYPLATA - 2 "
                            + "\n" + "WYCZYSC HISTORIE OPERACJI - 3 "
                            + "\n" + "SPRAWDZ KONTO KTÓRE MIAŁO NAJMNIEJ BLEDNYCH LOGOWAN I PRZYDZIEL BONUS - 4 "
                            + "\n" + "POROWNAJ DWA KONTA Z LISTY POD WZGLEDEM STANU KONTA - 5"
                            + "\n" + "SPRAWDZ STATYSTYKI TRANSAKCJI DLA KONT Z LISTY - 6"
                            + "\n" + "SPRAWDZ STAN KONTA Z HISTORII - 7"
                            + "\n" + "SPRAWDZ ILE BYŁO ANULOWAN W HISTORII OPERACJI- 8"
                            + "\n" + "WYJSCIE- 9");

                    String optionFromUser = new Scanner(System.in).nextLine();
                    if (!optionFromUser.matches("[0-9]*")) {
                        throw new NumberFormatException("FORMAT OR RANGE OF NUMBER EXCEPTION");
                    }
                    int intOptions = Integer.valueOf(optionFromUser);
                    if (intOptions < 0 || intOptions > 9) {
                        throw new NumberFormatException("OPTION OUT OF RANGE");
                    }
                    switch (intOptions) {
                        case 0:
                            System.out.println("HISTORIA OPERACJI DLA KARTY ID: "+(intCardFromUser+1));
                            curentCard.getKindOfOperation().forEach(System.out::println);
                            break;
                        case 1:
                            System.out.println("JAKA KWOTE CHCESZ WPLACIC?");
                            String cashPayment = new Scanner(System.in).nextLine();
                            if (!cashPayment.matches("-?(?:\\d+(?:\\.\\d+)?|\\.\\d+)")) {
                                throw new IllegalArgumentException("WRONG FORMAT OF BIG DECIMAL");
                            } else {
                                curentCard.payment(new BigDecimal(cashPayment));
                            }
                            break;
                        case 2:
                            System.out.println("JAKA KWOTE CHCESZ WYPLACIC?");
                            String cashWithdrawl = new Scanner(System.in).nextLine();
                            if (!cashWithdrawl.matches("-?(?:\\d+(?:\\.\\d+)?|\\.\\d+)")) {
                                throw new IllegalArgumentException("WRONG FORMAT OF BIG DECIMAL");
                            } else {
                                curentCard.withdrawal(new BigDecimal(cashWithdrawl));
                            }
                            break;
                        case 3:
                            curentCard.clearHistoryOfOperations();
                            break;
                        case 4:
                            CreditCard.leastErrors(cards.getCards());
                            break;
                        case 5:
                            System.out.println("PODAJ KTORE KONTA Z LISTY MAJA ZOSTAC POROWNANE, LISTA LICZY: "
                                    + cards.getCards().size() + " REKORDÓW");
                            System.out.println("PODAJ ID PIERWSZEGO KONTA");
                            String acount1 = new Scanner(System.in).nextLine();
                            if (!acount1.matches("[0-9]*")) {
                                throw new NumberFormatException("FORMAT OF NUMBER EXCEPTION");
                            }
                            int intAcount1 = Integer.valueOf(acount1);
                            if (intAcount1 > cards.getCards().size() || intAcount1 < 1) {
                                throw new NumberFormatException(" RANGE OF NUMBER EXCEPTION");
                            }
                            System.out.println("PODAJ ID DRUGIEGO KONTA");
                            String acount2 = new Scanner(System.in).nextLine();
                            if (!acount2.matches("[0-9]*")) {
                                throw new NumberFormatException("FORMAT OF NUMBER EXCEPTION");
                            }
                            int intAcount2 = Integer.valueOf(acount2);
                            if (intAcount2 > cards.getCards().size() || intAcount2 < 1) {
                                throw new NumberFormatException(" RANGE OF NUMBER EXCEPTION");
                            }

                            CreditCard.comprasionOfAccount(
                                    cards.getCards().get(intAcount1 - 1),
                                    cards.getCards().get(intAcount2 - 1));
                            break;
                        case 6:
                            CreditCard.viewRosterOfOperations(cards.getCards());
                            break;
                        case 7:
                            System.out.println("ILE OPRACJI DO TYLU CHCESZ SPRAWDZIC?");
                            String howManyOperations = new Scanner(System.in).nextLine();
                            if (!howManyOperations.matches("[0-9]*")) {
                                throw new NumberFormatException("FORMAT OF NUMBER EXCEPTION");
                            }
                            int intHowManyOperations = Integer.valueOf(howManyOperations);
                            if (intHowManyOperations > curentCard.getKindOfOperation().size() || intHowManyOperations < 1) {
                                throw new NumberFormatException(" RANGE OF NUMBER EXCEPTION");
                            }
                            curentCard.checkAccountBallanceInHistory(intHowManyOperations);
                            break;
                        case 8:
                            System.out.println("DLA DANEGO KONTA ILOSC ANULOWAN TO: " + curentCard.howManyAnullation());
                            break;
                        case 9:
                            continuation = false;
                            break;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.DATA, "DATA FROM USER EXCEPTION: " + e.getMessage());
        }
    }
}
