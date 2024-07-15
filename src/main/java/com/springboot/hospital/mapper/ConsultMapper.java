package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.ConsultDTO;
import com.springboot.hospital.model.Consult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ConsultMapper {


    @Mapping(source = "dateConsult", target = "dateConsult", qualifiedByName = "dateToString")
    ConsultDTO toDTO(Consult consult);

    @Mapping(source = "dateConsult", target = "dateConsult", qualifiedByName = "stringToDate")
    Consult toEntity(ConsultDTO consultDTO);

    @Named("dateToString")
    static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date != null ? dateFormat.format(date) : null;
    }

    @Named("stringToDate")
    static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date != null ? dateFormat.parse(date) : null;
    }
}
