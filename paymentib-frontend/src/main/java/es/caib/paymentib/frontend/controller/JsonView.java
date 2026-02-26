package es.caib.paymentib.frontend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Vista para renderizar como un JSON.
 *
 * @author Indra
 *
 */
public final class JsonView implements View {

	/**
	 * Log.
	 */
	private static Logger logger = LoggerFactory.getLogger(JsonView.class);

	/**
	 * Instancia view.
	 */
	private static final JsonView INSTANCE_JSONVIEW = new JsonView();

	/**
	 * Nombre del objeto pasado a la view para renderizar a JSON.
	 */
	public static final String JSON_OBJECT = "root";

	/**
	 * Mapper para convertir a JSON.
	 */
	private final ObjectMapper jacksonObjectMapper;

	/**
	 * Constructor.
	 */
	private JsonView() {
		jacksonObjectMapper = new ObjectMapper();
		jacksonObjectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		jacksonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		// jacksonObjectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
		// false);
	}

	/**
	 * Método de acceso a instance.
	 *
	 * @return instance
	 */
	public static JsonView getInstanceJsonview() {
		return INSTANCE_JSONVIEW;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.View#getContentType()
	 */
	@Override
	public String getContentType() {
		return "application/json; charset=UTF-8";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.View#render(java.util.Map,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", getContentType());
		PrintWriter writer;
		writer = response.getWriter();
		final Object root = model.get(JsonView.JSON_OBJECT);
		final String json = jsonToString(root);
		logger.trace("json: " + json);
		writer.write(json);
	}

	/**
	 * Convierte objeto a JSON.
	 *
	 * @param obj
	 *            Objeto
	 * @return Json
	 * @throws IOException
	 *             Excepcion al convertir a JSON.
	 */
	public String jsonToString(final Object obj) throws IOException {
		return jacksonObjectMapper.writeValueAsString(obj);
	}

}
