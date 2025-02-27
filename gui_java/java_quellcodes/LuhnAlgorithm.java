/*
  @author Mahdi Bagheri
 */

import java.math.BigInteger;

/*
LuhnAlgorithm Klasse, in der sich die Funktionen befinden,
für die Berechnung der Prüfziffer und zur Überprüfung der
Gültigkeit einer Zahl
 */
public class LuhnAlgorithm {

    
    //Methode, die eine Zahl entgegen nimmt und für diese eine Prüfziffer berechnet
    public Integer calculateCheckDigit(BigInteger number){
        String numAsString = number.toString();
        int numberLength = numAsString.length();
        int doublePosition = numberLength%2;
        String newNumAsString = "";
        int newNumAsStringLength = 0;
        int crossTotal = 0;
        int moduloValue;
        int returnValue = 0;

        /*
        Iteriert die Zahl von hinten nach vorne durch.
        Beginnend von der ersten Position wird jede zweite Zahl verdoppelt
        und ab einer zweistelligen Zahl, wird die Quersumme gebildet,
        welche die vorige Ziffer ersetzt
         */
        for(int i=numberLength-1; i>=0; i--){
            int currentIndexValue = 0;
            if((i+1)%2 == doublePosition){
                currentIndexValue = Integer.parseInt(String.valueOf(numAsString.charAt(i)));
                currentIndexValue *= 2;
                if(currentIndexValue >9){
                    currentIndexValue -= 9;
                }
                newNumAsString += Integer.toString(currentIndexValue);
            }else{
                newNumAsString += numAsString.charAt(i);
            }
        }

        newNumAsStringLength = newNumAsString.length();

        /*
        Die Quersumme, der neu entstandene Zahl wird gebildet und anschließend Modulo 10 verrechnet
        und im Anschluss wird die Prüfziffer ermittelt und von der Methode zurückgegeben
         */
        for(int i = 0; i< newNumAsStringLength; i++){
            crossTotal += Integer.parseInt(String.valueOf(newNumAsString.charAt(i)));
        }

        moduloValue = crossTotal % 10;
        
        if(moduloValue>0 && moduloValue<10){
            returnValue = 10 - moduloValue;
        }

        return returnValue;
    }

    //Methode, die eine Zahl entgegen nimmt und diese Zahl auf ihre Gültigkeit überprüft
    public Integer checkNumber(BigInteger number){
        String numAsString = number.toString();
        int numberLength = numAsString.length();
        int doublePosition = numberLength%2; //ermittelt, ob die Länge der Zahl gerade oder ungerade ist
        String newNumAsString = "";
        int newNumAsStringLength = 0;
        int crossTotal = 0;
        int moduloValue;
        Boolean returnValue = false;

        /*
        Iteriert die Zahl von hinten nach vorne durch.
        Beginnend von der zweiten Position wird jede zweite Zahl verdoppelt
        und ab einer zweistelligen Zahl, wird die Quersumme gebildet,
        welche die vorige Ziffer ersetzt
         */
        for(int i=numberLength-1; i>=0; i--){
            int currentIndexValue = 0;
            if((i+1)%2 !=doublePosition){
                currentIndexValue = Integer.parseInt(String.valueOf(numAsString.charAt(i)));
                currentIndexValue *= 2;
                if(currentIndexValue >9){
                    currentIndexValue -= 9;
                }
                newNumAsString += Integer.toString(currentIndexValue);
            }else{
                newNumAsString += numAsString.charAt(i);
            }
        }

        newNumAsStringLength = newNumAsString.length();

        /*
        Die Quersumme, der neu entstandene Zahl wird gebildet und anschließend Modulo 10 verrechnet.
        Kommt ein Rest von 0 raus, ist die Zahl gültig. Andernfalls nicht. Dies wird jedoch im LuhnWindow ermittelt,
        mit dem zurückgegebenen Wert
         */
        for(int i = 0; i< newNumAsStringLength; i++){
            crossTotal += Integer.parseInt(String.valueOf(newNumAsString.charAt(i)));
        }

        moduloValue = crossTotal % 10;

        return moduloValue;
    }



}
