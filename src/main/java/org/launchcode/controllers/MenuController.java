package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){

        List<Menu> menus = (List<Menu>) menuDao.findAll();
        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menus);

        return "/menu/index";
    }

    @RequestMapping(value = "/view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable(value = "menuId") int menuId){
        Menu menu = menuDao.findOne(menuId);
        model.addAttribute(menu);
        model.addAttribute("title", menu.getName());

        return "menu/view";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model){

        Menu menu = new Menu();
        model.addAttribute(menu);
        model.addAttribute("title", "Add Menu");

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors){
        if (errors.hasErrors()){
            model.addAttribute("title", "Add Menu");
            model.addAttribute("menu", menu);
            return "add";
        }
        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable(value = "menuId") int menuId){
        Menu menu = menuDao.findOne(menuId);
        List<Cheese> cheeses = (List<Cheese>) cheeseDao.findAll();
        AddMenuItemForm form = new AddMenuItemForm(menu, cheeses);
        model.addAttribute("form", form);
        model.addAttribute("title", "Add item to menu: " + menu.getName());

        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute  @Valid AddMenuItemForm form,
                          Errors errors){
        Menu menu = menuDao.findOne(form.getMenuId());
        Cheese newCheese = cheeseDao.findOne(form.getCheeseId());
        menu.addItem(newCheese);
        menuDao.save(menu);

        return "redirect:view/" + menu.getId();
    }
}
