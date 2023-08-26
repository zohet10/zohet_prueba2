package com.example.application.views.recuperacion202210040039;

import com.example.application.data.controller.EspeciesInteractor;
import com.example.application.data.controller.EspeciesInteractorImpl;
import com.example.application.data.entity.Especies;
import com.example.application.data.entity.Habitats;
import com.example.application.views.MainLayout;
import com.example.application.data.service.ReportGenerator;
import com.example.application.data.entity.EspeciesDataReport;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Recuperacion 202210040039")
@Route(value = "recuperacion-202210040039/:especiesID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class Recuperacion202210040039View extends Div implements RecuperacionView {
    private final String ESPECIES_ID = "especiesID";
    private final String ESPECIES_EDIT_ROUTE_TEMPLATE = "recuperacion-202210040039/%s/edit";

    private final Grid<Especies> grid = new Grid<>(Especies.class, false);
    private ComboBox<Habitats> habitat = new ComboBox<>("Habitat");;
    private TextField nombreCientifico;
    private TextArea descripcion;
    private NumberField cantidadDeEspecie;
    private DatePicker fechaRegistro;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button btnReport = new Button("Generar Reporte");
    
    private List<Especies> especies;
    private List<Habitats> habitats;
    private Especies especie;
    
    private EspeciesInteractor controlador;
    public Recuperacion202210040039View() {
        addClassNames("recuperacion202210040039-view");

        this.controlador = new EspeciesInteractorImpl(this);
        this.controlador.consultarEspecies();
        this.controlador.consultarHabitas();
        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombre_cientifico").setAutoWidth(true).setHeader("Nombre Cientifico");
        grid.addColumn("descripcion").setAutoWidth(true);
        grid.addColumn("habitat").setAutoWidth(true);
        grid.addColumn("cantidad_ejemplares").setAutoWidth(true).setHeader("Ejemplares Existentes");
        grid.addColumn("fecha_registro").setAutoWidth(true).setHeader("Fecha Registro");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        GridContextMenu<Especies> menu = grid.addContextMenu();
        menu.addItem("Eliminar", event -> {
         	ConfirmDialog dialog = new ConfirmDialog();
        	dialog.setHeader("Eliminar Especie "+event.getItem().get().getNombre_cientifico());
        	dialog.setText("Confirmar eliminacion");
        	dialog.setCancelable(true);
        	dialog.setCancelText("Cancelar");
        	dialog.setCancelButtonTheme("cancel primary");
        	dialog.addCancelListener(event2 -> {
             refreshGrid();	
        	});
        	dialog.setConfirmText("Eliminar");
        	dialog.setConfirmButtonTheme("error primary");
        	dialog.addConfirmListener(event3 -> {
        	this.controlador.deleteEspecie(event.getItem().get().getId());
        	refreshGrid();
        	});
        	dialog.open();
        	
        });
        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
            	selectedDataGrid(event.getValue());
            } else {
            	populateForm(null);
                clearForm();
            }
        });

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            if (this.especie == null) {
            	this.especie = new Especies();
                this.especie.setNombre_cientifico(this.nombreCientifico.getValue());
                this.especie.setDescripcion(this.descripcion.getValue());
                this.especie.setHabitat(this.habitat.getValue().getNombre_habitat());
                this.especie.setCantidad_ejemplares(this.cantidadDeEspecie.getValue());
                this.especie.setFecha_registro(this.fechaRegistro.getValue().toString());
                this.controlador.createEspecie(especie);
                refreshGrid();
                populateForm(null);
                clearForm();
            }
            if(this.especie != null) {
                this.especie.setNombre_cientifico(this.nombreCientifico.getValue());
                this.especie.setDescripcion(this.descripcion.getValue());
                this.especie.setHabitat(this.habitat.getValue().getNombre_habitat());
                this.especie.setCantidad_ejemplares(this.cantidadDeEspecie.getValue());
                this.especie.setFecha_registro(this.fechaRegistro.getValue().toString());
            	this.controlador.updateEspecie(especie);
                populateForm(null);
                clearForm();
                refreshGrid();
            }
        });
    }
    
    public void selectedDataGrid(Especies especie) {
    	if(especie != null) {
    		populateForm(especie);
    		this.nombreCientifico.setValue(this.especie.getNombre_cientifico()+"");
    	    this.descripcion.setValue(this.especie.getDescripcion()+"");
    	    Habitats habitatSelect = new Habitats();
    	    habitatSelect.setNombre_habitat(this.especie.getHabitat());
    	    this.habitat.setValue(habitatSelect);
    	    this.cantidadDeEspecie.setValue(this.especie.getCantidad_ejemplares());
    	}else {
    		clearForm();
    	}
    }

    private void generarReporte() {
    	ReportGenerator generador = new ReportGenerator();
    	Map<String, Object> parametros = new HashMap<>();
    
    	EspeciesDataReport datasourse = new EspeciesDataReport();
    	datasourse.setData(this.especies);
		boolean generado = generador.reportGeneratorPDF("Reporte_Especies_zohet", parametros, datasourse);
		if(generado) {
			String ubicacion = generador.getRute();
			Anchor url = new Anchor(ubicacion, "Abrir reporte PDF");
			url.setTarget("_blank");
			Notification notificacion = new Notification(url);
			notificacion.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			notificacion.setDuration(20000);
			notificacion.open();
		}else {
			Notification notificacion = new Notification("Error al generar reporte");
			notificacion.addThemeVariants(NotificationVariant.LUMO_ERROR);
			notificacion.setDuration(10000);
			notificacion.open();
		}
		this.especies.clear();
		refreshGrid();
	}
    
    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        H2 title = new H2("Datos de la Especia");
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nombreCientifico = new TextField("Nombre Cientifico");
        descripcion = new TextArea("Descripcion");
        habitat.setItems(this.habitats);
        habitat.setItemLabelGenerator(Habitats::getNombre_habitat);
        cantidadDeEspecie = new NumberField("Cantidad De Especie");
        fechaRegistro = new DatePicker("Fecha Registro");
        fechaRegistro.setEnabled(false);
        fechaRegistro.setValue(LocalDate.now());
        
        formLayout.add(title, nombreCientifico, descripcion, habitat, cantidadDeEspecie, fechaRegistro);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnReport.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnReport.addClickListener(e-> generarReporte());
        buttonLayout.add(save, cancel, btnReport);
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
        this.controlador.consultarEspecies();
    }

    private void clearForm() {
    	this.nombreCientifico.setValue("");
    	this.descripcion.setValue("");
    	this.habitat.clear();
    	this.cantidadDeEspecie.setValue(0.0);
    	this.fechaRegistro.setValue(LocalDate.now());
        populateForm(null);
    }

    private void populateForm(Especies value) {
        this.especie = value;

    }

	@Override
	public void refrescarGridEspecies(List<Especies> especies) {
		grid.setItems(especies);
		this.especies = especies;
	}

	@Override
	public void refrescarHabitats(List<Habitats> habitats) {
		this.habitats = habitats;
	}

	@Override
	public void statusMsgCreateEspecie(boolean value) {
		String msg = "Especie registrada con exito";
		if(!value) {
			msg = "Error al registrar especie";
		}
		Notification.show(msg);
	}

	@Override
	public void statusMsgUpdateEspecie(boolean value) {
		String msg = "Especie actualzada con exito";
		if(!value) {
			msg = "Error al actualizar especie";
		}
		Notification.show(msg);
	}

	@Override
	public void statusMsgDeleteEspecie(boolean value) {
		String msg = "Especie eliminada con exito";
		if(!value) {
			msg = "Error al eliminar especie";
		}
		Notification.show(msg);
	}
}
