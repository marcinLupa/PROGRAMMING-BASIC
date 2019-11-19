package exceptions;

public enum ExceptionCode {

    //Fajny przykład jak można nadać ENUMOWI wartości
    DB("WYJATEK Z BAZY DANYCH"),
    DATA("WYJATEK DOTYCZACY WPROWADZANYCH OD UŻYTKOWNIKA DANYCH"),
    JSON("WYJATEK ZWIAZANY Z ZAPISEM LUB ODCZYTEM OBIEKTOW JSON"),
    ELSE("INNY WYJATEK"),
    VALIDATION("WYJATEK ZWIAZANY Z WALIDACJA"),
    SERVICE("WYJATEK WARSTWY SERWISOWEJ"),
    BUILDER("BUILDER");
    private String description;

    ExceptionCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

