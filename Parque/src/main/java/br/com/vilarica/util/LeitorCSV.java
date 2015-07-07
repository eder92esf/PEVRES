package br.com.vilarica.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.EscolaridadeEnum;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.SexoEnum;

public class LeitorCSV implements Serializable {

	private static final long serialVersionUID = 1L;

	private BufferedReader reader;
	private FileReader fileReader;
	private File arquivo;

	public String gravar(InputStream stream, long size) throws IOException {
		String raiz = null;
		if (File.separator.equals("\\"))
			raiz = "C:\\PEVRES";
		else
			raiz = "/home";

		File diretorio = new File(raiz);
		this.arquivo = new File(new StringBuilder( diretorio.getAbsolutePath()
				+ File.separator + "acompanhantes.csv").toString() );

		byte[] buffer = new byte[(int) size];
		stream.read(buffer, 0, (int) size);
		stream.close();

		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}
		if (!arquivo.exists()) {
			arquivo.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(arquivo);
		fos.write(buffer);
		fos.flush();
		fos.close();

		return this.arquivo.getAbsolutePath();
	}

	public List<Acompanhante> ler(File file, Municipio municipio)
			throws IOException {
		List<Acompanhante> lista = new ArrayList<Acompanhante>();
		fileReader = new FileReader(file);
		reader = new BufferedReader(fileReader);

		String linha = null;

		while ((linha = reader.readLine()) != null) {
			String[] object = linha.split("; ");
			Acompanhante a = new Acompanhante();
			a.setNome(object[0]);
			a.setSexo(SexoEnum.getEnum(object[1]));
			a.setEscolaridade(EscolaridadeEnum.getEscolaridade(object[2]));

			String[] dn = object[3].split("/");
			int dia = Integer.valueOf(dn[0]);
			int mes = Integer.valueOf(dn[1]);
			int ano = Integer.valueOf(dn[2]);
			mes -= 1;
			ano -= 1900;
			
			Date dataNascimento = new Date(ano, mes, dia);
			a.setDataNascimento(dataNascimento);
			a.setMunicipio(municipio);

			lista.add(a);
		}
		return lista;
	}
}
