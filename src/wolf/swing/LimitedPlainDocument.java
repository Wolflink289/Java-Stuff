package wolf.swing;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * A limited vesion of PlainDocument.
 * 
 * @author Wolflink289
 */
final class LimitedPlainDocument extends PlainDocument {
	// Serial UID
	private static final long serialVersionUID = -131271801331310719L;
	
	// Fields
	int max_length;
	String allowed_characters;
	
	// Methods
	static private final void beepIfApplicable() {
		String prop1 = System.getProperty("wolf.swing.input.beep");
		String prop2 = System.getProperty("wolf.swing.input.text.beep");
		if (prop2 == null) {
			if (prop1.equalsIgnoreCase("true")) {
				Toolkit.getDefaultToolkit().beep();
			}
		} else {
			if (prop2.equalsIgnoreCase("true")) {
				Toolkit.getDefaultToolkit().beep();
			}
		} 
	}
	
	// Override
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertString(int pos, String str, AttributeSet att) throws BadLocationException {
		if (str == null) return;
		
		// Allowed Characters
		if (allowed_characters != null) {
			if (str.length() == 1) {
				// Single character
				if (allowed_characters.indexOf(str.charAt(0)) == -1) {
					beepIfApplicable();
					return;
				}
			} else {
				// Multi character (paste?)
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < str.length(); i++) {
					if (allowed_characters.indexOf(str.charAt(i)) != -1) {
						sb.append(str.charAt(i));
					}
				}
				
				str = sb.toString();
				sb = null;
				
				if (str.length() == 0) return;
			}
		}
		
		// Maximum Length
		if (max_length > 0) {
			if (getLength() + str.length() > max_length) {
				beepIfApplicable();
				return;
			}
		}
		
		// Do as usual
		super.insertString(pos, str, att);
	}
}
