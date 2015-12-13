import java.util.*;
import java.lang.*;
import java.io.*;

public class PdfToBase64{
	public static void main(String[] args) {
		String pathName = "Yang Deng's Resume.pdf";
		PdfToBase64 test = new PdfToBase64();
		test.convert(pathName);
	}
	private void convert(String pathName){
		File file = new File(pathName);
		if(!file.exists()){
			System.out.println("The file can't be found!");
			return;
		}

		FileInputStream input = null;
		try{
			input = new FileInputStream(file);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		// System.out.println(file.length());

		byte[] fileContent = new byte[(int)file.length()];
		try{
			//Reads up to fileContent.length bytes of data from this input stream into an array of bytes.
			input.read(fileContent);
		}catch(IOException e){
			e.printStackTrace();
		}

		String result = javax.xml.bind.DatatypeConverter.printBase64Binary(fileContent);
		System.out.println(result);
	}
}
