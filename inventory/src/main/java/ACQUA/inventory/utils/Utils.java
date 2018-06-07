package ACQUA.inventory.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {
	public static String generateID(){
		Random random = new Random();
		StringBuffer id=new StringBuffer("");
		String chars="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int n = chars.length();
		for(int i=0;i<6;i++){
			id=id.append(chars.charAt(random.nextInt(n)));
		}
		return id.toString();
	}
	
	public static String generateBatch(String name){
		StringBuffer batchNo=new StringBuffer();
		SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	    //Date currentDate=new Date();
	    Date currentDate = Calendar.getInstance().getTime();
		try {
			currentDate = sd.parse(sd.format(currentDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	    LocalDateTime localdate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		String [] array=name.split(" ");
		if(array.length==1){
			try{
				batchNo.append(array[0].substring(0, 5));
				batchNo.append("-");
			}
			catch(Exception e){
				System.out.println("Exception occured: "+e.getMessage());
				batchNo.append(array[0]);
				batchNo.append("-");
			}
		}
		else if(array.length > 1){
			for(String word:array){
				batchNo.append(word.substring(0,2));
				batchNo.append("-");
			}
		}
		batchNo.append(localdate.getDayOfMonth());
		batchNo.append(localdate.getMonthValue());
		batchNo.append(String.valueOf(localdate.getYear()).substring(2));
		batchNo.append(localdate.getHour());
		batchNo.append(localdate.getMinute());
		System.out.println(batchNo);
		return batchNo.toString();
	}
}
