package com.detection.multiBit;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

public class Detection {
	public static BitSet taintedBitSet = new BitSet();
    public static Map <String, String> intrestvar = new HashMap <String, String>();
    public static List taintedSet = new ArrayList <Integer>();
    public Stack newPath = new Stack();
    public void taint(TaintSet ts) {
        ts.istainted = true;
    }
    public boolean isTainted(TaintSet ts) {
        return ts.istainted;
    }
    public static String firstBit;
    public static String lastBit;
    public static boolean flag;
    public static LinkedList <String> LPPath = new LinkedList <String> ();
   

    public static void main(String[] args1, String[] args2) throws IOException {
    	
    	initialize(args1,args2);
    	
        taintTheVariables();
        
        checkEvasion();
       
    } //main complete
 
    public static void initialize(String[] args1, String[] args2) throws IOException {
    	
    	//taintedBitSet=args3;
        firstBit = args1[0];
        lastBit = args1[(args1.length)-1];
        taintedSet.add(firstBit);
      
        for (int i=0; i < args1.length; i++) {
        	System.out.println(args1[i]);
        	int flagv=0;
        	for(int j=0; j < args2[i].length(); j++) {
        		if(args2[i].charAt(j)=='1' || args2[i].charAt(j)=='0')
        		{
        			//System.out.print(" EQ ");
        			flagv=0;
        		}
        		else
        		{
        			flagv=1;
        			break;
        		}
        		
        	}
        	if(flagv==0) {
        		for(int k=0;k<args2[i].length();k++)
        		taintedBitSet.set(k);
        	}
        	
            intrestvar.put(args1[i], args2[i]);        	
        }
        System.out.println("taintedBitSet+="+taintedBitSet);
        intrestvar.put("", "");
       // Place to detect single-bit or multi-bit
        
     
        
        Map<String,String> returnedEdges = Detection.controlFlow("E:\\PROJECT ALL\\luna ws\\Multi-Bit_Detection\\src\\com\\detection\\multiBit\\TechniqueOne.java", intrestvar);
        
        Detection graph = new Detection();
        for (Map.Entry<String,String> entry : returnedEdges.entrySet()) {
        	graph.addEdge(entry.getKey(), entry.getValue());
        }
        LinkedList <String> visited = new LinkedList();
        visited.add(firstBit);
       
        new Detection().breadthFirst(graph, visited);
        
    }
    
    public static void checkEvasion() {
    	 if(taintedBitSet.get(0) && taintedBitSet.get((taintedBitSet.length()-1))){
    	        //if (taintedSet.contains(firstBit) && taintedSet.contains(lastBit)) {
    	        	System.out.println("\nFirst and last bits are found to be tainted.");
    	            System.out.println("Thus,this path is detected malicious with leakage from "+firstBit+" to "+lastBit+".");
    	        }
    }
    public static void taintTheVariables() {
    	 int flagbit = 0;
         int prevtaint = 0;
    	for (String temp: LPPath) {
    		if (intrestvar.get(temp).matches("[01]+") && flagbit == 1 && prevtaint == 1) {
                if (!taintedSet.contains(temp) && temp != "")
                    taintedSet.add(temp);
            }
            if (temp == "") {
                flagbit = 0;
                continue;
            } else {
                if (taintedSet.contains(temp)) {
                    prevtaint = 1;
                } else {
                	prevtaint = 0;
                }
                flagbit = 1;
            }
        }
        System.out.println("\nList of tainted variables : " + taintedSet);
    }
   
    public static Map<String, String> controlFlow(String filePath, Map <String, String> interestVariable) throws IOException {
    	   	
    	Map <String, String> edges = new HashMap <String, String>();
        FileReader fileReader =  new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while((line = bufferedReader.readLine()) != null) {
        	
        	for (Map.Entry<String,String> entry : interestVariable.entrySet()) {
        		String curruntKey = entry.getKey();
    			if(curruntKey != "" && line.contains(" = " + curruntKey)) {
    				String patternstr = "(\\w*)(\\s*)(\\%*)(\\/*)(\\**)(\\+*)(\\-*)=";
    				Pattern pattern = Pattern.compile(patternstr);
    				Matcher matcher = pattern.matcher(line);
    				String spaceRemovedcurruntValue = "";
    				if (matcher.find()) {
    					spaceRemovedcurruntValue = matcher.group().replaceAll("\\s", "");
    			    }
    				String curruntValue = spaceRemovedcurruntValue.substring(0,spaceRemovedcurruntValue.length() - 1);
    				if(interestVariable.containsKey(curruntValue)) {
    				//	System.out.println("CC"+curruntKey+"CC"+curruntValue);
    					edges.put(curruntKey, curruntValue);
    				}
    			}
        	}
        }
        bufferedReader.close();
        System.out.println("\n\nCFEdges : "+edges);
        edges =  dataFlow(edges,interestVariable);
       
        printTaintedBits();
        
        System.out.println("\n\nEdges : "+edges);
        return edges;
    }
    
    
    public static Map<String, String> dataFlow( Map<String, String> controlValues, Map<String, String> interestVariable ) {
    	String k_valueobtained = null,v_valueobtained=null;
		String k_keyobtained= null,v_keyobtained=null;
		String bfr_k= null,bfr_v=null;
		String key = null,val = null;
		
    	for(Map.Entry<String,String> cv : controlValues.entrySet())
    	{    		 key=cv.getKey();
			 val=cv.getValue();
			
			
			
			for(Map.Entry<String,String> ivmatch : interestVariable.entrySet()) {
				
				String k=ivmatch.getKey();
				String v=ivmatch.getValue();
				
				if(k.equals(key)) {
					k_keyobtained = k;
					k_valueobtained = v;
					bfr_k=k;
					
  				}
				else if(k.equals(val)) {
					v_keyobtained=k;
					v_valueobtained = v;
					bfr_v=v;
				}
			}
    	}
    	
    	int size=0;
    	String[] varlist=new String[50];
    	for(Map.Entry<String,String> entry : interestVariable.entrySet()) ////all variables
    	{
    		String currentIntrestVariable=entry.getKey();
    		//System.out.println("keyyyyyyyyyyyyyyy"+currentIntrestVariable);
    		String currentIntrestVariableVal=entry.getValue();

    		for(Map.Entry<String,String> cv : controlValues.entrySet())
    		{
    			
    			
    			String buffer = "";
    			
    			String regex = "\\d+";
    			if(!currentIntrestVariable.equals(key) && !currentIntrestVariable.equals(val) && !val.matches(regex) && !((currentIntrestVariable.trim()).equals("")) && !currentIntrestVariable.equals(null)) {
    				
    				  				
    				
    				
    				if(k_valueobtained.contains(currentIntrestVariableVal))//changes must be made for partition
    				{
    					buffer=k_valueobtained;
    					
    					k_valueobtained=k_valueobtained.replaceFirst(currentIntrestVariableVal, "");
    					varlist[size]=currentIntrestVariable;
    					size++;
    					//buffer = buffer.concat(k_valueobtained);
    					//if (currentIntrestVariableVal.equals(buffer)){
    					
    					if(k_valueobtained.equals("")) {	
    						String str=" ";
    						for(int i=0;i<size;i++) {
    							str = str.concat(varlist[i]+",");
    						}
    						str = str.substring(0,str.length()-1);
    						controlValues.put(k_keyobtained, str.trim());
        					varlist= null;size=0;
    						buffer=k_valueobtained;
    					}
    					
    				}
    				else if(v_valueobtained.contains(currentIntrestVariableVal))
    				{
    					buffer=v_valueobtained;
    					
    					v_valueobtained=v_valueobtained.replaceFirst(currentIntrestVariableVal, "");
    					varlist[size]=currentIntrestVariable;
    					size++;
    				//	buffer = buffer.concat(v_valueobtained);
    					
    					if (v_valueobtained.equals("")){
    						String str=" ";
    						for(int i=0;i<size;i++) {
    							str = str.concat(varlist[i]+",");
    						}
    						str = str.substring(0,str.length()-1);
    						controlValues.put(v_keyobtained, str.trim());
        						varlist= null;size=0;
    						
    						buffer=v_valueobtained;
    					}
    					
    				}
    				
    			}
    		}
    		
    	}
    	return controlValues;
    }
   
    private Map <String, LinkedHashSet <String>> map = new HashMap();
    public void addEdge(String node1, String node2) {
        LinkedHashSet <String> adjacent = map.get(node1);
        if (adjacent == null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }
    public void addTwoWayVertex(String node1, String node2) {
        addEdge(node1, node2);
        addEdge(node2, node1);
    }
    public boolean isConnected(String node1, String node2) {
        Set adjacent = map.get(node1);
        if (adjacent == null) {
            return false;
        }
        return adjacent.contains(node2);
    }
    public LinkedList <String> adjacentNodes(String last) {
        LinkedHashSet <String> adjacent = map.get(last);
        if (adjacent == null) {
            return new LinkedList();
        }
        return new LinkedList <String> (adjacent);
    }
    public int count = 0;
    private void breadthFirst(Detection graph,
        LinkedList <String> visited) {
        LinkedList <String> nodes = graph.adjacentNodes(visited.getLast());
        for (String node: nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.contains(lastBit)) {
                visited.add(node);
                printPath(visited);
                flag = true;
                visited.removeLast();
                break;
            }
        }
        for (String node: nodes) {
            if (visited.contains(node) || node.contains(lastBit)) {
                continue;
            }
            visited.addLast(node);
            breadthFirst(graph, visited);
            visited.removeLast();
        }
        if (flag == false) {
            System.out.println("No path exists between " + firstBit + " and " + lastBit);
            flag = true;
        }
    }
    private void printPath(LinkedList <String> visited) {
        if (flag == false)
            System.out.println("\nYes, path exists between " + firstBit + " and " + lastBit);
        String temp = "";
        for (String node: visited) {
            System.out.print(node);
            temp = node;
            if(temp.contains(",")) 
            	{String[] values = temp.split(",");
            	for (String var : values) 
            	{ 
            		LPPath.add(var);
            	}
            	
            	}
            else{LPPath.add(temp);}
            System.out.print(" -> ");
        }
        System.out.println("Leakage");
        LPPath.add("");
        count++;
    }
    public static void printTaintedBits() {
    	//print tainted bits
        for (int j=0;j<taintedBitSet.length();j++) {
        	boolean value = taintedBitSet.get(j);
        	if(value==true) {
            System.out.print("   Tainted Bit No. : "+j+" is Tainted. ");
        	}
        	else if(value==false) {
                System.out.print("   Bit No. : "+j+" is not Tainted. ");
            	}
            //i++;
            if((j+1)%3 == 0)
            {System.out.println();}
        }
    }
    
}


class TaintSet {
    String name, value;
    Boolean istainted, isintrest;
    public TaintSet(String name, String value) {
        this.isintrest = true;
        this.istainted = false;
        this.name = name;
        this.value = value;
    }
}