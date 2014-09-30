package lab7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class vMem {

	private RandomAccessFile _datastore;
	private int partofsection;
	private long[] longlist;
	private int currentsection;

	public vMem(int arraysize, int VIRTRATIO) throws IOException {
		
		RandomAccessFile storedata= new RandomAccessFile ("VMem.txt", "rw");
		_datastore = storedata;  //writing dummies to my new file
		
		for(int i=0;i<arraysize*2;i++){ 
			storedata.writeLong(100);
		}
		
		
		partofsection = arraysize/VIRTRATIO; 
		longlist = new long[partofsection];
		currentsection=0;
		_datastore.seek(0); //I want to only read from the beginning to that piece
		for(int i=0; i<partofsection;i++){
			longlist[i]=_datastore.readLong();
		}
		
	}

	public void put(int index, long value) throws IOException {//writing old array and new section in it than swapping
		
		_datastore.seek((partofsection*currentsection)*8);
		for(int i=0; i<partofsection; i++){
			
			_datastore.writeLong(longlist[i]);//Writes a long to the file as eight bytes, high byte first. The write starts at the current position of the file pointe
			
		}
		_datastore.seek((index/partofsection)*(partofsection*8));
		for(int i=0; i<partofsection;i++){
			longlist[i]=_datastore.readLong();
		}
		
		currentsection=index/partofsection; //swap start
		 
		longlist[index%partofsection] =value;   //operator % :remainder of division
	}
	
	public long get(int index) throws IOException {
		
		if(index/partofsection != currentsection){//if it doesn't equal?
			
			_datastore.seek((index/partofsection)*(partofsection*8));//retrieve the value from the new section
			for(int i=0; i<partofsection;i++){
				longlist[i]=_datastore.readLong();
			}
			
			currentsection=index/partofsection; //change it by assigning to currentsection
			return longlist[index%partofsection];
		}
		return longlist[index%partofsection];
	}
	

public void Nowdone() throws IOException{
	_datastore.seek(currentsection*partofsection*8);
	for(int i = 0; i < partofsection; i++){
		_datastore.writeLong(longlist[i]);
	}
}
}
	
