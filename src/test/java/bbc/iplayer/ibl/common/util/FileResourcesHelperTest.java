package bbc.iplayer.ibl.common.util;

import static org.junit.Assert.assertEquals;
import java.io.File;

import org.junit.Test;

public class FileResourcesHelperTest {

	private static final String SAMPLE_FILES_LOCATION = "target/test-classes/fixtures/util";
	private static final String SAMPLE_FILE_FILENAME = "target/test-classes/fixtures/util/Simple.txt";
	private static final String SAMPLE_FILE_CONTENTS_WITH_NEWLINES_AND_SPACES = "line1\nline2  \n(two spaces at the end of previous line)\nline3   \n(three spaces at the end of previous line)\n";
	private static final String SAMPLE_FILE_CONTENTS_WITH_NEWLINES_AND_NO_SPACES = "line1\nline2\n(two spaces at the end of previous line)\nline3\n(three spaces at the end of previous line)\n";
	private static final String SAMPLE_FILE_CONTENTS_NO_NEWLINES_AND_SPACES = "line1line2  (two spaces at the end of previous line)line3   (three spaces at the end of previous line)";
	private static final String SAMPLE_FILE_CONTENTS_NO_NEWLINES_NO_SPACES = "line1line2(two spaces at the end of previous line)line3(three spaces at the end of previous line)";

	@Test
	public void testReadDataFromFile() throws Exception {
		File testFile = new File(SAMPLE_FILE_FILENAME);

		String testFileContentsWithNewlinesAndSpaces = FileResourcesHelper.readDataFromFile(testFile, false, false);
		String testFileContentsWithNewlinesAndNoSpaces = FileResourcesHelper.readDataFromFile(testFile, false, true);
		String testFileContentsWithNoNewlinesAndSpaces = FileResourcesHelper.readDataFromFile(testFile, true, false);
		String testFileContentsWithNoNewlinesAndNoSpaces = FileResourcesHelper.readDataFromFile(testFile, true, true);

		assertEquals(SAMPLE_FILE_CONTENTS_WITH_NEWLINES_AND_SPACES, testFileContentsWithNewlinesAndSpaces);
		assertEquals(SAMPLE_FILE_CONTENTS_WITH_NEWLINES_AND_NO_SPACES, testFileContentsWithNewlinesAndNoSpaces);
		assertEquals(SAMPLE_FILE_CONTENTS_NO_NEWLINES_AND_SPACES, testFileContentsWithNoNewlinesAndSpaces);
		assertEquals(SAMPLE_FILE_CONTENTS_NO_NEWLINES_NO_SPACES, testFileContentsWithNoNewlinesAndNoSpaces);
	}

	@Test
	public void testFindAvailableResources() throws Exception {
		File[] availableResourcesAny = FileResourcesHelper.findAvailableResources(SAMPLE_FILES_LOCATION, null);
		File[] availableResourcesXml = FileResourcesHelper.findAvailableResources(SAMPLE_FILES_LOCATION, ".xml");
		File[] availableResourcesJpeg = FileResourcesHelper.findAvailableResources(SAMPLE_FILES_LOCATION, ".jpeg");

		assertEquals(3, availableResourcesAny.length);
		assertEquals(1, availableResourcesXml.length);
		assertEquals(0, availableResourcesJpeg.length);
	}
}
