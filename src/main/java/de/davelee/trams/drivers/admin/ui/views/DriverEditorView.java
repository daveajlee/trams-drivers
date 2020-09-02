package de.davelee.trams.drivers.admin.ui.views;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import de.davelee.trams.drivers.admin.ui.forms.DriverEditorForm;
import de.davelee.trams.drivers.admin.ui.components.LogoutLink;
import de.davelee.trams.drivers.data.Driver;
import de.davelee.trams.drivers.services.DriverService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Secured("ROLE_ADMIN")
@SpringView(name = DriverEditorView.NAME)
/**
 * This view represents the screen where drivers can be added or edited.
 * This view can only be accessed by those with admin rights.
 * @author David Lee
 */
public class DriverEditorView extends AbstractView {

    public static final String NAME = "driverEditor";

    private final DriverService service;

    private final Grid grid;

    /**
     * Construct a new driver editor view using the supplied driver service and driver editor to edit and access data.
     * @param driverService a <code>DriverService</code> object to allow access to the database.
     * @param driverEditor a <code>DriverEditorForm</code> object containing the form to edit drivers.
     */
    public DriverEditorView(final DriverService driverService, final DriverEditorForm driverEditor) {

        //Initialise variables which are needed in multiple methods.
        this.service = driverService;
        this.grid = new Grid();

        //Add filter field.
        TextField filter = new TextField();
        filter.setInputPrompt("Filter by company");
        addComponent(filter);

        //Add grid and editor to show results
        grid.setHeight(300, Unit.PIXELS);
        grid.setWidth(600, Unit.PIXELS);
        grid.setColumns("id", "name", "company", "dateOfBirth", "hourlyWage", "startDate");

        addComponent(grid);
        addComponent(driverEditor);

        //Add new driver button.
        Button addNewButton = new Button("New driver", FontAwesome.PLUS);
        addComponent(addNewButton);

        //Add logout link.
        addComponent(new LogoutLink());

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.addTextChangeListener(e -> listDrivers(e.getText()));

        // Connect selected Driver to editor or hide if none is selected
        grid.addSelectionListener(e -> {
            if (e.getSelected().isEmpty()) {
                driverEditor.setVisible(false);
            }
            else {
                driverEditor.editDriver((Driver) grid.getSelectedRow());
            }
        });

        // Instantiate and edit new Driver the new button is clicked
        addNewButton.addClickListener(e -> driverEditor.editDriver(new Driver("", "", LocalDate.now(), "0", LocalDate.now())));

        // Listen changes made by the editor, refresh data from backend
        driverEditor.setChangeHandler(() -> {
            driverEditor.setVisible(false);
            listDrivers(filter.getValue());
        });

        // Initialize listing
        listDrivers(null);

    }

    /**
     * Filter the displayed drivers according to the supplied company in the filter by company text field.
     * @param company a <code>String</code> representing the company to filter by.
     */
    private void listDrivers(final String company) {
        if (StringUtils.isEmpty(company)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(Driver.class, service.getAllDrivers()));
        }
        else {
            grid.setContainerDataSource(new BeanItemContainer(Driver.class,
                    service.getAllDriversForCompany(company)));
        }
    }
    // end::listDrivers[]

    @Override
    /**
     * The enter method is not currently used.
     * @param event a <code>ViewChangeEvent</code> object which will be ignored.
     */
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //Do nothing
    }
}
