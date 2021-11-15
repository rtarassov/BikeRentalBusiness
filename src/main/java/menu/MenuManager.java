package menu;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Getter
public class MenuManager {

    private List<Menu> menus;

    private Menu currentMenu;

    private Stack<Menu> menuHistory;

    public MenuManager() {
        menus = new ArrayList<>();
        menuHistory = new Stack<>();
    }

    public void addMenu(Menu menu, Menu previousMenu) {
        if (menus.stream().anyMatch(m -> m.getHeading().equals(menu.getHeading()))) {
            throw new IllegalArgumentException("A menu with this heading already exists.");
        }
        if (menu.getMenuItems() == null || menu.getMenuItems().isEmpty()) {
            throw new IllegalArgumentException("Menu has no MenuItems");
        }
        if (previousMenu != null) {
            menu.getMenuItems().add(new MenuItem("b", "Back", previousMenu::getHeading));
        }
        this.menus.add(menu);
    }

    public Menu getMenuByHeading(String heading) {
        for (Menu menu : menus) {
            if (menu.getHeading().equals(heading)) {
                return menu;
            }
        }
        throw new IllegalArgumentException("No such menu with heading: " + heading);
    }

    public void setCurrentMenu(Menu currentMenu) {
        menuHistory.push(this.currentMenu);
        this.currentMenu = currentMenu;
    }

    public Menu goToPreviousMenu() {
        if (menuHistory.size() != 0) {
            this.currentMenu = menuHistory.pop();
            return this.currentMenu;
        }
        return null;
    }

    public Menu getPreviousMenu() {
        return menuHistory.peek();
    }

    public void addItemToMenu(Menu menu, MenuItem menuItem) {
        if (menu.getMenuItems() == null) {
            menu.setMenuItems(new ArrayList<>());
        }
        menu.getMenuItems().add(menuItem);
    }
}
