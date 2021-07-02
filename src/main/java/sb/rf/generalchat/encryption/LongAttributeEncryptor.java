package sb.rf.generalchat.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

@Slf4j
@Converter
public class LongAttributeEncryptor  implements AttributeConverter<Long, String> {

    private static final String AES = "AES";
  private static final String SECRET =
      "QMZALGMvBSDFGHJK";

    private final Key key;
    private final Cipher cipher;

    public LongAttributeEncryptor() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(Long attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);

            String val=Base64.getEncoder().encodeToString(cipher.doFinal(attribute.toString().getBytes()));
            log.info("started to convertToDatabaseColumn string val is {}",val);
            return val;
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException( e);
        }
    }

    @Override
    public Long convertToEntityAttribute(String dbData) {
        try {
            log.info("covert to entity was invoked");
            cipher.init(Cipher.DECRYPT_MODE, key);
            Long val =Long.parseLong(new String(cipher.doFinal(Base64.getDecoder().decode(dbData))));
            log.info("started to convert in entity attr, returning value is {}",val);
            return val;
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
    }
}
