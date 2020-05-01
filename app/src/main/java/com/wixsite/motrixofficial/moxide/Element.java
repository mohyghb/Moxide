package com.wixsite.motrixofficial.moxide;

public class Element {
	
	//names
	private String name;
	private String symbol;
	
	//values
	private String atomicMass;
	private String atomicNumber;
	private String valenceElectron;
	private String electronegativity;
	private String density;//in g cm-3
	
	//properties
	private String meltingPoint;//C
	private String boilingPoint;//C
	
	//discovery
	private String yearDiscovered;
	private String discoveredBy;
	
	//place in periodic table
	private String group;
	private String period;
	private String block;
	
	//state
	private String stateAt20deg;
	private String type;
	
	private int howMany;
	
	Element(String n,
			String s,
			String am,
			String an,
			String ve,
			String en,
			String den,
			String mp,
			String bp,
			String yd,
			String dby,
			String g,
			String p,
			String blk,
			String stateAt20,
			String t)
	{
		this.name = n;
		this.symbol = s;
		this.atomicMass = am;
		this.atomicNumber = an;
		this.valenceElectron = ve;
		this.electronegativity = en;
		this.density = den;
		this.meltingPoint = mp;
		this.boilingPoint = bp;
		this.yearDiscovered = yd;
		this.discoveredBy = dby;
		this.group = g;
		this.period = p;
		this.block = blk;
		this.stateAt20deg = stateAt20;
		this.type = t;
		this.howMany = 1;
	}
	Element(String line)
	{
		this.readLine(line);
	}
	Element(String s,int howmany)
	{
		this.symbol = s;
		this.howMany = howmany;
	}
	
	
	//getters
	public String getName()
	{
		return this.name;
	}
	//get formula name;
	public String getSymbol()
	{
		return this.symbol;
	}
	public String getAtomicMass()
	{ 
		return this.atomicMass;
	}
	public String getAtomicNumber()
	{
		return this.atomicNumber;
	}
	public String getValenceElectron()
	{
		return this.valenceElectron;
	}
	public String getElectronegativity()
	{
		return this.electronegativity;
	}
	public String getDensity()
	{
		return this.density;
	}
	public String getMeltingPoint()
	{
		return this.meltingPoint;
	}
	public String getBoilingPoint()
	{
		return this.boilingPoint;
	}
	public String getYearDisscovered()
	{
		return this.yearDiscovered;
	}
	public String getDiscoveredBy()
	{
		return this.discoveredBy;
	}
	public String getGroup()
	{
		return this.group;
	}
	public String getPeriod()
	{
		return this.period;
	}
	public String getBlock()
	{
		return this.block;
	}
	public String getStateAt20deg()
	{
		return this.stateAt20deg;
	}
	public String getType()
	{
		return this.type;
	}
	public String toString()
	{
		return name+","+this.symbol+","+this.atomicMass+","+this.boilingPoint;
	}
	
	
	public void readLine(String line)
	{
		String s[] = line.split("\\|");
		if(s.length==16)
		{
			this.name = removeSpace(s[0]);
			this.symbol = removeSpace(s[1]);
			this.atomicMass = removeSpace(s[2].toLowerCase());
			this.atomicNumber = removeSpace(s[3].toLowerCase());
			this.valenceElectron = removeSpace(s[4].toLowerCase());
			this.electronegativity = removeSpace(s[5].toLowerCase());
			this.density = removeSpace(s[6].toLowerCase());
			this.meltingPoint = removeSpace(s[7].toLowerCase());
			this.boilingPoint = removeSpace(s[8].toLowerCase());
			this.yearDiscovered = removeSpace(s[9].toLowerCase());
			this.discoveredBy = removeSpace(s[10].toLowerCase());
			this.group = removeSpace(s[11].toLowerCase());
			this.period = removeSpace(s[12].toLowerCase());
			this.block = removeSpace(s[13].toLowerCase());
			this.stateAt20deg = removeSpace(s[14].toLowerCase());
			this.type = removeSpace(s[15].toLowerCase());

		}
	}
	public String removeSpace(String str)
	{
		if(str.isEmpty())
		{
			return str;
		}
		else if(str.charAt(0)==' '||str.charAt(0)=='?')
		{
			return removeSpace(str.substring(1));
		}
		return str.charAt(0)+str.substring(1);
	}
	
	
	public int getHowMany()
	{
		return this.howMany;
	}
	public void setHowMany(int hm)
	{
		this.howMany = hm;
	}
	

	public String getFormattedMolarMass()
	{
		String formatted = String.format("%d %s = %f",this.howMany,this.symbol,this.howMany*Double.parseDouble(this.atomicMass));
		return formatted;
	}

	public String percentageMassComposition()
	{
		double pc = (this.howMany*Double.parseDouble(this.atomicMass)/MainActivity.curMolarMass)*100;
		String mc = String.format("Percentage composition: %f percent", pc);
		return mc;
	}
	
	
	
	

}
