package view;

import interface_adapter.dnd_class.ClassController;
import interface_adapter.dnd_class.ClassState;
import interface_adapter.dnd_class.ClassViewModel;
import interface_adapter.inventory.InventoryController;
import interface_adapter.inventory.InventoryState;
import interface_adapter.inventory.InventoryViewModel;
import interface_adapter.race.RaceController;
import interface_adapter.race.RaceState;
import interface_adapter.race.RaceViewModel;
import interface_adapter.background.BackgroundController;
import interface_adapter.background.BackgroundState;
import interface_adapter.background.BackgroundViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CharacterCreatorView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "character creator";

    private final InventoryViewModel inventoryViewModel;
    private final InventoryController inventoryController;


    private final ClassViewModel classViewModel;
    private final ClassController classController;
    private final RaceViewModel raceViewModel;
    private final RaceController raceController;
    private final BackgroundViewModel backgroundViewmodel;
    private final BackgroundController backgroundController;

    private JLabel items;
//    private final JButton chooseBackground;

    public CharacterCreatorView(InventoryController controller,
                                InventoryViewModel inventoryViewModel,
                                ClassController classController,
                                ClassViewModel classViewModel,
                                RaceController raceController, RaceViewModel raceViewModel,
                                BackgroundController backgroundController, BackgroundViewModel backgroundViewModel) {

        this.inventoryController = controller;
        this.inventoryViewModel = inventoryViewModel;
        this.classController = classController;
        this.classViewModel = classViewModel;
        this.raceController = raceController;
        this.raceViewModel = raceViewModel;
        this.backgroundController = backgroundController;
        this.backgroundViewmodel = backgroundViewModel;
        inventoryViewModel.addPropertyChangeListener(this);
        classViewModel.addPropertyChangeListener(this);
        raceViewModel.addPropertyChangeListener(this);
        backgroundViewmodel.addPropertyChangeListener(this);


        items = new JLabel();
        items.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel(InventoryViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //create buttons for choosing class
        classController.execute();
        raceController.execute();

        JPanel buttons = new JPanel();
        JButton chooseBackground = new JButton("Background");
        buttons.add(chooseBackground);

        chooseBackground.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        backgroundController.execute();
                    }
                }
        );
        JButton chooseClass = new JButton("Class");
        buttons.add(chooseClass);

        chooseClass.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {classController.execute();}
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(buttons);
        this.add(title);
        this.add(items);

    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "Cancel not implemented yet.");
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
//        if (evt.getNewValue() instanceof ClassState) {
//            ClassState state = (ClassState) evt.getNewValue();
//
//            JPanel buttons = new JPanel();
//            for (String className: state.classes) {
//                JButton classAdd = new JButton(className);
//                buttons.add(classAdd);
//
//
//                classAdd.addActionListener(
//                        // This creates an anonymous subclass of ActionListener and instantiates it.
//                        new ActionListener() {
//                            public void actionPerformed(ActionEvent evt) {
//                                if (evt.getSource().equals(classAdd)) {
//                                    inventoryController.execute("/api/classes/" + className.toLowerCase());
//                                }
//                            }
//                        }
//                );
//
//
//            }
//            this.add(buttons);
//            revalidate();
//            repaint();
//
//        }
        if (evt.getNewValue() instanceof RaceState) {
            RaceState state = (RaceState) evt.getNewValue();

            JPanel buttons = new JPanel();
            for (String raceName: state.races) {
                JButton raceAdd = new JButton(raceName);
                buttons.add(raceAdd);


                raceAdd.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                if (evt.getSource().equals(raceAdd)) {
                                    inventoryController.execute("/api/races/" + raceName.toLowerCase());
                                }
                            }
                        }
                );


            }
            this.add(buttons);
            revalidate();
            repaint();

        }
//        if (evt.getNewValue() instanceof BackgroundState) {
//            BackgroundState state = (BackgroundState) evt.getNewValue();
//
//            JPanel buttons = new JPanel();
//            for (String backgroundName: state.backgrounds) {
//                JButton backgroundAdd = new JButton(backgroundName);
//                buttons.add(backgroundAdd);
//
//
//                backgroundAdd.addActionListener(
//                        new ActionListener() {
//                            public void actionPerformed(ActionEvent evt) {
//                                if (evt.getSource().equals(backgroundAdd)) {
//                                    inventoryController.execute("/api/races/" + backgroundName.toLowerCase());
//                                }
//                            }
//                        }
//                );
//
//
//            }
//            this.add(buttons);
//            revalidate();
//            repaint();
//
//        }
    }
}