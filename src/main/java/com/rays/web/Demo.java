package com.rays.web;

import com.rays.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by hand on 2017/3/21.
 */
@RestController
public class Demo {
    @Value("${info.author}")
    private String author;

    @Value("${info.create Date}")
    private  String date ;

    @Autowired
    private GirlProperties girlProperties;

    @RequestMapping("/hello")
    public String getResponse(){
        String girlInformation = girlProperties.getName()+" "+girlProperties.getAge()+" "+girlProperties.getAddress();
        return girlInformation;
    }

    @RequestMapping(value = "/demo/{id}",method = RequestMethod.GET)
    public String testRest(@PathVariable("id") Integer id){
        return "get id ="+id;
    }


}
