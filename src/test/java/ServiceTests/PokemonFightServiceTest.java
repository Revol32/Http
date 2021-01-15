package ServiceTests;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.service.PokemonAPIService;
import com.epam.izh.rd.online.service.PokemonFightService;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PokemonFightServiceTest {

    private static WireMockServer wireMockServer = new WireMockServer();
    private static PokemonFightService pokemonFight = new PokemonFightService();
    private static PokemonAPIService pokemonAPI = new PokemonAPIService();
    private Pokemon slowpoke;
    private Pokemon pikachu;

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
    public void doDamageTest () {
        slowpoke = pokemonAPI.fetchByName("slowpoke.json");
        pikachu = pokemonAPI.fetchByName("pikachu.json");
        pokemonFight.doDamage(slowpoke,pikachu);
        assertEquals (-4,pikachu.getHp());
    }

    @Test
    public void doBattleTest () {
        slowpoke = pokemonAPI.fetchByName("slowpoke.json");
        pikachu = pokemonAPI.fetchByName("pikachu.json");
        assertEquals (slowpoke.getPokemonName(),pokemonFight.doBattle(slowpoke,pikachu).getPokemonName());
    }

    @Test
    public void showWinnerTest () {
        File slowpokeImg = new File("src/slowpoke.png");
        if (slowpokeImg.exists()) {
            slowpokeImg.delete();
        }
        slowpoke = pokemonAPI.fetchByName("slowpoke.json");
        pokemonFight.showWinner(slowpoke);
        assertTrue(slowpokeImg.exists());
    }
}
