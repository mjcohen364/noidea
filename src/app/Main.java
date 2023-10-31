package app;

import data_access.DataAccessObject;

import interface_adapter.ItemController;
import interface_adapter.ItemPresenter;
import interface_adapter.ItemViewModel;
import interface_adapter.ViewManagerModel;
import use_case.ItemInteractor;
import view.ItemView;

import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Item Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        ItemViewModel itemViewModel = new ItemViewModel();

        DataAccessObject dataAccessObject;

        dataAccessObject = new DataAccessObject();


        ItemView itemView = new ItemView(new ItemController(new ItemInteractor(dataAccessObject, new ItemPresenter(itemViewModel))), itemViewModel);
                //viewManagerModel
        views.add(itemView, itemView.viewName);




        viewManagerModel.setActiveView(itemView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}