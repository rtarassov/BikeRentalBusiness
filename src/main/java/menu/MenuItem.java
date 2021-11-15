package menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private String option;
    private String description;
    private CodeToRun codeToRun;
    @Override
    public String toString() {
        return option + ") " + description;
    }

}