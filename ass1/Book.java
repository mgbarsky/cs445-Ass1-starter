package ass1;

/**
 * An immutable class containing information about a book.
 * 
 */
public class Book implements Comparable<Book> {
    /** The book's ISBN. */
    public final String isbn;
    /** The book's authors. */
    public final String[] authors;
    /** The book's title. */
    public final String title;
    /** The book's publisher. */
    public final String publisher;
    /** The book's GoodReads rating. */
    public final double rating;

    /**
     * Create a new BookData.
     * 
     * @param isbn      The book's ISBN.
     * @param authors   An array of authors.
     * @param title     The book's title.
     * @param publisher The book's publisher.
     * @param rating    The book's GoodReads rating.
     */
    Book(String isbn, String[] authors, String title, String publisher, double rating) {
        this.isbn = isbn;
        this.authors = authors.clone();
        this.title = title;
        this.publisher = publisher;
        this.rating = rating;
    }
    
    public String authorString() {
    	StringBuilder sb = new StringBuilder();
    		
    	for (int i=0; i<this.authors.length; i++) {
    		sb.append(authors[i]+", ");
    	}
    	
    	String result = sb.toString();
    	return result.substring(0, result.length() - 2);
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("<" + this.isbn + ": '"+ this.title);
    	sb.append("' " + authorString() + ". Publisher: "+this.publisher);
    	sb.append(". Rating: " + String.format("%.2f",this.rating) +"> ");
    	return sb.toString();    
    }
    
    @Override
    public boolean equals(Object o) {
    	Book b = (Book)o;
    	return (this.isbn.equals(b.isbn));
    }

	@Override
	public int compareTo(Book b) {
		int res = this.isbn.compareTo(b.isbn);
		if (res !=0) return res;
		
		res = this.title.compareTo(b.title);
		if (res !=0) return res;
		
		res = this.authorString().compareTo(b.authorString());
		if (res !=0) return res;
		
		res = this.publisher.compareTo(b.publisher);
		if (res !=0) return res;
		
		if (this.rating > b.rating) return 1;
		if (this.rating < b.rating) return -1;		
		return 0;
	}
}