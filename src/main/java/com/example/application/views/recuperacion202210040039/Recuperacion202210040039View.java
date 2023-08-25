package com.example.application.views.recuperacion202210040039;

import com.example.application.data.entity.Especies;
import com.example.application.data.service.EspeciesService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Recuperacion 202210040039")
@Route(value = "recuperacion-202210040039/:especiesID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class Recuperacion202210040039View extends Div implements BeforeEnterObserver {

    private final String ESPECIES_ID = "especiesID";
    private final String ESPECIES_EDIT_ROUTE_TEMPLATE = "recuperacion-202210040039/%s/edit";

    private final Grid<Especies> grid = new Grid<>(Especies.class, false);

    private TextField nombreCientifico;
    private TextField descripcion;
    private TextField habitad;
    private TextField cantidadDeEspecie;
    private TextField fechaRejistro;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Especies> binder;

    private Especies especies;

    private final EspeciesService especiesService;

    public Recuperacion202210040039View(EspeciesService especiesService) {
        this.especiesService = especiesService;
        addClassNames("recuperacion202210040039-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombreCientifico").setAutoWidth(true);
        grid.addColumn("descripcion").setAutoWidth(true);
        grid.addColumn("habitad").setAutoWidth(true);
        grid.addColumn("cantidadDeEspecie").setAutoWidth(true);
        grid.addColumn("fechaRejistro").setAutoWidth(true);
        grid.setItems(query -> especiesService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ESPECIES_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(Recuperacion202210040039View.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Especies.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.especies == null) {
                    this.especies = new Especies();
                }
                binder.writeBean(this.especies);
                especiesService.update(this.especies);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(Recuperacion202210040039View.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> especiesId = event.getRouteParameters().get(ESPECIES_ID).map(Long::parseLong);
        if (especiesId.isPresent()) {
            Optional<Especies> especiesFromBackend = especiesService.get(especiesId.get());
            if (especiesFromBackend.isPresent()) {
                populateForm(especiesFromBackend.get());
            } else {
                Notification.show(String.format("The requested especies was not found, ID = %s", especiesId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(Recuperacion202210040039View.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nombreCientifico = new TextField("Nombre Cientifico");
        descripcion = new TextField("Descripcion");
        habitad = new TextField("Habitad");
        cantidadDeEspecie = new TextField("Cantidad De Especie");
        fechaRejistro = new TextField("Fecha Rejistro");
        formLayout.add(nombreCientifico, descripcion, habitad, cantidadDeEspecie, fechaRejistro);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Especies value) {
        this.especies = value;
        binder.readBean(this.especies);

    }
}
