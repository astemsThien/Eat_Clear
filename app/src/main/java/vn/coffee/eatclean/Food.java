package vn.coffee.eatclean;

public class Food {
    int  imageid;
    String Ten;
    String Calo;
    String Carbs;
    String Fat;
    String Pro;
    String Name;
    String Gram;
    String Name1;
    String Gram1;
    String Name2;
    String Gram2;

    public Food(int imageid, String ten, String calo,
                String carbs, String fat, String pro,
                String name, String gram,
                String name1, String gram1,
                String name2, String gram2) {
        this.imageid = imageid;
        Ten = ten;
        Calo = calo;
        Carbs = carbs;
        Fat = fat;
        Pro = pro;
        Name = name;
        Gram = gram;
        Name1 = name1;
        Gram1 = gram1;
        Name2 = name2;
        Gram2 = gram2;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getCalo() {
        return Calo;
    }

    public void setCalo(String calo) {
        Calo = calo;
    }

    public String getCarbs() {
        return Carbs;
    }

    public void setCarbs(String carbs) {
        Carbs = carbs;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getPro() {
        return Pro;
    }

    public void setPro(String pro) {
        Pro = pro;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGram() {
        return Gram;
    }

    public void setGram(String gram) {
        Gram = gram;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getGram1() {
        return Gram1;
    }

    public void setGram1(String gram1) {
        Gram1 = gram1;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    public String getGram2() {
        return Gram2;
    }

    public void setGram2(String gram2) {
        Gram2 = gram2;
    }
}
