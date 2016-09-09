package com.miettine.greetings_program;



import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	CustomerService customerService = CustomerService.getInstance();
	
	private Grid grid = new Grid();
	
	private TextField filterText = new TextField();
	
	private CustomerForm form = new CustomerForm(this);
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	//Label label = new Label("Version 0.1");

		VerticalLayout layout = new VerticalLayout();

		CssLayout filteringLayout = new CssLayout();

		filterText.setInputPrompt("Filter by name");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customerService.findAll(e.getText())));
		});

		Button clearFilterButton = new Button(FontAwesome.TIMES);
		clearFilterButton.addClickListener(e -> {
			filterText.clear();
			updateList();
		});

		grid.setColumns("firstName", "lastName", "birthDate", "email");
		updateList();
		setContent(layout);

		filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		filteringLayout.addComponents(filterText, clearFilterButton);

		filteringLayout.setHeight("10%");
		// horizontalLayout.setWidth("100%");
		
		HorizontalLayout main = new HorizontalLayout();
		
		layout.addComponents((Component) form, grid);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setExpandRatio(grid, 1);

		
		
		layout.addComponents(filterText, main);

		// A button that takes all the space available in the layout.

		grid.setSizeFull();
		layout.addComponent(grid);

		layout.setHeight("100%");

		setContent(layout);

        /*
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Greetings, program!");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        */

        
        /*layout.setMargin(true);
        layout.setSpacing(true);
        
        layout.setSizeUndefined();
        */
        
        //layout.setExpandRatio(grid, 1.0f); // Expand
        
      
    }

	public void updateList() {
		List<Customer> customers = customerService.findAll(filterText.getValue());
        
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
