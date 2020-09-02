package de.davelee.trams.drivers.admin.ui.forms;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.davelee.trams.drivers.data.Driver;
import de.davelee.trams.drivers.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This form enables a user to add or edit drivers.
 * It is based on the Spring Vaadin Tutorial: https://spring.io/guides/gs/crud-with-vaadin/
 * @author Dave Lee
 */
@SpringComponent
@UIScope
public class DriverEditorForm extends VerticalLayout {

    private final DriverRepository repository;

    /**
     * The currently edited driver
     */
    private Driver selectedDriver;

    /* Fields to edit properties in Driver entity */
    private TextField name = new TextField("Name");
    private TextField company = new TextField("Company");
    private DateField dateOfBirth = new DateField("Date of Birth");
    private TextField contractedHours = new TextField("Contracted Hours");
    private TextField hourlyWage = new TextField("Hourly Wage");
    private DateField startDate = new DateField("Start Date");
    private TextField skills = new TextField("Skills");

    /* Action buttons */
    private Button save = new Button("Save", FontAwesome.SAVE);
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", FontAwesome.TRASH_O);
    private CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    /**
     * Create a new DriverEditorForm by supplying the repository where the data can be retrieved from.
     * @param repository a <code>DriverRepository</code> containing the data of all drivers.
     */
    public DriverEditorForm(final DriverRepository repository) {
        this.repository = repository;

        addComponents(name, company, dateOfBirth, contractedHours, hourlyWage, startDate, skills, actions);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(selectedDriver));
        delete.addClickListener(e -> repository.delete(selectedDriver));
        cancel.addClickListener(e -> editDriver(selectedDriver));
        setVisible(false);
    }

    /**
     * Simple internal interface to manage changes.
     */
    public interface ChangeHandler {

        /**
         * This method is called when a change takes place.
         */
        void onChange();
    }

    /**
     * Retrieve the data for a specified driver and save it for further editing.
     * @param driver a <code>Driver</code> object to retrieve from the database.
     */
    public final void editDriver(final Driver driver) {
        final boolean persisted = driver.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            selectedDriver = repository.findOne(driver.getId());
        }
        else {
            selectedDriver = driver;
        }
        cancel.setVisible(persisted);

        // Bind driver properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        BeanFieldGroup.bindFieldsUnbuffered(driver, this);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        name.selectAll();
    }

    /**
     * Initialise the change handler for save and deletion events.
     * @param changeHandler a <code>ChangeHandler</code> object to record change events.
     */
    public void setChangeHandler(final ChangeHandler changeHandler) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> changeHandler.onChange());
        delete.addClickListener(e -> changeHandler.onChange());
    }

}
