package ass1;


public class Product implements Comparable<Product>{
	private String name;
	private double price;
	
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public boolean equals(Product p) {
		return (this.name.equals(p.name) && this.price == p.price);
	}
	
	@Override
	public boolean equals(Object o) {
		Product p = (Product)o;
		return (this.name.equals(p.name) && this.price == p.price);
	}
	
	@Override
	public String toString() {
		return this.name +": " + this.price;
	}

	@Override
	public int compareTo(Product p) {
		int byName = this.getName().compareTo(p.getName());
    	if (byName == 0) {
    		if (this.getPrice() > p.getPrice()) return 1;
    		if (this.getPrice() < p.getPrice()) return -1;
    		return 0;
    	}    		
        return byName;
	}	
}
