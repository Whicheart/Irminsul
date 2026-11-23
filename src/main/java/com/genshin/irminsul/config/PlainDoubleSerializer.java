package com.genshin.irminsul.config;

import java.math.BigDecimal;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/** Double 序列化规则，避免 Jackson 在 10^7 以上切换为科学计数法。 */
public class PlainDoubleSerializer extends StdSerializer<Double> {

    public PlainDoubleSerializer() {
        super(Double.class);
    }

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
