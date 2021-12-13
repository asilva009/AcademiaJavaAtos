package br.as;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServeletPedalAlterar
 */
@WebServlet("/ServeletPedalAlterar")
public class ServeletPedalAlterar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection connection;
	private Statement statement;
	ResultSet rs;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServeletPedalAlterar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String data = request.getParameter("data");
		String nomeRota = request.getParameter("nomeRota");
		String distanciaPercorrida = request.getParameter("distanciaPercorrida");
		String tempo = request.getParameter("tempo");

		conectar();

		String query = "UPDATE pedal SET data='" + data + "', " + "nomeRota='" + nomeRota + "', distanciaPercorrida='"
				+ distanciaPercorrida + "', tempo='" + tempo + "'" + "WHERE id=" + id;
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
			out.println("<h3>A pedalada " + id + " foi alterada com sucesso!</h3>");
		}else {
            out.println("<h3>Erro ao excluir dados!</h3>");
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
			connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + dataBaseName
					+ "?user=" + user + "&password=" + password + "&useTimezone=true&serverTimezone=UTC");
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
