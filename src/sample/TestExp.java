package sample;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.StringTokenizer;

public class TestExp {
    public static void main(String ...args)
    {
        StringTokenizer strt=new StringTokenizer("-3x+y-z=-56","+=-");
        String str="-3x+y-z=-56";
        System.out.println(strt.countTokens());
        String token1=strt.nextToken();
        int index=str.indexOf(token1);
        token1=str.substring(index-1,index)+token1;
        System.out.println(token1);
        System.out.println("----------------");
        System.out.println(Integer.parseInt(token1.substring(0,token1.indexOf("x"))));
        System.out.println("----------------");
        StringTokenizer strt2=new StringTokenizer("-3x+y-z=-56","+=-");
        while(strt2.hasMoreTokens())
        {
            System.out.println(strt2.nextToken());
        }
        System.out.println("----------------");
        Function f = new Function("f(x, y, z) = 2 + 3*y + z ");
        Expression e = new Expression("f(3,2,5)", f);
        double v = e.calculate();
       System.out.println(v);

    }
}
