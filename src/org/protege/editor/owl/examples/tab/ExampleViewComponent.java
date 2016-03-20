package org.protege.editor.owl.examples.tab;

import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;

import org.protege.editor.owl.model.hierarchy.AssertedClassHierarchyProvider;
import org.protege.editor.owl.ui.tree.OWLModelManagerTree;
import org.protege.editor.owl.ui.tree.OWLObjectTree;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;	
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.hierarchy.*;

import org.semanticweb.owlapi.model.OWLEntity;
import org.protege.editor.owl.model.selection.OWLSelectionModel;
import org.protege.editor.owl.model.selection.OWLSelectionModelListener;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.lang.Math;
public class ExampleViewComponent extends AbstractOWLViewComponent implements ActionListener{
    private static final long serialVersionUID = -4515710047558710080L;
    
	private JTextField firstClass,secondClass;
	private JButton clear,measure;
	private JTextArea reasons;
	private JComboBox<String> measureList;
	private OWLSelectionModel selectionModel;
	private OWLSelectionModelListener listener = new OWLSelectionModelListener() {
		@Override
		public void selectionChanged() throws Exception {
			OWLClass entity = getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass();
			updateView(entity);
		}
	};
	
	private OWLClass classOne,classTwo;
	
	
	
	
	public void WuPalmer(){
		OWLHierarchyManager managerH = getOWLModelManager().getOWLHierarchyManager();
		OWLObjectHierarchyProvider<OWLClass> providerH = managerH.getOWLClassHierarchyProvider() ;
		Set<OWLClass> firstAnc = new HashSet<OWLClass>();
		firstAnc.add(classOne);
		Set<OWLClass> secondAnc = new HashSet<OWLClass>();
		secondAnc.add(classTwo);
		Set<OWLClass> firstAnc2 = providerH.getAncestors(classOne);
		firstAnc.addAll(firstAnc2);
		Set<OWLClass> secondAnc2 = providerH.getAncestors(classTwo);
		secondAnc.addAll(secondAnc2);
		int firstMax = firstAnc.size();
		int secondMax = secondAnc.size();
		OWLClass[] firstArray = firstAnc.toArray(new OWLClass[firstMax]);
		OWLClass[] secondArray = secondAnc.toArray(new OWLClass[secondMax]);
		List<OWLClass> firstList = Arrays.asList(firstArray);
		List<OWLClass> secondList = Arrays.asList(secondArray);
		
		List<OWLClass> commons = new ArrayList<OWLClass>();
		int maxF,maxS;
		maxF = firstList.size();
		maxS = secondList.size();
		
		for (int x=0 ; x<maxF;x++){
			for (int y=0; y<maxS;y++){
				if (firstList.get(x) == secondList.get(y))
					commons.add(firstList.get(x));
		}}
		

		// first has non common
		// second has common
		//System.out.println("\n\n\n"+commons.toString() +"\n\n\n");

		
		int common = commons.size()-1; // -1 represents the IS-A links 
		int newMax = (maxF - commons.size())+(maxS - commons.size());
		
		
		
		boolean change = false;
		// if no common set to 1
		String input ="The Wu & Palmer measure is the simplest out of \nthe hierarchically similarity measures "+
			"it is a \nvery simple formula that noly uses the common links and \nnon common links to create a value "+
			"between 0 and 1, \nin this measure the closer two classes are to each other \nthe more similar they are.\n\n";
		if (common == 0){common =1;
		change = true;}
		// wu and palmer formula
		double measureValue = (2.0*common)/(newMax+(2.0*common));		

		if (change){common = 0;}
		input += "Wu & Palmer measure: "+measureValue+"\nCommon Subsumers: "+commons.size()+ ": \n" ;
		for (int x = 0; x<commons.size(); x++){
				input += getOWLModelManager().getRendering(commons.get(x))+ ", ";
		}
		input += "\nMost Specific Subsumer: " + getOWLModelManager().getRendering(commons.get(commons.size()-1));
		reasons.setText(input);
		
	}
	
	public void LiEtAl(){
		double aplha = 0.2;
		double beta = 0.6;
		
		OWLHierarchyManager managerH = getOWLModelManager().getOWLHierarchyManager();
		OWLObjectHierarchyProvider<OWLClass> providerH = managerH.getOWLClassHierarchyProvider() ;
		Set<OWLClass> firstAnc = new HashSet<OWLClass>();
		firstAnc.add(classOne);
		Set<OWLClass> secondAnc = new HashSet<OWLClass>();
		secondAnc.add(classTwo);
		Set<OWLClass> firstAnc2 = providerH.getAncestors(classOne);
		firstAnc.addAll(firstAnc2);
		Set<OWLClass> secondAnc2 = providerH.getAncestors(classTwo);
		secondAnc.addAll(secondAnc2);
		int firstMax = firstAnc.size();
		int secondMax = secondAnc.size();
		OWLClass[] firstArray = firstAnc.toArray(new OWLClass[firstMax]);
		OWLClass[] secondArray = secondAnc.toArray(new OWLClass[secondMax]);
		List<OWLClass> firstList = Arrays.asList(firstArray);
		List<OWLClass> secondList = Arrays.asList(secondArray);
		
		List<OWLClass> commons = new ArrayList<OWLClass>();
		int maxF,maxS;
		maxF = firstList.size();
		maxS = secondList.size();
		
		for (int x=0 ; x<maxF;x++){
			for (int y=0; y<maxS;y++){
				if (firstList.get(x) == secondList.get(y))
					commons.add(firstList.get(x));
		}}
		
		List<OWLClass> pathLength = new ArrayList<OWLClass>();
		int noOfCommons;
		for (int x=0 ; x<maxF;x++){
			noOfCommons = 0;
			for (int y=0; y<commons.size();y++){
				if (firstList.get(x) == commons.get(y) )
					noOfCommons++;
			}
		if (noOfCommons == 0)
			pathLength.add(firstList.get(x));
		}
		
		for (int x=0 ; x<maxS;x++){
			noOfCommons = 0;
			for (int y=0; y<commons.size();y++){
				if (secondList.get(x) == commons.get(y) )
					noOfCommons++;
			}
		if (noOfCommons == 0)
			pathLength.add(secondList.get(x));
		}
		
		double H = commons.size() -1; // convert into IS-A relation number
		double L = pathLength.size();
		boolean change = false;
		// if no common set to 1
		String input ="Li et al's similarity measure is a hierarchical similaity\n measure that makes use of the e "+
			"function, depth of the \nontology and shortest path between two classes. It must be said \nthat when "+
			"choosing classes try not to choose those \nthat have multiple instances of the same name as the path "+
			"it \nfinds will be longer than wanted.\n\n";
		if (H == 0){H =1;
		change = true;}
		double measureValue = Math.pow(Math.E,(-1.0*aplha*L));
		double numerator = Math.pow(Math.E,beta*H)-Math.pow(Math.E,(-1.0*beta*H));
		double demoninator = Math.pow(Math.E,beta*H) + Math.pow(Math.E,(-1*beta*H));
		double fraction = numerator/ demoninator;
		measureValue *= fraction;
		
		
		if (change){H = 0;}
		input += "Li et al measure: "+measureValue+"\nCommon Subsumers: "+commons.size()+ ": \n" ;
		for (int x = 0; x<commons.size(); x++){
				input += getOWLModelManager().getRendering(commons.get(x))+ ", ";
		}
		input += "\nMost Specific Subsumer: " + getOWLModelManager().getRendering(commons.get(commons.size()-1))+"\n";
		input += "Length of Shortest Path: " + L + "\n";
		reasons.setText(input);
		
	}
	
	
	public void LeacockChodorow(){
		OWLHierarchyManager managerH = getOWLModelManager().getOWLHierarchyManager();
		OWLObjectHierarchyProvider<OWLClass> providerH = managerH.getOWLClassHierarchyProvider() ;
		Set<OWLClass> firstAnc = new HashSet<OWLClass>();
		firstAnc.add(classOne);
		Set<OWLClass> secondAnc = new HashSet<OWLClass>();
		secondAnc.add(classTwo);
		Set<OWLClass> firstAnc2 = providerH.getAncestors(classOne);
		firstAnc.addAll(firstAnc2);
		Set<OWLClass> secondAnc2 = providerH.getAncestors(classTwo);
		secondAnc.addAll(secondAnc2);
		int firstMax = firstAnc.size();
		int secondMax = secondAnc.size();
		OWLClass[] firstArray = firstAnc.toArray(new OWLClass[firstMax]);
		OWLClass[] secondArray = secondAnc.toArray(new OWLClass[secondMax]);
		List<OWLClass> firstList = Arrays.asList(firstArray);
		List<OWLClass> secondList = Arrays.asList(secondArray);
		
		List<OWLClass> commons = new ArrayList<OWLClass>();
		int maxF,maxS;
		maxF = firstList.size();
		maxS = secondList.size();
		for (int x=0 ; x<maxF;x++){
			for (int y=0; y<maxS;y++){
				if (firstList.get(x) == secondList.get(y))
					commons.add(firstList.get(x));
		}}
		
		List<OWLClass> pathLength = new ArrayList<OWLClass>();
		int noOfCommons;
		for (int x=0 ; x<maxF;x++){
			noOfCommons = 0;
			for (int y=0; y<commons.size();y++){
				if (firstList.get(x) == commons.get(y))
					noOfCommons++;
			}
		if (noOfCommons == 0)
			pathLength.add(firstList.get(x));
		}
		for (int x=0 ; x<maxS;x++){
			noOfCommons = 0;
			for (int y=0; y<commons.size();y++){
				if (secondList.get(x) == commons.get(y) )
					noOfCommons++;
			}
		if (noOfCommons == 0)
			pathLength.add(secondList.get(x));
		}
		int depth = recursiveDepth(providerH,commons.get(0),0);
		System.out.println(pathLength.size());
		double measureValue = -1.0 * Math.log(pathLength.size()/(2.0*depth));
		String input ="Leacock & Chodorow's similirity measure is a hierarchical similarity measure\n" +
			"it uses the negative log to smooth out the result and unlike \nmost of the measure it is not" +
			"based between 0 and 1, with this measure \nthe larger the value the more similar they are."+
			"\nThis measure is not designed to be used comparing the\n same class, please be wary.\n\n";
		input += "Leacock & Chodorow measure: "+measureValue+"\nCommon Subsumers: "+commons.size()+ ": \n" ;
		for (int x = 0; x<commons.size(); x++){
				input += getOWLModelManager().getRendering(commons.get(x))+ ", ";
		}
		input += "\nMost Specific Subsumer: " + getOWLModelManager().getRendering(commons.get(commons.size()-1))+"\n";
		input += "Length of Shortest Path: " + pathLength.size() + "\n";
		input += "Depth of Ontology: " + depth + "\n";
		reasons.setText(input);
		
	}
	
	public int recursiveDepth(OWLObjectHierarchyProvider<OWLClass> providerH, OWLClass owlClass,int depth){
		int newdepth =-1;
		Set<OWLClass> depthSearch = providerH.getChildren(owlClass);
		OWLClass[] depthHelp = depthSearch.toArray(new OWLClass[depthSearch.size()]);
		List<OWLClass> depthHelp2 = Arrays.asList(depthHelp);
		//System.out.println(depthHelp2.toString()+"   "+depth);
		for (int x =0;x<depthHelp2.size();x++){
			int temp = recursiveDepth(providerH,depthHelp2.get(x),depth+1);
			if ( temp > newdepth)
				newdepth = temp;
		}
		if (newdepth > depth)
			return newdepth;
		else
			return depth;
	}
	
	public void MaoEtAl(){
		double gamma = 0.9;// contant values given trough paper
				OWLHierarchyManager managerH = getOWLModelManager().getOWLHierarchyManager();
		OWLObjectHierarchyProvider<OWLClass> providerH = managerH.getOWLClassHierarchyProvider() ;
		Set<OWLClass> firstAnc = new HashSet<OWLClass>();
		firstAnc.add(classOne);
		Set<OWLClass> secondAnc = new HashSet<OWLClass>();
		secondAnc.add(classTwo);
		Set<OWLClass> firstAnc2 = providerH.getAncestors(classOne);
		firstAnc.addAll(firstAnc2);
		Set<OWLClass> secondAnc2 = providerH.getAncestors(classTwo);
		secondAnc.addAll(secondAnc2);
		int firstMax = firstAnc.size();
		int secondMax = secondAnc.size();
		OWLClass[] firstArray = firstAnc.toArray(new OWLClass[firstMax]);
		OWLClass[] secondArray = secondAnc.toArray(new OWLClass[secondMax]);
		List<OWLClass> firstList = Arrays.asList(firstArray);
		List<OWLClass> secondList = Arrays.asList(secondArray);
		
		List<OWLClass> commons = new ArrayList<OWLClass>();
		int maxF,maxS;
		maxF = firstList.size();
		maxS = secondList.size();
		for (int x=0 ; x<maxF;x++){
			for (int y=0; y<maxS;y++){
				if (firstList.get(x) == secondList.get(y))
					commons.add(firstList.get(x));
		}}
		
		List<OWLClass> pathLength = new ArrayList<OWLClass>();
		int noOfCommons;
		for (int x=0 ; x<maxF;x++){
			noOfCommons = 0;
			for (int y=0; y<commons.size();y++){
				if (firstList.get(x) == commons.get(y))
					noOfCommons++;
			}
		if (noOfCommons == 0)
			pathLength.add(firstList.get(x));
		}
		for (int x=0 ; x<maxS;x++){
			noOfCommons = 0;
			for (int y=0; y<commons.size();y++){
				if (secondList.get(x) == commons.get(y) )
					noOfCommons++;
			}
		if (noOfCommons == 0)
			pathLength.add(secondList.get(x));
		}
		
		Set<OWLClass> firstDescen = providerH.getDescendants(classOne) ;
		Set<OWLClass> secondDescen = providerH.getDescendants(classTwo) ;

		OWLClass[] firstDescen2 = firstDescen.toArray(new OWLClass[firstDescen.size()]);
		OWLClass[] secondDescen2 = secondDescen.toArray(new OWLClass[secondDescen.size()]);
		
		double path = pathLength.size();
		double logBase2 = Math.log(1.0+(double)firstDescen2.length)/Math.log(2.0);
		double measureValue = gamma /((path*logBase2) + (double)secondDescen2.length);
		
		String input ="The Mao et al similarity measure uses the descendents of \nthe classes picked to"+
			" claclaution how hierarchically\n similar they are. Because of this it is best used on a large\n"+
			"ontology with lots of descendents. When choosing two classes\n with no children the output is"+
			"infinate.\n\n";
		input += "Mao et al measure: "+measureValue+"\nCommon Subsumers: "+commons.size()+ ": \n" ;
		for (int x = 0; x<commons.size(); x++){
				input += getOWLModelManager().getRendering(commons.get(x))+ ", ";
		}
		input += "\nMost Specific Subsumer: " + getOWLModelManager().getRendering(commons.get(commons.size()-1))+"\n";
		input += "Length of Shortest Path: " + path + "\n";
		input += "Number of Descendants of "+firstClass.getText()+": " + firstDescen2.length+"\n";
		input += "Number of Descendants of "+secondClass.getText()+": " + secondDescen2.length+"\n";
		reasons.setText(input);
		}
	
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == clear){
			firstClass.setText("");
			secondClass.setText("");
			reasons.setText("");
			classOne = null;
			classTwo = null;
		}
		if (e.getSource() == measure){
			String value;
			String value2;
			
			switch (measureList.getSelectedIndex()){
				case 0 : reasons.setText("Please choose a measure fromthe drop down list");
				break;
				case 1 : value = firstClass.getText().toString();
						 value2 = secondClass.getText().toString();
					if (value == null || firstClass.getText().equals("") || value2 == null || secondClass.getText().equals(""))
					{	break;}
						this.WuPalmer();
				break;
				case 2 : value = firstClass.getText().toString();
						 value2 = secondClass.getText().toString();
					if (value == null || firstClass.getText().equals("") || value2 == null || secondClass.getText().equals(""))
					{	break;}
						this.LiEtAl();
				break;
				case 3 :value = firstClass.getText().toString();
						 value2 = secondClass.getText().toString();
					if (value == null || firstClass.getText().equals("") || value2 == null || secondClass.getText().equals(""))
					{	break;}
						LeacockChodorow();
				break;
				case 4 :value = firstClass.getText().toString();
						 value2 = secondClass.getText().toString();
					if (value == null || firstClass.getText().equals("") || value2 == null || secondClass.getText().equals(""))
					{	break;}
						MaoEtAl();
				break;
				default :break;
			}
		}
	}
	
    @Override
    protected void initialiseOWLView() throws Exception {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		selectionModel = getOWLWorkspace().getOWLSelectionModel();
		selectionModel.addListener(listener);
		JLabel label = new JLabel("First Class:");
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(label, c);
		firstClass = new JTextField(20); 
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		add(firstClass, c);
		label = new JLabel("Second Class:");
		c.weightx = 2;
		c.gridx = 0;
		c.gridy = 1;
		add(label, c);
		secondClass = new JTextField(20); 
		c.weightx = 2;
		c.gridx = 1;
		c.gridy = 1;
		add(secondClass, c);
		
		String[] measures = {"Similairty Measures","Wu & Palmer","Li et al","Leacock & Chodorow","Mao et al"};
		measureList = new JComboBox<String>(measures);
		c.weightx = 0;
		c.gridx = 3;
		c.gridy = 0;
		add(measureList, c);
		//measureList.addActionListener(this);
		
		reasons = new JTextArea(10,30);
		JScrollPane scroll = new JScrollPane ( reasons );
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		c.weightx = 0;
		c.gridx = 3;
		c.gridy = 1;
		add(scroll, c);
		
		clear = new JButton("Clear Values");
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 2;
		add(clear, c);
		clear.addActionListener(this);
		measure = new JButton("Create Measure");
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 2;
		add(measure, c);
		measure.addActionListener(this);
		}

		@Override
	protected void disposeOWLView() {
		selectionModel.removeListener(listener);
	}
	
	private void updateView(OWLClass e) {
		if (e != null) {
			String value = firstClass.getText().toString();
			if (value == null || firstClass.getText().equals("")){
				firstClass.setText(getOWLModelManager().getRendering(e));
				classOne = e;
			}
			else {
				value = secondClass.getText().toString();
				if (value == null || secondClass.getText().equals("")){
					secondClass.setText(getOWLModelManager().getRendering(e));
					classTwo = e;
				}
			}
		}
	}
	
	
}
