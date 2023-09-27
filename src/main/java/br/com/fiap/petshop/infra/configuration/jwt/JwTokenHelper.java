package br.com.fiap.petshop.infra.configuration.jwt;

import br.com.fiap.petshop.domain.entity.Authority;
import br.com.fiap.petshop.domain.entity.Pessoa;
import br.com.fiap.petshop.domain.entity.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;

//https://github.com/jwtk/jjwt#understanding-jjwt-dependencies
public class JwTokenHelper {


    private static JwTokenHelper jwTokenHelper = null;
    private static final long EXPIRATION_LIMIT = 30;
    private final SecretKey key = Keys.secretKeyFor( SignatureAlgorithm.HS256 );


    private JwTokenHelper() {
    }

    public static JwTokenHelper getInstance() {
        if (jwTokenHelper == null)
            jwTokenHelper = new JwTokenHelper();
        return jwTokenHelper;
    }

    //  1
    public String generatePrivateKey(Usuario usuario) {

        Map<String, Object> claim = new LinkedHashMap<>();
        claim.put("authorities", usuario.getAuthorities());
        claim.put("pessoa", usuario.getPessoa().getNome());


        return Jwts.builder()
                .setSubject(usuario.getUsername() )
                .setSubject(usuario.getPassword() )
                .setClaims(claim)
                .setExpiration( getExpirationDate() )
                .signWith( key )
                .compact();
    }

    // 2
    public void claimKey(String privateKey) throws ExpiredJwtException, MalformedJwtException, SignatureException {

        Jwts.parser()
                .setSigningKey( key )
                .parseClaimsJws( privateKey );
    }

    // 3
    private Date getExpirationDate() {
        long currentTimeInMillis = System.currentTimeMillis();
        long expMilliSeconds = TimeUnit.MINUTES.toMillis( EXPIRATION_LIMIT );
        return new Date( currentTimeInMillis + expMilliSeconds );
    }

}