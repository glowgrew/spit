package ru.glowgrew.spit.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;

import java.io.IOException;

/**
 * @author glowgrew
 */
public class PrettyPrinter extends DefaultPrettyPrinter {

    public PrettyPrinter() {
    }

    public PrettyPrinter(DefaultPrettyPrinter base) {
        super(base);
    }

    @Override
    public PrettyPrinter withSeparators(Separators separators) {
        _separators = separators;
        _objectFieldValueSeparatorWithSpaces = separators.getObjectFieldValueSeparator() + " ";
        return this;
    }

    @Override
    public PrettyPrinter createInstance() {
        return new PrettyPrinter(this);
    }

    @Override
    public void writeEndArray(JsonGenerator g, int nrOfValues) throws IOException {
        if (!_arrayIndenter.isInline()) {
            --_nesting;
        }
        if (nrOfValues > 0) {
            _arrayIndenter.writeIndentation(g, _nesting);
        }
        g.writeRaw(']');
    }

}