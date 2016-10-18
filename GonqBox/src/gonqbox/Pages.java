package gonqbox;

public enum Pages {
	
	REGISTER_PAGE			("register.jsp"),
	FOLDER					("folder.jsp"),
	INDEX					("index.jsp");
	
    private final String page;
    
    private Pages(final String page) {
        this.page = page;
    }
    
    @Override
    public String toString() {
        return page;
    }
    
}
