
package br.edu.ifrs.restinga.ads.projetce.aut;

import static br.edu.ifrs.restinga.ads.projetce.controller.Pessoas.SEGREDO;
import br.edu.ifrs.restinga.ads.projetce.dao.PessoaDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Pessoa;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 *
 * @author jezer
 */
public class TokenBasedAuthorizationFilter 
        extends BasicAuthenticationFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        } else {
            token = request.getParameter("token");
        }
        if (token != null && !token.isEmpty()) {
            Algorithm algorithm = Algorithm.HMAC256(SEGREDO);
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            Integer id = decode.getClaim("id").asInt();
            Pessoa pessoa = pessoaDAO.findOne(id);
            PessoaAut pessoaAut = new PessoaAut(pessoa);
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(pessoaAut
                            , null, pessoaAut.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        chain.doFilter(request, response);
    }
    PessoaDAO pessoaDAO;

    public TokenBasedAuthorizationFilter(AuthenticationManager authenticationManager, PessoaDAO pessoaDAO) {
        super(authenticationManager);
        this.pessoaDAO = pessoaDAO;

    }

}
