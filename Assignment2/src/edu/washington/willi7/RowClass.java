package edu.washington.willi7;

import java.io.Serializable;

/** class that will represent a row item in the ListView */
public class RowClass implements Serializable {

	private static final long serialVersionUID = -1440693207578504875L;
	private String m_joke;

	public void setM_joke(String m_joke) {
		this.m_joke = m_joke;
	}

	public void setM_rating(float m_rating) {
		this.m_rating = m_rating;
	}

	private float m_rating = 3.0f;
	
	RowClass(String j, float r){
		this.m_joke = j;
		this.m_rating = r;
	}
	
	RowClass(String j ){
		this.m_joke = j;
		this.m_rating = 3.0f;
	}
	
	public String GetJoke() {
		return this.m_joke;
	}
	
	public float GetRating() {
		return this.m_rating;
	}  	
}