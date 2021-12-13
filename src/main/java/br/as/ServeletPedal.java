package br.as;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServeletPedal")
public class ServeletPedal extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private Statement statement;
	ResultSet rs;
     
    
    public ServeletPedal() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		conectar();
		
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>\r\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<link rel=\"stylesheet\" href=\"style.css\">");
		out.println("<body>");
		out.println("<img src=\"imagens/bike.png\">");
		out.println("<h1>Pedaladas cadastradas</h1>");
		out.println("<table id=\"tabela\">\r\n"
				+ "		<thead>\r\n"
				+ "			<tr>\r\n"
				+ "				<th>ID</th>\r\n"
				+ "				<th>DATA</th>\r\n"
				+ "				<th>ROTA</th>\r\n"
				+ "				<th>DISTANCIA</t>\r\n"
				+ "				<th>TEMPO</th>\r\n"	
				+ "\r\n"
				+ "			</tr>\r\n"
				+ "		</thead>\r\n");
				

		//EXIBIR TODAS AS PEDALADAS CADASTRADAS NO BD
		
		try {
			rs = statement.executeQuery("SELECT * FROM Pedal");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
		

		if (rs != null) {
            try {
                while (rs.next()) { 
                	out.println("<tr>");
                	out.println("<td>" + rs.getString("ID") + "</td>");                	
                    out.println("<td>" + rs.getString("data") + "</td>");
                    out.println("<td>" + rs.getString("nomeRota") + "</td>");
                    out.println("<td>" + rs.getString("distanciaPercorrida") + "</td>");
                    out.println("<td>" + rs.getString("tempo") + "</td>" + "<br>");
                    out.println("</tr>");
                                      
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
		
						
		out.println("</table>");
		out.println("<br>"+ "<br>");
		out.println("<a href=\"cadastrar.html\" class=\"botao1\">Nova Pedalada</a>");
		out.println("<a href=\"alterar.html\" class=\"botao1\">Alterar</a>");
		out.println("<a href=\"excluir.html\" class=\"botao1\">Excluir</a>");
		out.println("<a href=\"index.html\" class=\"botao1\">Inicio</a>");
		out.println("</body>\r\n");
		out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("data");
		String nomeRota = request.getParameter("nomeRota");
		String distanciaPercorrida = request.getParameter("distanciaPercorrida");
		String tempo = request.getParameter("tempo");
		
		conectar();
		
		// INSERIR A PEDALADA
		String query = "INSERT INTO pedal (data, nomeRota, distanciaPercorrida, tempo) "
                + "values ('"+data+"', '"+nomeRota+"', '"+distanciaPercorrida+"', '"+tempo+"')";
        int status = executeUpdate(query);
        		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();        
		
		out.println("<html>\r\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<link rel=\"stylesheet\" href=\"style.css\">");
		out.println("<body>");
		out.println("<img src=\"imagens/bike.png\">");
		if (status == 1) {
			out.println("<h3>A pedalada "+ nomeRota + " foi cadastrada com sucesso!</h3>");
		}else {
            out.println("<h3>Erro ao inserir dados!</h3>");
		}
		
		
		
		out.println("<a href=\"ServeletPedal\" class=\"botao2\" >Consultar</a>");
		out.println("</body>\r\n");
		out.println("</html>");
	}
	
	

	private void conectar() {
    	try {
			String address = "localhost";
			String port = "3306";
			String dataBaseName = "projeto_final";
			String user = "root";
			String password = "root";
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+ address + ":" + port +"/"+ dataBaseName + "?user=" + user + "&password=" + password + "&useTimezone=true&serverTimezone=UTC"); 
			statement = connection.createStatement();
			
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    	
    }
	
	public int executeUpdate(String query) {     
        int status = 0;
        try {
        	statement = connection.createStatement();           
            status = statement.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return status;
    }
	
	

}
