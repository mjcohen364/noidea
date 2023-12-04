import app.Main;
import data_access.FileCharacterDataAccessObject;
import entity.Character;
import entity.CharacterFactory;
import entity.PlayerFactory;
import view.CharacterNameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class CreateCharacterTest {


    static String message = "";
    static boolean popUpDiscovered = false;

    /**
     * ensures there is at least 1 character in the CSV file for testing purposes
     */
    public void addCharacter() {
        CharacterFactory cf = new PlayerFactory();
        FileCharacterDataAccessObject fcdao;
        try {
            fcdao = new FileCharacterDataAccessObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fcdao.save(cf.create("character", LocalDateTime.now()));
    }


    public JButton getButton() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        assertNotNull(app); // found the window?

        Component root = app.getComponent(0);

        Component cp = ((JRootPane) root).getContentPane();

        JPanel jp = (JPanel) cp;

        JPanel jp2 = (JPanel) jp.getComponent(0);

        CharacterNameView sv = (CharacterNameView) jp2.getComponent(0);

     
        JPanel buttons = (JPanel) sv.getComponent(4);

        return (JButton) buttons.getComponent(2); // this should be the clear button
    }


    /**
     *
     * This test checks that the JDialog contains the names of
     * all character deleted from the file.
     */
    @org.junit.Test
    public void testClearUsersPopUpShown() {

        addCharacter();
        popUpDiscovered = false;

        Main.main(null);
        JFrame app = null;

        JButton button = getButton();


        // since clicking the button should end up displaying a JDialog to the user to report the
        // result, we set a timer, which will execute code necessary to complete the testing.
        createCloseTimer().start();

        //click the button
        button.doClick();

        // will continue execution here after the JDialog is closed

        // confirm a popUp was discovered
        assert(popUpDiscovered);
        System.out.println("popup was detected successfully.");

    }

    @org.junit.Test
    public void testClearUsersReturnedUsersDeleted() throws InterruptedException {

        addCharacter();
        message = "";

        Main.main(null);

        JButton button = getButton();

        // since clicking the button should end up displaying a JDialog to the user to report the
        // result, we set a timer, which will execute code necessary to complete the testing.
        createCloseTimer().start();

        //click the button
        button.doClick();

        // will continue execution here after the JDialog is closed

        // check the message
        assert(message.contains("user1") && message.contains("user2"));
    }

    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {

                    if (window instanceof JDialog) {

                        JDialog dialog = (JDialog)window;

                        // this ignores old dialogs
                        if (dialog.isVisible()) {
                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();
                            System.out.println("message = " + s);

                            // store the information we got from the JDialog
//                            ClearUsersTest.message = s;
//                            ClearUsersTest.popUpDiscovered = true;

                            System.out.println("disposing of..." + window.getClass());
                            window.dispose();
                        }
                    }
                }
            }

        };

        Timer t = new Timer(1000, close);
        t.setRepeats(false);
        return t;
    }
