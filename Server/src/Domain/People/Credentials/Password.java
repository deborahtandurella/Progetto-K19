// CRIS
package Domain.People.Credentials;

// Classe responsabile della creazione, controllo conformita e stampa password
public class Password {

    private CharAnalizer password;

    public Password(String password) {
        this.password = new CharAnalizer(password);
        checkValidity();
    }

    // CheckValidity() controlla che la password sia di dimensioni da 8 a 16 caratteri,
    // contenga almeno un numero, una maiuscola ed un carattere speciale.
    // Ritorna infine la password o stampa l'esito la causa del rifiuto.
    private CharAnalizer checkValidity() {
        if(password.getLine().length() >= 8) {
            if (password.getLine().length() <= 16) {
                if (password.isUpperCase()) {
                    if (password.isDigit()) {
                        if (password.isSpecialChar()) {
                            System.out.println("PASSWORD SUCCESSFULLY CREATED");
                            return password;
                        } else {
                            System.out.println("PASSWORD REJECTED: SPECIAL CHARACTER REQUIRED");
                            return password = null;
                        }
                    } else {
                        System.out.println("PASSWORD REJECTED: NUMBER REQUIRED");
                        return password = null;
                    }
                } else {
                    System.out.println("PASSWORD REJECTED: UPPER CASE CHARACTER REQUIRED");
                    return password = null;
                }
            } else {
                System.out.println("PASSWORD REJECTED: MAXIMUM DIMENSION ALLOWED 16 CHARACTERS");
                return password = null;
            }
        } else {
            System.out.println("PASSWORD REJECTED: MINIMUM DIMESION ALLOWED 8 CHARACTERS");
            return password = null;
        }
    }

    // getPassword() stampa l'oggetto password come una stringa
    public String getPassword() {
        return password.getLine();
    }
}

