import Pacman.*;
import org.junit.*;
import java.awt.*;

public class Test {
    GameWindow gw;
    Pacman pacman;
    Ghost ghost;
    @Before
    public void setUp() {
        gw = new GameWindow("nemletezofajl.txt");
        pacman = new Pacman(gw);
        ghost = new Ghost(10, pacman, gw);
    }
    @Test
    public void testPacman() {
        pacman.setStartPos(40, 60);
        pacman.setLives(10);
        pacman.setPowerUp();
        assertEquals("Pacman position x", 40, pacman.getX());
        assertEquals("Pacman position y", 60, pacman.getY());
        assertNotEquals("Pacman start position x", 50, pacman.getStartX());
        assertEquals("Pacman number of lives", 10, pacman.getLives());
        assertTrue("Pacman", pacman.isPoweredUp());
    }
    @Test
    public void testGhost() {
        ghost.setStartPos(120, 200);
        assertEquals("Ghost position x", 120, ghost.getX());
        assertEquals("Ghost position y", 200, ghost.getY());
        assertNotEquals("Ghost start position y", 310, ghost.getStartY());
        assertEquals("Ghost start position x", 120, ghost.getStartX());
    }
    @Test
    public void testGameWindow() {
        assertEquals("Game Window starting color", Color.BLUE, GameWindow.color);
        assertNotEquals("Game Window starting difficulty"), Difficulty.HARD, GameWindow.difficulty);
    }
}
