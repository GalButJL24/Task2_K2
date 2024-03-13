import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        Fractionable numProxy = Utility.Cache(new Fraction(2,3));
        numProxy.doubleValue(); // sout сработал
        numProxy.doubleValue(); // молчит
        numProxy.doubleValue(); // молчит
        numProxy.setNum(5);
        numProxy.doubleValue(); // сработал
        numProxy.doubleValue(); // молчит


        numProxy.setNum(7);
        numProxy.multiplyValue(); // sout сработал
        numProxy.multiplyValue(); // молчит
        numProxy.multiplyValue(); // молчит
        numProxy.setNum(3);
        numProxy.multiplyValue(); // сработал
        numProxy.multiplyValue(); // молчит


    }
}
