package washington.edu.willi7;

import android.provider.BaseColumns;

public final class TranslatorColumns implements BaseColumns {
	private TranslatorColumns() {}
	
	public final static String LANGUAGE_COLUMN = "language";
	public final static String CATEGORY_COLUMN = "category";
	public final static String MESSAGE_COLUMN = "message";

}
