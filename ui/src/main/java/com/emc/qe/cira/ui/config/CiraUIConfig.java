package com.emc.qe.cira.ui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.emc.qe.cira.config.CiraDbConfig;
import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@Import(CiraDbConfig.class)
@EnableVaadin
@ComponentScan({"com.emc.qe.cira.ui"})
public class CiraUIConfig {

}
