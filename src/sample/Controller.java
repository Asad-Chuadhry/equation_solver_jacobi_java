package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.StringTokenizer;


 public class Controller {

     int noOfEquatins;
     MyMath equationNo[];
     String functions[];
     boolean eqDominentStatus[];
     boolean systemDominentStatus = true;
     String initialGuess = null;
     int noOfIterations;
     StringTokenizer strT;
     String varNames[]= {"x","y","z","w","u"};
     @FXML
     TextArea txtarea;
     @FXML
     VBox solution;
     @FXML
     Button solve;
     @FXML
     TextField txtInitialGuess;
     @FXML
     TextField txtNoOfIteration;
     @FXML
     Label lblerror;
     @FXML
     Button btnedit;
     HBox hboxEqDomStatus[];
     String equations = null;

     public void onDone() {
         try {
             lblerror.setVisible(false);
             if (!verification()) return;
             equations = txtarea.getText();
             strT = new StringTokenizer(equations);
             noOfEquatins = strT.countTokens();
             equationNo = new MyMath[noOfEquatins];
             eqDominentStatus = new boolean[noOfEquatins];
             functions = new String[noOfEquatins];
             initialGuess = txtInitialGuess.getText();
             noOfIterations = Integer.parseInt(txtNoOfIteration.getText());

             systemDominentCheck();
             if (systemDominentStatus == true) {
                 Label message = new Label("System is Diagnolly Dominent So...");
                 message.setId("msjlbl");
                 message.setTextFill(Color.GREEN);
                 solution.getChildren().add(message);
                 showFunction();
                 iterations();
             }

             //System.out.println(eq1.setVaraiableAndCoefficient("x"));
             // System.out.println(eq1.isDominent(1));

             //solution.getChildren().addAll(label);
             solution.setPrefWidth(700);
             solution.getStylesheets().add(getClass().getResource("myCSS.css").toExternalForm());
             solve.setDisable(true);
             txtarea.setDisable(true);
             txtInitialGuess.setDisable(true);
             txtNoOfIteration.setDisable(true);
             btnedit.setVisible(true);
         }
         catch (Exception e)
         {
             System.out.println(e);
             lblerror.setText("Syntax Error");
             lblerror.setVisible(true);
             solution.getChildren().clear();
         }
     }
     public void onEdit()
     {
         txtNoOfIteration.setDisable(false);
         txtarea.setDisable(false);
         txtInitialGuess.setDisable(false);
         solution.getChildren().clear();
         btnedit.setVisible(false);
         solve.setDisable(false);
         systemDominentStatus=true;
     }

     void systemDominentCheck() {
         hboxEqDomStatus = new HBox[noOfEquatins];
         Label lbleq = new Label("Equation");
         lbleq.setId("eqlbl");
         Label lblDomStatus = new Label("Dominent Status");
         lblDomStatus.setId("eqlbl");

         HBox hbox = new HBox(lbleq, lblDomStatus);
         hbox.setId("hboxlbl");
         hboxEqDomStatus = new HBox[noOfEquatins];
         solution.getChildren().add(hbox);

         for (int i = 0; i < noOfEquatins; i++) {

             equationNo[i] = new MyMath(strT.nextToken());
             func(i);//to make function of equation set varaible and coefficient
             eqDominentStatus[i] = equationNo[i].isDominent(i + 1);
             Label eq = new Label(equationNo[i].str);
             Label eqstatus = new Label(eqDominentStatus[i] + "");
             eq.setId("lbl");
             eqstatus.setId("lbl");
             if (eqDominentStatus[i] == false) {
                 eqstatus.setTextFill(Color.RED);
                 systemDominentStatus = false;
             } else{ eqstatus.setTextFill(Color.GREEN); }
             hboxEqDomStatus[i] = new HBox(eq, eqstatus);
             hboxEqDomStatus[i].setId("hboxeq");
             solution.getChildren().add(hboxEqDomStatus[i]);
         }
     }


     private void func(int i) {
         switch (i) {
             case 0:
                 functions[i] = equationNo[i].setVaraiableAndCoefficient("x");
                 break;
             case 1:
                 functions[i] = equationNo[i].setVaraiableAndCoefficient("y");
                 break;
             case 2:
                 functions[i] = equationNo[i].setVaraiableAndCoefficient("z");
                 break;
             case 3:
                 functions[i] = equationNo[i].setVaraiableAndCoefficient("w");
                 break;
             case 4:
                 functions[i] = equationNo[i].setVaraiableAndCoefficient("u");
                 break;
         }
     }

     private void showFunction() {
         for (int i = 0; i < noOfEquatins; i++) {
             Label lblfunc = null;
             switch (i) {
                 case 0:
                     lblfunc = new Label("x = " + functions[0]);
                     break;
                 case 1:
                     lblfunc = new Label("y = " + functions[1]);
                     break;
                 case 2:
                     lblfunc = new Label("z = " + functions[2]);
                     break;
                 case 3:
                     lblfunc = new Label("w = " + functions[3]);
                     break;
                 case 4:
                     lblfunc = new Label("u = " + functions[4]);
                     break;
             }
             lblfunc.setId("lblf");
             solution.getChildren().add(lblfunc);
         }
     }

     private void iterations() {
         String functionOf = funcOf(); //return f(x,y,z) or (x,y)
         double var[] = new double[noOfEquatins];

         //to get first time initial value for inserting in diplay function
         StringTokenizer strTT=new StringTokenizer(initialGuess.substring(2),",)");
         String preVar[]=new String[noOfEquatins];
         for (int i=0;i<noOfEquatins;i++)
         {
             preVar[i]=strTT.nextToken();
             System.out.println(preVar[i]);
         }


         for (int i = 0; i < noOfIterations; i++) {
             Label lbliteration = new Label("Iteration# " + (i + 1));
             lbliteration.setId("lblit");
             solution.getChildren().add(lbliteration);
             String round;
             String tempPreVar[]=new String[noOfEquatins];

             for (int j = 0; j < noOfEquatins; j++) {

                 Function myFunc = new Function(functionOf + "=" + functions[j]);
                 Expression e = new Expression(initialGuess, myFunc);
                 var[j] = e.calculate();

                 StringBuffer funcForDisplay;
                 funcForDisplay=new StringBuffer(functions[j]);
                 //for diplaying fuunction with their initial values
                 int temp;
                 for (int k=0;k<noOfEquatins;k++) {
                     if(j==k) continue;
                     temp=funcForDisplay.indexOf(varNames[k]);
                     funcForDisplay = funcForDisplay.replace(temp,temp+1, preVar[k]);

                 }System.out.println(funcForDisplay);
                 Label lblvar = null;
                 round = String.format("%.7g%n", var[j]);
                 var[j] = Double.parseDouble(round);
                 if (j == 0) lblvar = new Label("x = " +funcForDisplay+" = "+ var[0]);
                 else if (j == 1) lblvar = new Label("y ="+funcForDisplay+" = " + var[1]);
                 else if (j == 2) lblvar = new Label("z ="+funcForDisplay+" = " + var[2]);
                 else if (j == 3) lblvar = new Label("w ="+funcForDisplay+" = " + var[3]);
                 else if (j == 4) lblvar = new Label("y ="+funcForDisplay+" = " + var[4]);
                 lblvar.setId("lblvar");
                 solution.getChildren().add(lblvar);
                 tempPreVar[j]=var[j]+"";
             }
             //for update previous value for var
             for(int z=0;z<noOfEquatins;z++)
             {
                 preVar[z]=tempPreVar[z]+"";
             }
             updateInitialValue(var, functionOf);
         }
     }

     private void updateInitialValue(double var[], String functionOf) {

         switch (noOfEquatins) {
             case 2:
                 functionOf = functionOf.replace("x", var[0] + "");
                 functionOf = functionOf.replace("y", var[1] + "");
                 initialGuess = functionOf;
                 break;
             case 3:
                 functionOf = functionOf.replace("x", var[0] + "");
                 functionOf = functionOf.replace("y", var[1] + "");
                 functionOf = functionOf.replace("z", var[2] + "");
                 initialGuess = functionOf;
                 break;
             case 4:
                 functionOf = functionOf.replace("x", var[0] + "");
                 functionOf = functionOf.replace("y", var[1] + "");
                 functionOf = functionOf.replace("z", var[2] + "");
                 functionOf = functionOf.replace("w", var[3] + "");
                 initialGuess = functionOf;
                 break;
             case 5:
                 functionOf = functionOf.replace("x", var[0] + "");
                 functionOf = functionOf.replace("y", var[1] + "");
                 functionOf = functionOf.replace("z", var[2] + "");
                 functionOf = functionOf.replace("w", var[3] + "");
                 functionOf = functionOf.replace("u", var[4] + "");
                 initialGuess = functionOf;
                 break;
         }

     }

     private String funcOf() {
         switch (noOfEquatins) {
             case 2:
                 return "f(x,y)";
             case 3:
                 return "f(x,y,z)";
             case 4:
                 return "f(x,y,z,w)";
             case 5:
                 return "f(x,y,z,w,u)";
             default:
                 return null;
         }
     }

     boolean verification() {
         if ((txtarea.getText()).isEmpty()) {
             lblerror.setText("Please Entre a System of Linear Equations");
             lblerror.setVisible(true);
             return false;
         } else if (txtInitialGuess.getText().isEmpty()) {
             lblerror.setText("Enter a Initial Guess");
             lblerror.setVisible(true); return false;
         } else if (txtNoOfIteration.getText().isEmpty()) {
             lblerror.setText("Please Entre no of Iterations");
             lblerror.setVisible(true);
             return false;
         }
          else return true;
     }
 }
