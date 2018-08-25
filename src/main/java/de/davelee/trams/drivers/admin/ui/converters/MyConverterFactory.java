package de.davelee.trams.drivers.admin.ui.converters;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;

import java.time.LocalDate;
import java.util.Date;

/**
 * This is a custom converter factory for Vaadin.
 * For LocalDate to Date and Date to LocalDate use the special converter.
 * Otherwise use the standard converters.
 * @author Dave Lee
 */
public class MyConverterFactory extends DefaultConverterFactory {

    @Override
    /**
     * Find an appropriate converter for the specified presentation and model types.
     * @param presentationType a <code>Class</code> object with the presentation type.
     * @param modelType a <code>Class</code> object with the model type.
     * @return a <code>Converter</code> object representing the correct converter to use.
     */
    protected <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> findConverter(
            Class<PRESENTATION> presentationType, Class<MODEL> modelType) {
        // Handle LocalDate <-> Date
        if (presentationType == Date.class && modelType == LocalDate.class) {
            return (Converter<PRESENTATION, MODEL>) new LocalDateToDateConverter();
        }
        // Let default factory handle the rest
        return super.findConverter(presentationType, modelType);
    }
}
