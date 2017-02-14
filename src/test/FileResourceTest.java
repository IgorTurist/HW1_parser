package test;

import org.junit.jupiter.api.Test;
import resources.TextResource;
import resources.FileResource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by igor on 13.02.2017.
 */
class FileResourceTest {
    @Test
    void isValid_null_path() {
        FileResource res = new FileResource(null);
        assertThrows(NullPointerException.class,() -> res.isValid());
    }

    @Test
    void isValid_incorrect_path() {
        FileResource res = new FileResource("$$$incorrect_path???");
        assertEquals(false, res.isValid());
    }

    @Test
    void isValid_empty_path() {
        FileResource res = new FileResource("");
        assertEquals(false, res.isValid());
    }

    @Test
    void isValid_unexisting_path() {
        FileResource res = new FileResource("./unexisting_file.txt");
        assertEquals(false, res.isValid());
    }

    @Test
    void isValid_normal_path() {
        FileResource res = new FileResource("./test_data/normal_file.txt");
        assertEquals(true, res.isValid());
    }

    @Test
    void runResourceParsing_normal_file() {
        FileResource res = new FileResource("./test_data/short_normal_file.txt");
        try {
            res.runResourceParsing();
        } catch (Exception e) {
            fail("Unexpected exception occurred:" + e.getMessage());
        }
        HashMap<String,Long> dict = TextResource.getDict();
        assertEquals(5,dict.size());
        assertEquals(true, dict.containsKey("слово"));
        assertEquals(new Long(2),dict.get("слово"));
        dict.clear();
    }

    @Test
    void runResourceParsing_foreign_symbol_file() {
        FileResource res = new FileResource("./test_data/foreign_symbol_file.txt");
        assertThrows(Exception.class,() -> res.runResourceParsing());
    }

    @Test
    void runResourceParsing_illegal_symbol_file() {
        FileResource res = new FileResource("./test_data/illegal_symbol_file.txt");
        assertThrows(Exception.class,() -> res.runResourceParsing());
    }
}