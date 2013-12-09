package uk.co.bbc.iplayer.common.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringToTypedValueHelperTest {

	private enum TestEnum {
		ENUM_VALUE1,
		ENUM_VALUE2
	}
	
	@Test
	public void givenEnumStringValue_testGetAsEnumType() throws Exception {
		String enumStringValue = TestEnum.ENUM_VALUE1.name();
		Enum<?> enumValueObject = (Enum<?>) StringToTypedValueHelper.getAsEnumType(enumStringValue, TestEnum.class);
		assertEquals(TestEnum.ENUM_VALUE1, enumValueObject);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenEnumCollectionStringValue_testGetAsEnumTypeCollection() throws Exception {
		String enumStringValueCollection = TestEnum.ENUM_VALUE1.name() + "," + TestEnum.ENUM_VALUE2.name();
		List<Enum<?>> enumValueObjectCollection = (List<Enum<?>>) StringToTypedValueHelper.getAsEnumTypeCollection(enumStringValueCollection, TestEnum.class);
		assertEquals(Arrays.asList(TestEnum.ENUM_VALUE1, TestEnum.ENUM_VALUE2), enumValueObjectCollection);
	}

	@Test
	public void givenBooleanStringValue_testGetAsType() throws Exception {
		String booleanStringValue = "true";
		Boolean booleanValueObject = (Boolean) StringToTypedValueHelper.getAsType(booleanStringValue, Boolean.class);
		boolean booleanValuePrimitive = (Boolean) StringToTypedValueHelper.getAsType(booleanStringValue, boolean.class);
		assertEquals(Boolean.TRUE, booleanValueObject);
		assertEquals(Boolean.parseBoolean("true"), booleanValuePrimitive);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void givenBooleanCollectionStringValue_testGetAsTypeCollection() throws Exception {
		String booleanCollectionStringValue = "true ,false, true";
		List<Boolean> booleanValueObjectCollection = (List<Boolean>) StringToTypedValueHelper.getAsTypeCollection(booleanCollectionStringValue, Boolean.class);		
		List<Boolean> booleanValuePrimitiveCollection = (List<Boolean>) StringToTypedValueHelper.getAsTypeCollection(booleanCollectionStringValue, boolean.class);
		assertEquals(Arrays.asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE), booleanValueObjectCollection);
		assertEquals(Arrays.asList(Boolean.parseBoolean("true"), Boolean.parseBoolean("false"), Boolean.parseBoolean("true")), booleanValuePrimitiveCollection);
	}
	
	@Test
	public void givenBooleanCollection_testGetAsString() throws Exception {
		List<Boolean> booleanValueObjectCollection = Arrays.asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
		String booleanValueObjectCollectionAsString = StringToTypedValueHelper.getAsString(booleanValueObjectCollection);
		assertEquals("true,false,true", booleanValueObjectCollectionAsString);
	}
	
	@Test
	public void givenIntegerStringValue_testGetAsType() throws Exception {
		String integerStringValue = "1";
		Integer integerValueObject = (Integer) StringToTypedValueHelper.getAsType(integerStringValue, Integer.class);
		int integerValuePrimitive = (Integer) StringToTypedValueHelper.getAsType(integerStringValue, int.class);
		assertEquals(new Integer(integerStringValue), integerValueObject);
		assertEquals(Integer.parseInt("1"), integerValuePrimitive);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenIntegerCollectionStringValue_testGetAsTypeCollection() throws Exception {
		String integerStringValueCollection = " 1,-2 ,5";
		List<Integer> integerValueObjectCollection = (List<Integer>) StringToTypedValueHelper.getAsTypeCollection(integerStringValueCollection, Integer.class);
		List<Integer> integerValuePrimitiveCollection = (List<Integer>) StringToTypedValueHelper.getAsTypeCollection(integerStringValueCollection, int.class);
		assertEquals(Arrays.asList(new Integer(1), new Integer(-2), new Integer(5)), integerValueObjectCollection);
		assertEquals(Arrays.asList(Integer.parseInt("1"), Integer.parseInt("-2"), Integer.parseInt("5")), integerValuePrimitiveCollection);
	}

	@Test
	public void givenShortStringValue_testGetAsType() throws Exception {
		String shortStringValue = "1";
		Short shortValueObject = (Short) StringToTypedValueHelper.getAsType(shortStringValue, Short.class);
		short shortValuePrimitive = (Short) StringToTypedValueHelper.getAsType(shortStringValue, short.class);
		assertEquals(new Short(shortStringValue), shortValueObject);
		assertEquals(Short.parseShort("1"), shortValuePrimitive);
	}	

	@SuppressWarnings("unchecked")
	@Test
	public void givenShortCollectionStringValue_testGetAsTypeCollection() throws Exception {
		String shortStringValueCollection = " 1,-2 ,5";
		List<Short> shortValueObjectCollection = (List<Short>) StringToTypedValueHelper.getAsTypeCollection(shortStringValueCollection, Short.class);
		List<Short> shortValuePrimitiveCollection = (List<Short>) StringToTypedValueHelper.getAsTypeCollection(shortStringValueCollection, short.class);
		assertEquals(Arrays.asList(new Short("1"), new Short("-2"), new Short("5")), shortValueObjectCollection);
		assertEquals(Arrays.asList(Short.parseShort("1"), Short.parseShort("-2"), Short.parseShort("5")), shortValuePrimitiveCollection);
	}

	@Test
	public void givenLongStringValue_testGetAsType() throws Exception {
		String longStringValue = "1";
		Long longValueObject = (Long) StringToTypedValueHelper.getAsType(longStringValue, Long.class);
		long longValuePrimitive = (Long) StringToTypedValueHelper.getAsType(longStringValue, long.class);
		assertEquals(new Long(longStringValue), longValueObject);
		assertEquals(Long.parseLong("1"), longValuePrimitive);
	}	

	@SuppressWarnings("unchecked")
	@Test
	public void givenLongCollectionStringValue_testGetAsTypeCollection() throws Exception {
		String longStringValueCollection = " 1,-2 ,5";
		List<Long> longValueObjectCollection = (List<Long>) StringToTypedValueHelper.getAsTypeCollection(longStringValueCollection, Long.class);
		List<Long> longValuePrimitiveCollection = (List<Long>) StringToTypedValueHelper.getAsTypeCollection(longStringValueCollection, long.class);
		assertEquals(Arrays.asList(new Long("1"), new Long("-2"), new Long("5")), longValueObjectCollection);
		assertEquals(Arrays.asList(Long.parseLong("1"), Long.parseLong("-2"), Long.parseLong("5")), longValuePrimitiveCollection);
	}

	@Test
	public void givenFloatStringValue_testGetAsType() throws Exception {
		String floatStringValue = "1.5";
		Float floatValueObject = (Float) StringToTypedValueHelper.getAsType(floatStringValue, Float.class);
		float floatValuePrimitive = (Float) StringToTypedValueHelper.getAsType(floatStringValue, float.class);
		assertEquals(new Float(floatStringValue), floatValueObject);
		assertEquals(Float.parseFloat("1.5"), floatValuePrimitive, 0.0);
	}	

	@SuppressWarnings("unchecked")
	@Test
	public void givenFloatCollectionStringValue_testGetAsTypeCollection() throws Exception {
		String floatStringValueCollection = " 1.5,-2.9 ,5.3";
		List<Float> floatValueObjectCollection = (List<Float>) StringToTypedValueHelper.getAsTypeCollection(floatStringValueCollection, Float.class);
		List<Float> floatValuePrimitiveCollection = (List<Float>) StringToTypedValueHelper.getAsTypeCollection(floatStringValueCollection, float.class);
		assertEquals(Arrays.asList(new Float("1.5"), new Float("-2.9"), new Float("5.3")), floatValueObjectCollection);
		assertEquals(Arrays.asList(Float.parseFloat("1.5"), Float.parseFloat("-2.9"), Float.parseFloat("5.3")), floatValuePrimitiveCollection);
	}

	@Test
	public void givenDoubleStringValue_testGetAsType() throws Exception {
		String doubleStringValue = "1.5";
		Double doubleValueObject = (Double) StringToTypedValueHelper.getAsType(doubleStringValue, Double.class);
		double doubleValuePrimitive = (Double) StringToTypedValueHelper.getAsType(doubleStringValue, double.class);
		assertEquals(new Double(doubleStringValue), doubleValueObject);
		assertEquals(Double.parseDouble("1.5"), doubleValuePrimitive, 0.0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenDoubleCollectionStringValue_testGetAsTypeCollection() throws Exception {
		String doubleStringValueCollection = " 1.5,-2.9 ,5.3";
		List<Double> doubleValueObjectCollection = (List<Double>) StringToTypedValueHelper.getAsTypeCollection(doubleStringValueCollection, Double.class);
		List<Double> doubleValuePrimitiveCollection = (List<Double>) StringToTypedValueHelper.getAsTypeCollection(doubleStringValueCollection, double.class);
		assertEquals(Arrays.asList(new Double("1.5"), new Double("-2.9"), new Double("5.3")), doubleValueObjectCollection);
		assertEquals(Arrays.asList(Double.parseDouble("1.5"), Double.parseDouble("-2.9"), Double.parseDouble("5.3")), doubleValuePrimitiveCollection);
	}

	@Test
	public void givenClassStringValue_testGetAsType() throws Exception {
		String classStringValue = "java.lang.Integer";
		Class<?> classValueObject = (Class<?>) StringToTypedValueHelper.getAsType(classStringValue, Class.class);
		assertEquals(Integer.class, classValueObject);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void givenClassCollectionStringValue_testGetAsTypeCollection() throws Exception {
		String classStringValueCollection = "java.lang.Integer,java.lang.Boolean";
		List<Class<?>> classValueObjectCollection = (List<Class<?>>) StringToTypedValueHelper.getAsTypeCollection(classStringValueCollection, Class.class);
		assertEquals(Arrays.asList(Integer.class, Boolean.class), classValueObjectCollection);
	}
	
	@Test
	public void givenString_testGetAsType() throws Exception {
		String aStringValue = "test";
		String aStringValueObject = (String) StringToTypedValueHelper.getAsType(aStringValue, String.class);
		assertEquals(aStringValue, aStringValueObject);
	}	

	@SuppressWarnings("unchecked")
	@Test
	public void givenStringCollection_testGetAsTypeCollection() throws Exception {
		String aStringValueCollection = "test1, test2, test3";
		List<String> aStringValueObjectCollection = (List<String>) StringToTypedValueHelper.getAsTypeCollection(aStringValueCollection, String.class);
		assertEquals(Arrays.asList("test1", "test2", "test3"), aStringValueObjectCollection);
	}	
	
	@Test(expected = Exception.class) 
	public void givenInvalidInteger_throwsException() throws Exception {
		String invalidIntegerStringValue = "1S";
		StringToTypedValueHelper.getAsType(invalidIntegerStringValue, Integer.class);
	}
}

