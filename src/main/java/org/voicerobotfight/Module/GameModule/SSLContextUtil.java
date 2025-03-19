package org.voicerobotfight.Module.GameModule;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

public class SSLContextUtil {
    public static X509Certificate loadCertificate(String path) throws IOException, CertificateException {
        try (FileInputStream fis = new FileInputStream(path)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(fis);
        }
    }

    public static PrivateKey loadPrivateKey(String path) throws IOException, GeneralSecurityException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(path));
//        keyBytes = removeBeginEnd(keyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
    private static byte[] removeBeginEnd(byte[] keyBytes) {
        String s = new String(keyBytes);
        s = s.replace("-----BEGIN RSA PRIVATE KEY-----", "");
        s = s.replace("-----END RSA PRIVATE KEY-----", "");
        return s.getBytes();
    }

    public static KeyManager[] createKeyManagers(X509Certificate cert, PrivateKey privateKey) {
        return new KeyManager[] {
                new X509KeyManager() {
                    public String[] getClientAliases(String keyType, Principal[] issuers) {
                        return null;
                    }

                    @Override
                    public String chooseClientAlias(String[] strings, Principal[] principals, Socket socket) {
                        return "";
                    }

                    public String[] getServerAliases(String keyType, Principal[] issuers) {
                        return null;
                    }

                    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
                        return "server";
                    }

                    public X509Certificate[] getCertificateChain(String alias) {
                        return new X509Certificate[] { cert };
                    }

                    public PrivateKey getPrivateKey(String alias) {
                        return privateKey;
                    }
                }
        };
    }
}
