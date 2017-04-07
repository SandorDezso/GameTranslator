package translator.utils;

public class Util {

	public Util() {
	}

	public static String getCause(Throwable e) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString() + System.lineSeparator());
		}
		return sb.toString();
	}
}
