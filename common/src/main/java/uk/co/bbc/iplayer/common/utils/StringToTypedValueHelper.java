package uk.co.bbc.iplayer.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 	Simple (final) class that provides helpful static methods to:
 * 	<ul>
 * 		<li>convert a String value to an Enumeration</li>
 * 		<li>convert a String value to a specific type</li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 */
public final class StringToTypedValueHelper {
	
	public static final String VALUE_DELIMITER = ",";
	
	public static final <E extends Enum<E>> Object getAsEnumType(String valueAsString, Class<E> type) throws Exception {
		Object result = null;
		
		if ((valueAsString != null) && (!valueAsString.isEmpty()) && (type != null)) {
			result = Enum.valueOf(type, valueAsString);
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final <E extends Enum<E>> List getAsEnumTypeCollection(String valueAsString, Class<E> type) throws Exception {
		List result = new ArrayList();
		String[] valuesArray = null;
		
		if ((valueAsString != null) && (!valueAsString.isEmpty()) && (type != null)) {
			valuesArray = valueAsString.split(VALUE_DELIMITER);
			for (int i=0; i<valuesArray.length; i++) {
				result.add(StringToTypedValueHelper.getAsEnumType(valuesArray[i], type));
			}
		}

		return result;
	}

	public static final Object getAsType(String valueAsString, Class<?> type) throws Exception {
		Object result = null;

		if ((valueAsString != null) && (!valueAsString.isEmpty()) && (type != null)) {
			if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				if (valueAsString.equalsIgnoreCase("yes") ||
					valueAsString.equalsIgnoreCase("y") ||
					valueAsString.equalsIgnoreCase("true")) {
					result = Boolean.TRUE;
				}
				else {
					result = Boolean.FALSE;
				}
			}				
			else if (type.equals(int.class) || type.equals(Integer.class)) {
				result = Integer.valueOf(valueAsString);
			}
			else if (type.equals(short.class) || type.equals(Short.class)) {
				result = Short.valueOf(valueAsString);
			}				
			else if (type.equals(long.class) || type.equals(Long.class)) {
				result = Long.valueOf(valueAsString);
			}
			else if (type.equals(float.class) || type.equals(Float.class)) {
				result = Float.valueOf(valueAsString);
			}
			else if (type.equals(double.class) || type.equals(Double.class)) {
				result = Double.valueOf(valueAsString);
			}
			else if (type.equals(String.class))	{
				result = valueAsString;
			}
			else {
				result = valueAsString;
			}
		}
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static final String getAsString(final Object valueObject) throws Exception {
		String result = null;
		
		if (valueObject != null) {
			if (valueObject.getClass().isAssignableFrom(Collection.class)) {
				result = getAsString((Collection) valueObject);
			}
			else {
				result = valueObject.toString();
			}
		}

		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final List getAsTypeCollection(final String valueAsString, Class<?> type) throws Exception {
		List result = new ArrayList();
		String[] valuesArray = null;
		
		if ((valueAsString != null) && (!valueAsString.isEmpty()) && (type != null)) {
			valuesArray = valueAsString.split(VALUE_DELIMITER);
			for (int i=0; i<valuesArray.length; i++) {
				result.add(StringToTypedValueHelper.getAsType(valuesArray[i].trim(), type));
			}
		}
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static final String getAsString(final List valueObjectCollection) throws Exception {
		StringBuilder tempSB = new StringBuilder();
		int index = 0;
		
		if ((valueObjectCollection != null) && (!valueObjectCollection.isEmpty())) {		
			for (Object valueObject: valueObjectCollection) {
				if (valueObject != null) {
					tempSB.append(valueObject.toString());
					if (index != valueObjectCollection.size() -1) {
						tempSB.append(VALUE_DELIMITER);
					}
					index++;
				}				
			}
		}
		return tempSB.toString();
	}
}
