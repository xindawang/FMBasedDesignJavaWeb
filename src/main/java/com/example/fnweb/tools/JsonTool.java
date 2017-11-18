package com.example.fnweb.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTool {
	public static <T> String javaBeanToJson(T javaBean){
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(javaBean);
		} catch (JsonProcessingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> String listToJson(List<T> list){
		final OutputStream out = new ByteArrayOutputStream();
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(out, list);
			return out.toString();
		} catch (JsonGenerationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
	public static String objectToJson(Object o){
		final ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Map<String,String>> jsonToListMap(String json) throws JsonParseException, JsonMappingException, IOException{
		final ObjectMapper om = new ObjectMapper();
		
			return om.readValue(json,  new TypeReference<List<Map<String,String>>>(){});
		
	}
}
