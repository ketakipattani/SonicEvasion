package com.detection.multiBit;


import java.lang.reflect.Field;
import java.util.BitSet;

public aspect Monitor {
	
	pointcut techniqueOneStart() : call (* TechniqueOne.startTechnique());
	pointcut techniqueTwoStart() : call (* TechniqueTwo.startTechnique());
	pointcut techniqueThreeStart() : call (* TechniqueThree.startTechnique());
	pointcut SVPS_MultibitStart() : call (* SVPS_Multibit.startTechnique());
	pointcut ERE_MultibitStart() : call (* ERE_Multibit.startTechnique());
	pointcut E2P_MultibitStart() : call (* E2P_Multibit.startTechnique());
	pointcut PCEUAStart() : call (* PCEUA.startTechnique());
	pointcut FLEIIStart() : call (* FLEII.startTechnique());
	
	before() : techniqueOneStart() {
		System.out.println("* ----- SVPS ----- *\n");
	}
	
	before() : techniqueTwoStart() {
		System.out.println("* ----- ERE ----- *\n");
	}

	before() : techniqueThreeStart() {
		System.out.println("* ----- E2P ----- *\n");
	}

	before() : SVPS_MultibitStart() {
		System.out.println("* ----- SVPS MULTI-BIT ----- *\n");
	}
	
	before() : ERE_MultibitStart() {
		System.out.println("* ----- ERE MULTI-BIT ----- *\n");
	}
	
	before() : E2P_MultibitStart() {
		System.out.println("* ----- E2P MULTI-BIT ----- *\n");
	}
	
	before() : PCEUAStart() {
		System.out.println("* ----- PCEUA ----- *\n");
	}

	before() : FLEIIStart() {
		System.out.println("* ----- FLEII ----- *\n");
	}
	
	after() : techniqueOneStart() {
		TechniqueOne technique = new TechniqueOne();
		Class<? extends TechniqueOne> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[i] = args0[i]; 	//field name
					args2[i] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
				///	k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	after() : techniqueTwoStart() {
		TechniqueTwo technique = new TechniqueTwo();
		Class<? extends TechniqueTwo> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[k] = args0[i]; 	//field name
					args2[k] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
					k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	after() : techniqueThreeStart() {
		TechniqueThree technique = new TechniqueThree();
		Class<? extends TechniqueThree> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[k] = args0[i]; 	//field name
					args2[k] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
					k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	after() : SVPS_MultibitStart() {
		SVPS_Multibit technique = new SVPS_Multibit();
		Class<? extends SVPS_Multibit> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[i] = args0[i]; 	//field name
					args2[i] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
				///	k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	after() : ERE_MultibitStart() {
		ERE_Multibit technique = new ERE_Multibit();
		Class<? extends ERE_Multibit> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[i] = args0[i]; 	//field name
					args2[i] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
				///	k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	after() : E2P_MultibitStart() {
		E2P_Multibit technique = new E2P_Multibit();
		Class<? extends E2P_Multibit> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[i] = args0[i]; 	//field name
					args2[i] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
				///	k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	after() : PCEUAStart() {
		PCEUA technique = new PCEUA();
		Class<? extends PCEUA> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[i] = args0[i]; 	//field name
					args2[i] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
				///	k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	after() : FLEIIStart() {
		FLEII technique = new FLEII();
		Class<? extends FLEII> techniqueClass = technique.getClass();
		try
		{
			Field fields[] = techniqueClass.getDeclaredFields();
			System.out.println("\n* ----- Symbol Table ----- *\n");
			String[] args0 = new String[fields.length];
			String[] args1 = new String[fields.length];
			String[] args2 = new String[fields.length];
			int k=0;
			for (int i=0; i < fields.length; i++) {
				args0[i] = fields[i].getName();
			//	System.out.println("args,,,, "+args0[i]);
				
					args1[i] = args0[i]; 	//field name
					args2[i] = (fields[i].get(technique)).toString(); //field value
					//System.out.println("agrs2["+k+"] ; "+args2[k]);
					System.out.println(fields[i].getName() + " = " + fields[i].get(technique));
				///	k++;
				
			}
			
			System.out.println("\n* ----- Detection Algorithm ----- *\n");
			Detection.main(args1, args2 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}