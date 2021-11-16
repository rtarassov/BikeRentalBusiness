package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String name;
    private Date dateOfBirth;
    private String email;
    private int id;
    private List<Bike> rentals;

    public Client(String name, Date dateOfBirth, String email, int id) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.id = id;
    }
}
