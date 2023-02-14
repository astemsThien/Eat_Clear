package vn.coffee.eatclean.addFoodList;

import java.util.HashMap;
import java.util.Map;

public class AddFood {
    int  calo;
    public AddFood(String ten,int calo){
        this.calo=calo;
    }

    public int getCalo(){
        return calo;
    }
    public void setCalo(int calo){
        this.calo = calo;
    }

}
