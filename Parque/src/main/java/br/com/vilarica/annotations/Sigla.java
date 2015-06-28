package br.com.vilarica.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "([a-z A-Z]{4,10})?")//\\d = [0-9]
public @interface Sigla {
	
@OverridesAttribute(constraint = Pattern.class, name = "message")
	
	//Buscando chave do arquivo ValidationMessages
	String message() default "{br.com.vilarica.contraints.SIGLA.message}";
	
	Class<?> [] groups() default {};
	
	Class<? extends Payload> [] payload() default {};

}
