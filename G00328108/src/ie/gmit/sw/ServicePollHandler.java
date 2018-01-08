package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Handles Pooling and displaying Results", urlPatterns = { "/poll" })
public class ServicePollHandler extends HttpServlet {
	private static final long serialVersionUID = 42423443566L;
	private static ConcurrentHashMap<Integer, StoreResults> outQueue;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicePollHandler() {
		super();
	}

	// Gets ConcurrentHashMap instance from Utility.class.
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (Util.getInQueue() != null)
			outQueue = Util.getOutQueue();
	}

	// Handles results requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = new String();
		int jobNumber = 0;
		PrintWriter out = response.getWriter();
		try {
			title = request.getParameter("title");
			jobNumber = Integer.parseInt(request.getParameter("jobNumber"));
			if (outQueue.size() > 0) {
				if (outQueue.containsKey(jobNumber)) {
					// Getting comparison results
					StoreResults results = outQueue.remove(jobNumber);
					// Display results
					String cssLocation = request.getContextPath() + "/css/results.css";
					String cssTag = "<link rel='stylesheet' type='text/css' href='" + cssLocation + "'>";
					out.printf("<html><head>%s</head><body>", cssTag);
					out.print("<div class='centered'><table>");
					out.printf("<h1 align=\"center\"><b>%s</b></h1>", title);
					out.print("<tr><th>Document title</th><th>Similarity</th></tr>");
					for (String docTitle : results.getDocuments()) {
						out.print("<tr><td>");
						out.print(docTitle);
						out.print("</td><td>");
						out.printf("%.0f %%", Double.valueOf(results.getResult(docTitle)));
						out.print("</td></tr>");
					}
					out.println();
					out.print("</table></div>");
					// Home button
					out.printf("<p align=\"center\">"
							+ "<button onclick=\"window.location.href=' /AdvanceOOProject/'\">Home</button>" + "</p>");
					out.print("</body></html>");
				}

			} else {
				response.setIntHeader("Refresh", Util.getRefreshRate());
				out.printf("<p align=\"center\">Processing document: <b>%s</b>, please wait...</p>", title);
				out.println();
				out.printf("<p align=\"center\">Job Number: <b>%d</b></p>", jobNumber);
				// Home button
				out.printf("<p align=\"center\">"
						+ "<button onclick=\"window.location.href=' /AdvanceOOProject/'\">Home</button>" + "</p>");
				out.print("</body></html>");
			}
		} catch (Exception exp) {
			Util.logMessage("[ERROR] UploadHandler: " + exp.getMessage());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
