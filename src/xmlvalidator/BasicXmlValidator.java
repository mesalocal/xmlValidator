package xmlvalidator;

import static java.lang.System.*;

import java.util.regex.*;

public class BasicXmlValidator implements XmlValidator {
	BasicStringStack stack = new BasicStringStack();
	BasicStringStack lineNumber = new BasicStringStack();
	String temp, newLineSearch;
	char nl;
	int newLineCount, quoteCount, equilCount;
	String returnList[] = new String[5];

	@Override
	public String[] validate(String xmlDocument) {
		// Build a regular expression pattern
		String expr = "<[^<>!?]+>";
		Pattern p = Pattern.compile(expr);

		// Get a matcher to process our XML string
		Matcher m = p.matcher(xmlDocument);

		// Find all of the matches for expression in text
		while (m.find()) {

			// extra credit fining missing double quotes

			temp = m.group();
			if (temp.contains("=")) {
				newLineSearch = xmlDocument.substring(m.start(), m.end());
				quoteCount = newLineSearch.split("\"").length - 1;
				equilCount = newLineSearch.split("=").length - 1;

				if (quoteCount != 2 && equilCount == 1) {
					newLineSearch = xmlDocument.substring(0, m.start());
					newLineCount = newLineSearch.split("\n").length;
					// making temp into what's in the tag
					temp = m.group().replace("<", "");

					if (temp.contains(" "))
						temp = temp.substring(0, temp.indexOf(" "));

					else
						temp = temp.substring(0, temp.indexOf(">"));

					returnList[0] = "Attribute not quoted";
					returnList[1] = temp;
					returnList[2] = "" + newLineCount;
					returnList[3] = xmlDocument.substring(m.start(), xmlDocument.indexOf("\n", m.start())).substring(xmlDocument.substring(m.start(), xmlDocument.indexOf("\n", m.start())).indexOf(" ")+1,xmlDocument.substring(m.start(), xmlDocument.indexOf("\n", m.start())).indexOf("="));
					newLineSearch = xmlDocument.substring(0, m.start());
					newLineCount = newLineSearch.split("\n").length;
					returnList[4] = "" + newLineCount;
					return returnList;
					// xmlDocument.substring(m.start(),xmlDocument.indexOf("\n", m.start()));
					// index of space xmlDocument.substring(m.start(),xmlDocument.indexOf("\n", m.start())).indexOf(" ")
					// index of equil sign xmlDocument.substring(m.start(),xmlDocument.indexOf("\n", m.start())).indexOf("=")
				}
			}

			// making temp into what's in the tag
			temp = m.group().replace("<", "");

			if (temp.contains(" "))
				temp = temp.substring(0, temp.indexOf(" "));

			else
				temp = temp.substring(0, temp.indexOf(">"));

			if (!temp.contains("/")) {// does NOT contain "/"
				stack.push(temp);
				newLineSearch = xmlDocument.substring(0, m.start());
				newLineCount = newLineSearch.split("\n").length + 1;
				lineNumber.push("" + newLineCount);

			} else {// does contain "/"
				temp = temp.replace("/", "");

				// stack is empty
				if (stack.getCount() == 0) {
					newLineSearch = xmlDocument.substring(0, m.start());
					newLineCount = newLineSearch.split("\n").length + 1;
					returnList[0] = "Orphan closing tag";
					returnList[1] = temp;
					returnList[2] = "" + newLineCount;
					return returnList;

				} else
				// closing tag does not match the top of the stack
				if (!temp.equals(stack.peek(0))) {
					newLineSearch = xmlDocument.substring(0, m.start());
					newLineCount = newLineSearch.split("\n").length + 1;
					returnList[0] = "Tag mismatch";
					returnList[1] = stack.peek(0);
					returnList[2] = "" + lineNumber.peek(0);
					returnList[3] = temp;
					returnList[4] = "" + newLineCount;
					return returnList;

				} else {
					stack.pop();
					lineNumber.pop();

				}
			}

		}

		// The stack is not empty @ end of xmlDocument
		if (stack.getCount() != 0) {
			returnList[0] = "Unclosed tag at end";
			returnList[1] = stack.peek(0);
			returnList[2] = "" + lineNumber.peek(0);
			return returnList;
		} else
			return null;
	}

}
