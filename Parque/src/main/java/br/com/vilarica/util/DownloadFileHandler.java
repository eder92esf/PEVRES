package br.com.vilarica.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe utilitária para download de arquivos com JSF
 *
 * @author <a href="http://www.osvaldojunior.co.cc">Osvaldo A. Júnior
 *         "Mwanalezi"</a> Email: lupazu@gmail.com Site: www.osvaldojunior.co.cc
 *         Licença Creative Commons
 */
public class DownloadFileHandler {

	/**
	 * Método para download do arquivo
	 *
	 * @param filename
	 * @param file
	 * @param mimeType
	 * @param facesContext
	 */
	public static void downloadFile(String filename, File file,
			String mimeType, FacesContext facesContext) {
		FileInputStream in = null;
		try {
			ExternalContext context = facesContext.getExternalContext();
			System.out.println("Aqui para baixar o " + filename);
			HttpServletResponse response = (HttpServletResponse) context
					.getResponse();
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ filename + "\"");
			response.setContentLength((int) file.length());
			response.setContentType(mimeType);
			in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			byte[] buf = new byte[(int) file.length()];
			int count;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			facesContext.responseComplete();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(DownloadFileHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IOException io) {
			Logger.getLogger(DownloadFileHandler.class.getName()).log(
					Level.SEVERE, null, io);
		} catch (Exception exc) {
			Logger.getLogger(DownloadFileHandler.class.getName()).log(
					Level.SEVERE, null, exc);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// Logger.getLogger(DownloadFileHandler.class.getName()).log(Level.SEVERE,
				// null, ex);
				// ex.printStackTrace();
			}
		}

	}
}