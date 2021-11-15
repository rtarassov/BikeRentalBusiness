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
}
