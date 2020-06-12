package Model;

import javafx.beans.property.StringProperty;

public class TeamNoticeDataModel {
	private StringProperty date;
	private StringProperty author;
	private StringProperty note;
	
	public TeamNoticeDataModel(StringProperty date, StringProperty author, StringProperty note) {
		this.date = date;
		this.author = author;
		this.note = note;
	}
	
	public StringProperty dateProperty() { return date; }
	public StringProperty authorProperty() { return author; }
	public StringProperty noteProperty() { return note; }
}
