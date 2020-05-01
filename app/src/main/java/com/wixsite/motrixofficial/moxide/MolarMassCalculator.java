package com.wixsite.motrixofficial.moxide;
import java.util.ArrayList;
import java.util.Hashtable;

public class MolarMassCalculator {
	
	
	
	public static double calculateMolarMass(String compound,ArrayList<Element> elem,ArrayList<Element>howmanyofeach)
	{
		String mul = getTheMultiplier(compound);
		int multiplier; 
		if(mul.equals(""))
		{
			multiplier = 1;
		}
		else 
		{
			multiplier = Integer.parseInt(getTheMultiplier(compound));
		}
		return recursionMolarMass(parseMultiplier(compound,mul),multiplier,1,1,elem,howmanyofeach,false,false);
	}
	
	
	public static boolean contains(ArrayList<Element> list,String str)
	{
		for(Element e:list)
		{
			if(e.getSymbol().equals(str))
			{
				return true;
			}
		}
		return false;
	}
	

	public static void put(ArrayList<Element> list,ArrayList<Element> elements,String str,int hm) 
	{
		boolean is = false;
		
		Element element = null ;
		
		for(Element e:elements)
		{
			if(e.getSymbol().equals(str))
			{
				element = e;
				break;
			}
		}
		
		
		if(element!=null)
		{
			for(Element e:list)
			{
				if(e.getSymbol().equals(str))
				{
					e.setHowMany(hm);
					is = true;
				}
			}
		}
		
		if(!is&&element!=null)
		{
			element.setHowMany(hm);
			list.add(element);
		}
		
	}
	public static int get(ArrayList<Element> list,String str)
	{
		int total = 0;
		for(Element e:list)
		{
			if(e.getSymbol().equals(str))
			{
				total += e.getHowMany();
			}
		}
		return total;
	}
	private static double getMolarMass(ArrayList<Element> list, String str)
	{
		for(Element e:list)
		{
			if(e.getSymbol().equals(str))
			{
				return Double.parseDouble(e.getAtomicMass());
			}
		}
		return -1;
	}
	
	
	
	
	
	
	public static String parseMultiplier(String compound,String multiplier)
	{
		return compound.substring(multiplier.length());
	}
	public static String getTheMultiplier(String compound)
	{
		if(compound.isEmpty())
		{
			return "";
		}
		else if(compound.charAt(0)=='(')
		{
			return getTheMultiplier(compound.substring(1));
		}
		else if(compound.charAt(0)>57||compound.charAt(0)<48)
		{
			return "";
		}
		return compound.charAt(0)+getTheMultiplier(compound.substring(1));
	}
	public static int getNumber(int right,String left)
	{
		String newNumber = left +""+ right;
		return Integer.parseInt(newNumber);
	}
	public static boolean isANumber(char c)
	{
		return (c<=57&&c>=48)?true:false;
	}
	public static boolean isALowerCase(char c)
	{
		return (c>=97&&c<=122)?true:false;
	}
	public static double recursionMolarMass(String compound, int multiplier,int suffix,int parenSuffix,ArrayList<Element> elements, ArrayList<Element> howManyOfEach,boolean hasNumber,boolean hasParen)
	{
		if(compound.isEmpty())
		{
			return 0;
		}
		else if(isANumber(compound.charAt(compound.length()-1)))
		{ 
			
			if(hasNumber)
			{
				suffix = getNumber(suffix,(compound.charAt(compound.length()-1)+""));
			}
			else {
				suffix = Integer.parseInt(compound.charAt(compound.length()-1)+"");
				hasNumber = true;
			}
			
			return recursionMolarMass(compound.substring(0, compound.length()-1),multiplier,suffix,parenSuffix,elements,howManyOfEach,hasNumber,hasParen);
		}
		else {
			hasNumber = false;
		}
		if(compound.endsWith(")"))
		{
			hasParen = true;
			parenSuffix *= suffix;
			suffix = 1;
			return  recursionMolarMass(compound.substring(0, compound.length()-1),multiplier,suffix,parenSuffix,elements,howManyOfEach,hasNumber,hasParen);
		}
		else if(compound.endsWith("("))
		{
			hasParen = false;
			parenSuffix = 1;
			return  recursionMolarMass(compound.substring(0, compound.length()-1),multiplier,suffix,parenSuffix,elements,howManyOfEach,hasNumber,hasParen);
		}
		
		if(isALowerCase(compound.charAt(compound.length()-1))&&compound.length()>1)
		{
			if(MolarMassCalculator.contains(howManyOfEach,compound.substring(compound.length()-2,compound.length())))
			{
				MolarMassCalculator.put(howManyOfEach,elements,(compound.substring(compound.length()-2,compound.length())), MolarMassCalculator.get(howManyOfEach,compound.substring(compound.length()-2,compound.length()))+(parenSuffix*suffix*multiplier));
			}
			else {
				MolarMassCalculator.put(howManyOfEach,elements,compound.substring(compound.length()-2,compound.length()), (parenSuffix*suffix*multiplier));
			}
			if(MolarMassCalculator.contains(elements,compound.substring(compound.length()-2,compound.length())))
			{
				return (parenSuffix*multiplier*suffix*MolarMassCalculator.getMolarMass(elements,(compound.substring(compound.length()-2,compound.length())))) + recursionMolarMass(compound.substring(0, compound.length()-2),multiplier,1,parenSuffix,elements,howManyOfEach,hasNumber,hasParen); 
			}
			else {
				return recursionMolarMass(compound.substring(0, compound.length()-2),multiplier,1,parenSuffix,elements,howManyOfEach,hasNumber,hasParen);
			}
			
		}
		
		if(MolarMassCalculator.contains(howManyOfEach,compound.substring(compound.length()-1,compound.length())))
		{
			MolarMassCalculator.put(howManyOfEach,elements,(compound.substring(compound.length()-1,compound.length())), MolarMassCalculator.get(howManyOfEach,compound.substring(compound.length()-1,compound.length()))+(parenSuffix*suffix*multiplier));
		}
		else {
			MolarMassCalculator.put(howManyOfEach,elements,compound.substring(compound.length()-1,compound.length()), (parenSuffix*suffix*multiplier));
		}
		
		
		
		
		
		if(MolarMassCalculator.contains(elements,(compound.charAt(compound.length()-1)+"")))
		{
			return (parenSuffix*multiplier*suffix*MolarMassCalculator.getMolarMass(elements,(compound.substring(compound.length()-1,compound.length())))) + recursionMolarMass(compound.substring(0, compound.length()-1),multiplier,1,parenSuffix,elements,howManyOfEach,hasNumber,hasParen); 
		}
		return recursionMolarMass(compound.substring(0, compound.length()-1),multiplier,1,parenSuffix,elements,howManyOfEach,hasNumber,hasParen);
		
		
		
		
		
	}
	
	public static double calculateMolarMass1(String compound,Hashtable<String,Double> elem)
	{
		String mul = getTheMultiplier(compound);
		int multiplier; 
		if(mul.equals(""))
		{
			multiplier = 1;
		}
		else 
		{
			multiplier = Integer.parseInt(getTheMultiplier(compound));
		}
		
		return recursionMolarMass1(compound,multiplier,1,elem);
	}
	public static double recursionMolarMass1(String compound, int multiplier,int suffix,Hashtable<String,Double> elements)
	{
		if(compound.isEmpty())
		{
			return 0;
		}
		else if(isANumber(compound.charAt(compound.length()-1)))
		{
			suffix = Integer.parseInt(compound.charAt(compound.length()-1)+"");
			return recursionMolarMass1(compound.substring(0, compound.length()-1),multiplier,suffix,elements);
		}
		
		if(isALowerCase(compound.charAt(compound.length()-1))&&compound.length()>1)
		{
			if(elements.containsKey(compound.substring(compound.length()-2,compound.length())))
			{
				return (multiplier*suffix*elements.get(compound.substring(compound.length()-2,compound.length()))) + recursionMolarMass1(compound.substring(0, compound.length()-2),multiplier,suffix,elements); 
			}
			return recursionMolarMass1(compound.substring(0, compound.length()-2),multiplier,suffix,elements); 
		}
		
		if(elements.containsKey((compound.charAt(compound.length()-1)+"")))
		{
			return (multiplier*suffix*elements.get(compound.charAt(compound.length()-1)+"")) + recursionMolarMass1(compound.substring(0, compound.length()-1),multiplier,1,elements);
		}
		return recursionMolarMass1(compound.substring(0, compound.length()-1),multiplier,1,elements);
		
	}
	
	
	public static String findFormula(String[] names,ArrayList<Element> elements)
	{
		String formulaFound = "";
		ArrayList<Element> foundElements = new ArrayList<>();
		
		for(int i = 0;i<names.length;i++)
		{
			for(Element e: elements)
			{
				char name1[] = e.getName().toCharArray();
				char name2[] = names[i].toCharArray();
				if(names[i].equals(e.getName()))
				{
					foundElements.add(e);
					break;
				}
			}
		}
		
		for(Element e:foundElements)
		{
			formulaFound+=e.toString()+"\n";
		}
		return formulaFound;
	}
	
	

}
