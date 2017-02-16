package jp.sample;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

	private static final Logger logegr = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions

        port(8989);

		get("/",(request, response)->{
			logegr.log(Level.INFO, "path /, get request accepeted! ");
			Map<String, Object> indexPage = new HashMap<>();
			indexPage.put("title", "hello, world!");
			return renderTemplate("velocity/index/index.vm", indexPage);
		});

		post("/",(request, response)->{
			logegr.log(Level.INFO, "path /, post request accepeted! ");

			Map<String, Object> indexPage = new HashMap<>();
			indexPage.put("title", "after post!");
			indexPage.put("name", request.queryParams("name"));
			indexPage.put("mail", request.queryParams("mail"));
			indexPage.put("password", request.queryParams("password"));

			logegr.log(Level.INFO, "params: "+request.queryParams());

			return renderTemplate("velocity/index/index.vm", indexPage);
		});

	}

	private static String renderTemplate(String template, Map<String, Object> model) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }

}
