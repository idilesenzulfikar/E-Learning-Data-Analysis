import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.dao.LvTransDaoImp;
import com.dao.SEtestResultDaoImp;
import com.dao.TMGDaoImp;
import com.entities.StudentEtestResult;
import com.entities.TMG;
import com.util.ConnectionConfiguration;

public class App {
	private static final String FILENAME = "/Users/idilesenzulfikar/Desktop/tmg/tmg_oct.sh";
	
    public static void main(String[] args){
    	SEtestResultDaoImp serdi = new SEtestResultDaoImp();
    	TMGDaoImp tmgdi = new TMGDaoImp();
    	LvTransDaoImp lvtdi = new LvTransDaoImp();
    	Scanner reader = new Scanner(System.in);  // Reading from System.in
  		System.out.println("Enter source table name: ");
    	String tableName = reader.nextLine();
    	System.out.println("Enter insertion table name: ");
    	String tableName2 = reader.nextLine(); 
    	
    	serdi.createStudentEtestResultTable(tableName); 
    	tmgdi.createTMGTable("TMG_October");
    	
    	// etestResults15_16 studentetestWS1516
    	// etestResults16_17 studentetestWS1617
    	List<StudentEtestResult> ser = serdi.selectAllEtestResult("etestResults16_17");
    	for(StudentEtestResult s:ser){
    		serdi.insertStudentEtestResult(s, "studentetestWS1617");
    	} 
    	
    	String s ="http://www3.elearning.rwth-aachen.de/ss16/16ss-06119/collaboration/Lists/DiscussionForum/Flat.aspx?RootFolder=%2fss16%2f16ss%2d06119%2fcollaboration%2fLists%2fDiscussionForum%2fKlausureinsicht%20%20Ergebnisse&FolderCTID=0x01200200266031FC38DEED44866632C657314CCD";
    	String s1 = s.replace("http://www3.elearning.rwth-aachen.de/","");
    	String semester = s1.substring( 0, s1.indexOf("/"));
    	String sr = s1.substring(s1.indexOf("/")+1, s1.length()); 
    	String lvNumber = sr.substring( 0, sr.indexOf("/"));
    	String uri = sr.substring(sr.indexOf("/")+1, sr.length()); 
    	Boolean isDiscussion = uri.toLowerCase().contains("discussionforum"); 	
    	
    	String u = "http://www3.elearning.rwth-aachen.de/ws15/15ws-04213";

    	
    	tmgdi.preProcessTMG(FILENAME);
    	tmgdi.preProcessTMGlvNumber("/Users/idilesenzulfikar/Desktop/tmg_lvNumber/tmg_jan_lvNumber.sh");
    	try {
			tmgdi.findAlllvNumber("/Users/idilesenzulfikar/Desktop/tmg_jan_lvNumber.sh");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	tmgdi.findAllUniquelvNumber("/Users/idilesenzulfikar/Desktop/tmg_AlllvNumber.sh");
    	
    	tmgdi.findAllStudent("/Users/idilesenzulfikar/Desktop/tmg_jan_lvNumber.sh");
    	tmgdi.findAllUniqueStudent("/Users/idilesenzulfikar/Desktop/tmg_AllStudent.sh");
    	String file_tmg_AllStudent = "/Users/idilesenzulfikar/Desktop/tmg_Student.sh";
    	String file_AlllvNumber = "/Users/idilesenzulfikar/Desktop/tmg_lvNumber.sh";
    	String file_tmglvNumber = "/Users/idilesenzulfikar/Desktop/tmg_Student_lvNumber.sh";
    	
    	tmgdi.generateStudentlvNumber(file_tmg_AllStudent, file_AlllvNumber, file_tmglvNumber);
    	
    	lvtdi.transactionDuplicates("/Users/idilesenzulfikar/Desktop/data2.sh");
     	 
     }
}
