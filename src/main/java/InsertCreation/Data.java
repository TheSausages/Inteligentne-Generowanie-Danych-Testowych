package InsertCreation;

public class Data {
    String name;
    String surname;
    String car;
    Integer number;
    String wypisz() {
        String str = "INSERT INTO table_name (name, surname, car, number) VALUES ('"+name+"','"+surname+"','"+car+"','"+number+"');";
        return str;
    }
}
