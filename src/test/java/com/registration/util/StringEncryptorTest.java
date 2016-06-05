package com.registration.util;

import configuration.SpringTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class StringEncryptorTest {
    private final String TEST = "test message";

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void testEncrypt() throws Exception {
        assertNotNull("Encrypted text must not be null", stringEncryptor.encrypt(TEST));
    }

    @Test
    public void testDecrypt() throws Exception {
        assertEquals("Decrypted text should be equal original text", TEST, stringEncryptor.decrypt(stringEncryptor.encrypt(TEST)));
    }
}