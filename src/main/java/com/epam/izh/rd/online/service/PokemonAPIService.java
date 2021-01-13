package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.factory.PokemonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class PokemonAPIService implements PokemonFetchingService {

    private final String url = "https://pokeapi.co/api/v2/pokemon/";

    @Override
    public Pokemon fetchByName(String name) throws IllegalArgumentException {
        Pokemon pokemon;
        try {
            ObjectMapper objectMapper = new PokemonMapper().getObjectMapper();
            pokemon = objectMapper.readValue(getHttpResponseAsString(name), Pokemon.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Wrong name or Url");
        }
        return pokemon;
    }

    @Override
    public byte[] getPokemonImage(String name) throws IllegalArgumentException {
        byte[] image;
        try {
            String imageUrl = new PokemonMapper().getObjectMapper().readTree(getHttpResponseAsString(name)).get("sprites").get("front_default").toString().replace("\"", "");
            URL urlImage = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "");
            image = new byte[connection.getContentLength()];
            connection.getInputStream().read(image);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error download image");
        }
        return image;
    }

    public String getHttpResponseAsString(String name) throws IllegalArgumentException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(new HttpGet(url + name));
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new IllegalArgumentException("Server answer: " + httpResponse.getStatusLine());
        }
        return new BasicResponseHandler().handleResponse(httpResponse);
    }

}
