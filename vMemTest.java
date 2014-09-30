package lab7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class vMemTest {

	private final static int ARRAYSIZE = 1000;
	private final static int VIRTRATIO = 100;


	public static void main(String[] args) throws FileNotFoundException, IOException{

		vMem array = new vMem(ARRAYSIZE, ARRAYSIZE/VIRTRATIO);

		Random random = new Random();

		int index;
		long value;
		long gotten = 0;
		long startTime = System.nanoTime();

		for(int i = 0; i < ARRAYSIZE*ARRAYSIZE; i++){

			//pick random index and value to put in
			index = random.nextInt(ARRAYSIZE);
			value = random.nextLong();

			try{

				array.put(index, value);
				gotten = array.get(index);
			}
			catch(IndexOutOfBoundsException e){

				long upper = ARRAYSIZE - 1;
				System.out.println("The value " + index + " is outside 0.." + upper);
			}


			//check for error
			if(gotten != value){
				System.out.println("error at " +i);
				return;
			}
		}  

		long endTime = System.nanoTime();
		System.out.println("Elapsed time (ms) is "+ (endTime - startTime)/1000000);
		
		System.out.println("Test complete");


	}
}
	