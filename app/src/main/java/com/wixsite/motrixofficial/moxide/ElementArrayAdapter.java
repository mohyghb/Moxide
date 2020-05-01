package com.wixsite.motrixofficial.moxide;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ElementArrayAdapter extends ArrayAdapter<Element> {

    private Context context;
    private List<Element> elements;


    public ElementArrayAdapter(Context context, int resource, ArrayList<Element> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.elements = objects;
    }

    //called when rendering the list
    public View getView(final int position, final View convertView, ViewGroup parent) {

        //get the property we are displaying
        Element element = elements.get(position);

        //get the inflater and inflate the XML layout for each item

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.element_layout_listview, null);

        TextView name,symbol,atomicMass,atomicNumber,valenceElectron,electronegativity,density,meltingPoint,boilingPoint,yearDiscovered,
        discoveredBy,group,period,block,stateAt20deg,type,PercentageComposition;

        name = view.findViewById(R.id.name_of_element);
        symbol = view.findViewById(R.id.Symbol);
        atomicMass = view.findViewById(R.id.AtomicMass);
        atomicNumber = view.findViewById(R.id.AtomicNumber);
        valenceElectron = view.findViewById(R.id.ValenceElectrons);
        electronegativity = view.findViewById(R.id.electronegativity);
        density = view.findViewById(R.id.density);
        meltingPoint = view.findViewById(R.id.MeltingPoint);
        boilingPoint = view.findViewById(R.id.BoilingPoint);
        yearDiscovered = view.findViewById(R.id.YearDiscovered);
        discoveredBy = view.findViewById(R.id.DiscoveredBy);
        group = view.findViewById(R.id.Group);
        period = view.findViewById(R.id.Period);
        block = view.findViewById(R.id.Block);
        stateAt20deg = view.findViewById(R.id.State);
        type = view.findViewById(R.id.Type);
        PercentageComposition = view.findViewById(R.id.PercentageComposition);


        name.setText(element.getName());
        symbol.setText(element.getSymbol());
        atomicMass.setText("Atomic mass: "+element.getAtomicMass()+" g/mol");
        atomicNumber.setText("Atomic number: "+element.getAtomicNumber());
        valenceElectron.setText("Valence Electrons: "+element.getValenceElectron());
        electronegativity.setText("Electronegativity: "+element.getElectronegativity());
        density.setText("Density: "+element.getDensity());
        meltingPoint.setText("Melting Point: "+element.getMeltingPoint());
        boilingPoint.setText("Boiling Point: "+element.getBoilingPoint());
        yearDiscovered.setText("Year Discovered: "+element.getYearDisscovered());
        discoveredBy.setText("Discovered By: "+element.getDiscoveredBy());
        group.setText("Group: "+element.getGroup());
        period.setText("Period: "+element.getPeriod());
        block.setText("Block: "+element.getBlock());
        stateAt20deg.setText("State at 20°: "+element.getStateAt20deg());
        type.setText("Type: "+element.getType());
        PercentageComposition.setText(element.percentageMassComposition());



        //showing the cards with a fade animation
//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_in);
//        view.startAnimation(animation);

        return view;
    }




}