package utility;



import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;






import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import exceptions.ElementNotFoundException;

public class ExcelReader {
	Logger LOGGER;  
	String strCaseID;
	String strJsonFile;

	public ExcelReader(Logger LOGGER, String strCaseID)
	{
		this.LOGGER = LOGGER;
		this.strCaseID = strCaseID; 
	}
	public ExcelReader(Logger LOGGER, String strJasonFile, String strCaseID)
	{
		this.LOGGER = LOGGER;
		this.strJsonFile = strJasonFile;
		this.strCaseID = strCaseID; 
	}




	public Hashtable<String, String> FetchTestDataFromJason()throws ElementNotFoundException
	{
		return FetchTestDataFromJason(strCaseID);
	}



	public Hashtable<String, String> FetchTestDataFromJason(String strCaseID)throws ElementNotFoundException
	{

		Hashtable<String, String> dicTestData = new Hashtable<>();
		Gson gson = new Gson();
		try 
		{
			dicTestData = getCredsMap();
			InputStream stream = new FileInputStream(".\\TestData\\"+strJsonFile+".json");
			JsonReader  jsonReader = new JsonReader(new InputStreamReader(stream, "UTF-8"));	  

			JsonToken peek = jsonReader.peek();
			if (peek == JsonToken.BEGIN_OBJECT)
			{
				gson.fromJson(jsonReader, null);
			}
			jsonReader.beginArray();
			while (jsonReader.hasNext()) 
			{						
				jsonReader.beginObject();
				while (jsonReader.hasNext())
				{	
					String name = jsonReader.nextName();

					if(name.equals("testcase"))
					{	
						String str = jsonReader.nextString();
						if(str.equalsIgnoreCase(strCaseID))
						{
							while (jsonReader.hasNext()) 
							{
								dicTestData.put(jsonReader.nextName().toString(), jsonReader.nextString().toString());
							}
						}
					}					
					else
					{
						jsonReader.skipValue();				
					}

				}
				jsonReader.endObject();
			}

			jsonReader.endArray();
			jsonReader.close();

			return dicTestData;

		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured while fetching data for "+strCaseID+" "+e.getMessage(), LOGGER);
		}

	}

	public List<Hashtable<String, String>> FetchTestMultiDataFromJason(String strCaseID)throws ElementNotFoundException
	{

		Hashtable<String, String> dicTestData = new Hashtable<>();
		List<Hashtable<String, String>> listDataMap = new ArrayList<Hashtable<String,String>>();
		Gson gson = new Gson();
		try {
			/*
			dicTestData = getCredsMap();*/

			InputStream stream = new FileInputStream(".\\TestData\\"+strJsonFile+".json");
			JsonReader  jsonReader = new JsonReader(new InputStreamReader(stream, "UTF-8"));	  

			JsonToken peek = jsonReader.peek();
			if (peek == JsonToken.BEGIN_OBJECT) {
				gson.fromJson(jsonReader, null);
			}
			jsonReader.beginArray();
			while (jsonReader.hasNext()) {						
				jsonReader.beginObject();
				while (jsonReader.hasNext()) {	
					String name = jsonReader.nextName();

					if(name.equals("testcase_MultiData"))
					{
						if(jsonReader.nextString().equalsIgnoreCase(strCaseID)){
							jsonReader.nextName();
							jsonReader.beginArray();
							while(jsonReader.hasNext()) {
								Hashtable<String, String> temp = new Hashtable<String, String>();
								jsonReader.beginObject();
								while (jsonReader.hasNext()) {

									temp.put(jsonReader.nextName().toString(), jsonReader.nextString().toString());
								}
								listDataMap.add(mergeMap(dicTestData,temp));
								jsonReader.endObject();
							}

							jsonReader.endArray();					

						}

					}					
					else
					{
						jsonReader.skipValue();


					}

				}
				jsonReader.endObject();
			}

			jsonReader.endArray();
			jsonReader.close();

			return listDataMap;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ElementNotFoundException("Exception occured while fetching data for "+strCaseID+" "+e.getMessage(), LOGGER);
		}

	}


	public Hashtable<String, String> mergeMap(Hashtable<String, String> map1, Hashtable<String, String> map2)
	{
		// assuming the maps are of type String / String
		for(Map.Entry<String, String> entry : map1.entrySet()){
			if(map2.containsKey(entry.getKey())){
				map2.remove(entry.getKey());
			}else{
				map2.put(entry.getKey(), entry.getValue());
			}
		}
		return map2;
	}








	public Hashtable<String, String> getCredsMap() throws ElementNotFoundException
	{
		Hashtable<String, String> dicTestData = new Hashtable<>();
		try {
//			JsonReader jsonReader = new JsonReader(new FileReader(new SetUp().getValueFromPropertyFile("credsFile")));
//			jsonReader.beginArray();
//			while (jsonReader.hasNext()) 
//			{
//
//				jsonReader.beginObject();
//				String strTemp = "",strUser = "";
//				while (jsonReader.hasNext()) {					
//					String name = jsonReader.nextName();
//					switch (name) {
//					case "User":
//						strUser = jsonReader.nextString();
//
//						break;
//					case "UserID":
//						strTemp = jsonReader.nextString();
//						break;
//					case "Password":
//						strTemp = strTemp+":"+jsonReader.nextString();
//						break;
//
//					default:
//						break;
//					} 
//					dicTestData.put(strUser, strTemp);
//				}
//
//				jsonReader.endObject();
//			}
//
//			jsonReader.endArray();
//			jsonReader.close();
			return dicTestData;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ElementNotFoundException("Exception occured while fetching Creds for "+strCaseID+" "+e.getMessage(), LOGGER);

		}
	}



}
