package ServiceTests;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.service.PokemonAPIService;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class PokemonAPIServiceTest {

    private static PokemonAPIService pokemonAPI = new PokemonAPIService();
    private static WireMockServer wireMockServer = new WireMockServer();

    @BeforeAll
    public static void initialize (){
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo("/raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/79.png")).
                willReturn(aResponse().withStatus(200)
                .withBodyFile("79.png")));
        pokemonAPI.setUrl("http://localhost:8080/");
    }

    @AfterAll
    public static void drop () {
        wireMockServer.stop();
    }

    @Test
    public void fetchByNameTest () {
        Pokemon pokemonTester = new Pokemon(79L,"slowpoke",(short) 90,(short) 65,(short) 65);
        Pokemon pokemonFetch = pokemonAPI.fetchByName("slowpoke.json");
        assertEquals (pokemonTester,pokemonFetch);
    }

    @Test
    public void getPokemonImageTest () {
        byte [] image = pokemonAPI.getPokemonImage("slowpoke.json");
        assertTrue (image.length > 0);
    }
}
