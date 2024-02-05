package ass1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Comparator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


class MyBagTest {
	public static final boolean DEBUG = true;
	
	public final String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	         + "0123456789"
	         + "abcdefghijklmnopqrstuvxyz"; 
	public final String digitString = "12345678910";
	
	@Test
	void testGetSize() {
		MyBag<Product> mybag = new MyBag<>(1);

		assertEquals(0, mybag.getSize());
		mybag.add(new Product("Milk", 2.15));
		assertEquals(1, mybag.getSize());
		mybag.add(new Product("Brittania", 2.15));
		assertEquals(1, mybag.getSize());
	}

	@Test
	void testIsEmpty() {
		MyBag<Product> mybag = new MyBag<>(10);
		
		// in assertEquals first is what we expect, second what the program produced
		assertEquals( 0, mybag.getSize());
		mybag.add(new Product("Milk", 2.15));
		assertFalse(mybag.isEmpty());
	}

	@Test
	void testAdd() {
		MyBag<Product> mybag = new MyBag<>(3);
		int initalSizeOfProducts = mybag.getSize();
		mybag.add(new Product("Milk", 2.15));
		assertEquals(initalSizeOfProducts + 1, mybag.getSize());
		Product p = new Product("Milk", 3.15);
		mybag.add(p);
		int pFrequecyBefore = mybag.getFrequencyOf(p);
		mybag.add(p);
		int pFrequencyAfter = mybag.getFrequencyOf(p);
		assertEquals(pFrequecyBefore+1, pFrequencyAfter);		
		assertFalse(mybag.add(new Product("Brittania", 5.34)));
	}

	@Test
	void testRemove() {
		MyBag<Product> mybag = new MyBag<>(2);
		assertFalse(mybag.remove(new Product("Brittania", 5.34)));
		Product p1 = new Product("Milk", 3.15);
		mybag.add(p1);
		Product p2 = new Product("Milk", 3.15);
		mybag.add(p2);
		int productSizeBefore = mybag.getSize();
		int pFrequecyBefore = mybag.getFrequencyOf(p1);
		mybag.remove(p1);
		int pFrequencyAfter = mybag.getFrequencyOf(p1);
		int productSizeAfter = mybag.getSize();
		assertEquals(pFrequecyBefore-1, pFrequencyAfter);
		assertEquals(productSizeBefore-1, productSizeAfter);
	}

	@Test
	void testClear() {
		MyBag<Product> mybag = new MyBag<>(2);
		mybag.add(new Product("Milk", 2.15));
		mybag.add(new Product("Bread", 4.15));
		mybag.clear();
		assertTrue(mybag.getSize() == 0);
	}

	@Test
	void testContains() {
		MyBag<Product> mybag = new MyBag<>(10);
		mybag.add(new Product("Milk", 2.15));
		mybag.add(new Product("Bread", 4.15));
		assertTrue(mybag.contains(new Product("Milk", 2.15)));
		assertFalse(mybag.contains(new Product("Chocolate Milk", 1.15)));
	}

	@Test
	void testGetFrequencyOf() {
		MyBag<Product> mybag = new MyBag<>(10);
		assertTrue(mybag.getFrequencyOf(new Product("Milk", 2.15)) == 0);

		mybag.add(new Product("Milk", 2.15));
		mybag.add(new Product("Bread", 4.15));
		mybag.add(new Product("Milk", 2.15));

		assertTrue(mybag.getFrequencyOf(new Product("Milk", 2.15)) == 2);
		assertTrue(mybag.getFrequencyOf(new Product("Bread", 4.15)) == 1);
	}	
	
	@Test
	void testSort() {		
		MyBag<Product> mybag = new MyBag<>(100);
		MyBag<Product> sameBag = new MyBag<>(100);
		int i=0; // we start at position 0
		for(; i<mybag.capacity/2; i++) {
			String randName = getRandString(alphaNumericString, 10);
			int randPrice = (int)(100 * Math.random());
			mybag.add(new Product(randName,randPrice));
			sameBag.add(new Product(randName,randPrice));
		}
		
		// the second half is filled with equal names
		for(; i<mybag.capacity; i++) {
			int randPrice = (int)(100 * Math.random());
			mybag.add(new Product("Same name",randPrice));
			sameBag.add(new Product("Same name",randPrice));
		}
		
		mybag.sort();
		sameBag.sort(new Comparator<Product>() {        
	        public int compare(Product p1, Product p2) {
	        	int byName = p1.getName().compareTo(p2.getName());
	        	if (byName == 0) {
	        		if (p1.getPrice() > p2.getPrice()) return 1;
	        		if (p1.getPrice() < p2.getPrice()) return -1;
	        		return 0;
	        	}    		
	            return byName;
	        }
	    });       
 
		assertEquals(mybag, sameBag);
		
		MyBag<Book> bookBag = new MyBag<>(100);
		MyBag<Book> sameBookBag = new MyBag<>(100);
		i=0; // we start at position 0
		for(; i<bookBag.capacity/2; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
			sameBookBag.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		
		// the second half is filled with equal isbn and title and authors
		for(; i<bookBag.capacity; i++) {
			String title = "Same title";			
			String [] authors = {"Same author"};
			String randPublisher = getRandString(alphaNumericString, 10);
			String ISBN = "111222333";
			int randRating = (int)(100 * Math.random());
			bookBag.add(new Book(ISBN, authors, title, randPublisher, (double) randRating));
			sameBookBag.add(new Book(ISBN, authors, title, randPublisher, (double) randRating));
		}
		
		bookBag.sort();
		sameBookBag.sort(new Comparator<Book>() {        
	        public int compare(Book b1, Book b2) {
	        	int res = b1.isbn.compareTo(b2.isbn);
	    		if (res !=0) return res;
	    		
	    		res = b1.title.compareTo(b2.title);
	    		if (res !=0) return res;
	    		
	    		res = b1.authorString().compareTo(b2.authorString());
	    		if (res !=0) return res;
	    		
	    		res = b1.publisher.compareTo(b2.publisher);
	    		if (res !=0) return res;
	    		
	    		if (b1.rating > b2.rating) return 1;
	    		if (b1.rating < b2.rating) return -1;		
	    		return 0;
	        }
	    });       
 
		assertEquals(bookBag, sameBookBag);			
	}
	
//==============================================================
	// Multi-set operations
	@Test
	void testUnion() {
		Character [] arrX =  {'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n'};
		Character [] arrY =  {'l', 'm', 'm', 'm', 'n'};
		MyBag<Character> X = new MyBag<>(10);		
		for (int i=0; i<arrX.length; i++) {
			X.add(arrX[i]);
		}
		
		MyBag<Character> Y = new MyBag<>(10);		
		for (int i=0; i<arrY.length; i++) {
			Y.add(arrY[i]);
		}
		
		Character [] arrUnion = {'l', 'l', 'm', 'm', 'm', 'n', 'n', 'n', 'n' };
		MyBag<Character> union = new MyBag<>(10);		
		for (int i=0; i<arrUnion.length; i++) {
			union.add(arrUnion[i]);
		}
		
		if (DEBUG) System.out.println(System.lineSeparator()+ X 
				+ " UNION " + Y + " = " + X.union(Y));
		assertEquals(union, X.union(Y));		
	}

	@Test
	void testIntersection() {
		Character [] arrX =  {'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n'};
		Character [] arrY =  {'l', 'm', 'm', 'm', 'n'};
		MyBag<Character> X = new MyBag<>(10);		
		for (int i=0; i<arrX.length; i++) {
			X.add(arrX[i]);
		}
		MyBag<Character> Y = new MyBag<>(10);		
		for (int i=0; i<arrY.length; i++) {
			Y.add(arrY[i]);
		}
		
		Character [] arrIntersect = {'l', 'm', 'm', 'n'};
		MyBag<Character> intersect = new MyBag<>(10);		
		for (int i=0; i<arrIntersect.length; i++) {
			intersect.add(arrIntersect[i]);
		}
		if (DEBUG) System.out.println(System.lineSeparator()+ X +
				" INTERSECT " +Y +" = "+ X.intersection(Y));
		assertEquals(intersect, X.intersection(Y));		
	}

	@Test
	void testDifference() {
		Character [] arrX =  {'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n'};
		Character [] arrY =  {'l', 'm', 'm', 'm', 'n'};		
		
		MyBag<Character> X = new MyBag<>(10);		
		for (int i=0; i<arrX.length; i++) {
			X.add(arrX[i]);
		}
		MyBag<Character> Y = new MyBag<>(10);		
		for (int i=0; i<arrY.length; i++) {
			Y.add(arrY[i]);
		}
		
		Character [] arrDiff = {'l', 'n', 'n', 'n'};
		MyBag<Character> diff = new MyBag<>(10);		
		for (int i=0; i<arrDiff.length; i++) {
			diff.add(arrDiff[i]);
		}
		if (DEBUG) System.out.println(System.lineSeparator()+ X +
				" MINUS " +Y +" = "+ X.difference(Y));
		assertEquals(diff, X.difference(Y));
	}

	@Test
	void testIsSubBagOf() {
		Character [] arrX =  {'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n'};
		Character [] arrY =  {'l', 'm', 'm', 'm', 'n'};
		Character [] arrZ =  {'l', 'm', 'm', 'n'};
		
		MyBag<Character> X = new MyBag<>(10);		
		for (int i=0; i<arrX.length; i++) {
			X.add(arrX[i]);
		}
		MyBag<Character> Y = new MyBag<>(10);		
		for (int i=0; i<arrY.length; i++) {
			Y.add(arrY[i]);
		}
		MyBag<Character> Z = new MyBag<>(10);		
		for (int i=0; i<arrZ.length; i++) {
			Z.add(arrZ[i]);
		}
		
		if (DEBUG) {
			System.out.println("\nSets:");
			System.out.println("X:"+X + ", Y:" + Y + ", Z:"+Z);
			System.out.println("X sub-multiset of Y?: "+ X.isSubBagOf(Y));		
			System.out.println("Y sub-multiset of X?: "+ Y.isSubBagOf(X));
			System.out.println("Z sub-multiset of X?: "+ Z.isSubBagOf(X));
			System.out.println("Z sub-multiset of Y?: "+ Z.isSubBagOf(Y));
		}
		assertTrue(Z.isSubBagOf(X));
		assertTrue(Z.isSubBagOf(Y));
		assertFalse(X.isSubBagOf(Y));
		assertFalse(Y.isSubBagOf(X));
	}

	@Test
	void testIsSuperBagOf() {
		Character [] arrX =  {'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n'};
		Character [] arrY =  {'l', 'm', 'm', 'm', 'n'};
		Character [] arrZ =  {'l', 'm', 'm', 'n'};
		
		MyBag<Character> X = new MyBag<>(10);		
		for (int i=0; i<arrX.length; i++) {
			X.add(arrX[i]);
		}
		MyBag<Character> Y = new MyBag<>(10);		
		for (int i=0; i<arrY.length; i++) {
			Y.add(arrY[i]);
		}
		MyBag<Character> Z = new MyBag<>(10);		
		for (int i=0; i<arrZ.length; i++) {
			Z.add(arrZ[i]);
		}
		
		if (DEBUG) {
			System.out.println("\nSets:");
			System.out.println("X:"+X + ", Y:" + Y + ", Z:"+Z);
			System.out.println("X super-multiset of Y?: "+ X.isSuperBagOf(Y));
			System.out.println("X super-multiset of Z?: "+ X.isSuperBagOf(Z));
			System.out.println("Y super-multiset of Z?: "+ Y.isSuperBagOf(Z));
			System.out.println("Z super-multiset of Y?: "+ Z.isSuperBagOf(Y));
		}
		assertTrue(X.isSuperBagOf(Z));
		assertTrue(Y.isSuperBagOf(Z));
		assertFalse(X.isSuperBagOf(Y));
		assertFalse(Z.isSuperBagOf(Y));
	}

	@Test
	void testSum() {
		Character [] arrX =  {'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n'};
		Character [] arrY =  {'l', 'm', 'm', 'm', 'n'};
		MyBag<Character> X = new MyBag<>(10);		
		for (int i=0; i<arrX.length; i++) {
			X.add(arrX[i]);
		}
		
		MyBag<Character> Y = new MyBag<>(10);		
		for (int i=0; i<arrY.length; i++) {
			Y.add(arrY[i]);
		}
		
		Character [] arrSum = {'l', 'l', 'l', 'm', 'm','m','m','m', 'n', 'n', 'n', 'n', 'n'};
		MyBag<Character> sum = new MyBag<>(15);		
		for (int i=0; i<arrSum.length; i++) {
			sum.add(arrSum[i]);
		}
		
		assertEquals(sum, X.sum(Y));
	}
	
	@Test
	void testCardinality() {
		Character [] arrX =  {'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n'};
		Character [] arrY =  {'l', 'n', 'n', 'n', 'n'};
		Character [] arrZ =  {'m', 'm', 'm', 'm'};
		
		MyBag<Character> X = new MyBag<>(10);		
		for (int i=0; i<arrX.length; i++) {
			X.add(arrX[i]);
		}
		MyBag<Character> Y = new MyBag<>(10);		
		for (int i=0; i<arrY.length; i++) {
			Y.add(arrY[i]);
		}
		MyBag<Character> Z = new MyBag<>(10);		
		for (int i=0; i<arrZ.length; i++) {
			Z.add(arrZ[i]);
		}
		MyBag<Character> empty = new MyBag<>(10);
		
		// in assertEquals first is what we expect, second what the program produced
		assertEquals( 3, X.cardinality());
		assertEquals( 2, Y.cardinality());
		assertEquals( 1, Z.cardinality());
		assertEquals( 0, empty.cardinality());
	}

	//==============================================
	// Timing  -- performance
	@Timeout(10)
	@Test
	void testToStringEfficiency() {
		MyBag<Product> mybag = new MyBag<>(10000000);
    	for (int i=0; i<10000000; i++) {
    		mybag.add(new Product("Prod "+i, i));
		}
		assertTimeout(Duration.ofSeconds(5), () -> {	
			long start = System.currentTimeMillis();
			mybag.toString();
			long end = System.currentTimeMillis();
			if (DEBUG) System.out.println("\nTIMING: toString runs in: " + (end - start)/1000 + "sec");		   
	    });
	} 

	
	@Timeout(15)
	@Test
	void testIntersectionEfficiency() {
		MyBag<Book> bookBag1 = new MyBag<>(100000);
		MyBag<Book> bookBag2 = new MyBag<>(100000);
		
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag1.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag2.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		assertTimeout(Duration.ofSeconds(5), () -> {	
			long start = System.currentTimeMillis();
			bookBag1.intersection(bookBag2);
			long end = System.currentTimeMillis();
			if (DEBUG) System.out.println("\nTIMING: Intersection runs in: " + (end - start)/1000 + "sec");		   
	    });
	} 
	
	@Timeout(15)
	@Test
	void testUnionEfficiency() {
		MyBag<Book> bookBag1 = new MyBag<>(100000);
		MyBag<Book> bookBag2 = new MyBag<>(100000);
		
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag1.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag2.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		assertTimeout(Duration.ofSeconds(5), () -> {	
			long start = System.currentTimeMillis();
			bookBag1.union(bookBag2);
			long end = System.currentTimeMillis();
			if (DEBUG) System.out.println("\nTIMING: Union runs in: " + (end - start)/1000 + "sec");		   
	    });
	} 

	@Timeout(15)
	@Test
	void testDifferenceEfficiency() {
		MyBag<Book> bookBag1 = new MyBag<>(100000);
		MyBag<Book> bookBag2 = new MyBag<>(100000);
		
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag1.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag2.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		assertTimeout(Duration.ofSeconds(5), () -> {	
			long start = System.currentTimeMillis();
			bookBag1.difference(bookBag2);
			long end = System.currentTimeMillis();
			if (DEBUG) System.out.println("\nTIMING: Difference runs in: " + (end - start)/1000 + "sec");		   
	    });
	} 
	
	@Timeout(15)
	@Test
	void testCardinalityEfficiency() {
		MyBag<Book> bookBag1 = new MyBag<>(100000);
		MyBag<Book> bookBag2 = new MyBag<>(100000);
		
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag1.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		for(int i=0; i<100000; i++) {
			String randTitle = getRandString(alphaNumericString, 10);			
			String [] randAuthors = {getRandString(alphaNumericString, 10)};
			String randPublisher = getRandString(alphaNumericString, 10);
			String randISBN = getRandString(digitString, 10);
			int randRating = (int)(100 * Math.random());		
			bookBag2.add(new Book(randISBN, randAuthors, randTitle, randPublisher, (double) randRating));
		}
		assertTimeout(Duration.ofSeconds(5), () -> {	
			long start = System.currentTimeMillis();
			bookBag1.cardinality();
			long end = System.currentTimeMillis();
			if (DEBUG) System.out.println("\nTIMING: Difference runs in: " + (end - start)/1000 + "sec");		   
	    });
	} 
	//=================================================
	//Utility methods
	private static String getRandString(String alphabet, int len) {  
	  // create StringBuffer size of AlphaNumericString 
	  StringBuilder sb = new StringBuilder(len); 
	 
	  for (int i = 0; i < len; i++) { 	 
		  // generate a random number between 
		  // 0 to AlphaNumericString variable length 
		  int index = (int)(alphabet.length() * Math.random()); 
		  // choose a Character random from the alphabet
		  // add Character one by one in end of sb 
		  sb.append(alphabet.charAt(index)); 
	  } 
	 
	  return sb.toString(); 
	} 
}
