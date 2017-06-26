package webNovel;

import org.junit.Assert;
import org.junit.Test;

public class RegexTest {

    @Test public void regex(){
        String originalExtension = "";
        String regEx = "(jpg|jpeg|png|gif|bmp)";
        Assert.assertTrue("someLibraryMethod should return 'true'", originalExtension.matches(regEx));
    }
}
