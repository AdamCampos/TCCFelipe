package com.main.controlador;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
@Controller
public class ControladorErros extends ResponseEntityExceptionHandler implements ErrorController {

	private final static ModelAndView MAV = new ModelAndView("error");

	@RequestMapping("/error")
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(Exception ex, HttpServletRequest request, HttpServletResponse response) {

		String errorMsg = "";
		String errorMsgFull = "";

		log.error("::Erro - código " + request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));

		if (ex.toString().contains("EmptyResultDataAccessException")) {
			log.error(
					"::Erro - nenhum valor encontrado no banco de dados com os parâmetros solicitados. Se houve uma tentativa de registro, confira usuário e senha."
							+ ex.getCause());
			errorMsg = "Erro na pesquisa. Nenhum valor encontrado no banco de dados com os parâmetros solicitados. Se houve uma tentativa de registro, confira usuário e senha.";
			errorMsgFull = ex.getLocalizedMessage();
		} else if (ex.toString().contains("BeanPropertyBindingResult")) {
			log.error("::Erro - objeto não pode ter todos seus parâmetros associados." + ex.getCause());
			errorMsg = "Erro no objeto: objeto não pode ter todos seus parâmetros associados.";
			errorMsgFull = ex.getLocalizedMessage();
		} else {
			errorMsg = "Erro sem tratamento. " + ex.toString();
		}

		MAV.addObject("errorMsg", errorMsg);
		MAV.addObject("errorMsgFull", errorMsgFull);
		return MAV;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("::" + exception);
		String bodyOfResponse = exception.getMessage();
		Map<String, Object> body = new HashMap<>();
		body.put("error", exception);
		return new ResponseEntity<>(bodyOfResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
