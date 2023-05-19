import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    static int[] typeOfMethod = {0,0};//1 число какой знак(0-4), второе какая система(0-1)
    private static void ExpressionTypeCheck(String a) {
        int count = 0;
        Pattern[] pattern = {
                Pattern.compile("[A-Z]*\\+[A-Z]*"), //1
                Pattern.compile("[0-9]*\\+[0-9]*"), //2

                Pattern.compile("[A-Z]*\\-[A-Z]*"), //4
                Pattern.compile("[0-9]*\\-[0-9]*"), //8

                Pattern.compile("[A-Z]*\\*[A-Z]*"), //16
                Pattern.compile("[0-9]*\\*[0-9]*"), //32

                Pattern.compile("[A-Z]*\\/[A-Z]*"), //64
                Pattern.compile("[0-9]*\\/[0-9]*")  //128
        };
        for(int i = 0; i < 8; i++){
            Matcher matcher = pattern[i].matcher(a);
            boolean found = matcher.matches();
            if(found){
                count+= Math.pow(2, i);
                //System.out.println(count);
            }
        }
        switch(count){
            case 1:
                typeOfMethod[0] = 0;
                typeOfMethod[1] = 1;
                break;
            case 2:
                typeOfMethod[0] = 0;
                typeOfMethod[1] = 0;
                break;
            case 4:
                typeOfMethod[0] = 1;
                typeOfMethod[1] = 1;
                break;
            case 8:
                typeOfMethod[0] = 1;
                typeOfMethod[1] = 0;
                break;
            case 16:
                typeOfMethod[0] = 2;
                typeOfMethod[1] = 1;
                break;
            case 32:
                typeOfMethod[0] = 2;
                typeOfMethod[1] = 0;
                break;
            case 64:
                typeOfMethod[0] = 3;
                typeOfMethod[1] = 1;
                break;
            case 128:
                typeOfMethod[0] = 3;
                typeOfMethod[1] = 0;
                break;
            default:
                try{
                    int y = 0/0;
                }
                catch (ArithmeticException qwe){
                    System.out.println("Not definded");
                    System.exit(1);
                }
        }


    }
    private static int decodeSingle(char letter) {
        switch(letter) {
            case 'M': return 1000;
            case 'D': return 500;
            case 'C': return 100;
            case 'L': return 50;
            case 'X': return 10;
            case 'V': return 5;
            case 'I': return 1;
            default: return 0;
        }
    }
    private static int ConventToInt(String a){
        int result = 0;
        String uRoman = a.toUpperCase();
        for(int i = 0;i < uRoman.length() - 1;i++) {
            if (decodeSingle(uRoman.charAt(i)) < decodeSingle(uRoman.charAt(i+1))) {
                result -= decodeSingle(uRoman.charAt(i));
            } else {
                result += decodeSingle(uRoman.charAt(i));
            }
        }
        result += decodeSingle(uRoman.charAt(uRoman.length()-1));
        return result;
    }
    public static String Input(){
        Scanner in = new Scanner(System.in);
        System.out.print("Input a expression: ");
        String In = in.next();
        return In;
    }
    private static int MainLogic(String input){
        int[] NumSys = {0,0};
        String[] numbers = input.split("[+-/*]");
        ExpressionTypeCheck(input);
        if(typeOfMethod[1] == 1){
            NumSys[0] = ConventToInt(numbers[0]);
            NumSys[1] = ConventToInt(numbers[1]);
        }
        else {
            for (int i = 0; i < 2; i++) {
                NumSys[i] = Integer.parseInt(numbers[i].trim());
            }
        }
        if(typeOfMethod[0] == 0){
            return NumSys[0] + NumSys[1];
        }
        else if (typeOfMethod[0] == 1) {
            if(((typeOfMethod[1] == 1) & ((NumSys[0] - NumSys[1]) >= 0)) | (typeOfMethod[1] == 0)){
                return NumSys[0] - NumSys[1];
            } else {
                try{
                    int y = 0/0;
                }
                catch (ArithmeticException qwe){
                    System.out.println("Римская система не имеет отрицательных чисел");
                    System.exit(1);
                }
            }
        }
        else if (typeOfMethod[0] == 2) {
            return NumSys[0] * NumSys[1];
        }
        else if (typeOfMethod[0] == 3) {
            return NumSys[0] / NumSys[1];
        }
        return 0;
    }
    enum Numeral {
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);
        int weight;

        Numeral(int weight) {
            this.weight = weight;
        }
    };
    private static void Output(int in){
        if(typeOfMethod[0]==0){
            StringBuilder buf = new StringBuilder();

            final Numeral[] values = Numeral.values();
            for (int i = values.length - 1; i >= 0; i--) {
                while (in >= values[i].weight) {
                    buf.append(values[i]);
                    in -= values[i].weight;
                }
            }
            System.out.println(buf.toString());
        } else {
            System.out.println(in);
        }
    }
    public static void main(String[] args) {
        String in = Input();
        Output(MainLogic(in));
    }
}
