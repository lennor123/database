package com.example.database.controller;
import com.example.database.entity.*;
import com.example.database.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/loginIn",method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam String User,@RequestParam String Key){
        User user = loginService.loginInfo(User,Key);
        if(user != null){
            return "loginsuccess";
        }else{
            return "logician";
        }
    }
    @RequestMapping(value = "/findInfo")
    @ResponseBody
    public List<Repository> findInfo() {
        List<Repository> userLists = loginService.findInfo();
        return userLists;
    }

    @RequestMapping(value = "/alltablename")
    @ResponseBody
    public List<Table> alltablename(@RequestParam String tableName) {
        List<Table> alluser = loginService.alltablename(tableName);
        return alluser;
    }

    @RequestMapping(value = "/detail")
    @ResponseBody
    public List<Det> detail(@RequestParam String anyName) {
        List<Det> detlist = loginService.detail(anyName);
        return detlist;
    }

    @RequestMapping(value = "/insertviewtable", method = RequestMethod.POST)
    @ResponseBody
    public int  insertviewtable(@RequestBody Insert insert) {
        loginService.insertviewtable(insert);

        System.out.println(insert.getId());
        return insert.getId();
    }
    @RequestMapping(value = "/findRepositoryInfo",method = RequestMethod.POST)
    @ResponseBody
    public Repository findRepositoryInfo(@RequestBody Repository repository) {
        int id = Integer.parseInt(repository.getStrId());
        Repository repositoryInfo = loginService.findRepositoryInfo(id);
        return repositoryInfo;
    }
//    @RequestMapping(value = "/findFieldsInfo",method = RequestMethod.POST)
//    @ResponseBody
//    public List<Field>findFieldsInfo(@RequestBody Field field) {
//        int id = Integer.parseInt(field.getStrId());
//        List<Field> fieldlist = loginService.findFieldsInfo(id);
//        return fieldlist;
//    }

    @RequestMapping(value = "/findDataModelInfo")
    @ResponseBody
    public List<Repository>  findDataModelInfo() {
        List<Repository> rlist = loginService.findDataModelInfo();
        return rlist;
    }

    @RequestMapping(value = "/findFieldInfo")
    @ResponseBody
    public List<Field> findFieldInfo(@RequestParam int id) {
        List<Field> flist = loginService.findFieldInfo(id);
        return flist;
    }
}
