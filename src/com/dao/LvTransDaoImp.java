package com.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.util.CSVUtils;

public class LvTransDaoImp implements LvTransDao {

	@Override
	public void transactionDuplicates(String fileName) {
		BufferedReader br = null;
		FileReader fr = null;
		HashSet<List<String>> transactions = new HashSet<List<String>>();
			
		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			int counter = 0;
				
			while ((sCurrentLine = br.readLine()) != null) {
					
				String[] ar=sCurrentLine.split(",");
					
				List<String> trns = new ArrayList<String>();
					
				for(int i = 0 ; i < ar.length;i++){
					trns.add(ar[i]);
				}
				
				java.util.Collections.sort(trns);

				transactions.add(trns);

	
				counter++;
				System.out.println(counter);
			}
		}catch (IOException e) {

			e.printStackTrace();

		}finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			}catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		String csvFile = "/Users/idilesenzulfikar/Desktop/transactions.csv";
	    FileWriter writer;
		try {
			writer = new FileWriter(csvFile);
			//CSVUtils.writeLine(writer, Arrays.asList("a", "b", "c", "d"));
			Iterator hashSetIterator = transactions.iterator();
		    while(hashSetIterator.hasNext()){
		    	CSVUtils.writeLine(writer,(List<String>) hashSetIterator.next());
		    }

			writer.flush();
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
