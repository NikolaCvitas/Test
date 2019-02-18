package hr.nikola.test;

import java.util.ArrayList;
import java.util.List;

public abstract class TriggerConcurrentModificationException {

	public static void main(String[] args) throws InterruptedException {
		
		whilstRemovingDuringIteration_shouldThrowException();

	}
	
	
	
	
	public static void whilstRemovingDuringIteration_shouldThrowException() throws InterruptedException {
		 
	    List<Integer> integers = new ArrayList();
	    
	    integers.add(new Integer(1));
	    integers.add(new Integer(2));
	    integers.add(new Integer(3));
	    
	    List<Integer> toRemove = new ArrayList();
	    
	    for (Integer integer : integers) {
	        if(integer == 2) {
	            toRemove.add(integer);
	        }
	    }
	    
	    
	    integers.removeAll(toRemove);
	 
	    for (Integer integer : integers) {
	      //  integers.remove(1);
	    	
	    	System.out.println("integer: "+integer);
	    }
	}

}
