package menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private String heading;
    private List<MenuItem> menuItems;

    public Menu(String heading) {
        this.heading = heading;
    }

    public void printMenu() {
        System.out.println(heading);
        menuItems.forEach(System.out::println);
        System.out.print("Type your option: ");
    }
}

