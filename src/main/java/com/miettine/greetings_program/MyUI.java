package com.miettine.greetings_program;



import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	//Label label = new Label("Version 0.1");

    	VerticalLayout layout = new VerticalLayout();
        
        grid.setColumns("firstName", "lastName","birthDate","email");
        updateList();
        setContent(layout);
        
        layout.addComponent(grid);
        
      

     // A button that takes all the space available in the layout.

     grid.setSizeFull();
     layout.addComponent(grid);

     // We must set the layout to a defined height vertically, in
     // this case 100% of its parent layout, which also must
     // not have undefined size.
     layout.setHeight("100%");
        
     setContent(grid);
     
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
		List<Customer> customers = customerService.findAll();
        
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
