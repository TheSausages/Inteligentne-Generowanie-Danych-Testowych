package InsertCreation;

public class Data {
    private String name;
    public void setNamee(String name) {
        this.name = name;
    }
    public String getNamee() {
        return name;
    }

    private String surname;
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getSurname() {
        return surname;
    }

    private String car;
    public void setCar(String car) {
        this.car = car;
    }
    public String getCar() {
        return car;
    }

    private Integer number;
    public void setNumber(Integer number) {
        this.number = number;
    }
    public Integer getNumber() {
        return number;
    }

    String wypisz() {
        String str = "INSERT INTO table_name (name, surname, car, number) VALUES ('"+name+"','"+surname+"','"+car+"','"+number+"');";
        return str;
    }
}
