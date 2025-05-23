import java.util.InputMismatchException;
import java.util.Scanner;

public class InCheck {
    static int CheckInput(Scanner s, String msg){
        int n = 0;
        boolean valid;
        do{
            valid = true;
            System.out.println(msg);

            try{
                n = s.nextInt();
            }catch (InputMismatchException e){
                s.next();
                valid = false;
            }
        }while(!valid);
        return n;
    }
}
