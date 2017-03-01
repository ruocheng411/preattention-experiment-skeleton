package fr.m1m2.advancedEval;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CRectangularShape;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CTag;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;

public class Trial {

	protected boolean practice = false;
	protected int block;
	protected int trial;
	protected String visualVariable;
	protected int objectsCount;
	
	protected Experiment experiment;
	
	protected CExtensionalTag instructions = new CExtensionalTag() {};
	protected CExtensionalTag visualMarks = new CExtensionalTag(){};
    protected CExtensionalTag targetMark = new CExtensionalTag(){};
    
    protected ArrayList<Double> listX = new ArrayList<Double>(){};
    protected ArrayList<Double> listY = new ArrayList<Double>(){};
    
    protected double mouseX;
    protected double mouseY;
    
    protected double targetX;
    protected double targetY;
    
    protected double time1,time2,timeTotal;
   
	
	protected KeyListener enterListener = new KeyAdapter() {
		
		@Override
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				time1 = System.currentTimeMillis();
				System.out.println("press ENTER");
				hideInstructions();
				Canvas canvas = experiment.getCanvas();
				canvas.removeKeyListener(enterListener);
				displayMainScene();
			}
			
		}
	};
	
	protected KeyListener spaceListener = new KeyAdapter(){
		@Override
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				System.out.println("press SPACE");
				time2 = System.currentTimeMillis();
				hideMainScene();
				Canvas canvas = experiment.getCanvas();
				canvas.removeKeyListener(spaceListener);
				displayPlaceholders();
				canvas.addMouseListener(clickListener);
			}
			
		}
		
	};
	
	protected MouseListener clickListener = new MouseAdapter(){
		@Override
		public void mousePressed(MouseEvent e){
			System.out.println("click MOUSE");
			mouseX = e.getX();
			mouseY = e.getY();
			System.out.println("mouse position"+mouseX+","+mouseY);
			
			hidePlaceholders();
			Canvas canvas = experiment.getCanvas();
			canvas.removeMouseListener(clickListener);
			experiment.nextTrial();
		}
		
	};
	public Trial(Experiment experiment, boolean practice, int block, int trial, String targetChange, int nonTargetsCount) {
		this.practice = practice;
		this.block = block;
		this.trial = trial;
		this.visualVariable = targetChange;
		this.objectsCount = nonTargetsCount;

//		this.visualVariable = "VV1VV2";
//		this.objectsCount=36;
		this.experiment = experiment;
	}
	
	public void displayInstructions() {
		Canvas canvas = experiment.getCanvas();
		CText text1 = canvas.newText(0, 0, "A scene with multiple shapes will get displayed", Experiment.FONT);
		CText text2 = canvas.newText(0, 50, "Identify the shape that is different from all other shapes", Experiment.FONT);
		CText text3 = canvas.newText(0, 100, "    1. Press Space bar", Experiment.FONT);
		CText text4 = canvas.newText(0, 150, "    2. Click on the identified shape", Experiment.FONT);
		CText text5 = canvas.newText(0, 200, "Do it AS FAST AND AS ACCURATELY AS POSSIBLE", Experiment.FONT);
		CText text6 = canvas.newText(0, 350, "--> Press Enter key when ready", Experiment.FONT.deriveFont(Font.PLAIN, 15));
		text1.addTag(instructions);
		text2.addTag(instructions);
		text3.addTag(instructions);
		text4.addTag(instructions);
		text5.addTag(instructions);
		text6.addTag(instructions);
		double textCenterX = instructions.getCenterX();
		double textCenterY = instructions.getCenterY();
		double canvasCenterX = canvas.getWidth()/2;
		double canvasCenterY = canvas.getHeight()/2;
		double dx = canvasCenterX - textCenterX;
		double dy = canvasCenterY - textCenterY;
		instructions.translateBy(dx, dy);
		canvas.setAntialiased(true);

		canvas.addKeyListener(enterListener);
		// TODO install keyboard listener to handle user input
		canvas.requestFocus();
	}
	
	public void hideInstructions(){
		Canvas canvas = experiment.getCanvas();
		canvas.removeShapes(instructions);
	}
	
	public void displayMainScene(){
		System.out.println(visualVariable+" "+objectsCount);
		Canvas canvas = experiment.getCanvas();
		canvas.removeShapes(visualMarks);
		canvas.removeShapes(targetMark);
		
		//ObjectCount, L, target
		if (visualVariable.equals("VV1")){
		
			System.out.println("The visual variable is VV1");
			ArrayList<CShape> listShapes = new ArrayList<CShape>();
			double rand = Math.random();//random number [0,1]	
			
			if(rand<0.5){
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				CRectangle targetRect = canvas.newRectangle(0, 0, 15,30);
				targetRect.addTag(targetMark);
				
				System.out.println("-------test-------");
				System.out.println(targetMark);
				
				targetRect.setPickable(true);
				targetRect.setFillPaint(Color.LIGHT_GRAY);
				rectList.add(targetRect);
				for(int i=1;i<objectsCount;i++){
					CRectangle rect = canvas.newRectangle(0, 0, 15,30);
					rect.setFillPaint(Color.GRAY);
					rect.addTag(visualMarks);
					rectList.add(rect);
				}
				
				System.out.println("==========");
				for(int i=0;i<objectsCount;i++){
					listShapes.add(rectList.get(i));	
				}	
				System.out.println("---- first element in the list ----");
				System.out.println(listShapes.get(0).getTags());
				
			}
			else{
				
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				CRectangle targetRect = canvas.newRectangle(0, 0, 15,30);
				targetRect.addTag(targetMark);
				
				System.out.println("-------test-------");
				System.out.println(targetMark);
				
				targetRect.setPickable(true);
				targetRect.setFillPaint(Color.GRAY);
				rectList.add(targetRect);
				for(int i=1;i<objectsCount;i++){
					CRectangle rect = canvas.newRectangle(0, 0, 15,30);
					rect.setFillPaint(Color.LIGHT_GRAY);
					rect.addTag(visualMarks);
					rectList.add(rect);
				}
				
				System.out.println("==========");
				for(int i=0;i<objectsCount;i++){
					listShapes.add(rectList.get(i));
				}
				
				System.out.println("---- first element in the list ----");
				System.out.println(listShapes.get(0).getTags());
				
			}
			
			
			Collections.shuffle(listShapes);
			System.out.println("--------------");
			double canvasWidth = canvas.getPreferredSize().getWidth();
			double canvasHeight = canvas.getPreferredSize().getHeight();
			
			
			for(int i=0;i<objectsCount;i++){
				double randNum1 = canvasWidth * Math.random();
				double randNum2 = canvasHeight * Math.random();
				listShapes.get(i).translateTo(randNum1, randNum2);
				
				listX.add(randNum1);
				listY.add(randNum2);
				
//				System.out.println("list position: "+listX.get(i)+","+listY.get(i)+" "+listX.size());
				if(listShapes.get(i).hasTag(targetMark)){
					targetX = listShapes.get(i).getCenterX();
					targetY = listShapes.get(i).getCenterY();
				}
				
//				System.out.println("listShapes position: "+listShapes.get(i).getCenterX()+","+listShapes.get(i).getCenterY()+ " "+listShapes.get(i).getTags());
				
			}
				
		}else if(visualVariable.equals("VV2")){
			System.out.println("The visual variable is VV2");
						
			ArrayList<CShape> listShapes = new ArrayList<CShape>();
			double rand = Math.random();//random number [0,1]	
			if(rand<0.5){
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				
				CRectangle rectHorizontal = canvas.newRectangle(0, 0, 30,15);
				rectHorizontal.setFillPaint(Color.LIGHT_GRAY);
				rectHorizontal.addTag(targetMark);
				rectHorizontal.setPickable(true);
				rectList.add(rectHorizontal);
				
				for(int i=1;i<objectsCount;i++){
					CRectangle rectVertical = canvas.newRectangle(0, 0, 15,30);
					rectVertical.addTag(visualMarks);
					rectVertical.setFillPaint(Color.LIGHT_GRAY);
					rectList.add(rectVertical);
				}
				
				for(int i=0;i<objectsCount;i++){
					listShapes.add(rectList.get(i));	
				}				
			}
			else{
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				
				CRectangle rectVertical = canvas.newRectangle(0, 0, 15,30);
				rectVertical.setFillPaint(Color.LIGHT_GRAY);				
				
				rectVertical.addTag(targetMark);
				rectVertical.setPickable(true);
				
				rectList.add(rectVertical);
				
				for(int i=1;i<objectsCount;i++){
					CRectangle rectHorizontal = canvas.newRectangle(0, 0, 30,15);
					rectHorizontal.addTag(visualMarks);
					rectHorizontal.setFillPaint(Color.LIGHT_GRAY);
					rectList.add(rectHorizontal);
				}
				for(int i=0;i<objectsCount;i++){
					listShapes.add(rectList.get(i));
				}
				
			}
			Collections.shuffle(listShapes);
		
			double canvasWidth = canvas.getPreferredSize().getWidth();
			double canvasHeight = canvas.getPreferredSize().getHeight();
			
			
			for(int i=0;i<objectsCount;i++){
				double randNum1 = canvasWidth * Math.random();
				double randNum2 = canvasHeight * Math.random();
				listX.add(randNum1);
				listY.add(randNum2);
				listShapes.get(i).translateTo(randNum1, randNum2);
				if(listShapes.get(i).hasTag(targetMark)){
					targetX = listShapes.get(i).getCenterX();
					targetY = listShapes.get(i).getCenterY();
				}
				System.out.println("listShapes: "+ listShapes.get(i).getCenterX()+", "+listShapes.get(i).getCenterY() + " "+listShapes.get(i).getTags());
			}
		}else if(visualVariable.equals("VV1VV2")){
			System.out.println("The visual variable is VV1VV2");
			ArrayList<CShape> listShapes = new ArrayList<CShape>();
			if(objectsCount==9){	
				double rand = Math.random();//random number [0,1]	
				if(rand<0.25){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalLight1 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalLight2 = canvas.newRectangle(0, 0, 15,30);
					
					CRectangle rectHorizontalDark1 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalDark2 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalDark3 = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalDark1 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalDark2 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalDark3 = canvas.newRectangle(0, 0, 15,30);
					
					
					rectVerticalLight1.addTag(visualMarks);
					rectVerticalLight2.addTag(visualMarks);
				
					rectHorizontalDark1.addTag(visualMarks);
					rectHorizontalDark2.addTag(visualMarks);
					rectHorizontalDark3.addTag(visualMarks);
					
					rectVerticalDark1.addTag(visualMarks);
					rectVerticalDark2.addTag(visualMarks);
					rectVerticalDark3.addTag(visualMarks);
					
					rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
					
					rectVerticalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectVerticalLight2.setFillPaint(Color.LIGHT_GRAY);
					
					rectHorizontalDark1.setFillPaint(Color.GRAY);
					rectHorizontalDark2.setFillPaint(Color.GRAY);
					rectHorizontalDark3.setFillPaint(Color.GRAY);
					
					rectVerticalDark1.setFillPaint(Color.GRAY);
					rectVerticalDark2.setFillPaint(Color.GRAY);
					rectVerticalDark3.setFillPaint(Color.GRAY);
					
					rectHorizontalLight.addTag(targetMark);
					rectHorizontalLight.setPickable(true);
					
					rectList.add(0, rectHorizontalLight);
					rectList.add(1, rectVerticalLight1);
					rectList.add(2, rectVerticalLight2);
					rectList.add(3, rectHorizontalDark1);
					rectList.add(4, rectHorizontalDark2);
					rectList.add(5, rectHorizontalDark3);
					rectList.add(6, rectVerticalDark1);
					rectList.add(7, rectVerticalDark2);
					rectList.add(8, rectVerticalDark3);
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
//						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
					}			
					
				}
				else if(((rand>0.25)||(rand==0.25))&&(rand<0.5)){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalLight1 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalLight2 = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);
					
					CRectangle rectHorizontalDark1 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalDark2 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalDark3 = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalDark1 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalDark2 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalDark3 = canvas.newRectangle(0, 0, 15,30);
					
					rectHorizontalLight1.addTag(visualMarks);
					rectHorizontalLight2.addTag(visualMarks);
					
					rectHorizontalDark1.addTag(visualMarks);
					rectHorizontalDark2.addTag(visualMarks);
					rectHorizontalDark3.addTag(visualMarks);
					
					rectVerticalDark1.addTag(visualMarks);
					rectVerticalDark2.addTag(visualMarks);
					rectVerticalDark3.addTag(visualMarks);
					
					rectHorizontalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectHorizontalLight2.setFillPaint(Color.LIGHT_GRAY);
					
					rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
					
					rectHorizontalDark1.setFillPaint(Color.GRAY);
					rectHorizontalDark2.setFillPaint(Color.GRAY);
					rectHorizontalDark3.setFillPaint(Color.GRAY);
					
					rectVerticalDark1.setFillPaint(Color.GRAY);
					rectVerticalDark2.setFillPaint(Color.GRAY);
					rectVerticalDark3.setFillPaint(Color.GRAY);
					
					
					rectVerticalLight.addTag(targetMark);
					rectVerticalLight.setPickable(true);
					
					rectList.add(0, rectVerticalLight);
					rectList.add(1, rectHorizontalLight1);
					rectList.add(2, rectHorizontalLight2);
					rectList.add(3, rectHorizontalDark1);
					rectList.add(4, rectHorizontalDark2);
					rectList.add(5, rectHorizontalDark3);
					rectList.add(6, rectVerticalDark1);
					rectList.add(7, rectVerticalDark2);
					rectList.add(8, rectVerticalDark3);
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
//						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
					}		
				}
				else if(((rand>0.5)||(rand==0.5))&&rand<0.75){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalLight1 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalLight2 = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalLight1= canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalLight2 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalLight3 = canvas.newRectangle(0, 0, 15,30);
					
					CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalDark1 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalDark2 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalDark3 = canvas.newRectangle(0, 0, 15,30);
					
					rectHorizontalLight1.addTag(visualMarks);
					rectHorizontalLight2.addTag(visualMarks);
					
					rectVerticalLight1.addTag(visualMarks);
					rectVerticalLight2.addTag(visualMarks);
					rectVerticalLight3.addTag(visualMarks);
					
					
					rectVerticalDark1.addTag(visualMarks);
					rectVerticalDark2.addTag(visualMarks);
					rectVerticalDark3.addTag(visualMarks);
					
					rectHorizontalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectHorizontalLight2.setFillPaint(Color.LIGHT_GRAY);
					
					rectVerticalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectVerticalLight2.setFillPaint(Color.LIGHT_GRAY);
					rectVerticalLight3.setFillPaint(Color.LIGHT_GRAY);
					
					rectHorizontalDark.setFillPaint(Color.GRAY);
					
					rectVerticalDark1.setFillPaint(Color.GRAY);
					rectVerticalDark2.setFillPaint(Color.GRAY);
					rectVerticalDark3.setFillPaint(Color.GRAY);
					
					rectHorizontalDark.addTag(targetMark);
					rectHorizontalDark.setPickable(true);
					
					rectList.add(0, rectHorizontalDark);
					rectList.add(1, rectHorizontalLight1);
					rectList.add(2, rectHorizontalLight2);
					rectList.add(3, rectVerticalLight1);
					rectList.add(4, rectVerticalLight2);
					rectList.add(5, rectVerticalLight3);
					rectList.add(6, rectVerticalDark1);
					rectList.add(7, rectVerticalDark2);
					rectList.add(8, rectVerticalDark3);
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
//						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
					}			
				}
				else{
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalLight1 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalLight2 = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalLight1 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalLight2 = canvas.newRectangle(0, 0, 15,30);
					CRectangle rectVerticalLight3 = canvas.newRectangle(0, 0, 15,30);
					
					CRectangle rectHorizontalDark1 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalDark2 = canvas.newRectangle(0, 0, 30,15);
					CRectangle rectHorizontalDark3 = canvas.newRectangle(0, 0, 30,15);
					
					CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
					
					rectHorizontalLight1.addTag(visualMarks);
					rectHorizontalLight2.addTag(visualMarks);
					
					rectVerticalLight1.addTag(visualMarks);
					rectVerticalLight2.addTag(visualMarks);
					rectVerticalLight3.addTag(visualMarks);
					
					rectHorizontalDark1.addTag(visualMarks);
					rectHorizontalDark2.addTag(visualMarks);
					rectHorizontalDark3.addTag(visualMarks);
					
					
					rectHorizontalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectHorizontalLight2.setFillPaint(Color.LIGHT_GRAY);
					
					rectVerticalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectVerticalLight2.setFillPaint(Color.LIGHT_GRAY);
					rectVerticalLight3.setFillPaint(Color.LIGHT_GRAY);
					
					rectHorizontalDark1.setFillPaint(Color.GRAY);
					rectHorizontalDark2.setFillPaint(Color.GRAY);
					rectHorizontalDark3.setFillPaint(Color.GRAY);
					
					rectVerticalDark.setFillPaint(Color.GRAY);
					
					rectVerticalDark.addTag(targetMark);
					rectVerticalDark.setPickable(true);
					
					
					rectList.add(0, rectVerticalDark);
					rectList.add(1, rectHorizontalLight1);
					rectList.add(2, rectHorizontalLight2);
					rectList.add(3, rectVerticalLight1);
					rectList.add(4, rectVerticalLight2);
					rectList.add(5, rectVerticalLight3);
					rectList.add(6, rectHorizontalDark1);
					rectList.add(7, rectHorizontalDark2);
					rectList.add(8, rectHorizontalDark3);
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
					}			
				}
			}
			else if(objectsCount==16){
				
				double rand = Math.random();//random number [0,1]	
				if(rand<0.25){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);
					rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
					
					ArrayList<CRectangle> rectVerticalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalDarkList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectVerticalDarkList = new ArrayList<CRectangle>();
					
					for(int i=0;i<5;i++){
						CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);					
						rectVerticalLight.addTag(visualMarks);
						rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
						rectVerticalLightList.add(rectVerticalLight);						
						
						CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalDark.addTag(visualMarks);
						rectHorizontalDark.setFillPaint(Color.GRAY);
						rectHorizontalDarkList.add(rectHorizontalDark);
						
						CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
						rectVerticalDark.addTag(visualMarks);
						rectVerticalDark.setFillPaint(Color.GRAY);
						rectVerticalDarkList.add(rectVerticalDark);
					}									
					
					rectHorizontalLight.addTag(targetMark);
					rectHorizontalLight.setPickable(true);
					
					rectList.add(rectHorizontalLight);
					
					for(int i=0;i<5;i++){
						rectList.add(rectVerticalLightList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectHorizontalDarkList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectVerticalDarkList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
					}			
				}
				else if(((rand>0.25)||(rand==0.25))&&(rand<0.5)){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);
					rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
					
					ArrayList<CRectangle> rectHorizontalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalDarkList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectVerticalDarkList = new ArrayList<CRectangle>();
					
					for(int i=0;i<5;i++){
						CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);					
						rectHorizontalLight.addTag(visualMarks);
						rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
						rectHorizontalLightList.add(rectHorizontalLight);						
						
						CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalDark.addTag(visualMarks);
						rectHorizontalDark.setFillPaint(Color.GRAY);
						rectHorizontalDarkList.add(rectHorizontalDark);
						
						CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
						rectVerticalDark.addTag(visualMarks);
						rectVerticalDark.setFillPaint(Color.GRAY);
						rectVerticalDarkList.add(rectVerticalDark);
					}									
					
					rectVerticalLight.addTag(targetMark);
					rectVerticalLight.setPickable(true);
					
					rectList.add(rectVerticalLight);
					
					for(int i=0;i<5;i++){
						rectList.add(rectHorizontalLightList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectHorizontalDarkList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectVerticalDarkList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
					}
				}
				else if(((rand>0.5)||(rand==0.5))&&rand<0.75){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
					rectHorizontalDark.setFillPaint(Color.GRAY);
					
					ArrayList<CRectangle> rectVerticalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectVerticalDarkList = new ArrayList<CRectangle>();
					
					for(int i=0;i<5;i++){
						CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);					
						rectVerticalLight.addTag(visualMarks);
						rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
						rectVerticalLightList.add(rectVerticalLight);						
						
						CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalLight.addTag(visualMarks);
						rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
						rectHorizontalLightList.add(rectHorizontalLight);
						
						CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
						rectVerticalDark.addTag(visualMarks);
						rectVerticalDark.setFillPaint(Color.GRAY);
						rectVerticalDarkList.add(rectVerticalDark);
					}									
					
					rectHorizontalDark.addTag(targetMark);
					rectHorizontalDark.setPickable(true);
					
					rectList.add(rectHorizontalDark);
					
					for(int i=0;i<5;i++){
						rectList.add(rectVerticalLightList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectHorizontalLightList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectVerticalDarkList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
					}								
				}
				else{
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
					rectVerticalDark.setFillPaint(Color.GRAY);
					
					ArrayList<CRectangle> rectVerticalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalDarkList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalLightList = new ArrayList<CRectangle>();
					
					for(int i=0;i<5;i++){
						CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);					
						rectVerticalLight.addTag(visualMarks);
						rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
						rectVerticalLightList.add(rectVerticalLight);						
						
						CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalDark.addTag(visualMarks);
						rectHorizontalDark.setFillPaint(Color.GRAY);
						rectHorizontalDarkList.add(rectHorizontalDark);
						
						CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalLight.addTag(visualMarks);
						rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
						rectHorizontalLightList.add(rectHorizontalLight);
					}									
					
					rectVerticalDark.addTag(targetMark);
					rectVerticalDark.setPickable(true);
					
					rectList.add(rectVerticalDark);
					
					for(int i=0;i<5;i++){
						rectList.add(rectVerticalLightList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectHorizontalDarkList.get(i));
					}
					
					for(int i=0;i<5;i++){
						rectList.add(rectHorizontalLightList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
					}		
				}		
			}else if(objectsCount==36){
				double rand = Math.random();//random number [0,1]	
				if(rand<0.25){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);
					rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
					
					ArrayList<CRectangle> rectVerticalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalDarkList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectVerticalDarkList = new ArrayList<CRectangle>();
					
					for(int i=0;i<12;i++){
						CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);					
						rectVerticalLight.addTag(visualMarks);
						rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
						rectVerticalLightList.add(rectVerticalLight);						
						
						CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalDark.addTag(visualMarks);
						rectHorizontalDark.setFillPaint(Color.GRAY);
						rectHorizontalDarkList.add(rectHorizontalDark);
						
						
					}
					
					for(int i=0;i<11;i++){
						CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
						rectVerticalDark.addTag(visualMarks);
						rectVerticalDark.setFillPaint(Color.GRAY);
						rectVerticalDarkList.add(rectVerticalDark);
					}
					
					rectHorizontalLight.addTag(targetMark);
					rectHorizontalLight.setPickable(true);
					
					rectList.add(rectHorizontalLight);
					
					for(int i=0;i<12;i++){
						rectList.add(rectVerticalLightList.get(i));
					}
					
					for(int i=0;i<12;i++){
						rectList.add(rectHorizontalDarkList.get(i));
					}
					
					for(int i=0;i<11;i++){
						rectList.add(rectVerticalDarkList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
					}			
				}
				else if(((rand>0.25)||(rand==0.25))&&(rand<0.5)){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);
					rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
					
					ArrayList<CRectangle> rectHorizontalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalDarkList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectVerticalDarkList = new ArrayList<CRectangle>();
					
					for(int i=0;i<12;i++){
						CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);					
						rectHorizontalLight.addTag(visualMarks);
						rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
						rectHorizontalLightList.add(rectHorizontalLight);						
												
						CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
						rectVerticalDark.addTag(visualMarks);
						rectVerticalDark.setFillPaint(Color.GRAY);
						rectVerticalDarkList.add(rectVerticalDark);
					}	
					
					for(int i=0;i<11;i++){
						CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalDark.addTag(visualMarks);
						rectHorizontalDark.setFillPaint(Color.GRAY);
						rectHorizontalDarkList.add(rectHorizontalDark);
					}
					
					rectVerticalLight.addTag(targetMark);
					rectVerticalLight.setPickable(true);
					
					rectList.add(rectVerticalLight);
					
					for(int i=0;i<12;i++){
						rectList.add(rectHorizontalLightList.get(i));
					}
					
					for(int i=0;i<11;i++){
						rectList.add(rectHorizontalDarkList.get(i));
					}
					
					for(int i=0;i<12;i++){
						rectList.add(rectVerticalDarkList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
					}
				}
				else if(((rand>0.5)||(rand==0.5))&&rand<0.75){
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
					rectHorizontalDark.setFillPaint(Color.GRAY);
					
					ArrayList<CRectangle> rectVerticalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectVerticalDarkList = new ArrayList<CRectangle>();
					
					for(int i=0;i<12;i++){
															
						CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalLight.addTag(visualMarks);
						rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
						rectHorizontalLightList.add(rectHorizontalLight);
						
						CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
						rectVerticalDark.addTag(visualMarks);
						rectVerticalDark.setFillPaint(Color.GRAY);
						rectVerticalDarkList.add(rectVerticalDark);
					}	
					
					for(int i=0;i<11;i++){
						CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);					
						rectVerticalLight.addTag(visualMarks);
						rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
						rectVerticalLightList.add(rectVerticalLight);		
					}
					
					rectHorizontalDark.addTag(targetMark);
					rectHorizontalDark.setPickable(true);
					
					rectList.add(rectHorizontalDark);
					
					for(int i=0;i<11;i++){
						rectList.add(rectVerticalLightList.get(i));
					}
					
					for(int i=0;i<12;i++){
						rectList.add(rectHorizontalLightList.get(i));
					}
					
					for(int i=0;i<12;i++){
						rectList.add(rectVerticalDarkList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
//						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
					}		
					
				}
				else{
					ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
					
					CRectangle rectVerticalDark = canvas.newRectangle(0, 0, 15,30);
					rectVerticalDark.setFillPaint(Color.GRAY);
					
					ArrayList<CRectangle> rectVerticalLightList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalDarkList = new ArrayList<CRectangle>();
					ArrayList<CRectangle> rectHorizontalLightList = new ArrayList<CRectangle>();
					
					for(int i=0;i<12;i++){
						CRectangle rectVerticalLight = canvas.newRectangle(0, 0, 15,30);					
						rectVerticalLight.addTag(visualMarks);
						rectVerticalLight.setFillPaint(Color.LIGHT_GRAY);
						rectVerticalLightList.add(rectVerticalLight);						
						
						CRectangle rectHorizontalDark = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalDark.addTag(visualMarks);
						rectHorizontalDark.setFillPaint(Color.GRAY);
						rectHorizontalDarkList.add(rectHorizontalDark);
											
					}	
					
					for(int i=0;i<11;i++){
						CRectangle rectHorizontalLight = canvas.newRectangle(0, 0, 30,15);
						rectHorizontalLight.addTag(visualMarks);
						rectHorizontalLight.setFillPaint(Color.LIGHT_GRAY);
						rectHorizontalLightList.add(rectHorizontalLight);
					}
					
					rectVerticalDark.addTag(targetMark);
					rectVerticalDark.setPickable(true);
					
					rectList.add(rectVerticalDark);
					
					for(int i=0;i<12;i++){
						rectList.add(rectVerticalLightList.get(i));
					}
					
					for(int i=0;i<12;i++){
						rectList.add(rectHorizontalDarkList.get(i));
					}
					
					for(int i=0;i<11;i++){
						rectList.add(rectHorizontalLightList.get(i));
					}
					
					
					for(int i=0;i<objectsCount;i++){
						listShapes.add(rectList.get(i));	
//						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
					}		
					
				}		
			}
					
			Collections.shuffle(listShapes);
		
			double canvasWidth = canvas.getPreferredSize().getWidth();
			double canvasHeight = canvas.getPreferredSize().getHeight();
			
			for(int i=0;i<objectsCount;i++){
				double randNum1 = Math.random();
				double randNum2 = Math.random();
				listX.add(randNum1*canvasWidth);
				listY.add(randNum2*canvasHeight);
				listShapes.get(i).translateTo(randNum1*canvasWidth, randNum2*canvasHeight);
				if(listShapes.get(i).hasTag(targetMark)){
					targetX = listShapes.get(i).getCenterX();
					targetY = listShapes.get(i).getCenterY();
				}
				
//				System.out.println("listShapes: "+ listShapes.get(i).getCenterX()+", "+listShapes.get(i).getCenterY() + " "+listShapes.get(i).getTags());
			}				
			
		}
		canvas.addKeyListener(spaceListener);
	}
	
	public void hideMainScene(){
		Canvas canvas = experiment.getCanvas();
		visualMarks.setDrawable(false);
		targetMark.setDrawable(false);
	}
	
	public void displayPlaceholders(){
		Canvas canvas = experiment.getCanvas();
		
		ArrayList<CEllipse> listPlaceholders = new ArrayList<CEllipse>();
		
		for(int i=0;i<objectsCount;i++){			
			CEllipse ph = canvas.newEllipse(0,0, 20, 20);
			ph.setFillPaint(Color.WHITE);
	
			ph.translateTo(listX.get(i), listY.get(i));
			if(ph.getCenterX()==targetX && ph.getCenterY()==targetY){
				ph.addTag(targetMark);
//				System.out.println("tag added");
			}
			ph.addTag(visualMarks);
			listPlaceholders.add(ph);
			
		}
		
		
	}
	
	public void hidePlaceholders(){
		Canvas canvas = experiment.getCanvas();
		Point2D.Double pt = new Point2D.Double();
		pt.x=mouseX;
		pt.y=mouseY;
		
		System.out.println(pt.x+","+pt.y);
		CShape picked = canvas.pick(pt);
		System.out.println("----- picked position -----");
		System.out.println(picked.getTags());
		System.out.println(picked.getCenterX()+","+picked.getCenterY());
		System.out.println("----- target position -----");
		System.out.println(targetMark);
		System.out.println(targetMark.getCenterX()+","+targetMark.getCenterY());
		System.out.println("---- visual marks ----");
		System.out.println(visualMarks);
		
		if(picked.hasTag(targetMark)){
			System.out.println("Right!");
			timeTotal = time2 - time1;
			System.out.println("time Total = time1 ("+time1+") - time2 ("+time2+") = " +timeTotal+" MS");
		}
		else if(!picked.hasTag(targetMark)){
			System.out.println("Not right; Redo");
//			canvas.removeShapes(visualMarks);
			experiment.currentTrial--;
			listX.clear();
			listY.clear();
			System.out.println("trial: "+experiment.currentTrial);
			
		}
		else System.out.println("fail");
		
		
	
		canvas.removeShapes(visualMarks);
		canvas.removeShapes(targetMark);
	}
	

	
	public void start(){
		
	}
	
	public void stop(){
		
	}
	//VV1: Color VV2:Size
	
	
}
