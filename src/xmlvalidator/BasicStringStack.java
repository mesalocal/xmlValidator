package xmlvalidator;

import java.util.*;

public class BasicStringStack implements StringStack {

	ArrayList<String> blah = new ArrayList<String>();
	ArrayList<Integer> theLineNumber = new ArrayList<Integer>();
	int lineNumber;

	/**
	 * Pushes the given item onto the top of the stack.
	 * 
	 * @param item
	 */
	@Override
	public void push(String item) {
		blah.add(item);

	}

	/**
	 * Removes the top item from the stack.
	 * 
	 * @return The removed item.
	 */
	@Override
	public String pop() {
		if (blah.size() > 0) {
			String temp = blah.get(blah.size() - 1);
			blah.remove(blah.size() - 1);
			return temp;
		} else
			return null;
	}

	/**
	 * Returns, but does not remove, the item at the given position. 0 is the
	 * top, 1 is the second item, and so on.
	 * 
	 * @param position
	 * 
	 * @return The item at the given position.
	 */
	@Override
	public String peek(int position) {
		return blah.get(blah.size() - position - 1);
	}

	@Override
	public int getCount() {
		return blah.size();
	}

	// public int getLineNumber(String xmlDoc, Matcher match) {
	// lineNumber = 0;
	// Pattern dummyP = Pattern.compile("\n");
	// Matcher dummyM = dummyP.matcher(xmlDoc);
	//
	// do {
	// dummyM.find();
	// lineNumber++;
	// } while (dummyM.start() < match.start());
	// return lineNumber;
	// }

}
