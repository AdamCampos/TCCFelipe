package com.main.controlador;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
@Controller
public class ControladorErros implements ErrorController {

	private final static ModelAndView MAV = new ModelAndView("error");

	@RequestMapping("/error")
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(Exception ex, HttpServletRequest request, HttpServletResponse response) {

		String errorMsg = "";
		String errorMsgFull = "";

		log.error("::Erro - código " + request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));

		if (ex.toString().contains("EmptyResultDataAccessException")) {
			log.error("::Erro - nenhum valor encontrado no banco de dados com os parâmetros solicitados."
					+ ex.getCause());
			errorMsg = "Erro na pesquisa. Nenhum valor encontrado no banco de dados com os parâmetros solicitados.";
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

	/*
	 * @ExceptionHandler(Exception.class) public ResponseEntity<Object>
	 * handle(Exception ex, HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * log.debug("::Erro pego " + ex);
	 * 
	 * if (ex instanceof NullPointerException) { return new
	 * ResponseEntity<>(HttpStatus.BAD_REQUEST); } else if (ex instanceof
	 * SQLException) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body("Erro no SQL Server " + ex.getMessage()); } else { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage())
	 * ; }
	 * 
	 * }
	 */

}
