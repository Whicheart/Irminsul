package com.genshin.irminsul.config;

import java.math.BigDecimal;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/** Double 序列化规则，避免 Jackson 在 10^7 以上切换为科学计数法 */
public class PlainDoubleSerializer extends StdSerializer<Double> {

    // 声明被序列化的类型为 Double
    public PlainDoubleSerializer() {
        super(Double.class);
    }

    /**
     * 将 Double 输出为普通十进制形式
     *
     * <p>null 输出 JSON null；NaN/Infinity 输出字符串；其余经
     * {@link BigDecimal#toPlainString()} 输出，保证大数不出现科学计数法
     *
     * @param value 待序列化的值，允许为 null
     * @param gen   JSON 输出器
     * @param ctxt  序列化上下文
     * @throws JacksonException 写入失败时抛出
     */
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        if (!Double.isFinite(value)) {
            gen.writeString(value.toString());
            return;
        }
        gen.writeNumber(BigDecimal.valueOf(value).toPlainString());
    }
}
