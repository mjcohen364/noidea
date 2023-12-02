package view;
import interface_adapter.character_creator.CharacterCreatorController;
import interface_adapter.race_desc.RaceDescController;
import interface_adapter.race_desc.RaceDescState;
import interface_adapter.race_desc.RaceDescViewModel;
import interface_adapter.race.RaceState;
import interface_adapter.race.RaceViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChooseRaceView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Choose Race";
    private final RaceViewModel raceViewModel;
    private final RaceDescController raceDescController;
    private final RaceDescViewModel raceDescViewModel;
    private final CharacterCreatorController characterCreatorController;
    private boolean raceChoicesAdded = false;
    final JButton mainScreen;
    private JLabel description;
    public ChooseRaceView(RaceDescController raceDescController, RaceDescViewModel raceDescViewModel,
                          CharacterCreatorController characterCreatorController, RaceViewModel raceViewModel) {
        this.characterCreatorController = characterCreatorController;
        this.raceViewModel = raceViewModel;
        this.raceDescController = raceDescController;
        this.raceDescViewModel = raceDescViewModel;
        this.raceDescViewModel.addPropertyChangeListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        raceViewModel.addPropertyChangeListener(this);
        JLabel title = new JLabel("Choose Race");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

        description = new JLabel("Click a race to view its description!");
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        mainScreen = new JButton(RaceViewModel.MAIN_SCREEN_LABEL);
        buttons.add(mainScreen);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(buttons);
        this.add(description);

        mainScreen.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(mainScreen)) {
                            RaceState currentState = raceViewModel.getState();
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
        if (evt.getNewValue() instanceof RaceDescState) {
            RaceDescState state = (RaceDescState) evt.getNewValue();
            description.setText(state.desc);
            revalidate();
            repaint();

        }
        if (evt.getNewValue() instanceof RaceState) {
            RaceState state = (RaceState) evt.getNewValue();
            JPanel buttons2 = new JPanel();
            for (String raceName: state.races) {
                JButton raceAdd = new JButton(raceName);
                buttons2.add(raceAdd);
                raceAdd.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                raceDescController.execute(raceName.toLowerCase());
                            }
                        }
                );
            }
            buttons2.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (!this.raceChoicesAdded){
                this.add(buttons2, 1);
            }
            this.raceChoicesAdded = true;
            revalidate();
            repaint();
        }
    }
}