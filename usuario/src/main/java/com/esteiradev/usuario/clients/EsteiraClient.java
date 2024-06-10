package com.esteiradev.usuario.clients;

import com.esteiradev.usuario.dto.EsteiraDto;
import com.esteiradev.usuario.dto.ResponsePageDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class EsteiraClient {

    @Autowired
    RestTemplate restTemplate;

    String REQUEST_URI =  "http://localhost:8082/esteiradev";

    public Page<EsteiraDto> getAllEsteirasByUser(UUID userId, Pageable pageable){
        List<EsteiraDto> searchResult= null;
        ResponseEntity<ResponsePageDto<EsteiraDto>> result = null;
        String url = REQUEST_URI + "/esteira?userId=" + userId + "&page=" + pageable.getPageNumber()+ "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try{
            ParameterizedTypeReference<ResponsePageDto<EsteiraDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<EsteiraDto>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null,responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response number of Elements {}", searchResult.size());
        } catch(HttpStatusCodeException e){
            log.error("Error request /esteira {} ", e);
        }

        log.info("Ending request /esteira userId {}", userId);
        return result.getBody();
    }
}
