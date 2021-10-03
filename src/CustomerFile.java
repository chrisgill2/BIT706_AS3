import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class CustomerFile {
    
    public void writeToFile(List<Customer> customerList) {
    	try {
//    		thing -- clear file
//    		new FileOutputStream("customers.txt").close();
			FileOutputStream f = new FileOutputStream(new File("customers.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write customer objects to file
			o.writeObject(customerList);

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public List<Customer> readFromFile() {
    	try {
			FileInputStream fi = new FileInputStream(new File("customers.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			@SuppressWarnings("unchecked")
			List <Customer> customer = (List<Customer>) oi.readObject();

			oi.close();
			fi.close();
			return customer;

		} catch (Exception e) {
			return null;
		}
    }
}
