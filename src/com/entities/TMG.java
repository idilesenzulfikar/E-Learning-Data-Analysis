package com.entities;

public class TMG {
	
	private int id;
    private String logTime;
    private String clientIp;
    private String operation;
    private String semester;
    private String lvNumber;
    private String uri;
    private String activity;
    private Boolean isDiscussion;
    private String discussionTopic;
    
    
    public TMG(){

    }

    public TMG(String logTime,String clientIp,String operation,String uri,String semester,String lvNumber,String activity,Boolean isDiscussion,String discussionTopic){
        this.logTime = logTime;
        this.clientIp = clientIp;
        this.operation = operation;
        this.semester = semester;
        this.lvNumber = lvNumber;
        this.uri = uri;
        this.activity = activity;
        this.isDiscussion = isDiscussion;
        this.discussionTopic = discussionTopic;
    }
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getLvNumber() {
		return lvNumber;
	}

	public void setLvNumber(String lvNumber) {
		this.lvNumber = lvNumber;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Boolean getIsDiscussion() {
		return isDiscussion;
	}

	public void setIsDiscussion(Boolean isDiscussion) {
		this.isDiscussion = isDiscussion;
	}

	public String getDiscussionTopic() {
		return discussionTopic;
	}

	public void setDiscussionTopic(String discussionTopic) {
		this.discussionTopic = discussionTopic;
	}
}
