package com.ff.help;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import atg.nucleus.GenericService;

import com.ff.constant.FFConstant;

public class FFUtils extends GenericService {
	public static String NULL_POINTER="Error101";
	public static String USER_NAME_POINTER="Error102";
	public static String PASSWORD_POINTER="Error103";
	public static String PASSWORD_MATCH_POINTER="Error104";
	public static String EMAIL_POINTER="Error105";
	public static String NAME_POINTER="Error106";
	public String emailPattern;
	public boolean error;
	public FFConstant FFConstant;
	public HashMap errorInfo;
	public HashMap getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(HashMap errorInfo) {
		this.errorInfo = errorInfo;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public FFConstant getFFConstant() {
		return FFConstant;
	}
	public void setFfconstant(FFConstant FFConstant) {
		this.FFConstant = FFConstant;
	}
	public void validateFields(HashMap fields)
	{
		
		errorInfo=new HashMap();
		Set object=fields.keySet();
		Iterator iterate=object.iterator();
		List rFields=Arrays.asList(getFFConstant().requiredFields);
		
		
		while(iterate.hasNext())
		{
			String value=iterate.next().toString();
			if(rFields.contains(value) && isNull(fields.get(value)))
			{
				errorInfo.put(value,NULL_POINTER);
			}
			else if(!isNull(fields.get(value)))
			{
				
				if(value.equals(getFFConstant().firstName))
				{
					checkNumeric(value,fields.get(getFFConstant().firstName).toString());
				}
				else if(value.equals(getFFConstant().lastName))
				{
					checkNumeric(value,fields.get(getFFConstant().lastName).toString());
				}
				else if(value.equals(getFFConstant().password))
				{
					checkPassword(value,fields.get(value).toString(),fields.get(getFFConstant().confirmPassword).toString());
				}
				else if(value.equals(getFFConstant().email))
				{
					checkValidEmail(value,fields.get(getFFConstant().email).toString());
				}
				
			}
		}
		
		
	}
	public void checkValidEmail(String key,String value)
	{
		Pattern emailPattern = Pattern
		.compile(getEmailPattern().toString());
		Matcher emailMatcher = emailPattern.matcher((String) value);
		boolean matchFound = emailMatcher.matches();
		if (!matchFound) {
			errorInfo.put(key,this.EMAIL_POINTER);
		}
	}
	public void checkPassword(String key,String password,String cPassword)
	{
		if(password.length()<6 || checkValidPassword(password))
		{
			errorInfo.put(key,this.PASSWORD_POINTER);
		}
		else if(!password.equals(cPassword))
		{
			errorInfo.put(key,this.PASSWORD_MATCH_POINTER);
		}
	}
	public boolean checkValidPassword(String password)
	{
		boolean value=false;
		int numeric=0,special=0,letter=0;
		for(int i=0;i<password.length();i++)
		{
			if(Character.isDigit(password.charAt(i)))
			{
				numeric++;
			}
			else if(Character.isLetter(password.charAt(i)))
			{
				letter++;
			}
			else
			{
				special++;
			}
		}
		if(numeric<2 || letter<2 || special<2)
		{
			value=true;
		}
		return value;
	}
	public void checkNumeric(String key,String value)
	{
		for(int i=0;i<value.length();i++)
		{
			if(Character.isDigit(value.charAt(i)))
			{
				errorInfo.put(key,this.NAME_POINTER);
				break;
			}
		}
	}
	public boolean isNull(Object value)
	{
		boolean val=true;
		if(value!=null && value.toString().trim().length()>0)
			val=false;
		return val;
	}
	public String getEmailPattern() {
		return emailPattern;
	}
	public void setEmailPattern(String emailPattern) {
		this.emailPattern = emailPattern;
	}
}
