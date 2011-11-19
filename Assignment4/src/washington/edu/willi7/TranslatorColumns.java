package washington.edu.willi7;

import android.provider.BaseColumns;

public final class TranslatorColumns implements BaseColumns {
	private TranslatorColumns() {}
	
	public final static String LANGUAGE_TABLE = "available_languages";
	public final static String CATEGORY_TABLE = "available_categories";
	public final static String MESSAGE_TABLE = "available_messages";
	
	public final static String LANGUAGE_COLUMN = "language";
	public final static String CATEGORY_COLUMN = "category";
	public final static String MESSAGE_1_COLUMN = "message1";
	public final static String MESSAGE_2_COLUMN = "message2";

}
