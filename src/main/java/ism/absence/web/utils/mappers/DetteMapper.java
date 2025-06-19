package ism.absence.web.utils.mappers;

import ism.absence.data.models.Client;
import ism.absence.data.models.Dette;
import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.request.DetteRequest;
import ism.absence.web.dto.response.ClientResponseDTO;
import ism.absence.web.dto.response.DetteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DetteMapper {
    DetteMapper INSTANCE = Mappers.getMapper(DetteMapper.class);

    DetteResponseDTO  toDetteResponseDTO(Dette dette);

    Dette toDette(DetteRequest detteRequest);
}