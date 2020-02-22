package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MyMath {
    String function=null;
    int numberOfVaraible;
    String varaible[];
    float coefficient[];
    String str;
    MyMath(String equation)
    {
        str=equation;
    }
    public static void main(String ...args)
    {
        MyMath jacobi=new MyMath("-3x+2y-3z=2");
       System.out.println(jacobi.setVaraiableAndCoefficient("y"));
       // System.out.println(jacobi.setVaraiableAndCoefficient("z"));


        // Expression= 2x+y-z+345w=6 and FunctionOf=x
    }

    public String setVaraiableAndCoefficient(String functionOf)
    {


        // first sperate the left handside of equation
        String strEnd= str.substring(str.indexOf("=")+1);
        str=str.substring(0,str.indexOf("="));
        function="("+strEnd;
        //add + operator if no operator at the start of equation
        //+2x+y-z......
        if(str.charAt(0)!='-') str="+"+str;

        //Seperate varable with their coefficient and store in a varaible array
        //add multiplication sign if there is no sign between coefficient and varaible
        //+2*x
        //+345*w...
        StringTokenizer strT=new StringTokenizer(str,"+-");
        numberOfVaraible=strT.countTokens();
        varaible=new String[numberOfVaraible];
        coefficient=new float[numberOfVaraible];
        Arrays.fill(coefficient,1);
        for(int i=0;i<numberOfVaraible;i++)
        {
            varaible[i]=strT.nextToken();
            varaible[i]=str.charAt(str.indexOf(varaible[i])-1)+varaible[i];
            //add multiplication sign
            //get coefficient
            int varLenght=varaible[i].length();
            if(varLenght>2)
            {
                coefficient[i]=Float.parseFloat(varaible[i].substring(0,varaible[i].length()-1));
               char var= varaible[i].charAt(varLenght-1);
               varaible[i]=varaible[i].substring(0,varaible[i].indexOf(var))+"*"+var;
            }
        }
        switch (functionOf) {
            case "x": return getFunction("x",coefficient[0]);
            case "y": return getFunction("y",coefficient[1]);
            case "z": return getFunction("z",coefficient[2]);
            case "w": return getFunction("w",coefficient[3]);
            case "u": return getFunction("u",coefficient[4]);
            default: return  "no varaible of this name";
        }
    }

    private String getFunction(String functionOf, float coefficient)
    {
        for (int i = 0; i < numberOfVaraible; i++) {
            if (varaible[i].contains(functionOf)) {
                continue;
            }
            if (varaible[i].contains("+"))
                varaible[i]=varaible[i].replace("+", "-");
            else varaible[i]=varaible[i].replace("-", "+");
            function = function + varaible[i];
        }
        return function + ")/" + coefficient;
    }

    public boolean isDominent(int EquationNo)
    {
        float coefficient[]=new float[numberOfVaraible];

        //take mode of coefficients
        for(int i=0;i<numberOfVaraible;i++)
        {
            if(this.coefficient[i]<0)
            {
                coefficient[i]=this.coefficient[i]*-1;
            }
            else coefficient[i]=this.coefficient[i];
        }
        //
        if(numberOfVaraible==2 && EquationNo==1)
        {
            if(coefficient[0]>coefficient[1]) return true;
        }
        if(numberOfVaraible==2 && EquationNo==2)
        {
            if(coefficient[1]>coefficient[0]) return true;
        }

        if(numberOfVaraible==3 && EquationNo==1)
        {
            if(coefficient[0]>(coefficient[1]+coefficient[2])) return true;
        }
        if(numberOfVaraible==3 && EquationNo==2)
        {
            if(coefficient[1]>(coefficient[0]+coefficient[2])) return true;
        }
        if(numberOfVaraible==3 && EquationNo==3)
        {
            if(coefficient[2]>(coefficient[1]+coefficient[0])) return true;
        }

        if(numberOfVaraible==4 && EquationNo==1)
        {
            if(coefficient[0]>(coefficient[1]+coefficient[2]+coefficient[3])) return true;
        }
        if(numberOfVaraible==4 && EquationNo==2)
        {
            if(coefficient[1]>(coefficient[2]+coefficient[3]+coefficient[0])) return true;
        }
        if(numberOfVaraible==4 && EquationNo==3)
        {
            if(coefficient[2]>(coefficient[1]+coefficient[0]+coefficient[3])) return true;
        }
        if(numberOfVaraible==4 && EquationNo==4)
        {
            if(coefficient[3]>(coefficient[1]+coefficient[2]+coefficient[0])) return true;
        }

        if(numberOfVaraible==5 && EquationNo==1)
        {
            if(coefficient[0]>(coefficient[1]+coefficient[2]+coefficient[3]+coefficient[4])) return true;
        }
        if(numberOfVaraible==5 && EquationNo==2)
        {
            if(coefficient[1]>(coefficient[0]+coefficient[2]+coefficient[3]+coefficient[4])) return true;
        }
        if(numberOfVaraible==5 && EquationNo==3)
        {
            if(coefficient[2]>(coefficient[1]+coefficient[0]+coefficient[3]+coefficient[4])) return true;
        }
        if(numberOfVaraible==5 && EquationNo==4)
        {
            if(coefficient[3]>(coefficient[1]+coefficient[2]+coefficient[0]+coefficient[4])) return true;
        }
        if(numberOfVaraible==5 && EquationNo==5)
        {
            if(coefficient[4]>(coefficient[1]+coefficient[2]+coefficient[3]+coefficient[0])) return true;
        }

        return false;
    }
    public int makeDominent()
    {
        if(this.isDominent(1))
            return 1;
        else if (this.isDominent(2))
            return 2;
        else if (this.isDominent(3))
            return 3;
        else if(this.isDominent(4))
            return 4;
        else if(this.isDominent(5))
            return 5;
        return 0;
    }
}
