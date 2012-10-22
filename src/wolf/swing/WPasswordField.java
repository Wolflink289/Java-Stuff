package wolf.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;
import javax.swing.text.Document;

/**
 * A text field with a WebKit-like placeholder.
 * 
 * @author Wolflink289
 */
public class WPasswordField extends JPasswordField {
	
	private static final long serialVersionUID = 5438038861749081662L;
	
	// Variables
	protected String placeholder;
	protected Color placeholderColor;
	
	// Constuctors
	/**
	 * Create a regular text field. No placeholder is used.
	 */
	public WPasswordField() {
		this(null, 0);
	}
	
	/**
	 * Create a password field with a placeholder.
	 * 
	 * @param placeholder the placeholder text for the field.
	 * @param length the maximum text length of the field.
	 */
	public WPasswordField(String placeholder) {
		this(placeholder, 0);
	}
	
	/**
	 * Create a limited length password field with a placeholder.
	 * 
	 * @param placeholder the placeholder text for the field.
	 * @param length the maximum text length of the field.
	 */
	public WPasswordField(String placeholder, int length) {
		this.placeholder = placeholder;
		this.placeholderColor = Color.gray;
		setMaximumLength(length);
	}
	
	// Functions
	/**
	 * Set the maximum text length (only if the document was not set).
	 * 
	 * @param length The maximum text length. 0 or less is unlimited.
	 */
	public void setMaximumLength(int length) {
		if (getDocument() instanceof LimitedPlainDocument) {
			((LimitedPlainDocument) getDocument()).max_length = length;
		}
	}
	
	/**
	 * Get the maximum text length.
	 * 
	 * @return the maximum text length. 0 is unlimited, -1 if maximum text length is unavailable.
	 */
	public int getMaximumTextLength() {
		if (this.getDocument() instanceof LimitedPlainDocument) { return ((LimitedPlainDocument) this.getDocument()).max_length; }
		return -1;
	}
	
	/**
	 * Set the characters that are allowed to be entered in the field. Use null for every character.<br>
	 * <b>THE SPECIFIED CHARACTERS ARE CASE-SENSATIVE!</b>
	 * 
	 * @param characters the allowed characters.
	 */
	public void setAllowedCharacters(String characters) {
		if (getDocument() instanceof LimitedPlainDocument) {
			((LimitedPlainDocument) getDocument()).allowed_characters = characters;
		}
	}
	
	/**
	 * Get the characters that are allowed to be entered in the field.
	 * 
	 * @return the allowed characters. If null is returned, every character may be used.
	 */
	public String getAllowedCharacters() {
		if (this.getDocument() instanceof LimitedPlainDocument) { return ((LimitedPlainDocument) this.getDocument()).allowed_characters; }
		return null;
	}
	
	/**
	 * Set the color of the placeholder text.
	 * 
	 * @param color the color of the placeholder text.
	 */
	public void setPlaceholderColor(Color color) {
		if (color == null) color = Color.gray;
		this.placeholderColor = color;
	}
	
	/**
	 * Get the color of the placeholder text.
	 * 
	 * @return color of the placeholder text.
	 */
	public Color getPlaceholderColor() {
		return this.placeholderColor;
	}
	
	/**
	 * Set the placeholder text. Use null for no placeholder.
	 * 
	 * @param placeholder the placeholder text.
	 */
	public void setPlaceholderText(String placeholder) {
		if (placeholder != null) if (placeholder.trim().isEmpty()) this.placeholder = null;
		this.placeholder = placeholder;
	}
	
	/**
	 * Get the placeholder text.
	 * 
	 * @return the placeholder text, or null for no placeholder.
	 */
	public String getPlaceholderText() {
		return this.placeholder;
	}
	
	// Placeholder
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics G) {
		super.paint(G);
		
		// Should the placeholder be drawn?
		if (placeholder == null || getPassword().length != 0) return;
		
		// Draw the placeholder
		Graphics2D G2D = (Graphics2D) G;
		G2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		G.setColor(placeholderColor);
		G.setFont(getFont());
		
		G.drawString(placeholder, this.getInsets().left, (int) (getHeight() / 2D) + (int) (G.getFontMetrics(getFont()).getHeight() / 2D) - 3);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Document createDefaultModel() {
		return new LimitedPlainDocument();
	}
}
