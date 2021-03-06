package gonqbox;

public enum Pages {
	
	REGISTER_PAGE			("register.jsp"),
	FOLDER					("folder.jsp"),
	COMMENT					("comment.jsp"),
	INDEX					("index.jsp"),
	GENERIC_ERROR			("error.jsp");

    private final String page;
    
    private Pages(final String page) {
        this.page = page;
    }
    
    @Override
    public String toString() {
        return page;
    }
    
}
