package bbc.iplayer.ibl.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import bbc.iplayer.ibl.common.GlobalConstants;

/**
 * <p>
 * Simple class that provides helpful static methods to:
 * <ul>
 * <li>find files using a specific extensionhelps reading in files from specific
 * locations of the filesystem.</li>
 * <li></li>
 * </ul>
 * </p>
 *
 * @author Stelios Psarras
 *
 */
public class FileResourcesHelper {

	public static File[] findAvailableResources(String location, String filteredExtension) throws Exception {
		File[] result = null;
		File tempFileLocation = null;
		FileFilter tempFileFilter = null;

		if (location != null) {
			tempFileLocation = new File(location);
			tempFileFilter = new GenericFileFilter(filteredExtension);
			if (tempFileLocation.isDirectory()) {
				result = tempFileLocation.listFiles(tempFileFilter);
			}
		}

		return result;
	}

	public static String readDataFromFile(File file, boolean ignoreNewLine, boolean ignoreLineWhitespaces) throws Exception {
		String result = null;
		BufferedReader tempBR = null;
		StringBuilder tempSB = null;
		String tempLine = null;

		if (file != null) {
			tempSB = new StringBuilder();
			tempBR = new BufferedReader(new FileReader(file));
			while ((tempLine = tempBR.readLine()) != null) {
				if (ignoreLineWhitespaces) {
					tempLine = tempLine.trim();
				}
				tempSB.append(tempLine);
				if (!ignoreNewLine) {
					tempSB.append("\n");
				}
			}
			tempBR.close();
			result = tempSB.toString();
		}

		return result;
	}

	public static String readDataFromInputStream(InputStream source, boolean ignoreNewLine, boolean ignoreLineWhitespaces) throws Exception {
		return readDataFromInputStream(source, ignoreNewLine, ignoreLineWhitespaces, GlobalConstants.DEFAULT_ENCODING);
	}

	public static String readDataFromInputStream(InputStream source, boolean ignoreNewLine, boolean ignoreLineWhitespaces, String encoding) throws Exception {
		String result = null;
		BufferedReader tempBR = null;
		StringBuilder tempSB = null;
		String tempLine = null;

		if (source != null) {
			tempSB = new StringBuilder();
			tempBR = new BufferedReader(new InputStreamReader(source, encoding));
			while ((tempLine = tempBR.readLine()) != null) {
				if (ignoreLineWhitespaces) {
					tempLine = tempLine.trim();
				}
				tempSB.append(tempLine);
				if (!ignoreNewLine) {
					tempSB.append("\n");
				}
			}
			tempBR.close();
			result = tempSB.toString();
			source.close();
		}

		return result;
	}

	public static class GenericFileFilter
	implements FileFilter {

		private String filteredExtension;

		public GenericFileFilter(String filteredExtension) {
			this.filteredExtension = filteredExtension;
		}

		@Override
		public boolean accept(File pathname) {
			boolean result = false;

			if (filteredExtension != null) {
				if (!filteredExtension.startsWith(".")) {
					filteredExtension = "." + filteredExtension;
				}
				if ((pathname != null) && (pathname.getAbsolutePath().endsWith(filteredExtension))) {
					result = true;
				}
			}
			else {
				if (pathname != null) {
					result = true;
				}
			}

			return result;
		}
	}
}
