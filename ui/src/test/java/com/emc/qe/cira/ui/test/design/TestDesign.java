package com.emc.qe.cira.ui.test.design;

import javax.annotation.PostConstruct;

import com.vaadin.data.util.TextFileProperty;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
public class TestDesign extends VerticalLayout{
	private TextField text;
	@PostConstruct
	void init()
	{
		setSpacing(true);
		text = new TextField();
		addComponent(text);
	}

}
