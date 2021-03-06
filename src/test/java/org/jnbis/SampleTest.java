package org.jnbis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * @author <a href="mailto:m.h.shams@gmail.com">M. H. Shamsi</a>
 * @version 1.0.0
 * @since Oct 31, 2007
 */
public class SampleTest {
    private NistDecoder nistDecoder;
    private WsqDecoder wsqDecoder;
    private ImageUtils imageUtils;

    @Before
    public void setUp() throws Exception {
        nistDecoder = new NistDecoder();
        wsqDecoder = new WsqDecoder();
        imageUtils = new ImageUtils();
    }

    @Test
    public void wsq2jpeg() throws Exception {
        Bitmap decoded = wsqDecoder.decode(FileUtils.read("samples/sample.wsq"));

        Assert.assertNotNull(decoded);

        Assert.assertEquals(8, decoded.getDepth());
        Assert.assertEquals(622, decoded.getHeight());
        Assert.assertEquals(545, decoded.getWidth());
        Assert.assertEquals(622 * 545, decoded.getLength());
        Assert.assertEquals(1, decoded.getLossyFlag());
        Assert.assertEquals(-1, decoded.getPpi());
        Assert.assertEquals(622 * 545, decoded.getPixels().length);

        // For local check.
        //FileUtils.save(imageUtils.bitmap2jpeg(decoded), "/path/to/file.jpeg");
    }

    @Test
    public void nist2jpeg() throws Exception {
        DecodedData decoded = nistDecoder.decode(FileUtils.read("samples/sample.an2"), DecodedData.Format.JPEG);

        Set<Integer> keys = decoded.getBinaryKeys();

        Assert.assertEquals(14, keys.size());

        for (Integer key : keys) {
            DecodedData.BinaryData image = decoded.getBinary(key);

            Assert.assertNotNull(image);
            Assert.assertEquals(DecodedData.Format.JPEG.code(), image.getType());

            // For local check
            //FileUtils.save(image.getData(), "/path/to/file-" + key + ".jpeg");
        }
    }
 }