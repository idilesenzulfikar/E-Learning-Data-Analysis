package com.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.entities.StudentEtestResult;
import com.entities.TMG;
import com.util.CSVUtils;
import com.util.ConnectionConfiguration;

public class TMGDaoImp implements TMGDao{

	@Override
	public void createTMGTable(String tableName) {
		
		Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id int primary key unique auto_increment," +
                    "logtime varchar(150),clientip varchar(50),operation varchar(10),semester varchar(100),LVNumber varchar(100),uri varchar(10000)," +
                    "activity varchar(250),isDiscussion boolean,discussionTopic varchar(1000))");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		
	}

	@Override
	public void insertTMG(TMG tmg, String tableName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO " + tableName +
										" (logtime,clientip,operation,semester,LVNumber,uri,activity,isDiscussion,discussionTopic)" + 
										"VALUES (?,?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1,tmg.getLogTime());
			preparedStatement.setString(2,tmg.getClientIp());
			preparedStatement.setString(3,tmg.getOperation());
			preparedStatement.setString(4,tmg.getSemester());
			preparedStatement.setString(5,tmg.getLvNumber());
			preparedStatement.setString(6,tmg.getUri());
			preparedStatement.setString(7,tmg.getActivity());
			preparedStatement.setBoolean(8,tmg.getIsDiscussion());
			preparedStatement.setString(9,tmg.getDiscussionTopic());
			
			preparedStatement.executeUpdate();
			/*System.out.println("INSERT INTO " + tableName +
								" (mailAdresse,clientip,operation,semester,LVNumber,uri,activity,isDiscussion,discussionTopic)" + 
					"VALUES (?,?,?,?,?,?,?,?,?)"); */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public TMG selectTMG(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TMG> selectAllTMG(String tableName) {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		int i = 0;
		int j = 0;
		int counter = 0;
		
		try {
			connection = ConnectionConfiguration.getConnection2();
			statement = connection.createStatement();
			
			do{
			resultset = statement.executeQuery("SELECT * FROM " + tableName + " LIMIT "+ j + "," + 10000);
			List<TMG> tmg = new ArrayList<TMG>();
			
			while(resultset.next()) {
				String logTime = resultset.getString("logtime");
				String clientIp = resultset.getString("clientip");
				String operation = resultset.getString("operation");
				String u = resultset.getString("uri");
				
				counter++;
				System.out.println("Counter: " + counter);
				
				TMG t = new TMG();
				t.setId(i+1);
				t.setLogTime(logTime);
				t.setClientIp(clientIp);
				t.setOperation(operation);
				
				String s1 = u.replace("http://www3.elearning.rwth-aachen.de/","");
				if(s1.toLowerCase().contains("/")){
			    	String semester = s1.substring( 0, s1.indexOf("/"));
			    	if(semester.toLowerCase().contains("ss") || semester.toLowerCase().contains("ws")){
			    		String sr = s1.substring(s1.indexOf("/")+1, s1.length());
			    		if(sr.toLowerCase().contains("/")){
				    	String lvNumber = sr.substring( 0, sr.indexOf("/"));
				    	String uri = sr.substring(sr.indexOf("/")+1, sr.length()); 
				    	Boolean isDiscussion = uri.toLowerCase().contains("discussionforum");
						
						t.setLvNumber(lvNumber);
				    	t.setIsDiscussion(isDiscussion);
						t.setUri(uri);
				    	
			    		}else{
				    	String lvNumber = sr.substring( 0,sr.length());
						t.setIsDiscussion(false);
						t.setLvNumber(lvNumber);
				    	}

						t.setSemester(semester);
						t.setActivity(null);

						t.setDiscussionTopic(null);
						tmg.add(t);
						i++;
				    	System.out.println("Entery: " + i);
			    	}
				}
			}
			for(TMG t:tmg){
				insertTMG(t, tableName);
	    	} 
			j = j + 10000;
			System.out.println("Limit: " + j);
			}while(resultset != null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public void preProcessTMG(String fileName){
		
		BufferedReader br = null;
		FileReader fr = null;
		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();

		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			int counter = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] ar=sCurrentLine.split("\t");
				// System.out.println(ar[0]); logtime
				// System.out.println(ar[1]); clientip
				// System.out.println(ar[2]); operation
				// System.out.println(ar[3]); uri

				if(ar.length > 1){
					String clientip = ar[1];
					String s1 = ar[3].replace("http://www3.elearning.rwth-aachen.de/","");
					if(s1.toLowerCase().contains("/")){
				    	String semester = s1.substring( 0, s1.indexOf("/"));
				    	if(!semester.toLowerCase().contains("siteassets")){
				    		if((semester.toLowerCase().contains("ss") || semester.toLowerCase().contains("ws"))){
					    		String sr = s1.substring(s1.indexOf("/")+1, s1.length());
					    		String lvNumber = null;
					    		if(sr.toLowerCase().contains("/")){
					    			lvNumber = sr.substring( 0, sr.indexOf("/"));
					    		}else{
					    			lvNumber = sr.substring( 0,sr.length());
						    	}

					    		if(map.containsKey(clientip)){
					    			map.get(clientip).add(lvNumber);
					    		}else{
					    			HashSet<String> hs = new HashSet<String>();
					    			hs.add(lvNumber);
					    			map.put(clientip, hs);
					    		}
					    	}
				    	}
					} 
				} 
				counter++;
				System.out.println(counter);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		try{
		    PrintWriter writer = new PrintWriter("/Users/idilesenzulfikar/Desktop/tmg_lvNumber/tmg_oct_lvNumber.sh", "UTF-8");
		    for (Map.Entry<String, HashSet<String>> entry : map.entrySet()){
		    	writer.println(entry.getKey() + "\t" + entry.getValue());
	        }
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public void preProcessTMGlvNumber(String fileName){
		
		BufferedReader br = null;
		FileReader fr = null;
		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
		
		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			int counter = 0;
			
			while ((sCurrentLine = br.readLine()) != null) {
				HashSet<String> lvNumbers = new HashSet<String>();
				
				String[] ar=sCurrentLine.split("\t");
				if(ar.length>1){
					String clientip = ar[0];
					String s1 = ar[1];
					if(s1.contains(",")){
						String[] slv = s1.split(",");
						for(int i=0;i<slv.length;i++){
							if(slv[i].contains("?")){
								slv[i]=slv[i].substring(0, slv[i].indexOf("?"));
							}
							lvNumbers.add(slv[i]);
						}
					}else if(s1.contains("?")){
						s1=s1.substring(0, s1.indexOf("?"));
						lvNumbers.add(s1);
					}else{
						lvNumbers.add(s1);
					}
						
					map.put(clientip, lvNumbers);
				}
				counter++;
				System.out.println(counter);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		try{
		    PrintWriter writer = new PrintWriter("/Users/idilesenzulfikar/Desktop/tmg_jan_lvNumber.sh", "UTF-8");
		    for (Map.Entry<String, HashSet<String>> entry : map.entrySet()){
		    	writer.println(entry.getKey() + "\t" + entry.getValue());
	        }
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
		
	}
	
	public void findAlllvNumber(String fileName) throws IOException
	{
		BufferedReader br = null;
		FileReader fr = null;
		HashSet<String> lvNumbers = new HashSet<String>();
		
		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			int counter = 0;
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] ar=sCurrentLine.split("\t");
				if(ar.length>1){
					String s1 = ar[1];
					if(s1.contains(",")){
						String[] slv = s1.split(",");
						for(int i=0;i<slv.length;i++){
							lvNumbers.add(slv[i]);
						}
					}else{
						lvNumbers.add(s1);
					}
				}
				counter++;
				System.out.println(counter);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
		    fw = new FileWriter("/Users/idilesenzulfikar/Desktop/tmg_AlllvNumber.sh", true);
		    bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    Iterator hashSetIterator = lvNumbers.iterator();
		    while(hashSetIterator.hasNext()){
		    	out.println(hashSetIterator.next());
		    }
		    out.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		finally {
		    if(out != null)
			    out.close();
		    try {
		        if(bw != null)
		            bw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		    try {
		        if(fw != null)
		            fw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		}
		return;

	}
	
	public void findAllUniquelvNumber(String fileName){

		BufferedReader br = null;
		FileReader fr = null;
		HashSet<String> lvNumbers = new HashSet<String>();
			
			try {

				fr = new FileReader(fileName);
				br = new BufferedReader(fr);

				String sCurrentLine;

				br = new BufferedReader(new FileReader(fileName));

				int counter = 0;
				
				while ((sCurrentLine = br.readLine()) != null) {
					
						if(sCurrentLine.contains("ws")||sCurrentLine.contains("ss")){
							lvNumbers.add(sCurrentLine);
						}
					counter++;
					System.out.println(counter);
				}
			}catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (br != null)
						br.close();

					if (fr != null)
						fr.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
			
			try{
			    PrintWriter writer = new PrintWriter("/Users/idilesenzulfikar/Desktop/tmg_lvNumber.sh", "UTF-8");
			    Iterator hashSetIterator = lvNumbers.iterator();
			    while(hashSetIterator.hasNext()){
			    	writer.println(hashSetIterator.next());
			    }
			    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		return;
	}
	
	public void findAllStudent(String fileName){
		
		BufferedReader br = null;
		FileReader fr = null;
		HashSet<String> students = new HashSet<String>();
		
		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			int counter = 0;
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] ar=sCurrentLine.split("\t");
				String clientip = ar[0];
				students.add(clientip);
				counter++;
				System.out.println(counter);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
		    fw = new FileWriter("/Users/idilesenzulfikar/Desktop/tmg_AllStudent.sh", true);
		    bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    Iterator hashSetIterator = students.iterator();
		    while(hashSetIterator.hasNext()){
		    	out.println(hashSetIterator.next());
		    }
		    out.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		finally {
		    if(out != null)
			    out.close();
		    try {
		        if(bw != null)
		            bw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		    try {
		        if(fw != null)
		            fw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		}
		return;

		
	}

	public void findAllUniqueStudent(String fileName){
		
		BufferedReader br = null;
		FileReader fr = null;
		HashSet<String> student = new HashSet<String>();
			
			try {

				fr = new FileReader(fileName);
				br = new BufferedReader(fr);

				String sCurrentLine;

				br = new BufferedReader(new FileReader(fileName));

				int counter = 0;
				
				while ((sCurrentLine = br.readLine()) != null) {
				
					student.add(sCurrentLine);

					counter++;
					System.out.println(counter);
				}
			}catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (br != null)
						br.close();

					if (fr != null)
						fr.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
			
			try{
			    PrintWriter writer = new PrintWriter("/Users/idilesenzulfikar/Desktop/tmg_Student.sh", "UTF-8");
			    Iterator hashSetIterator = student.iterator();
			    while(hashSetIterator.hasNext()){
			    	writer.println(hashSetIterator.next());
			    }
			    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		return;
	}
	
	public void generateStudentlvNumber(String file_tmg_AllStudent,String file_AlllvNumber,String file_tmglvNumber){
		
//		BufferedReader br = null;
//		BufferedReader br2 = null;
		BufferedReader br3 = null;
		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
//		Map<String, HashMap<String,Integer>> map = new HashMap<String, HashMap<String,Integer>>();
//		HashMap<String,Integer> hsm = new HashMap<String,Integer>();
		
		try {

			String sCurrentLine;

//			br = new BufferedReader(new FileReader(file_AlllvNumber));
//			br2 = new BufferedReader(new FileReader(file_tmg_AllStudent));
			br3 = new BufferedReader(new FileReader(file_tmglvNumber));	
			int counter = 0;
/*			
			while ((sCurrentLine = br.readLine()) != null) {
				hsm.put(sCurrentLine,0);
				counter++;
			}
			
			System.out.println("All-lvNumbers file is read as: " + counter + " lines.");
			counter = 0;*/

/*			while ((sCurrentLine = br2.readLine()) != null) {
				map.put(sCurrentLine, hsm);
				counter++;
			}
			
			System.out.println("All-student file is read as: " + counter + " lines.");
			counter = 0;*/
			
			
			while((sCurrentLine =br3.readLine()) != null){
				String[] ar=sCurrentLine.split("\t");
				if(ar.length>1){
					String clientip = ar[0];
					String s1 = ar[1];
					s1 = s1.replaceAll("\\s+","");
					if(s1.contains(",")){
						String[] slv = s1.split(",");
						for(int i=0;i<slv.length;i++){
							if(map.containsKey(clientip)){
				    			map.get(clientip).add(slv[i]);
				    		}else{
				    			HashSet<String> hs = new HashSet<String>();
				    			hs.add(slv[i]);
				    			map.put(clientip, hs);
				    		}
						}
					}else{
						if(map.containsKey(clientip)){
			    			map.get(clientip).add(s1);
			    		}else{
			    			HashSet<String> hs = new HashSet<String>();
			    			hs.add(s1);
			    			map.put(clientip, hs);
			    		}
					}
				}
				counter++;	
			}

			System.out.println("All-student-lvNumber file is generated from: " + counter + " lines.");
			counter = 0;
			

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

//				if (br != null)
//					br.close();

//				if (br2 != null)
//					br2.close();
				
				if (br3 != null)
					br3.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
//		try{
//		    PrintWriter writer = new PrintWriter("/Users/idilesenzulfikar/Desktop/tmgStudentlvNumber.txt", "UTF-8");
//		    Set set = hsm.entrySet();
//		    Iterator i = set.iterator();
//		    writer.print("\t");
//		    while(i.hasNext()) {
//		    	Map.Entry me = (Map.Entry)i.next();
//	            writer.print(me.getKey()+ "\t");
//		    }
//		    writer.print("\n");
//		    for (Map.Entry<String, HashMap<String,Integer>> entry : map.entrySet()){
//		    	writer.print(entry.getKey() + "\t");
//		    	set = entry.getValue().entrySet();
//		    	i = set.iterator();
//		    	while(i.hasNext()) {
//		            Map.Entry me = (Map.Entry)i.next();
//		            writer.print(me.getValue() + "\t");
//		        }
//		    	writer.print("\n");
//	        }
//		    writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		String csvFile = "/Users/idilesenzulfikar/Desktop/tmg-Student-lvNumber.csv";
	    FileWriter writer;
		try {
			writer = new FileWriter(csvFile);
			//CSVUtils.writeLine(writer, Arrays.asList("a", "b", "c", "d"));
			
		    for (Map.Entry<String, HashSet<String>> entry : map.entrySet()){
		    	List<String> list = new ArrayList<String>(entry.getValue());
		    	//list.add(entry.getKey());
		    	CSVUtils.writeLine(writer, list);
	        }
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return;
	}
	
	
	@Override
	public void deleteTMG(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTMG(TMG tmg, int id) {
		// TODO Auto-generated method stub
		
	}

}
