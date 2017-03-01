package fr.m1m2.advancedEval;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

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
	
	protected KeyListener enterListener = new KeyAdapter() {
		
		@Override
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				System.out.println("\t ENTER");
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
				System.out.println("\t SPACE");
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
			System.out.println("\t MOUSE");
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
//		this.visualVariable = "VV1VV2";
		this.objectsCount = nonTargetsCount;
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
		//System.out.println(x);
		System.out.println(visualVariable+" "+objectsCount);
		Canvas canvas = experiment.getCanvas();
		canvas.removeShapes(visualMarks);
		
		//canvas.addKeyListener(spaceListener);
		
		//rect.setFillPaint(Color.RED);
		
		//System.out.println(visualVariable+"................");
		//ObjectCount, L, target
		if (visualVariable.equals("VV1")){
			//CRectangle rect = canvas.newRectangle(0,0,15,30);
			//rect.addTag(visualMarks);
			
			System.out.println("The visual variable is VV1");
			ArrayList<CShape> listShapes = new ArrayList<CShape>();
			double rand = Math.random();//random number [0,1]	
			
			if(rand<0.5){
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				CRectangle targetRect = canvas.newRectangle(0, 0, 15,30);
				targetRect.addTag(targetMark);
				targetRect.setFillPaint(Color.LIGHT_GRAY);
				rectList.add(targetRect);
				for(int i=1;i<objectsCount;i++){
					CRectangle rect = canvas.newRectangle(0, 0, 15,30);
					rect.setFillPaint(Color.GRAY);
					rect.addTag(visualMarks);
					rectList.add(rect);
				}
				//rect.setFillPaint(Color.LIGHT_GRAY);
				rectList.set(0, targetRect);
				//rectList.get(0).setFillPaint(Color.LIGHT_GRAY);//the first object is light gray in color
				for(int i=1;i<objectsCount;i++){
					rectList.get(i).setFillPaint(Color.GRAY);//the rest are gray in color
				}
				System.out.println("==========");
				for(int i=0;i<objectsCount;i++){
					//rect.setFillPaint(Color.BLACK);
					listShapes.add(rectList.get(i));	
					System.out.println(listShapes.get(i).getFillPaint());
					
				}				
				
			}
			else{
				//CRectangle targetRect = canvas.newRectangle(0, 0, 15,30);
				
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				CRectangle targetRect = canvas.newRectangle(0, 0, 15,30);
				targetRect.addTag(targetMark);
				targetRect.setFillPaint(Color.GRAY);
				rectList.add(targetRect);
				for(int i=1;i<objectsCount;i++){
					CRectangle rect = canvas.newRectangle(0, 0, 15,30);
					rect.setFillPaint(Color.LIGHT_GRAY);
					rect.addTag(visualMarks);
					rectList.add(rect);
				}
				
				//rect.setFillPaint(Color.LIGHT
				rectList.set(0, targetRect);
				//rectList.get(0).setFillPaint(Color.GRAY);//the first object is gray in color
				for(int i=1;i<objectsCount;i++){
					rectList.get(i).setFillPaint(Color.LIGHT_GRAY);//the rest are gray in color
				}
				System.out.println("==========");
				for(int i=0;i<objectsCount;i++){
					//rect.setFillPaint(Color.GRAY);
					//listShapes.add(rect);
					listShapes.add(rectList.get(i));
					System.out.println(listShapes.get(i).getFillPaint());
				}
				
			}
			
			
			Collections.shuffle(listShapes);
			System.out.println("--------------");
			for(int i=0;i<objectsCount;i++){
				//listShapes.add(rect);	
				System.out.println(listShapes.get(i).getFillPaint());
			}
			
			double canvasWidth = canvas.getPreferredSize().getWidth();
			double canvasHeight = canvas.getPreferredSize().getHeight();
			
			
			for(int i=0;i<objectsCount;i++){
				double randNum1 = Math.random();
				double randNum2 = Math.random();
				
				//CShape tRect = listShapes.get(i);
				//tRect.translateTo(randNum1*canvasWidth, randNum2*canvasHeight);
				//listShapes.set(i, tRect);
				listShapes.get(i).translateTo(randNum1*canvasWidth, randNum2*canvasHeight);
				
				//canvas.add(canvas,listShapes.get(i));
				//canvas.addShape(listShapes.get(i));
				System.out.println(listShapes.get(i).getCenterX()+", "+listShapes.get(i).getCenterY());
			}
			
			
						
		}
		else if(visualVariable.equals("VV2")){
			System.out.println("The visual variable is VV2");
						
			ArrayList<CShape> listShapes = new ArrayList<CShape>();
			double rand = Math.random();//random number [0,1]	
			//CRectangle targetRect = canvas.newRectangle(0, 0, 15,30);
			//targetRect.addTag(targetMark);
			//targetRect.setFillPaint(Color.LIGHT_GRAY);
			if(rand<0.5){
				//rect.setFillPaint(Color.LIGHT_GRAY);
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				
				CRectangle rectHorizontal = canvas.newRectangle(0, 0, 30,15);
				//CRectangle rectVertical = canvas.newRectangle(0, 0, 15,30);
				
				rectHorizontal.addTag(visualMarks);
				
				rectHorizontal.setFillPaint(Color.LIGHT_GRAY);
				
				
				//CRectangle targetRect = rectHorizontal;	
				//CRectangle otherRect = rectVertical;
				
				rectHorizontal.addTag(targetMark);
				
				rectList.add(0, rectHorizontal);
				for(int i=1;i<objectsCount;i++){
					CRectangle rectVertical = canvas.newRectangle(0, 0, 15,30);
					rectVertical.addTag(visualMarks);
					rectVertical.setFillPaint(Color.LIGHT_GRAY);
					rectList.add(i, rectVertical);
				}
				
				System.out.println("==========");
				for(int i=0;i<objectsCount;i++){
					listShapes.add(rectList.get(i));	
					System.out.println(listShapes.get(i).getHeight());
				}				
			}
			else{
				ArrayList<CRectangle> rectList = new ArrayList<CRectangle>();
				CRectangle rectVertical = canvas.newRectangle(0, 0, 15,30);
				rectVertical.addTag(visualMarks);
				rectVertical.setFillPaint(Color.LIGHT_GRAY);				
				
				rectVertical.addTag(targetMark);
				
				rectList.add(0, rectVertical);
				
				for(int i=1;i<objectsCount;i++){
					CRectangle rectHorizontal = canvas.newRectangle(0, 0, 30,15);
					rectHorizontal.addTag(visualMarks);
					rectHorizontal.setFillPaint(Color.LIGHT_GRAY);
					rectList.add(i,rectHorizontal);
				}
				System.out.println("==========");
				for(int i=0;i<objectsCount;i++){
					listShapes.add(rectList.get(i));
					System.out.println(listShapes.get(i).getHeight());
				}
				
			}
			Collections.shuffle(listShapes);
			for(int i=0;i<objectsCount;i++){
				System.out.println(listShapes.get(i).getHeight());
			}
			
			double canvasWidth = canvas.getPreferredSize().getWidth();
			double canvasHeight = canvas.getPreferredSize().getHeight();
			
			
			for(int i=0;i<objectsCount;i++){
				double randNum1 = Math.random();
				double randNum2 = Math.random();
				listShapes.get(i).translateTo(randNum1*canvasWidth, randNum2*canvasHeight);
				System.out.println(listShapes.get(i).getCenterX()+", "+listShapes.get(i).getCenterY());
			}
		}
		else if(visualVariable.equals("VV1VV2")){
			System.out.println("The visual variable is VV1VV2");
			if(objectsCount==9){
				
				
				ArrayList<CShape> listShapes = new ArrayList<CShape>();
				
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
					
					rectHorizontalLight.addTag(visualMarks);
					
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
						//rect.setFillPaint(Color.BLACK);
						listShapes.add(rectList.get(i));	
						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
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
					
					rectVerticalLight.addTag(visualMarks);
					
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
					
					//CRectangle targetRect = rectVerticalLight;
					//CRectangle rect1 = rectHorizontalLight;
					//CRectangle rect2 = rectHorizontalDark;
					//CRectangle rect3 = rectVerticalDark;
					
					rectVerticalLight.addTag(targetMark);
					
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
						//rect.setFillPaint(Color.BLACK);
						listShapes.add(rectList.get(i));	
						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
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
					
					rectHorizontalDark.addTag(visualMarks);
					
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
						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
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
					
					rectVerticalDark.addTag(visualMarks);
					
					rectHorizontalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectHorizontalLight2.setFillPaint(Color.LIGHT_GRAY);
					
					rectVerticalLight1.setFillPaint(Color.LIGHT_GRAY);
					rectVerticalLight2.setFillPaint(Color.LIGHT_GRAY);
					rectVerticalLight3.setFillPaint(Color.LIGHT_GRAY);
					
					rectHorizontalDark1.setFillPaint(Color.GRAY);
					rectHorizontalDark2.setFillPaint(Color.GRAY);
					rectHorizontalDark3.setFillPaint(Color.GRAY);
					
					rectVerticalDark.setFillPaint(Color.GRAY);
					
					//CRectangle targetRect = rectVerticalDark;
					//CRectangle rect1 = rectHorizontalLight;
					//CRectangle rect2 = rectVerticalLight;
					//CRectangle rect3 = rectHorizontalDark;
					
					rectVerticalDark.addTag(targetMark);
					
					
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
						//rect.setFillPaint(Color.BLACK);
						listShapes.add(rectList.get(i));	
						System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());					
					}		
					
				}
				
				Collections.shuffle(listShapes);
				System.out.println("--------------");
				for(int i=0;i<objectsCount;i++){
					//listShapes.add(rect);	
					System.out.println(listShapes.get(i).getWidth()+", "+listShapes.get(i).getHeight()+", "+listShapes.get(i).getFillPaint());
				}
				
				double canvasWidth = canvas.getPreferredSize().getWidth();
				double canvasHeight = canvas.getPreferredSize().getHeight();
				
				
				for(int i=0;i<objectsCount;i++){
					double randNum1 = Math.random();
					double randNum2 = Math.random();
					listShapes.get(i).translateTo(randNum1*canvasWidth, randNum2*canvasHeight);
					System.out.println(listShapes.get(i).getCenterX()+", "+listShapes.get(i).getCenterY());
				}				
			}
		}
					
		
		canvas.addKeyListener(spaceListener);
	}
	
	public void hideMainScene(){
		Canvas canvas = experiment.getCanvas();
		canvas.removeShapes(visualMarks);
		canvas.removeShapes(targetMark);
//		canvas.addMouseListener(clickListener);
	}
	
	public void displayPlaceholders(){
		Canvas canvas = experiment.getCanvas();
		
		CRectangle rect = canvas.newRectangle(0, 0, 30, 30);
		rect.setFillPaint(Color.BLUE);
		rect.addTag(visualMarks);
	}
	
	public void hidePlaceholders(){
		Canvas canvas = experiment.getCanvas();
		canvas.removeShapes(visualMarks);
	}
	

	
	public void start(){
		
	}
	
	public void stop(){
		
	}
	
	//VV1: Color VV2:Size
	
	
}
