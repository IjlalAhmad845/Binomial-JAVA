package sample;


import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {
@FXML
HBox Hbox; //declared Horizontal box
@FXML
    TextField textField1,textField2;
@FXML
    ComboBox comboBox,limit1,limit2;
@FXML
    AnchorPane anchorPane;
@FXML Label label;
int counter=0;//counter for calculating terms in binomial expression
    int n=1;
public void calc() {
    int specialcase=0;//for sign checking
    int cache1 = 0, cache2 = 0;//temp values for cutting substrings to appropriate length

    if (textField1.getText().isEmpty() || textField2.getText().isEmpty() )
    {textField1.setPromptText("Empty");textField2.setPromptText("0");}
    //checking if Fields is empty or not
    else {
        n =(int)Double.parseDouble(textField2.getText());//binomial power must be integer
        textField2.setText(String.valueOf(n));
        String string = textField1.getText();//binomial expression
        if(string.indexOf("-")==0)
            specialcase=1;//if expression starts with negative sign


        int coeffX = 0, coeffY = 0;
        String s1 , s2 , s3 ;//substrings from binomial expression
        try {//for + and - whatever it is, there is no exception


            for (int i = 0; i < string.indexOf("+"); i++)//checking expression before + for any coefficient of X
                for (int j = 0; j < 10; j++) {//checking every number for coefficient
                    if (string.substring(i, i + 1).equals(String.valueOf(j))) {
                        coeffX = Integer.parseInt(string.substring(i, i + 1)) + coeffX * 10;
                        cache1++;//now string must be cut by more length
                    }

                }

            for (int i = string.indexOf("+"); i < string.length(); i++)//checking for Y blah blah
                for (int j = 0; j < 10; j++) {
                    if (string.substring(i, i + 1).equals(String.valueOf(j))) {
                        coeffY = Integer.parseInt(string.substring(i, i + 1)) + coeffY * 10;
                        cache2++;
                    }
                }

            if (specialcase==1)cache1++;//if there is negative sign present then string must be cut one more step
            s1 = textField1.getText().substring(cache1, string.lastIndexOf("+"));// cutting expression to generate terms
            s2 = textField1.getText().substring(string.lastIndexOf("+"), string.lastIndexOf("+") + 1);
            s3 = textField1.getText().substring(string.lastIndexOf("+") + 1 + cache2, string.length());


        } catch (Exception e) {// blah blah blah
            for (int i = 0; i < string.indexOf("-"); i++)// blah blah blah
                for (int j = 0; j < 10; j++) {
                    if (string.substring(i, i + 1).equals(String.valueOf(j))) {
                        coeffX = Integer.parseInt(string.substring(i, i + 1)) + coeffX * 10;
                        cache1++;
                    }
                }

            for (int i = string.indexOf("-"); i < string.length(); i++)// blah blah blah
                for (int j = 0; j < 10; j++) {
                    if (string.substring(i, i + 1).equals(String.valueOf(j))) {
                        coeffY = Integer.parseInt(string.substring(i, i + 1)) + coeffY * 10;
                        cache2++;

                    }
                }

            if (specialcase==1)cache1++;//same thing

            s1 = textField1.getText().substring(cache1, string.lastIndexOf("-"));// blah blah blah
            s2 = textField1.getText().substring(string.lastIndexOf("-"), string.lastIndexOf("-") + 1);
            s3 = textField1.getText().substring(string.lastIndexOf("-") + 1 + cache2, string.length());

        }

        if(specialcase==0 && s2.equals("+"))specialcase=0;              //+ +
        else if(specialcase==0 && s2.equals("-"))specialcase=1;         //+ -
        else if(specialcase==1 && s2.equals("+"))specialcase=2;         //- +
        else if(specialcase==1 && s2.equals("-")) specialcase=3;        //- -


        if (coeffX == 0) coeffX++;//changing counter part of these variables to coefficient if there is no number
        if (coeffY == 0) coeffY++;

        Hbox.getChildren().clear();// clearing Horizontal box for further use
        counter = 1;//here is the game start

        int a = 0;//nCr coefficient

        for (int r = lim1(); r <= lim2(); r++) {
            // loop to generate every term of binomial expansion according to limits defined by functions below
            counter++;//counting terms
            a = fact(n) / (fact(n - r) * fact(r));//nCr

            //every single label for different things in single term
            Label coeff = new Label();coeff.setScaleX(1.2);coeff.setScaleY(1.2);
            Label X     = new Label();X.setScaleX(1.2);X.setScaleY(1.2);
            Label powX  = new Label();powX.setScaleX(.8);powX.setScaleY(.8);
            Label A     = new Label();A.setScaleX(1.2);A.setScaleY(1.2);
            Label powA  = new Label();powA.setScaleX(.8);powA.setScaleY(.8);
            Label sign  = new Label();sign.setScaleX(1.2);sign.setScaleY(1.2);

            Hbox.getChildren().addAll(sign,coeff, X, powX, A, powA);

            Hbox.setSpacing(2);//space for every part in the single term
            Hbox.setPrefSize(580, 90);

            if(specialcase==0)//+ + then every time there must be +
            counter=0;
         else if(specialcase==1);//+ - then alt plus minus
         else if(specialcase==2 && n%2==0);//- + then alt +,- same thing if power is even
         else if(specialcase==2 && n%2==1){counter++;specialcase=1;}//- + alt -,+ shifted one step if power is odd then shift back  to original
         else if(specialcase==3 && n%2==0)counter=0;//- - same case as 1st if power is even
         else if(specialcase==3 && n%2==1)counter=1;//- - every single time there must be minus cause power is odd



            if(counter%2==0){//putting signs in terms
                if(r!=lim1())//+ is not considered in very first term
                sign.setText("+");
            }
                // Alternate plus minus operations
            else if(counter%2==1)
                sign.setText("-");


            if (a != 1 || coeffX != 1) coeff.setText(String.valueOf(a * pow(coeffX, n - r) * pow(coeffY, r)));
            //checking for first term coefficient to eliminate 1

            if (n - r != 0) {//if power is zero then whole part is deleted either x or a
                X.setText(s1);
                if (n - r != 1)//if power is 1 then power is eliminated
                    powX.setText(String.valueOf(n - r));

            }

            if (r != 0) {//blah blah blah blah same thing
                A.setText(s3);
                if (r != 1)
                    powA.setText(String.valueOf(r));

            }



        }
        if(limit1.getItems().isEmpty()==false && limit2.getItems().isEmpty()==false)
            //≤ sign only appears when both fields must not be empty
        {if(lim1()>lim2()) {        //RED ≤ sign
            label.setText("≤");
            label.setTextFill(Color.RED);
        }
        else if(lim2()>=lim1()){//GREEN ≤ sign
            label.setText("≤");
            label.setTextFill(Color.GREEN);
        }
        }else label.setText("");//if fields is empty then sign is removed


    }
}

    public int fact(int f){//factorial program by recursion
        if(f==0)return 1;
        else if(f==1)return 1;
        else 
            return f*fact(f-1);//here u can see the recursion sign as well
    }
    //power program however there is inbuilt but that is giving float value and to convert that float into int is messy
    public int pow(int base,int power){
    int pow=1;
    for(int i=1;i<=power;i++)
        pow*=base;
    return pow;
    }

    public void setComboBox() {//selection of different modes
    if(comboBox.getSelectionModel().getSelectedItem()=="Full Expression"){
        limit1.setDisable(true);limit2.setDisable(true);//enabling disabling further combo-boxes
    }
    else if(comboBox.getSelectionModel().getSelectedItem()=="rth term"){
        limit1.setDisable(false);limit2.setDisable(true);//enabling disabling further combo-boxes
    }
    else if(comboBox.getSelectionModel().getSelectedItem()=="Range"){
        limit1.setDisable(false);limit2.setDisable(false);//enabling disabling further combo-boxes
    }
    }

    ArrayList<Integer> a=new ArrayList<Integer>(10);//Dynamic list for user input power that is filled in the limits section

public void arraylist(){//function to do that

    for(int i=1;i<=n+1;i++)
    {a.add(i);
   }
}

    public void setlimit1(){//setting value to combo box 1
        if(textField2.getText().isEmpty()==false){//checking is power field is empty or not
            n=Integer.parseInt(textField2.getText());
            arraylist();

            limit1.getItems().setAll(a);//filling power values
            a.clear();//clearing dynamic list
        }


    }
    public void setlimit2(){//same thing
        if(textField2.getText().isEmpty()==false){
            n=Integer.parseInt(textField2.getText());
            arraylist();

            limit2.getItems().setAll(a);//blah blah blah
        }a.clear();//blah blah blah


    }

public void setTextField2(){//both limits must be vanished for new input power by the user
    limit1.getItems().clear();
    limit2.getItems().clear();
    label.setText("");//and ≤ sign as well
}
        //here is the filtering happens
        public int lim1(){
            if(comboBox.getSelectionModel().getSelectedItem()=="Full Expression")
            return 0;//loop stars from zero generating whole binomial expression
            else if(comboBox.getSelectionModel().getSelectedItem()=="rth term")//mode switched
                if(limit1.getItems().isEmpty()==false){//but limit 1 must not be empty
             // now loop starts form this value -1 because binomial expression has one extra term so for eliminating that
            return Integer.parseInt(limit1.getSelectionModel().getSelectedItem().toString())-1;
                }//if empty then loop starts from that value that it never runs
                else return n+1;//checking of power field is done by upper function so no need to worry for that
            else if(comboBox.getSelectionModel().getSelectedItem()=="Range")//mode switched
                if(limit1.getItems().isEmpty()==false)//same thing
                return Integer.parseInt(limit1.getSelectionModel().getSelectedItem().toString())-1;//same thing
                else return n+1;//same thing
            return 0;
        }
        public int lim2(){//upper limit
            if(comboBox.getSelectionModel().getSelectedItem()=="Full Expression")
                return n;//returning max value to generate whole binomial expression
            else if(comboBox.getSelectionModel().getSelectedItem()=="rth term" && limit1.getItems().isEmpty()==false)//blah blah
                return Integer.parseInt(limit1.getSelectionModel().getSelectedItem().toString())-1;
            else if(comboBox.getSelectionModel().getSelectedItem()=="Range" && limit2.getItems().isEmpty()==false)
                return Integer.parseInt(limit2.getSelectionModel().getSelectedItem().toString())-1;//blah blah blah blah

            return n;
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll("Full Expression","rth term","Range");
        //program initializes first combo box at the beginning of the program
    }
}
