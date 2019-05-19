package br.com.involvesfullstack.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomErrorController extends AbstractErrorController {

  public CustomErrorController(ErrorAttributes errorAttributes) {
      super(errorAttributes);
  }

  @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ApiError handleError(HttpServletRequest request) {
      return createApiError(super.getErrorAttributes(request, true));
  }
  
  private ApiError createApiError(final Map<String, Object> errorAttributes) {
	  return new ApiError((Integer)errorAttributes.get("status"), (String)errorAttributes.get("message"));
  }

  @Override
  public String getErrorPath() {
      return "/error";
  }
}