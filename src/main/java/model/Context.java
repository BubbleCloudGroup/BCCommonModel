package model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: u6613739
 * Date: 2019/9/1
 * Time: 15:38
 * Description:
 */
public class Context
{
    private Map<String, Object> contextMap = new LinkedHashMap<>();

    public Object get(String key) throws NullPointerException
    {
        if (contextMap.containsKey(key))
        {
            return contextMap.get(key);
        }
        else
        {
            throw new NullPointerException();
        }
    }

    public boolean contains(String key)
    {
        return contextMap.containsKey(key);
    }

    public Object put(String key, String object)
    {
        return contextMap.put(key, object);
    }
}
