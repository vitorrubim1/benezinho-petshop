package br.com.fiap.petshop.infra.configuration.criptografia;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;

import java.security.Provider;
import java.security.Security;
import java.util.Objects;
import java.util.function.Function;

public class UpdatableBCrypt {
    private static final Logger log = LoggerFactory.getLogger(UpdatableBCrypt.class);

    private final int logRounds;

    private static volatile UpdatableBCrypt instance;

    private UpdatableBCrypt(int logRounds) {
        this.logRounds = logRounds;
    }


    public static UpdatableBCrypt build(int logRounds) {
        UpdatableBCrypt result = instance;
        if (Objects.nonNull(result)) return result;
        synchronized (UpdatableBCrypt.class) {
            if (Objects.isNull(instance)) {
                instance = new UpdatableBCrypt(logRounds);
            }
            return instance;
        }
    }


    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }


}

