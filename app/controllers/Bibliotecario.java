package controllers;

import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

import Interface.Administrador;
import models.Bibliotecarios;

@Administrador
public class Bibliotecario extends Controller {

	@Before(only = { "listar", "remover", "editar" })
	static void verificar() {
		if (session.get("Bibliotecarios.email") == null)
			Login.loginn();

	}

	public static void listar(String termo) {
		List<Bibliotecarios> bibli = null;
		if (termo == null || termo.isEmpty()) {
			bibli = Bibliotecarios.findAll();
		} else {
			bibli = Bibliotecarios.find("lower(nome) like ?1 or lower(email) like ?1", "%" + termo.toLowerCase() + "%")
					.fetch();
		}
		List<Bibliotecarios> bibi = Bibliotecarios.findAll();
		render(bibli, termo, bibi);

	}

	public static void remover(long id) {
		Bibliotecarios d = Bibliotecarios.findById(id);
		d.delete();
		listar("");
	}

	public static void salvar(@Valid Bibliotecarios d) {
		
		if(validation.hasErrors()) {
			validation.keep();
			form();
		}
		d.save();
		Login.loginn();
	}

	public static void form() {
		render();
	}

	public static void editar(long id) {
		Bibliotecarios d = Bibliotecarios.findById(id);
		renderTemplate("Bibliotecario/form.html", d);

	}

}
