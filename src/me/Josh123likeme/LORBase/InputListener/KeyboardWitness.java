package me.Josh123likeme.LORBase.InputListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyboardWitness implements KeyListener {

	private ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (!keysPressed.contains(e.getKeyCode())) keysPressed.add(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		try {
			
			keysPressed.removeAll(Collections.singleton(e.getKeyCode()));
			
		}
		catch (Exception exception) {}
		
	}
	
	public List<Integer> getHeldKeys(){
		
		return keysPressed;
		
	}

}
