package com.surya.sp.api;

import com.surya.sp.domain.Book;
import com.surya.sp.domain.Product;
import com.surya.sp.utils.DatabaseUtils;
import com.surya.sp.utils.JdbcTemplateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by suryakanta.sahoo on 10/09/2021.
 */
@RestController
@RequestMapping("api/product-sp")
public class ProductApi {
    private DatabaseUtils utils;
    @Autowired
    public void setUtils(DatabaseUtils utils) {
        this.utils = utils;
    }
    private JdbcTemplateUtils jdbcUtils;

    @Autowired
    public void setJdbcUtils(JdbcTemplateUtils jdbcUtils){
        this.jdbcUtils=jdbcUtils;
    }

    @PostMapping("/sp-create-product")
    public String callProcedure(@RequestBody Product product){
        Object[] params=new Object[]{
                UUID.randomUUID().toString(),
                product.getCode(),
                product.getName(),
                product.getWeight()
        };
        utils.callStoredProcedure("create_product(?,?,?,?)",params);
        return "success";
    }
    @GetMapping("/sp-count-product")
    public Long callFunctionCountProduct(){
        Long count=(Long) utils.callStoredFunction(Types.BIGINT, "count_product()",null);
        return count;
    }

    @PostMapping("/sp-create-product-jdbc")
    public String callProcedureJdbc(@RequestBody Product product){
        Map<String,Object> params=new HashMap<>();
        params.put("id",UUID.randomUUID().toString());
        params.put("p_code",product.getCode());
        params.put("p_name",product.getName());
        params.put("weight",product.getWeight());
        jdbcUtils.callStoreProcedure("create_product",params);
        return "success";
    }
    @GetMapping("/sp-count-product-jdbc")
    public int callFunctionCountProductJdbc(){
    	BigDecimal count= (BigDecimal) jdbcUtils.callStoredFunction( "count_product",null,Long.class);
        return count.intValue();
    }
    
    @GetMapping("/sp-count-ref-product-jdbc")
    public Object callFunctionRefBookProductJdbc(@RequestParam String paraid,@RequestParam String serchby){
        Map<String,Object> params=new HashMap<>();
        params.put(paraid,serchby);
        Object books= jdbcUtils.callStoredRefFunction( "get_book_by_name","o_c_book",params,Book.class);
        return books;
    }
}
