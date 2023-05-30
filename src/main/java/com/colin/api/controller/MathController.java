package com.colin.api.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.colin.api.entities.Greeting;
import com.colin.api.exceptions.UnsupportedMathOperationException;

@RestController
public class MathController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }

    
    
    //Sum
    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception{
    	
    	if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
    		throw new UnsupportedMathOperationException("Please set a numeric value");
    	}
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }
    
    //Sub
    @RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sub(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception{
    	
    	if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
    		throw new UnsupportedMathOperationException("Please set a numeric value");
    	}
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }
    
    //mult
    @RequestMapping(value = "/mult/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double mult(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception{
    	
    	if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
    		throw new UnsupportedMathOperationException("Please set a numeric value");
    	}
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }
    
    //div
    @RequestMapping(value = "/div/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double div(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception{
    	
    	if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
    		throw new UnsupportedMathOperationException("Please set a numeric value");
    	}
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }
    
    //avg
    @RequestMapping(value = "/avg/{numberOne}/{numberTwo}/{numberThree}", method = RequestMethod.GET)
    public Double avg(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo,
            @PathVariable(value = "numberThree") String numberThree)
    		 throws Exception{
    	
    	if(!isNumeric(numberOne) || !isNumeric(numberTwo) || !isNumeric(numberThree)) {
    		throw new UnsupportedMathOperationException("Please set a numeric value");
    	}
        return (convertToDouble(numberOne) + convertToDouble(numberTwo) + convertToDouble(numberThree))/3;
    }
    
    //pow
    @RequestMapping(value = "/pow/{numberOne}", method = RequestMethod.GET)
    public Double pow(
            @PathVariable(value = "numberOne") String numberOne)
    		 throws Exception{
    	
    	if(!isNumeric(numberOne)) {
    		throw new UnsupportedMathOperationException("Please set a numeric value");
    	}
        return Math.pow(convertToDouble(numberOne),2);
    }
    
    

	private Double convertToDouble(String strNumber) {
		if(strNumber == null) return 0D;
		String number = strNumber.replaceAll(",", ".");
		if(isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}

	private boolean isNumeric(String strNumber) {
		if(strNumber == null) {
			return false;
		}
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
