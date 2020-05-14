package edu.miu.cs544.medappointment.utils;

import edu.miu.cs544.medappointment.entity.Status;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Status> {
    @Override
    public Status convert(String source) {
        return Status.valueOf(source.toUpperCase());
    }
}
