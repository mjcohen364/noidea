package view;

import interface_adapter.character_creator.CharacterCreatorController;
import interface_adapter.dnd_class.ClassState;
import interface_adapter.dnd_class.ClassViewModel;
import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChooseClassView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Choose Class";
    private final ClassViewModel classViewModel;
    private final CharacterCreatorController characterCreatorController;
    private boolean classChoicesAdded = false;
    final JButton mainScreen;
    private JLabel items;

    public ChooseClassView(CharacterCreatorController characterCreatorController, ClassViewModel classViewModel) {
        this.characterCreatorController = characterCreatorController;
        this.classViewModel = classViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        classViewModel.addPropertyChangeListener(this);
        JLabel title = new JLabel("Choose Class");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

        items = new JLabel();
        items.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel inventoryTitle = new JLabel(InventoryViewModel.TITLE_LABEL);
        inventoryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(inventoryTitle);
        this.add(items);

        JPanel buttons = new JPanel();
        mainScreen = new JButton(classViewModel.MAIN_SCREEN_LABEL);
        buttons.add(mainScreen);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(buttons);

        mainScreen.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(mainScreen)) {
                            ClassState currentState = classViewModel.getState();
                            characterCreatorController.execute();
                        }
                    }
                }
        );
    }
    //    TODO FINISH ACTIONPERFORMED
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "[NOT SURE WHAT TO PUT HERE YET]");
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof InventoryState) {
            InventoryState state = (InventoryState) evt.getNewValue();

            items.setText("");
            for (int i = 0; i < state.items.size(); i++) {
                items.setText(items.getText() + "    " + state.items.get(i));
            }
            revalidate();
            repaint();

        }
        if (evt.getNewValue() instanceof ClassState) {
            ClassState state = (ClassState) evt.getNewValue();

            JPanel buttons2 = new JPanel();
            for (String className: state.classes) {
                JButton classAdd = new JButton(className);
                buttons2.add(classAdd);
            }
            buttons2.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (!this.classChoicesAdded){
                this.add(buttons2, 1);
            }
            this.classChoicesAdded = true;
            revalidate();
            repaint();
        }
    }
}