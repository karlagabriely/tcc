package arquitetura;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Filtro que verifica se um usu�rio est� logado para poder ter acesso a determinadas
 * funcionalidades do sistema.
 * @author Renan
 */
public class LoginFilter implements Filter {
	
	/** 
	 * Se existirem p�ginas espec�ficas cujo acesso deva ser permitido mesmo sem estar logado,
	 * devem ser adicionadas neste array. 
	 * */
	private final String[] urlsPermitidas = {"pag_visitante.xhtml", "camisas.xhtml"};
	
	@Override
	public void destroy() {
		
	}

	/** 
	 * M�todo que ir� filtrar as requisi��es de acesso �s p�ginas do sistema,
	 * e que ir� permitir ou bloquear o acesso, dependendo se o usu�rio
	 * est� logado ou n�o. 
	 * */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		/* Obtendo requisi��o de acesso */
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession();
        String reqUrl = request.getRequestURI(); //Obtendo URL de acesso atual
        
        //Indica se o acesso ser� permitido ou n�o. Por padr�o, n�o � permitido. 
   		boolean permitido = false; 

   		/* Se a URL contiver o nome "p�blico" ou se for a URL inicial do sistema ou 
   		se for o JSF (Faces) que estiver requisitando acesso, ent�o o acesso
   		deve ser permitido */
   		if (reqUrl.contains("/publico/") || reqUrl.equals("/TCC/") 
   				|| reqUrl.contains(".faces."))
   			permitido = true;
   		
   		/* Se at� aqui ainda n�o foi permitido, deve verificar o array de URLs permitidas, para
   		 * decidir se a URL atual � permitida ou n�o */
   		if (!permitido){
	   		for (String url : urlsPermitidas){
	   			//Se encontrou a URL atual no array, ent�o deve permitir
	   			if (reqUrl.contains(url)){
	   				permitido = true;
	   				break;
	   			}
	   		}
   		}

   		/* 
   		 * Se houver usu�rio logado ou ent�o o acesso foi previamente permitido,
   		 * deve-se liberar o acesso
   		 */
        if (session.getAttribute("usuarioLogado") != null || permitido) {
            chain.doFilter(request, response); //Libera o acesso
        } else {
        	//Neste caso, n�o foi permitido, ent�o redireciona para a p�gina de login
        	response.sendRedirect("/TCC/publico/loginmembro.xhtml");
            return;
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	
	
}
