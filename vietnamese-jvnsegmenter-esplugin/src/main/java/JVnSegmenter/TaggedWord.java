package JVnSegmenter;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 *         <p>
 *         This class represents a single token from an input stream, matched by
 *         a LexerRule.
 */
public class TaggedWord implements Comparable<TaggedWord> {
	/**
	 * A lexer type
	 */
	private final String type;
	/**
	 * The text
	 */
	private final String text;

	/**
	 * The line location of the text in the file
	 */
	private int line;

	/**
	 * The column location of the text in the file
	 */
	private int column;


	/**
	 * Create a LexerToken
	 *
	 * @param t
	 *            a type
	 * @param text
	 *            the text
	 * @param line
	 *            the line location of the text in a file
	 * @param column
	 *            the column location of the text in a file
	 */
	public TaggedWord(String t, String text, int line, int column) {
		this.type = t;
		this.text = text;
		this.line = line;
		this.column = column;
	}

	/**
	 * Create a lexer token from a text
	 *
	 * @param text
	 *            a text
	 */
	public TaggedWord(String text) {
		this.type = null;
		this.text = text;
		this.line = -1;
		this.column = -1;
	}


	/**
	 * Create a lexer token from a lexer type and a text.
	 * @param t
	 * @param text
	 */
	public TaggedWord(String t, String text) {
		this.type = t;
		this.text = text;
		this.line = -1;
		this.column = -1;
	}

	/**
	 * Return the type that matched this token
	 *
	 * @return the type that match this token
	 */
	public String getType() {
		return type;
	}

	/**
	 * Return the text that matched by this token
	 *
	 * @return the text matched by this token
	 */
	public String getText() {
		return text.trim();
	}

	/**
	 * Test if this type is a phrase type. A phrase is processed
	 * by a lexical segmenter.
	 *
	 * @return true/false
	 */
	public boolean isPhrase() {
		return type.equals("phrase");
	}

	/**
	 * Test if this type is a named entity type.
	 *
	 * @return true/false
	 */
	public boolean isNamedEntity() {
		return type.startsWith("name");
	}

	/**
	 * @return true/false
	 */
	public boolean isDate() {
		return type.startsWith("date");
	}

	/**
	 * @return true/false
	 */
	public boolean isDateDay() {
		return type.contains("day");
	}

	/**
	 * @return true/false
	 */
	public boolean isDateMonth() {
		return type.contains("month");
	}

	public boolean isDateYear() {
		return type.contains("year");
	}

	public boolean isNumber() {
		return type.startsWith("number");
	}
	/**
	 * @return Returns the column.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            The column to set.
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return Returns the line.
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @param line
	 *            The line to set.
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * Return a string representation of the token
	 */
	@Override
	public String toString() {
		// return "[\"" + text + "\"" + " at (" + line + "," + column + ")]";
		// return type.getName() + ": " + text;
		return text.trim();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getText().hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof TaggedWord)) {
			return false;
		}
		// two lexer is considered equal if their text are equal.
		//
		return ((TaggedWord)obj).getText().equals(getText());
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(TaggedWord o) {
		return getText().compareTo(o.getText());
	}
}
