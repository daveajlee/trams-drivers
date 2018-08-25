package de.davelee.trams.drivers.admin.ui.converters;

import com.vaadin.data.util.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

/**
 * Provides a conversion between old {@link Date} and new {@link LocalDate} API.
 * Based on: https://stackoverflow.com/questions/37236457/how-to-bind-a-vaadin-datefield-to-a-localdatetime
 * @author Dave Lee
 */
public class LocalDateToDateConverter implements Converter<Date, LocalDate> {

    private static final long serialVersionUID = 1L;

    @Override
    /**
     * Convert a Date to a Local Date.
     * @param value a <code>Date</code> object to convert.
     * @param targetType a <code>Class</code> object with the object type to convert to (always LocalDate).
     * @param locale a <code>Locale</code> which should be the localised options for conversion.
     * @return a <code>LocalDate</code> object with the converted value.
     */
    public LocalDate convertToModel(final Date value, final Class<? extends LocalDate> targetType, final Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {

        if (value != null) {
            return value.toInstant().atZone(ZoneOffset.systemDefault()).toLocalDate();
        }

        return null;
    }

    @Override
    /**
     * Convert a Local Date to a Date.
     * @param value a <code>LocalDate</code> object to convert.
     * @param targetType a <code>Class</code> object with the object type to convert to (always Date).
     * @param locale a <code>Locale</code> which should be the localised options for conversion.
     * @return a <code>Date</code> object with the converted value.
     */
    public Date convertToPresentation(final LocalDate value, final Class<? extends Date> targetType, final Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {

        if (value != null) {
            return Date.from(value.atStartOfDay().toInstant(ZoneOffset.UTC));
        }

        return null;
    }

    @Override
    /**
     * Return the model type as a class (always LocalDate).
     * @return a <code>Class</code> object containing the model type.
     */
    public Class<LocalDate> getModelType() {
        return LocalDate.class;
    }

    @Override
    /**
     * Return the presentation type as a class (always Date).
     * @return a <code>Class</code> object containing the presentation type.
     */
    public Class<Date> getPresentationType() {
        return Date.class;
    }

}
