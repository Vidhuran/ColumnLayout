package org.vaadin.alump.columnlayout.demo.views;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.vaadin.alump.columnlayout.ColumnLayout;
import org.vaadin.alump.columnlayout.demo.components.SmallButton;

import java.util.HashSet;
import java.util.Set;

/**
 * Basic behavior tests of ColumnLayout
 */
@ViewIdentifier("Test")
@ViewDescription("Basic behavior tests of ColumnLayout")
public class BasicView extends CLView {

    private int defaultColumnWidth = 300;

    private Set<SpacingHandler> spacingHandlers = new HashSet<>();

    @Override
    protected ColumnLayout createLayout() {
        ColumnLayout layout = new ColumnLayout();
        defaultColumnWidth = layout.getColumnWidth();
        spacingHandlers.add(layout);
        layout.setExpandingColumns(false);
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setWidth("100%");

        TextField firstName = new TextField("First name");
        firstName.setWidth("100%");
        layout.addComponent(firstName, 0);

        TextField lastName = new TextField("Last name");
        lastName.setWidth("100%");
        layout.addComponent(lastName, 0);

        TextField address = new TextField("Address");
        address.setWidth("100%");
        layout.addComponent(address, 1);

        HorizontalLayout cityAndZipCode = new HorizontalLayout();
        spacingHandlers.add(cityAndZipCode);
        cityAndZipCode.setWidth("100%");
        layout.addComponent(cityAndZipCode, 1);

        TextField city = new TextField("City");
        city.setWidth("100%");
        cityAndZipCode.addComponent(city);
        cityAndZipCode.setExpandRatio(city, 1.0f);

        TextField zipCode = new TextField("Zip code");
        zipCode.setWidth("100%");
        cityAndZipCode.addComponent(zipCode);
        cityAndZipCode.setExpandRatio(zipCode, 1.0f);

        return layout;
    }

    @Override
    protected void createMenuBar(HorizontalLayout menuBar) {
        CheckBox spacing = new CheckBox("Spacing");
        spacing.addValueChangeListener(e ->
                spacingHandlers.forEach(h -> h.setSpacing((Boolean) e.getProperty().getValue())));
        menuBar.addComponent(spacing);

        CheckBox margin = new CheckBox("Margin");
        margin.addValueChangeListener(e -> getLayout().setMargin((Boolean) e.getProperty().getValue()));
        menuBar.addComponent(margin);

        CheckBox expand = new CheckBox("Expanding columns");
        expand.addValueChangeListener(e -> getLayout().setExpandingColumns((Boolean) e.getProperty().getValue()));
        menuBar.addComponent(expand);

        ComboBox columnWidth = new ComboBox();
        columnWidth.setDescription("Column width in pixels");
        columnWidth.setTextInputAllowed(false);
        columnWidth.setNullSelectionAllowed(false);
        columnWidth.addItem("Default");
        columnWidth.addItem(new Integer(100));
        columnWidth.addItem(new Integer(200));
        columnWidth.addItem(new Integer(400));
        columnWidth.addItem(new Integer(800));
        columnWidth.addItem(new Integer(1600));
        columnWidth.setValue("Default");
        columnWidth.addValueChangeListener(e -> {
            if(e.getProperty().getValue() instanceof Integer) {
                getLayout().setColumnWidth((Integer)e.getProperty().getValue());
            } else {
                getLayout().setColumnWidth(defaultColumnWidth);
            }
        });
        menuBar.addComponent(columnWidth);

        CheckBox outlines = new CheckBox("Show outlines");
        outlines.addValueChangeListener(e -> {
            if((Boolean)e.getProperty().getValue()) {
                getLayout().addStyleName("outlined");
            } else {
                getLayout().removeStyleName("outlined");
            }
        });
        menuBar.addComponent(outlines);
    }
}
