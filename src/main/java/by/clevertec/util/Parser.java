package by.clevertec.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Parser {

    //    MAIN METHOD TO PARSE OBJECT TO JSON
    public static String parseObjectToJson(Object obj) {
        if (obj instanceof Collection<?> collection) {
            StringBuilder json = new StringBuilder("[");

            for (Object item : collection) {
                json.append(parseObjectToJson(item)).append(",");
            }

            if (json.charAt(json.length() - 1) == ',') {
                json.deleteCharAt(json.length() - 1);
            }

            json.append("]");
            return json.toString();
        } else if (obj instanceof Map<?, ?> map) {
            StringBuilder json = new StringBuilder("{");

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    json.append("\"").append(entry.getKey()).append("\":").append(parseObjectToJson(entry.getValue())).append(",");
                }
            }

            if (json.charAt(json.length() - 1) == ',') {
                json.deleteCharAt(json.length() - 1);
            }

            json.append("}");
            return json.toString();
        } else if (obj instanceof UUID || obj instanceof String || obj instanceof LocalDate || obj instanceof OffsetDateTime) {
            return "\"" + obj + "\"";
        } else if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        } else if (obj == null) {
            return "null";
        } else {
            return parseObjectToJson(parseObjectToMap(obj));
        }
    }

    private static Map<String, Object> parseObjectToMap(Object obj) {
        Map<String, Object> map = new LinkedHashMap<>();
        Class<?> cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                map.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    //    MAIN METHOD TO PARSE JSON TO OBJECT
    public static <T> T parseJsonToObject(String json, Class<T> clazz) {
        T t;
        try {
            t = parseMapToObject(parseJsonToMap(json), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    private static Map<String, Object> parseJsonToMap(String json) {
        json = json.trim();
        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new IllegalArgumentException("Invalid JSON object");
        }
        return parseJsonObjectToMap(json.substring(1, json.length() - 1).trim());
    }

    private static Map<String, Object> parseJsonObjectToMap(String json) {
        Map<String, Object> map = new LinkedHashMap<>();
        int length = json.length();
        int i = 0;

        while (i < length) {
            i = skipWhitespace(json, i);

            if (json.charAt(i) == '"') {
                int keyEnd = json.indexOf('"', i + 1);
                String key = json.substring(i + 1, keyEnd);
                i = skipWhitespace(json, keyEnd + 1);

                if (json.charAt(i) != ':') {
                    throw new IllegalArgumentException("Expected ':' after key");
                }
                i = skipWhitespace(json, i + 1);

                Object value;
                try {
                    value = parseJsonValueToObject(json, i);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                map.put(key, value);

                if (value instanceof String) {
                    i = json.indexOf('"', i + 1) + 1;
                } else if (value instanceof Number || value instanceof Boolean || value == null) {
                    while (i < length && json.charAt(i) != ',' && json.charAt(i) != '}') {
                        i++;
                    }
                } else {
                    i = getIndex(json, i, value);
                }

                i = skipWhitespace(json, i);

                if (i < length && json.charAt(i) == ',') {
                    i++;
                }
            } else {
                throw new IllegalArgumentException("Expected string key");
            }
        }
        return map;
    }

    private static int getIndex(String json, int i, Object value) {
        if (value instanceof Map) {
            try {
                i = findClosingBracket(json, i, '{', '}') + 1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (value instanceof List) {
            try {
                i = findClosingBracket(json, i, '[', ']') + 1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return i;
    }

    private static Object parseJsonValueToObject(String json, int i) {
        i = skipWhitespace(json, i);

        char currentChar = json.charAt(i);

        if (currentChar == '"') {
            int valueEnd = json.indexOf('"', i + 1);
            return json.substring(i + 1, valueEnd);
        } else if (currentChar == '{') {
            int objectEnd;
            try {
                objectEnd = findClosingBracket(json, i, '{', '}');
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return parseJsonObjectToMap(json.substring(i + 1, objectEnd));
        } else if (currentChar == '[') {
            int arrayEnd;
            try {
                arrayEnd = findClosingBracket(json, i, '[', ']');
                return parseJsonArrayToList(json.substring(i + 1, arrayEnd));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            int valueEnd = findValueEnd(json, i);
            String value = json.substring(i, valueEnd).trim();
            if (value.equals("true")) {
                return true;
            } else if (value.equals("false")) {
                return false;
            } else if (value.equals("null")) {
                return null;
            } else if (value.contains(".") || value.contains("e") || value.contains("E")) {
                return Double.parseDouble(value);
            } else {
                return Long.parseLong(value);
            }
        }
    }

    private static List<Object> parseJsonArrayToList(String json) {
        List<Object> list = new ArrayList<>();
        int length = json.length();
        int i = 0;

        while (i < length) {
            i = skipWhitespace(json, i);
            if (json.charAt(i) == ']') {
                break;
            }

            Object value = parseJsonValueToObject(json, i);
            list.add(value);

            if (value instanceof String) {
                i = json.indexOf('"', i + 1) + 1;
            } else if (value instanceof Number || value instanceof Boolean || value == null) {
                while (i < length && json.charAt(i) != ',' && json.charAt(i) != ']') {
                    i++;
                }
            } else {
                i = getIndex(json, i, value);
            }

            i = skipWhitespace(json, i);

            if (i < length && json.charAt(i) == ',') {
                i++;
            }
        }
        return list;
    }

    private static int findValueEnd(String json, int i) {
        int length = json.length();
        while (i < length && json.charAt(i) != ',' && json.charAt(i) != ']' && json.charAt(i) != '}') {
            i++;
        }
        return i;
    }

    private static int findClosingBracket(String json, int start, char open, char close) {
        int depth = 0;
        for (int i = start; i < json.length(); i++) {
            if (json.charAt(i) == open) {
                depth++;
            } else if (json.charAt(i) == close) {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Unmatched " + open + " in JSON string");
    }

    private static int skipWhitespace(String json, int i) {
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) {
            i++;
        }
        return i;
    }

    private static <T> T parseMapToObject(Map<String, Object> map, Class<T> clazz) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                continue;
            }

            field.setAccessible(true);

            Object fieldValue = convertValueTypeToInstanceFieldType(value, field.getType());
            field.set(instance, fieldValue);
        }

        return instance;
    }

    private static Object convertValueTypeToInstanceFieldType(Object value, Class<?> fieldType) throws Exception {
        if (value == null) {
            return null;
        }

        if (fieldType.isAssignableFrom(value.getClass())) {
            return value;
        }

        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value.toString());
        } else if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(value.toString());
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value.toString());
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (fieldType == String.class) {
            return value.toString();
        }

        if (fieldType.isArray()) {
            Class<?> componentType = fieldType.getComponentType();
            if (value instanceof List<?> list) {
                Object array = Array.newInstance(componentType, list.size());
                for (int i = 0; i < list.size(); i++) {
                    Array.set(array, i, convertValueTypeToInstanceFieldType(list.get(i), componentType));
                }
                return array;
            }
        }

        if (Collection.class.isAssignableFrom(fieldType)) {
            if (value instanceof List<?> list) {
                Collection<Object> collection;

                if (List.class.isAssignableFrom(fieldType)) {
                    collection = new ArrayList<>();
                } else if (Set.class.isAssignableFrom(fieldType)) {
                    collection = new LinkedHashSet<>();
                } else {
                    throw new IllegalArgumentException("Unsupported collection type: " + fieldType);
                }

                for (Object item : list) {
                    collection.add(convertValueTypeToInstanceFieldType(item, Object.class));
                }
                return collection;
            }
        }

        if (Map.class.isAssignableFrom(fieldType)) {
            if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                Map<Object, Object> resultMap = new LinkedHashMap<>();

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    resultMap.put(entry.getKey(), convertValueTypeToInstanceFieldType(entry.getValue(), Object.class));
                }
                return resultMap;
            }
        }

        if (UUID.class.isAssignableFrom(fieldType)) {
            return UUID.fromString(value.toString());
        }

        if (OffsetDateTime.class.isAssignableFrom(fieldType)) {
            return OffsetDateTime.parse(value.toString());
        }

        if (LocalDate.class.isAssignableFrom(fieldType)) {
            return LocalDate.parse(value.toString());
        }

        if (!fieldType.isPrimitive() && !fieldType.isEnum()) {
            if (value instanceof Map) {
                return parseMapToObject((Map<String, Object>) value, fieldType);
            }
        }
        throw new IllegalArgumentException("Unsupported data type for field: " + fieldType);
    }
}