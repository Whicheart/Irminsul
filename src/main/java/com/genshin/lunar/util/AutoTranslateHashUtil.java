package com.genshin.lunar.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.lang.reflect.Field;

@Slf4j
public class AutoTranslateHashUtil {
    public static void translateAndCopy(Object source, Object target, TextMapChsUtil textUtil) {
        BeanUtils.copyProperties(source, target);

        for(Field sourceField : source.getClass().getDeclaredFields()){
            String sourceFieldName = sourceField.getName();

            if(sourceFieldName.endsWith("TextMapHash")){
                sourceField.setAccessible(true);
                try{
                    String hashValue = (String) sourceField.get(source);
                    if(hashValue != null && !hashValue.isEmpty() && !"0".equals(hashValue)){
                        String chsText = textUtil.getText(hashValue);

                        String targetFieldName = sourceFieldName.replace("TextMapHash", "");

                        try{
                            Field targetField = target.getClass().getDeclaredField(targetFieldName);
                            targetField.setAccessible(true);

                            targetField.set(target, chsText);
                        } catch(NoSuchFieldException | IllegalAccessException e){}
                    }
                } catch(Exception e){
                    log.error("自动翻译失败, 字段名: {}", sourceFieldName, e);
                }
            }
        }
    }
}
