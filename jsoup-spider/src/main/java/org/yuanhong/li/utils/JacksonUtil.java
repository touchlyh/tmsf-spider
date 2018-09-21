package org.yuanhong.li.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.MissingNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

public final class JacksonUtil {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    /**
     * @param json
     * @return 数组返回ArrayList，对象返回LinkedHashMap, 或者null
     */
    public static final <T> T parseJson(String json) {
        return parseJson(json, new TypeReference<T>() {});
    }

    /**
     * @param json
     * @return 总是返回一个List, 如果是空或对象类型的json，则返回空的List
     */
    @SuppressWarnings("unchecked")
    public static final <T> List<T> parseArrayJson(String json) {
        List<T> result = null;
        Object obj = parseJson(json);
        if (obj != null && List.class.isInstance(obj)) {
            result = (List<T>) obj;
        }
        if (result == null) {
            result = new ArrayList<T>();
        }
        return result;
    }

    /**
     * @param json
     * @return 总是返回一个Map, 如果是空或数组类型的json，则返回空的Map
     */
    @SuppressWarnings("unchecked")
    public static final <K, V> Map<K, V> parseObjectJson(String json) {
        Map<K, V> result = null;
        Object obj = parseJson(json);
        if (obj != null && Map.class.isInstance(obj)) {
            result = (Map<K, V>) obj;
        }
        if (result == null) {
            result = new HashMap<K, V>();
        }
        return result;
    }

    /**
     * 序列化json
     * @param value
     * @return
     */
    public static final String toJson(Object value) {
        return toJson(OBJECT_MAPPER, value);
    }

    /**
     * 使用一个自定义配置来序列化json
     * @param mapper
     * @param value
     * @return
     */
    public static final String toJson(ObjectMapper mapper, Object value) {
        if (value == null) {
            return null;
        }
        String result = null;
        try {
            result = mapper.writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析为json树
     * @param json
     * @return
     */
    public static final JsonNode parseJsonToTree(String json) {
        if (json == null || StringUtils.isEmpty(json)) {
            return MissingNode.getInstance();
        }
        JsonNode node = MissingNode.getInstance();
        try {
            node = OBJECT_MAPPER.readTree(json);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return node;
    }

    /**
    * 解析为json树
    * @param json
    * @return
    */
   public static final JsonNode parseJsonToTree(InputStream json) {
       if (json == null) {
           return MissingNode.getInstance();
       }
       JsonNode node = MissingNode.getInstance();
       try {
           node = OBJECT_MAPPER.readTree(json);
       } catch (Exception e) {
    	   e.printStackTrace();
       } finally {
           try {
            json.close();
        } catch (IOException ignore) {
        }
       }
       return node;
   }

    /**
     * 获取子节点
     * @param json
     * @param path
     * @return
     */
    public static final JsonNode parseJsonToTree(String json, String[] path) {
        return getChildNode(parseJsonToTree(json), path);
    }

    /**
     * 获取子节点
     * @param node
     * @param path
     * @return
     */
    public static final JsonNode getChildNode(JsonNode node, String[] path) {
        if (path != null && path.length > 0) {
            for (int i = 0; i < path.length && (node!= null && !node.isMissingNode()); i++) {
                node = node.get(path[i]);
            }
        }
        if (node == null) {
            node = MissingNode.getInstance();
        }
        return node;
    }

    /**
     * 节点是否存在
     * @param node
     * @param name
     * @return
     */
    public static final boolean exist(JsonNode node, String name) {
        return node != null && node.has(name);
    }

    /**
     * 获取当前节点的值
     * @param node
     * @return
     */
    public static final String getNodeValue(JsonNode node) {
        return getNodeValue(node, null);
    }

    /**
     * 获取当前节点的值
     * @param node
     * @param defaultValue 仅当该节点不存在或值为null时，才返回默认值
     * @return
     */
    public static final String getNodeValue(JsonNode node, String defaultValue) {
        String value = defaultValue;
        if (node != null) {
            try {
                if (node.isValueNode() && !node.isNull()) {
                    value = node.asText();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 获取当前节点的下某个属性的值
     * @param node
     * @param name
     * @return
     */
    public static final String getTextValue(JsonNode node, String name) {
        return getTextValue(node, name, null);
    }

    /**
     * 获取当前节点的下某个属性的值
     * @param node
     * @param name
     * @param defaultValue 仅当该属性不存在或值为null时，才返回默认值
     * @return
     */
    public static final String getTextValue(JsonNode node, String name, String defaultValue) {
        String value = defaultValue;
        if (node != null) {
            try {
                JsonNode current = node.get(name);
                value = getNodeValue(current, defaultValue);
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 直接获取某个属性的值
     * @param json
     * @param name
     * @return
     */
    public static final String getTextValue(String json, String name) {
        return getTextValue(parseJsonToTree(json), name);
    }

    /**
     * 直接获取某个属性的值
     * @param json
     * @param name
     * @param defaultValue 仅当该属性不存在或值为null时，才返回默认值
     * @return
     */
    public static final String getTextValue(String json, String name, String defaultValue) {
        return getTextValue(parseJsonToTree(json), name, defaultValue);
    }

    /**
     * 当前节点的下某个属性是否为整型
     * @param node
     * @param name
     * @return
     */
    public static final boolean isIntegralValue(JsonNode node, String name) {
        boolean succ = false;
        if (node != null) {
            try {
                JsonNode current = node.get(name);
                if (current != null && current.isValueNode() && current.isIntegralNumber()) {
                    succ = true;
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return succ;
    }

    /**
     * 获取当前节点的下某个整形属性的值
     * {"a":"123"}会返回0
     * @param node
     * @param name
     * @return
     */
    public static final long getIntegralValue(JsonNode node, String name) {
        long value = 0;
        if (node != null) {
            try {
                JsonNode current = node.get(name);
                if (current != null && current.isValueNode() && current.isIntegralNumber()) {
                    value = current.asLong();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 获取当前节点的下某个浮点属性的值
     * {"a":"123.0"}会返回0.0
     * @param node
     * @param name
     * @return
     */
    public static final double getFloatingPointValue(JsonNode node, String name) {
        double value = 0;
        if (node != null) {
            try {
                JsonNode current = node.get(name);
                if (current != null && current.isValueNode() && current.isFloatingPointNumber()) {
                    value = current.asDouble();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 获取当前节点的下某个布尔属性的值
     * 仅当{"a":true}才返回true, {"a":"true"}会返回false
     * @param node
     * @param name
     * @return
     */
    public static final boolean getBooleanValue(JsonNode node, String name) {
        boolean value = false;
        if (node != null) {
            try {
                JsonNode current = node.get(name);
                if (current != null && current.isValueNode() && current.isBoolean()) {
                    value = current.asBoolean();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return value;
    }

    public static final <T> T parseJson(String json, TypeReference<T> type) {
        if (json == null || json.trim().length() <= 0) {
            return null;
        }
        T result = null;

        try {
            result = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return result;
    }
    
    public static final <T> T parseJsonByClass(String json, Class<T> type) {
        if (json == null || json.trim().length() <= 0) {
            return null;
        }
        T result = null;

        try {
            result = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return result;
    }

    public static final <T> T parseJson(ObjectMapper mapper, JsonNode root, TypeReference<T> type) {
        if (root == null) {
            return null;
        }
        T result = null;

        try {
            result = mapper.readValue(root, type);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return result;
    }

}
