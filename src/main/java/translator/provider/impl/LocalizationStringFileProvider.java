package translator.provider.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import translator.provider.LocalizationStringProvider;

public class LocalizationStringFileProvider implements LocalizationStringProvider {
	private File file;
	
	public LocalizationStringFileProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> read() {
		
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO apply the builder pattern
	public LocalizationStringFileProvider setThe(File file){
		this.file=file;
		return this;
	}
	
	private boolean fileCheck(){
		
		return false;
	}
	
	private List<String> readAllLinesFromFile(File file) throws IOException {
		Path firstFilePath = Paths.get(file.getAbsolutePath());
		List<String> firstFileLines = Files.readAllLines(firstFilePath);
		return firstFileLines;
	}
}
