package br.com.vilarica.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import com.outjected.email.api.ContentDisposition;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.TipoAtividadeExcursao;
import br.com.vilarica.util.mail.Mailer;

public class EmailAnexoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject Mailer mailer;
	
	public String sendEmail(ExcursaoEscolar excursao) {
		try {
			MailMessage message = mailer.novaMensagem();
			message.to(excursao.getInstituicao().getContato().getEmail());
			message.bcc(excursao.getGuia().getContato().getEmail());
			message.subject("Comprovante de Agendamento de Excursão");
			
			message.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/excursao.template")));
			message.put("instituicao", excursao.getInstituicao().getNome());
			message.put("data", new SimpleDateFormat("dd-MMMM-yyyy HH:mm").format(excursao.getDataExcursao()));
			message.put("guia", excursao.getGuia().getNome());
			message.put("total", excursao.getTotalVisitantes());
			message.put("municipio", excursao.getInstituicao().getMunicipio().getNome());
			
			StringBuilder sb = new StringBuilder();
			for (TipoAtividadeExcursao t : excursao.getAtividades()) {
				sb.append(t.getAtividadeEnum().getAtividade()).append(", ");
			}
			message.put("atividade", sb.toString());
			message.charset("UTF-8"); // UTF-8 ou ISO-8859-1
			message.addAttachment("lista_visitantes.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					ContentDisposition.ATTACHMENT, getClass().getResourceAsStream("/data/Modelo_Lista.xlsx"));
			
			message.send();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
}
