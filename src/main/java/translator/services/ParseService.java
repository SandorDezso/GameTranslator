package translator.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javafx.concurrent.Task;
import translator.persistence.model.LocalizationModel;
import translator.provider.LocalizationStringProvider;
import translator.provider.impl.LocalizationStringFileProvider;

public class ParseService extends Task<List<LocalizationModel>> {
	private LocalizationStringProvider provider;
	private Validator validator;
	private List<LocalizationModel> lines;
	private static final Logger logger = LogManager.getLogger(ParseService.class);
	private long full = 0;
	private long actuallyProgress = 0;
	private Map<Integer, String> firstLang = new HashMap<Integer, String>();
	private Map<Integer, String> secondLang = new HashMap<Integer, String>();
	private List<LocalizationModel> result;
	private File firstFile;
	private File secondFile;
	private long maxProgress = 0;
	private long workDone = 0;

	
	
	public ParseService(Validator validator, File firstFile, File secondFile) {
		super();
		this.validator = validator;
		this.firstFile = firstFile;
		this.secondFile = secondFile;
	}

	
	private List<LocalizationModel> parse(File firstFile, File secondFile) throws IOException {
		Path firstFilePath = Paths.get(firstFile.getAbsolutePath());
		Path secondFilePath = Paths.get(secondFile.getAbsolutePath());
		List<String> firstFileLines = Files.readAllLines(firstFilePath);
		List<String> secondAllLines = Files.readAllLines(secondFilePath);
		this.full = firstFileLines.size() + secondAllLines.size();
		Map<String, String> collect = innerParse(firstFileLines);
		Map<String, String> secondCollect = innerParse(secondAllLines);
		List<LocalizationModel> model = collect.keySet().stream()
				.map(id -> new LocalizationModel(id, collect.get(id), secondCollect.get(id))).collect(Collectors.toList());

		return model;

	}

	public long getFullTask() {
		return full;
	}

	public long getActuallyProgress() {
		return actuallyProgress;
	}

	private Optional<Entry<String, String>> parseMainModel(String s) {
		BindException bindException = new BindException(s, s);
		validator.validate(s, bindException);
		if (bindException.hasErrors()) {
			logger.warn(bindException.getLocalizedMessage());
			for(ObjectError obj: bindException.getAllErrors()){
				
				logger.warn(obj.getCode());
				
			}
			return Optional.empty();
		}
		String[] split = s.split("\\s{1,}", 2);
		logger.debug("split lenght:" + split.length + "split first: " + split[0] + "; split second: " + split[1]);
		actuallyProgress++;
		Entry<String, String> retVal = new EntryImpl<String, String>(split[0], split[1]);
		return Optional.of(retVal);
	}


	@Override
	protected List<LocalizationModel> call() throws Exception {
		List<String> firstFileLines = ((LocalizationStringFileProvider)provider).setThe(firstFile).read();//.readAllLinesFromFile(firstFile);
		List<String> secondAllLines = ((LocalizationStringFileProvider)provider).setThe(secondFile).read();

		maxProgress = firstFileLines.size() + secondAllLines.size();
		super.updateProgress(0, maxProgress);

		Map<String, String> collect = innerParse(firstFileLines);
		Map<String, String> secondCollect = innerParse(secondAllLines);

		List<LocalizationModel> model = collect.keySet().stream()
				.map(id -> new LocalizationModel(id, collect.get(id), secondCollect.get(id)))
				.collect(Collectors.toList());
		return model;
	}
	


	private Map<String, String> innerParse(List<String> readAllLines) {

		return readAllLines.stream().filter(line -> !line.startsWith("//")).map(l -> {
			workDone++;
			updateProgress(workDone, maxProgress);
			return parseMainModel(l);
		}).filter(Optional::isPresent).map(Optional::get)
				.collect(Collectors.toMap(i -> i.getKey(), i -> i.getValue(), (e1, e2) -> e1));

	}
	public boolean isEmpty(){return false;}
	
}

class EntryImpl<K, V> implements Map.Entry<K, V> {
	private final K key;
	private V value;

	public EntryImpl(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

}