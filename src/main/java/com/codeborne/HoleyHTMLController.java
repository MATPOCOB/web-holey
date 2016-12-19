package com.codeborne;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class HoleyHTMLController {

  @RequestMapping(value = "/search", method = GET)
  public String index(Model model, @RequestParam(required = false) String query) {
    model.addAttribute("query", query);
    return "search";
  }

  @RequestMapping(value = "/search", method = POST)
  public String search(Model model, @RequestParam(required = false) String query) {
    model.addAttribute("query", query);
    return "search";
  }

  @RequestMapping(value = "/rest/partner", method = GET/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
  @ResponseBody
  public String partner(@RequestParam(required = false) String name) {
    name = (name == null) ? "Partner name" : name;
    return "{\"name\":\""+ name +"\"}";
  }
}