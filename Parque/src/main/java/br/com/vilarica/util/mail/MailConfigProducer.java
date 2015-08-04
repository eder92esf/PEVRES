package br.com.vilarica.util.mail;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

public class MailConfigProducer {

	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws Exception{
		Properties p = new Properties();
		p.load(getClass().getResourceAsStream("/mail.properties"));
		
		SimpleMailConfig config = new SimpleMailConfig();
		config.setServerHost(p.getProperty("mail.server.host"));
		config.setServerPort(Integer.parseInt(p.getProperty("mail.server.port")));
		config.setEnableSsl(Boolean.parseBoolean(p.getProperty("mail.enable.ssl")));
		config.setAuth(Boolean.parseBoolean(p.getProperty("mail.auth")));
		config.setUsername(p.getProperty("mail.username"));
		config.setPassword(p.getProperty("mail.password"));
		
		return config;
	}
	
}
