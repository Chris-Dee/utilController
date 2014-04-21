package data.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import data.GameData;
import data.InvalidDataFileException;

public class GameDataTest {
	GameData gameData;

	@Before
	public void setUp() throws InvalidDataFileException {
		gameData = new GameData("");
	}

	@Test
	public void testChildlessClass() {
		TestChildClass childless = new TestChildClass();
		gameData.addObj(childless);
		assertEquals(gameData.toString(),
				"{\"data.test.TestChildClass\":[{\"aNumber\":5}]}");
	}

	@Test
	public void testParsingForChildlessClass() {
		try {
			Map<String, List<Object>> objectMap = gameData.parse();
			assertEquals(objectMap.get("data.test.TestChildClass").get(0)
					.getClass().getName(), "data.test.TestChildClass");
		} catch (Exception e) {
		}
	}

	@Test
	public void testClassWithChild() {
		TestParentClass parent = new TestParentClass();
		gameData.addObj(parent);
		assertEquals(gameData.toString(),
				"{\"data.test.TestParentClass\":[{\"aNumber\":5,\"child\":{\"aNumber\":5}}]}");
	}

	@Test
	public void testParsingforClassWithChild() {
		try {
			Map<String, List<Object>> objectMap = gameData.parse();
			assertEquals(objectMap.get("data.test.TestParentClass").get(0)
					.getClass().getName(), "data.test.TestParentClass");
			assertEquals(
					((TestParentClass) objectMap.get(
							"data.test.TestParentClass").get(0)).getNumber(), 5);
			assertEquals(
					((TestParentClass) objectMap.get(
							"data.test.TestParentClass").get(0)).getChild()
							.getClass().getName(), "data.test.TestChildClass");
		} catch (Exception e) {
		}

	}

	@Test
	public void testWritingToAndReadingFromFile() {
		GameData data;
		try {
			data = new GameData("test.txt");
			data.addObj(new Integer(5));
			data.addObj(new Integer(7));
			data.write();
			data = new GameData("test.txt");
			Map<String, List<Object>> value = (Map<String, List<Object>>) data
					.parse();
			assertEquals(value.toString(), "{java.lang.Integer=[5, 7]}");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIgnoredFields() {
		TestWithTransientClass obj = new TestWithTransientClass();
		gameData.addObj(obj);
		assertEquals(gameData.toString(),
				"{\"data.test.TestWithTransientClass\":[{\"serializeMe\":5}]}");
	}
}

class TestParentClass {
	private int aNumber;
	private TestChildClass child;

	public TestParentClass() {
		aNumber = 5;
		child = new TestChildClass();
	}

	public int getNumber() {
		return aNumber;
	}

	public TestChildClass getChild() {
		return child;
	}
}

class TestChildClass {
	private int aNumber;

	public TestChildClass() {
		aNumber = 5;
	}
}

class TestWithTransientClass {
	private int serializeMe = 5;
	private transient int hideMe = 5;
}
