package com.servicecity.common;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author Yedukondalu K        
 *
 */

public class DatabaseSequence {

	@Id
	private String _id;
	private long sequence;
	
	public DatabaseSequence() {
		super();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}
	

}
