package com.logreader;

public class Event {
	
	private String id;
	private EventEntry eventStart;
	private EventEntry eventFinish;
		
	public Event() {
	}
		
	public Event(String id, EventEntry eventStart, EventEntry eventFinish) {
		this.id = id;
		this.eventStart = eventStart;
		this.eventFinish = eventFinish;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EventEntry getEventStart() {
		return eventStart;
	}

	public void setEventStart(EventEntry eventStart) {
		this.eventStart = eventStart;
	}

	public EventEntry getEventFinish() {
		return eventFinish;
	}

	public void setEventFinish(EventEntry eventFinish) {
		this.eventFinish = eventFinish;
	}
	public Long getDuration(){				
		if(this.eventStart != null && this.eventFinish != null) {
				return this.eventFinish.getTimestamp() - this.eventStart.getTimestamp();							
		}else {
			return 0L;
		}	
	}
	
	public String getType() {
		if(this.eventStart.getType() != null) {
			return  eventStart.getType();						
		}else if((eventFinish.getType() != null) ){
			return  eventFinish.getType();	
		}else {
			return null;
		}
	}
	
	public String getHost() {
		if(this.eventStart.getHost() != null) {
			return  eventStart.getHost();						
		}else if((eventFinish.getHost() != null) ){
			return  eventFinish.getHost();	
		}else {
			return null;
		}		
	}
	
	public boolean getAlert() {
		if(getDuration()!=0) {
			if(getDuration()>4){
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Event [id=" + id + " Duration=" +this.getDuration() + " Type=" +this.getType() + " Host=" +this.getHost() + " Alert=" + this.getAlert() + "]";
	}
	

}
