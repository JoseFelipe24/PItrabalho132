package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import Interface.Administrador;
import models.Cliente;
import models.Livro;
import play.cache.Cache;
import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
import play.mvc.Controller;
import play.mvc.With;


@Administrador
@With(Seguranca.class)
public class Livros extends Controller{

	
	public static void listar () {
		List<Livro> lili = Livro.findAll();;
		 render(lili);
	}
	public static void listaAjax(String termo) {
		
		List<Livro> lili = Collections.emptyList();
		if (termo == null || termo.isEmpty()) {
			lili = Livro.findAll();
		} else {
			lili = Livro.find("lower(nome) like ?1 or lower(autor) like ?1",
					"%"+ termo.toLowerCase() +"%").fetch();
		}
		 renderJSON(lili);
	}
	
	public static void remover (long id) {
		Livro l = Livro.findById(id);
		l.delete();
		listar();
	}
	
	public static void form () {
		render();
	}
	
	public static void editar (long id) {
		Livro l = Livro.findById(id);
		renderTemplate("Livros/form.html", l);
		
	}	
 
	public static void salvar (@Valid Livro ll) {
		
		if(validation.hasErrors()) {
			validation.keep();
			form();
		}
		
		ll.save();
		listar();

	}

}
