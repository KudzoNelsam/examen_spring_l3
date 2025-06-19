package ism.absence.web.utils.mappers;

import ism.absence.data.models.Client;
import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.response.ClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientResponseDTO  toClientResponseDTO(Client client);

    Client toClient(ClientResponseDTO clientResponseDTO);

    Client toClient2(ClientRequest clientRequest);
}